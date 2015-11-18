package edu.semweb.main;

import java.io.IOException;

import ontology.CustomOntology;
import edu.semweb.nlp.EntityRelationExtractor;
import edu.semweb.nlp.RelationsInDocument;

public class MainClass {
	public static void main(final String[] args)
			throws IOException {
		final CustomOntology customOntology = new CustomOntology();

		final EntityRelationExtractor ere = new EntityRelationExtractor();
		final RelationsInDocument rd = ere.getRelationships("http://www.doc.url",
				"Satya Nadella is the CEO of Microsoft. It's headquarters is in Redmond, Washington. He was born in India. She lives in Dallas, Texas. She sold the company for $1000.");

		customOntology.getXMLFromOntology(rd);
	}
}