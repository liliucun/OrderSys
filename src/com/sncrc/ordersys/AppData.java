package com.sncrc.ordersys;

import android.app.Application;


public class AppData extends Application{
	private String Power="";
	private String User="";
	private String County="";
	private String Phone="";
	private String Name="";
	private String NeedPhoto="";
	private String BitmapData="";
	private String Role="";
	
	public String getRole(){
		return this.Role;
	}
	public String getBitmapData(){
		return this.BitmapData;
	}
	public String getPower(){
		return this.Power;
	}
	public void setPower(String c){
		this.Power= c;
	}
	
	public String getUser(){
		return this.User;
	}
	public void setUser(String c){
		this.User= c;
	}
	
	public String getCounty(){
		return this.County;
	}
	
	public String GetNeedPhoto(){
		return this.NeedPhoto;
	}
	
	public void setRole(String c){
		this.Role= c;
	}
	public void setBitmapData(String c){
		this.BitmapData= c;
	}
	
	public void setCounty(String c){
		this.County= c;
	}
	
	public String getPhone(){
		return this.Phone;
	}
	public void setPhone(String c){
		this.Phone= c;
	}
	
	public String getName(){
		return this.Name;
	}
	public void setName(String c){
		this.Name= c;
	}
	
	public void setNeedPhoto(String c){
		this.NeedPhoto=c;
	}
	@Override
	public void onCreate(){
		Power="";
		User="";
		County="";
		Phone="";
		Name="";
		NeedPhoto="";
		BitmapData="";
		super.onCreate();
	}
}