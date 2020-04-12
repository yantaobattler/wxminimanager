package com.wx.wxminimanager.itsm.model;

/**
 *
 * 
 * @version 1.0 2020年3月27日
 * @author yantao
 */

public class ItsmModel {
	
	private String id;
	
	private String topic;
	
	private String text;
	
	private String date;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
