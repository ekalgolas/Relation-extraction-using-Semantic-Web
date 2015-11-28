package edu.semweb.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import edu.semweb.nlp.EntityRelationExtractor;
import edu.semweb.nlp.RelationsInDocument;
import edu.semweb.rdf.CustomOntology;

public class MainClass {
	public static void main(final String[] args)
			throws IOException {
		final CustomOntology customOntology = new CustomOntology();
		final SolrClient solrClient = new HttpSolrClient("http://52.32.188.33:8983/solr/Semantic_Web/");
		final SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setParam("wt", "json");

		QueryResponse response;
		SolrDocumentList list = null;

		try {
			response = solrClient.query(query);
			list = response.getResults();
		} catch (final SolrServerException e) {
			e.printStackTrace();
		}

		final EntityRelationExtractor ere = new EntityRelationExtractor();
		final List<RelationsInDocument> rds = new ArrayList<>();
		for (final SolrDocument solrDocument : list) {
			final RelationsInDocument rd = ere.getRelationships(solrDocument.get("id").toString(), solrDocument.get("text").toString());

			rds.add(rd);
		}

		customOntology.getXMLFromOntology(rds);
	}
}