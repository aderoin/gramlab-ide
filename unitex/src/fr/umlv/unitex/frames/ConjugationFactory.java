package fr.umlv.unitex.frames;

import leximir.Shell;

public class ConjugationFactory {
	private ConjugationFrame dialog;
	public ConjugationFrame newConjugaisonDialog() {
		dialog = new ConjugationFrame();
		return dialog;
	}

}
