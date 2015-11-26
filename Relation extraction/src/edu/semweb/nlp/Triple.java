package edu.semweb.nlp;

public class Triple {
	
	private String subject;
	private String subjectType;
	private String predicate;
	private String object;
	private String objectType;
	private String reificationSourceSentence;
	private String reificationSourceUrl;
	
	public Triple(String subject, String subjectType, String predicate, String object, String objectType, String reificationSourceSentence, String reificationSourceUrl) {
		this.subject = subject;
		this.subjectType = subjectType;
		this.predicate = predicate;
		this.object = object;
		this.objectType = objectType;
		this.reificationSourceSentence = reificationSourceSentence;
		this.reificationSourceUrl = reificationSourceUrl;
	}
	
	public String getObject() {
		return object;
	}
	
	public String getObjectType() {
		return objectType;
	}
	
	public String getPredicate() {
		return predicate;
	}
	
	public String getReificationSourceSentence() {
		return reificationSourceSentence;
	}
	
	public String getReificationSourceUrl() {
		return reificationSourceUrl;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getSubjectType() {
		return subjectType;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[\n\t"+subjectType+":'"+subject+"'   "+predicate+"   "+objectType+":'"+object+"'\n\tSourceSentence:"+reificationSourceSentence+"\n\tSourceUrl:"+reificationSourceUrl+"\n]";
	}

}
