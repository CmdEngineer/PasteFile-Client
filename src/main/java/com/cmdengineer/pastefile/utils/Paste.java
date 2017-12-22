package com.cmdengineer.pastefile.utils;

import com.mrcrayfish.device.api.io.File;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class Paste {
	
	public String user = "";
	public NBTTagCompound info;
	public NBTTagCompound data;
	private String id;
	
	public Paste(String user, File file){
		this.user = user;
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		if(file != null && !file.isFolder()){
			info.setString("user", user);
			info.setString("name", file.getName());
			info.setString("app", file.getOpeningApp());
			data = file.getData();
		}
	}
	
	public Paste(String user, NBTTagCompound info, NBTTagCompound  data){
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		this.info = info;
		this.data = data;
		this.user = user;
	}
	
	public Paste(NBTTagCompound info, NBTTagCompound  data){
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		this.info = info;
		this.data = data;
	}
	
	public Paste(String fromServer){
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		try {
			String[] content = fromServer.split("<=Data=>");
			System.out.println("INFO: " + content[0]);
			System.out.println("DATA: " + content[1]);
			this.data = JsonToNBT.getTagFromJson(content[1]);
			this.info = JsonToNBT.getTagFromJson(content[0]);
		} catch (NBTException e) {
			e.printStackTrace();
		}
	}
	
	public Paste(String info, String data){
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		try {
			this.data = JsonToNBT.getTagFromJson(data);
			this.info = JsonToNBT.getTagFromJson(info);
		} catch (NBTException e) {
			e.printStackTrace();
		}
	}
	
	public Paste(String user, String info, String data){
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		try {
			this.data = JsonToNBT.getTagFromJson(data);
			this.info = JsonToNBT.getTagFromJson(info);
		} catch (NBTException e) {
			e.printStackTrace();
		}
		this.user = user;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
