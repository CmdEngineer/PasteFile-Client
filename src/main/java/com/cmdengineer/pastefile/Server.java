package com.cmdengineer.pastefile;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import com.cmdengineer.pastefile.utils.HTTPRequestAPI;
import com.cmdengineer.pastefile.utils.Paste;

public class Server {

	private String url;
	private HTTPRequestAPI http;
	
	public Server(String url){
		this.url = url;
		http = new HTTPRequestAPI();
	}
	 
	public Server(String url, HTTPRequestAPI http){
		this.url = url;
		this.http = http;
	}
	
	public String postPaste(Paste p){
		String id = "";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if(p.user != null && p.user != ""){
			params.add(new BasicNameValuePair("user", p.user));
		}
		params.add(new BasicNameValuePair("info", p.info.toString()));
		params.add(new BasicNameValuePair("json", p.data.toString()));
		params.add(new BasicNameValuePair("cmd", "UPLOAD"));
		try {
			id = http.post(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public Paste getPaste(String id){
		Paste p = null;
		try {
			System.out.println(http.get(url + id));
			p = new Paste(http.get(url + id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public boolean editPaste(String id, Paste p){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if(p.user != null && p.user != ""){
			params.add(new BasicNameValuePair("user", p.user));
		}
		params.add(new BasicNameValuePair("info", p.info.toString()));
		params.add(new BasicNameValuePair("json", p.data.toString()));
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("cmd", "EDIT"));
		try {
			String result = http.post(url, params);
			if(result == "Edited!"){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean removePaste(String user, String id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user", user));
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("cmd", "REMOVE"));
		try {
			String result = http.post(url, params);
			if(result == "Removed!"){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean removePaste(String id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("cmd", "REMOVE"));
		try {
			String result = http.post(url, params);
			if(result == "Removed!"){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int getAmount(){
		int amount = -1;
		try {
			amount = Integer.parseInt(http.get(url));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}
	
	public int getAmount(String user){
		int amount = -1;
		try {
			amount = Integer.parseInt(http.get(url + "get/" + user));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}
}
