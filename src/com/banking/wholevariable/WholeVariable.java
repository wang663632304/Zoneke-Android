package com.banking.wholevariable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;

public class WholeVariable {
	//private static final String BASEURL="http://192.168.20.177:9090/";
	private static final String BASEURL="http://www.zoneke.com/";
	private static boolean firstLogin = false;
	private static boolean rememberPassword = false;
	private static String nowUserId = "";
	private static String nowUserName = "";
	private static String scope = "200";
	
	private static String tag = "dota";
	private static boolean[] viewLoading = new boolean[5];
	private static int[] interests = new int[6];
	private static String LAT ="30.5270";
	private static String LNG="114.3550";
	private static boolean getRightLocation = true;
	private static List<Activity> activitiesList=new ArrayList<Activity>();
	public static String getLAT() {
		return LAT;
	}
	public static void setLAT(String lAT) {
		LAT = lAT;
	}
	public static String getLNG() {
		return LNG;
	}
	public static void setLNG(String lNG) {
		LNG = lNG;
	}
	public static int[] getInterests() {
		return interests;
	}
	public static void setInterests(int[] interests) {
		WholeVariable.interests = interests;
	}
	public static boolean[] getViewLoading() {
		return viewLoading;
	}
	public static void setViewLoading(boolean[] viewLoading) {
		WholeVariable.viewLoading = viewLoading;
	}
	public static String getBaseUrl(){
		return BASEURL;
	}
	public static boolean isFirstLogin() {
		return firstLogin;
	}
	public static void setFirstLogin(boolean firstLogin) {
		WholeVariable.firstLogin = firstLogin;
	}
	public static boolean isRememberPassword() {
		return rememberPassword;
	}
	public static void setRememberPassword(boolean rememberPassword) {
		WholeVariable.rememberPassword = rememberPassword;
	}
	public static String getNowUserId() {
		return nowUserId;
	}
	public static void setNowUserId(String nowUserId) {
		WholeVariable.nowUserId = nowUserId;
	}
	public static String getNowUserName() {
		return nowUserName;
	}
	public static void setNowUserName(String nowUserName) {
		WholeVariable.nowUserName = nowUserName;
	}
	public static String getScope() {
		return scope;
	}
	public static void setScope(String scope) {
		WholeVariable.scope = scope;
	}
	public static String getTag() {
		return tag;
	}
	public static void setTag(String tag) {
		WholeVariable.tag = tag;
	}
	public static List<Activity> getList(){
		return activitiesList;
	}
	public static boolean isGetRightLocation() {
		return getRightLocation;
	}
	public static void setGetRightLocation(boolean getRightLocation) {
		WholeVariable.getRightLocation = getRightLocation;
	}
	
}
