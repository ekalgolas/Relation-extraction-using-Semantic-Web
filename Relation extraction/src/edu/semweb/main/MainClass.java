package edu.semweb.main;

import java.io.IOException;
import java.util.ArrayList;
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
		final RelationsInDocument rd = ere.getRelationships("https://en.wikipedia.org/wiki/Donald_Trump",
				"Donald John Trump (born June 14, 1946) is an American business magnate, investor, author, television personality and candidate for President of the United States in the 2016 presidential election. He is the chairman and president of The Trump Organization, and the founder of Trump Entertainment Resorts. Trump's career, branding efforts, lifestyle and outspoken manner helped make him a celebrity, a status amplified by the success of his NBC reality show, The Apprentice. Trump is a son of Fred Trump, a New York City real estate developer. He worked for his father's firm, Elizabeth Trump & Son, while attending the Wharton School of the University of Pennsylvania, and officially joined the company in 1968. In 1971, he was given control of the company, renaming it The Trump Organization. Trump remains a major figure in American real estate. On June 16, 2015, Trump formally announced his candidacy for president in the 2016 election, seeking the nomination of the Republican Party. Trump's early campaigning drew intense media coverage and saw him rise to high levels of popular support. Since early July 2015, he has consistently been the front-runner in public opinion polls for the Republican Party nomination.");
		System.out.println(rd);
		List<Triple> triples = rd.getAllTriples();
		for(Triple t : triples) {
			System.out.println(t);
		}
		List<RelationsInDocument> rds = new ArrayList<>();
		rds.add(rd);
		customOntology.getXMLFromOntology(rds);
	}
}