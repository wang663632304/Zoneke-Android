package com.banking.entity;



public class message {

    public String username;

    public int num;

    public int weight;
    
    public String[] tags;
    
    public int userID;
    
    public String content;
    
    public String time;
    
    public String lat;
    
    public String lng;
    
    public String _id;
    //public Date Birthday;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String id) {
		_id = id;
	}
    public String toString(){
    	String returnValue="消息内容"+this.content+"消息tag"+this.tags[0];
    	return returnValue;
    }
}
