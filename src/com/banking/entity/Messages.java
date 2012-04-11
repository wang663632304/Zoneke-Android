package com.banking.entity;

import java.util.ArrayList;


public class Messages {
	 private ArrayList<message> dotamessageAL = new ArrayList<message>();
     private ArrayList<message> eatmessageAL = new ArrayList<message>();
     private ArrayList<message> outmessageAL = new ArrayList<message>();
     private ArrayList<message> findmessageAL = new ArrayList<message>();
     private ArrayList<message> sportmessageAL = new ArrayList<message>();
     int scope;
	public ArrayList<message> getDotamessageAL() {
		return dotamessageAL;
	}
	public void setDotamessageAL(ArrayList<message> dotamessageAL) {
		this.dotamessageAL = dotamessageAL;
	}
	public ArrayList<message> getEatmessageAL() {
		return eatmessageAL;
	}
	public void setEatmessageAL(ArrayList<message> eatmessageAL) {
		this.eatmessageAL = eatmessageAL;
	}
	public ArrayList<message> getOutmessageAL() {
		return outmessageAL;
	}
	public void setOutmessageAL(ArrayList<message> outmessageAL) {
		this.outmessageAL = outmessageAL;
	}
	public ArrayList<message> getFindmessageAL() {
		return findmessageAL;
	}
	public void setFindmessageAL(ArrayList<message> findmessageAL) {
		this.findmessageAL = findmessageAL;
	}
	public ArrayList<message> getSportmessageAL() {
		return sportmessageAL;
	}
	public void setSportmessageAL(ArrayList<message> sportmessageAL) {
		this.sportmessageAL = sportmessageAL;
	}
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
	}
    public String toString(){
    	String result="";
    	result+="下面是Messages:";
    	result+="下面是dota message:";
    	for(int i1=0;i1<dotamessageAL.size();i1++)
    	{
    		result+=dotamessageAL.get(i1).content;
    	}
    	result+="下面是eat message:";
    	for(int i2=0;i2<eatmessageAL.size();i2++)
    	{
    		result+=eatmessageAL.get(i2).content;
    	}
    	result+="下面是out message:";
    	for(int i3=0;i3<outmessageAL.size();i3++)
    	{
    		result+=outmessageAL.get(i3).content;
    	}
    	result+="下面是find message:";
    	for(int i4=0;i4<findmessageAL.size();i4++)
    	{
    		result+=findmessageAL.get(i4).content;
    	}
    	result+="下面是sport message:";
    	for(int i5=0;i5<sportmessageAL.size();i5++)
    	{
    		result+=sportmessageAL.get(i5).content;
    	}
    	return result;
    }
	

}
