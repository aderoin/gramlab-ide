/*
 * Unitex
 *
 * Copyright (C) 2001-2018 Université Paris-Est Marne-la-Vallée <unitex@univ-mlv.fr>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 *
 */
package fr.umlv.unitex.leximir.util;

import fr.umlv.unitex.leximir.model.DictionaryPath;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Anas Ait cheikh
 */
public class cRuleMatcher {

    private final List<String> anyPOS = Arrays.asList(/*deprecated*/"MOT", "WORD");
    private final List<String> StartsWithUpperCase = Arrays.asList(/*deprecated*/"$PRE", "$FIRST");
    private final String notInDic = "!SDIC";

    private final List<String> GramCats = new ArrayList<>(); // all gramcats possible for the current language
    private Map<String, String[]> CurrentLangGramCats; //possible values for each gramcat
    private final NodeList ndWord; // the NodeListe that contains the rules
    private final List<Map<String, String>> GramCatswords; //contain(Case,Num,Gen,Anim,Det)from the Delas of each word 
    private final JTable jTableRule; //jTable of Delas rules of each word
    private final JTable jTableDlf; //jTable of Delas rules of each word
    private final List<String> wordofLemma; // contain words of this lemma
    private final char separator;

    public cRuleMatcher(NodeList ndWord, List<String> wordofLemma, char separator, JTable jTableRule, JTable jTableDlf) throws IOException {
        this.ndWord = ndWord;
        this.wordofLemma = wordofLemma;
        this.jTableRule = jTableRule;
        this.jTableDlf = jTableDlf;
        this.separator = separator;
        GramCatswords = new ArrayList<>();
        FillMorphologyGramCats();
        this.getGramCatWord();
    }

    private void FillMorphologyGramCats() throws FileNotFoundException, IOException {
        CurrentLangGramCats = new HashMap<>();
        File morphology = new File(DictionaryPath.inflectionPath + "/Morphology.txt");
        BufferedReader br = new BufferedReader(new FileReader(morphology));
        String line = "";
        int nbLine = 0;
        while ((line = br.readLine()) != null) {
            nbLine++;
            line = line.replaceAll("[^a-zA-z1-9:,<>]", "");
            if (line.equals("<CLASSES>")) {
                break;
            }
            if (nbLine > 2 && line.contains(":")) {
                String gc = line.substring(0, line.indexOf(":"));
                GramCats.add(gc);
                String tmp = line.substring(line.indexOf(":") + 1);
                CurrentLangGramCats.put(gc, tmp.split(","));

            }
        }
        br.close();

//        for (int s = 0; s < ndCondItems.getLength(); s++) {
//            final Element CondItems_elem = (Element) ndCondItems.item(s); //<CondItems>
//            final NodeList CondItem = CondItems_elem.getElementsByTagName("ndCondItem"); // <CondItem>
//            for (int w = 0; w < CondItem.getLength(); w++) {
//                final Element CondItem_elem = (Element) ndCondItems.item(w);
//                String tmp = CondItem_elem.getAttribute("Condition");
//                CurrentLangGramCats.put(CondItem_elem.getAttribute("Code"), tmp.contains(",") ? tmp.split(",") : new String[]{tmp});
//            }
//        }
    }

    private void getGramCatfromDelas(String Feature, String Pos) {
        Map<String, String> tmp = new HashMap<>();
        for (Map.Entry<String, String[]> s : CurrentLangGramCats.entrySet()) {
            for (String gcat : s.getValue()) {
                if (Feature.contains(gcat)) {
                    tmp.put(s.getKey(), gcat);
                }
            }
        }
        if (!Pos.equals("")) {
            tmp.put("POS", Pos);
        }
        GramCatswords.add(tmp);
    }

