package edu.semweb.nlp;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * Contains the methods required for gathering all non-trivial co-occurrence relationships between entity names in a document 
 * @author Prashanth Govindaraj
 *
 */
public class EntityRelationExtractor {
	
	private StanfordCoreNLP pipeline;
	
	public EntityRelationExtractor() {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		pipeline = new StanfordCoreNLP(props);
	}
	
	/**
	 * Returns all non-trivial co-occurrence relationships between entity names in a document
	 * @param documentUrl	URL of the document
	 * @param content		Complete content of the document that has to be processed
	 * @return				all non-trivial co-occurrence relationships between entity names in a document wrapped in a RelationsInDocument object
	 */
	public RelationsInDocument getRelationships(String documentUrl, String content) {
		RelationsInDocument relationsInDocument = new RelationsInDocument(documentUrl);
		Annotation document = new Annotation(content);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		Map<Integer, CorefChain> corefchain = document.get(CorefChainAnnotation.class);
		for(CoreMap sentence : sentences) {
			RelationsInSentence relationsInSentence = new RelationsInSentence(sentence.get(TextAnnotation.class));
			List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
			//Loop through all tokens in the sentence
			for(int i = 0; i < tokens.size(); i++) {
				String actualText = tokens.get(i).get(TextAnnotation.class);
				String posTag = tokens.get(i).get(PartOfSpeechAnnotation.class);
				String nerTag = tokens.get(i).get(NamedEntityTagAnnotation.class);
				//if a token is a pronoun, check if the end of its coreference chain (the representative CorefMention) is a named entity of interest   
				if(posTag.equals("PRP")) {
					try {
						CorefMention corefMention = 
								corefchain
								.get(tokens.get(i).get(CorefCoreAnnotations.CorefClusterIdAnnotation.class))
								.getRepresentativeMention();
						String corefEntityNerTag = sentences.get(corefMention.sentNum - 1)
								.get(TokensAnnotation.class).get(corefMention.startIndex - 1)
								.get(NamedEntityTagAnnotation.class);
						if(!corefEntityNerTag.equals("O")) {
							relationsInSentence.addEntity(corefMention.mentionSpan, corefEntityNerTag);
						}
					}
					catch(Exception e) {
						//When CorefClusterIdAnnotaion is null, the token doesn't have a coref chain, hence do nothing
						e.printStackTrace();
					}
				}
				//If a named entity of interest is encountered, get its complete name
				if(nerTag.matches("LOCATION|ORGANIZATION|DATE|MONEY|PERSON|PERCENT|TIME")) {
					while(i < tokens.size()) {
						if(tokens.get(++i).get(NamedEntityTagAnnotation.class).equals(nerTag)) {
							actualText += " "+tokens.get(i).get(TextAnnotation.class);
						}
						else {
							break;
						}
					}
					relationsInSentence.addEntity(actualText, nerTag);
				}
			}
			//If the co-occurrence relationships in this sentence are not trivial, store it
			if(!relationsInSentence.isEmpty()) {
				relationsInDocument.addRelationsInSentence(relationsInSentence);
			}
		}
		return relationsInDocument;
	}

}
