package edu.semweb.nlp;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean class for representing all the co-occurrence relationships recorded in from all sentences in a document
 * Wraps a List of RelationsInSentence objects
 * @author Prashanth Govindaraj
 *
 */
public class RelationsInDocument {
	
	/**
	 * relations	- a List of RelationsInSentence objects, representing all co-occurrences of entities in the document
	 * documentUrl	- the URL of the source document
	 */
	private List<RelationsInSentence> relations;
	private String documentUrl;
	
	public RelationsInDocument(String documentUrl) {
		this.relations = new ArrayList<RelationsInSentence>();
		this.documentUrl = documentUrl;
	}
	
	/**
	 * Adds a RelationsInSentence object corresponding to a sentence in this document
	 * @param rs
	 */
	public void addRelationsInSentence(RelationsInSentence rs) {
		this.relations.add(rs);
	}
	
	/**
	 * Returns the List of RelationsInSentence object corresponding to this document
	 * @return	List of RelationsInSentence object corresponding to this document
	 */
	public List<RelationsInSentence> getRelationsInSentences() {
		return relations;
	}
	
	/**
	 * Returns the document URL
	 * @return document URL
	 */
	public String getDocumentUrl() {
		return documentUrl;
	}
	
	/**
	 * Checks if this document contains at least one co-occurrence relationship between any entities 
	 * @return true if no co-occurrence relationship exists between any entity encountered in this document
	 */
	public boolean isEmpty() {
		return this.relations.size() == 0;
	}
	
	public List<Triple> getAllTriples() {
		List<Triple> triples = new ArrayList<>();
		for(RelationsInSentence rs : this.relations) {
			triples.addAll(rs.getAllTriple(documentUrl));
		}
		return triples;
	}
	
	/**
	 * Returns a pretty string representation of this RelationsInDocument object
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Relations in: "+documentUrl);
		int i = 0;
		for(RelationsInSentence rs : relations) {
			sb.append("\n"+ (++i) + ". " +rs);
		}
		return sb.toString();
	}
	
}
