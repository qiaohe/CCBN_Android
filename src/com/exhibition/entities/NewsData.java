package com.exhibition.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsData implements Serializable{
	private News[] newses;
	
	public News[] getNewses() {
		return newses;
	}

	public void setNewses(News[] newses) {
		this.newses = newses;
	}

	public  class News implements Serializable{
		private String title;
		private String content;  
		private long date;
		public long getDate() {
			return date;
		}
		public void setDate(long date) {
			this.date = date;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}  
	}
	
	
}
