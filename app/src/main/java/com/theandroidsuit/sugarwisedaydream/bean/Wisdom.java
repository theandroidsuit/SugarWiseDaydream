package com.theandroidsuit.sugarwisedaydream.bean;


/**
 *
 * THE ANDROID SUIT 2014
 * @author Virginia Hernandez
 * @version 1.0
 *
 */

public class Wisdom {

	public String id;
	public String title;
	public String sentence;
	public String extras;
	public String author;
	public String type;
	
	public Wisdom() {
		
		setTitle("");
		setAuthor("");
		setExtras("");
		setSentence("");
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public String getExtras() {
		return extras;
	}
	public void setExtras(String extras) {
		this.extras = extras;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getWisdomSentence() {
		StringBuffer sb = new StringBuffer();
		
		if(null == this.getTitle() || !"".equals(this.getTitle()))
			sb.append(this.getTitle()).append("\n");
		
		if(null == this.getSentence() || !"".equals(this.getSentence()))
			sb.append(this.getSentence());
		
		if(null == this.getExtras() || !"".equals(this.getExtras()))
			sb.append(" -- ").append(this.getExtras());
		
		return sb.toString();
	}
	
	public String getWisdomTitle() {
		return "Sugar Wise - " + this.getType();
	}
	
	
	public String getWisdomAuthor (){
		
		if(null == this.getAuthor() || !"".equals(this.getAuthor()))
			return this.getAuthor();
		
		return "";
	}
}
