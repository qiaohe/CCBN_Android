package com.exhibition.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**会展数据实体*/
public class EventData implements Serializable{
	/**数据更改时间*/
	private Long updatedAt;
	/**会展名*/
	private String name;
	/**会展简介*/
	private String description;
	/**会展地址*/
	private String address;
	/**会展开始时间*/
	private Long startDate;
	/**会展结束时间*/
	private Long endDate;
	/**会展主办方*/
	private String organizer;
	/**发言人数组*/
	private ArrayList<Speaker> speakers;
	/**参展商数组*/
	private ArrayList<Exhibitor> exhibitors;
	/**会展日程数组*/
	private ArrayList<EventSchedule> eventSchedules;
	
	public Long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	public Long getEndDate() {
		return endDate;
	}
	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public ArrayList<Speaker> getSpeakers() {
		return speakers;
	}
	public void setSpeakers(ArrayList<Speaker> speakers) {
		this.speakers = speakers;
	}
	public ArrayList<Exhibitor> getExhibitors() {
		return exhibitors;
	}
	public void setExhibitors(ArrayList<Exhibitor> exhibitors) {
		this.exhibitors = exhibitors;
	}
	public ArrayList<EventSchedule> getEventSchedules() {
		return eventSchedules;
	}
	public void setEventSchedules(ArrayList<EventSchedule> eventSchedules) {
		this.eventSchedules = eventSchedules;
	}
	/**发言者实体*/
	public class Speaker implements Serializable{
		/**演讲者名字*/
		private String name;
		/**演讲主题*/
		private String profile;
		/**职务*/
		private String position;
		/**公司*/
		private String company;
		/**相片*/
		private String photo;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getProfile() {
			return profile;
		}
		public void setProfile(String profile) {
			this.profile = profile;
		}
		public String getPosition() {
			return position;
		}
		public void setPosition(String position) {
			this.position = position;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getPhoto() {
			return photo;
		}
		public void setPhoto(String photo) {
			this.photo = photo;
		}
		
	}
	/**参展商实体*/
	public class Exhibitor implements Serializable{
		/**公司名称*/
		private String company;
		/**展位*/
		private String location;
		private String address;  //地址
		private String phone;  //电话
		private String website;     //邮箱
		private String description;  //介绍
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getWebsite() {
			return website;
		}
		public void setWebsite(String website) {
			this.website = website;
		}
		
		
		
	}
	/**会展日程实体*/
	public class EventSchedule implements Serializable{
		/**主题*/
		private String name;
		/**简介*/
		private String description;
		/**地点*/
		private String place;
		/**开始时间*/
		private Long dateFrom;
		/**结束时间*/
		private Long dateTo;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getPlace() {
			return place;
		}
		public void setPlace(String place) {
			this.place = place;
		}
		public Long getDateFrom() {
			return dateFrom;
		}
		public void setDateFrom(Long dateFrom) {
			this.dateFrom = dateFrom;
		}
		public Long getDateTo() {
			return dateTo;
		}
		public void setDateTo(Long dateTo) {
			this.dateTo = dateTo;
		}
	}
}
