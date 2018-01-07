package com.cmdengineer.pastefile.utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import scala.util.parsing.json.JSON;

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
			p = new Paste(http.get(url + id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public Paste getPaste(String user, String id){
		Paste p = null;
		try {
			p = new Paste(user, http.get(url + id));
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
			String listAsString = http.get(url + "get/" + user);
			listAsString = listAsString.replaceAll("[", "").replaceAll("]", "").replaceAll(".txt", "").replaceAll("\"", "");
			String[] list = listAsString.split(",");
			amount = list.length;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}
	
	public List<Paste> getPastes(String user){
		List<Paste> pastes = new ArrayList<Paste>();
		try {
			String listAsString = http.get(url + "get/" + user);
			listAsString = listAsString.replace("[", "").replace("]", "").replaceAll(".txt", "").replaceAll("\"", "");
			if(listAsString.contains(",")){
				String[] list = listAsString.split(",");
				for(String paste : list){
					Paste p = getPaste(user, paste);
					p.setId(paste);
					pastes.add(p);
				}
			}else if(listAsString.length() == 8){
				Paste p = getPaste(user, listAsString);
				p.setId(listAsString);
				pastes.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pastes;
	}
	
	public List<Paste> getPastes(){
		List<Paste> pastes = new ArrayList<Paste>();
		try {
			String listAsString = http.get(url + "get");

			listAsString = listAsString.replace("[", "").replace("]", "").replaceAll(".txt", "").replaceAll("\"", "");
			String[] list = listAsString.split(",");
			for(String paste : list){
				Paste p = getPaste(paste);
				p.setId(paste);
				pastes.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pastes;
	}
}