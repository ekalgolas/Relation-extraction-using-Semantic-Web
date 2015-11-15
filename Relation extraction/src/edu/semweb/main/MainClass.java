package edu.semweb.main;

import edu.semweb.nlp.EntityRelationExtractor;
import edu.semweb.nlp.RelationsInDocument;

public class MainClass {
	public static void main(String[] args) {
		EntityRelationExtractor ere = new EntityRelationExtractor();
		RelationsInDocument rd = ere.getRelationships("http://www.doc.url","Satya Nadella is the CEO of Microsoft. It's headquarters is in Redmond, Washington. He was born in India. She lives in Dallas, Texas.");
		System.out.println(rd);
	}
}