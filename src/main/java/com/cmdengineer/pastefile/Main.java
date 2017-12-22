package com.cmdengineer.pastefile;

import com.mrcrayfish.device.api.ApplicationManager;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = Info.MOD_NAME, modid = Info.MOD_ID, version = Info.VERSION, acceptedMinecraftVersions = Info.ACCEPTED_VERSIONS, dependencies = Info.DEPENDS)
public class Main {
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e){
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		ResourceLocation resLoc = new ResourceLocation(Info.MOD_ID, "PasteFile");
		ApplicationManager.registerApplication(resLoc, App.class);
	}
}
