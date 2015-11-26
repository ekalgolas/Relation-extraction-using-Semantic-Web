package edu.semweb.main;

import java.io.IOException;
import java.util.List;

import ontology.CustomOntologyOld;
import edu.semweb.nlp.EntityRelationExtractor;
import edu.semweb.nlp.RelationsInDocument;
import edu.semweb.nlp.Triple;
import edu.semweb.rdf.CustomOntology;

public class MainClass {
	public static void main(final String[] args)
			throws IOException {
		final CustomOntology customOntology = new CustomOntology();

		final EntityRelationExtractor ere = new EntityRelationExtractor();
		final RelationsInDocument rd = ere.getRelationships("http://www.exampledoc.com",
				"Satya Nadella is the CEO of Microsoft. It's headquarters is in Redmond, Washington. He was born in India.");
		System.out.println(rd);
		List<Triple> triples = rd.getAllTriples();
		for(Triple t : triples) {
			System.out.println(t);
		}
		customOntology.getXMLFromOntology(rd);
	}
}