    public void getGramCatWord() {
        boolean found;
        for (int k = 0; k < wordofLemma.size(); k++) {
            found = false;
            for (int i = 0; i < jTableRule.getRowCount(); i++) {
                if (jTableRule.getValueAt(i, 0) != null) {
                    String compare = jTableRule.getValueAt(i, 0).toString();
                    if (compare.equals(wordofLemma.get(k))) {
                        getGramCatfromDelas(jTableRule.getValueAt(i, 3).toString(), jTableRule.getValueAt(i, 2).toString());
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                for (int i = 0; i < jTableRule.getRowCount(); i++) {
                    if (jTableRule.getValueAt(i, 4) != null) {
                        String compare = jTableRule.getValueAt(i, 4).toString();
                        if (compare.equals(wordofLemma.get(k))) {
                            getGramCatfromDelas(jTableRule.getValueAt(i, 3).toString(), jTableRule.getValueAt(i, 2).toString());
                            break;
                        }
                    }
                }
            }
        }

    }

    public boolean match() {
        if (GramCatswords.size() < ndWord.getLength()) {
            return false;
        }
        // iterating on word tags
        for (int k = 0; k < ndWord.getLength(); k++) {
            Node nNodeWord = ndWord.item(k);
            if (nNodeWord.getNodeType() == Node.ELEMENT_NODE) {
                Element eElementWord = (Element) nNodeWord;

                // for POS
                if (eElementWord.hasAttribute("POS")) {
                    String gCat = eElementWord.getAttribute("POS");
                    if (gCat.equals(notInDic)) {
                        for (int i = 0; i < jTableDlf.getRowCount(); i++) {
                            if (jTableDlf.getValueAt(i, 1) != null) {
                                String compare = jTableDlf.getValueAt(i, 1).toString();
                                if (compare.equals(wordofLemma.get(k))) {
                                    return false;
                                }
                            }
                        }
                    } else if (gCat.contains(",")) {
                        String[] Tab = gCat.split(",");
                        boolean flag = false;
                        for (String e : Tab) {
                            if (e.equals(GramCatswords.get(k).get("POS"))) {
                                flag = true;
                            }
                        }
                        if (!flag) {
                            return false;
                        }
                    } else if (gCat.contains("!")) {
                        if (gCat.replace("!", "").equals(GramCatswords.get(k).get("POS"))) {
                            return false;
                        }
                    } else if (!gCat.equals(GramCatswords.get(k).get("POS")) && !anyPOS.contains(gCat)) {
                        return false;
                    }
                }

                // for Nb,Det,Case,Gen,Anim
                for (String g : GramCats) {
                    if (eElementWord.hasAttribute(g)) {
                        String gCat = eElementWord.getAttribute(g);
                        if (gCat.contains(",")) {
                            String[] Tab = gCat.split(",");
                            boolean flag = false;
                            for (String e : Tab) {
                                if (e.equals(GramCatswords.get(k).get(g))) {
                                    flag = true;
                                }
                            }
                            if (!flag) {
                                return false;
                            }
                        } else if (gCat.startsWith(":$")) {
                                // don't do any thing
                        } else if (gCat.startsWith("$")) {
                            if (!GramCatswords.get(k).get(g).equals(getVariableValue(g))) {
                                return false;
                            }
                        } else if (gCat.startsWith("!$")) {
                            if (GramCatswords.get(k).get(g).equals(getVariableValue(g))) {
                                return false;
                            }
                        } else if (gCat.startsWith("!")) {
                            if (gCat.replace("!", "").equals(GramCatswords.get(k).get(g))) {
                                return false;
                            }
                        } else if (!gCat.equals(GramCatswords.get(k).get(g))) {
                            return false;
                        }

                    }
                }

                //for Sep
                if (eElementWord.hasAttribute("Sep")) {
                    String s = eElementWord.getAttribute("Sep");
                    if (s.contains("!")) {
                        if (s.replace("!", "").equals(separator)) {
                            return false;
                        }
                    } else if (!s.equals(separator)) {
                        return false;
                    }
                }

                // for Cond
                if (eElementWord.hasAttribute("Cond")) {
                    String s = eElementWord.getAttribute("Cond");
                    if (StartsWithUpperCase.contains(s)) {
                        if (!Character.isUpperCase(this.wordofLemma.get(k).charAt(0))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private String getVariableValue(String g) {
        for (int k = 0; k < ndWord.getLength(); k++) {
            Node nNodeWord = ndWord.item(k);
            if (nNodeWord.getNodeType() == Node.ELEMENT_NODE) {
                Element eElementWord = (Element) nNodeWord;
                if (eElementWord.getAttribute(g).startsWith(":$")) {
                    return GramCatswords.get(k).get(g);
                }
            }
        }
        return null;
    }

}
