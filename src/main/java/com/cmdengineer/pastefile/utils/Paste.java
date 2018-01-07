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
	private File file;
	
	public Paste(String user, File file){
		this.user = user;
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		this.file = file;
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
		this.file = new File(this.info.getString("name"), this.info.getString("app"), this.data);
	}
	
	public Paste(NBTTagCompound info, NBTTagCompound  data){
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		this.info = info;
		this.data = data;
		this.file = new File(this.info.getString("name"), this.info.getString("app"), this.data);
	}
	
	public Paste(String fromServer){
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		try {
			String[] content = fromServer.split("<=Data=>");
			this.data = JsonToNBT.getTagFromJson(content[1]);
			this.info = JsonToNBT.getTagFromJson(content[0]);
			this.file = new File(this.info.getString("name"), this.info.getString("app"), this.data);
		} catch (NBTException e) {
			e.printStackTrace();
		}
	}
	
	public Paste(String user, String fromServer){
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		this.user = user;
		try {
			String[] content = fromServer.split("<=Data=>");
			this.data = JsonToNBT.getTagFromJson(content[1]);
			this.info = JsonToNBT.getTagFromJson(content[0]);
			this.file = new File(this.info.getString("name"), this.info.getString("app"), this.data);
		} catch (NBTException e) {
			e.printStackTrace();
		}
	}
	
	public Paste(int mode, String info, String data){
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		try {
			this.data = JsonToNBT.getTagFromJson(data);
			this.info = JsonToNBT.getTagFromJson(info);
			this.file = new File(this.info.getString("name"), this.info.getString("app"), this.data);
		} catch (NBTException e) {
			e.printStackTrace();
		}
	}
	
	public Paste(String user, String info, String data){
		this.info = new NBTTagCompound();
		this.data = new NBTTagCompound();
		this.user = user;
		try {
			this.data = JsonToNBT.getTagFromJson(data);
			this.info = JsonToNBT.getTagFromJson(info);
			this.file = new File(this.info.getString("name"), this.info.getString("app"), this.data);
		} catch (NBTException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		if(file != null && !file.isFolder()){
			return file.getName();	
		}
		if(info != null){
			return info.getString("name");	
		}
		return "Unknown.";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
