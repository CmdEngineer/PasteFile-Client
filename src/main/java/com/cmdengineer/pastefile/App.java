package com.cmdengineer.pastefile;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import com.cmdengineer.pastefile.utils.HTTPRequestAPI;
import com.cmdengineer.pastefile.utils.Paste;
import com.cmdengineer.pastefile.utils.Server;
import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.api.app.Dialog;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.Layout.Background;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.ComboBox;
import com.mrcrayfish.device.api.app.component.ItemList;
import com.mrcrayfish.device.api.app.listener.ClickListener;
import com.mrcrayfish.device.api.app.listener.ItemClickListener;
import com.mrcrayfish.device.api.app.renderer.ListItemRenderer;
import com.mrcrayfish.device.api.app.component.Spinner;
import com.mrcrayfish.device.api.app.component.Text;
import com.mrcrayfish.device.api.app.component.TextField;
import com.mrcrayfish.device.api.io.File;
import com.mrcrayfish.device.core.Laptop;
import com.mrcrayfish.device.programs.ApplicationExample;
import com.mrcrayfish.device.programs.ApplicationTest;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;

public class App extends Application {

	int width = 200, height = 144;
	// txt -> Text
	// txf -> TextField
	// btn -> Button
	// spn -> Spinner
	// itm -> ItemList.
	
	// DATA
	Server server;
	List<Paste> pastes;
	
	// APP
	Layout main;
	Boolean hasSelected;
	
	/*
	 *  MAIN HUB:
	 * 
	 */
	
	TextField txfUser;
	int txfUserWidth;
	
	Button btnRefresh;
	int btnRefreshX, btnRefreshWidth;
	
	Button btnImport;
	int btnImportX, btnImportWidth;
	
	Button btnExport;
	int btnExportX, btnExportWidth;
	
	Button btnRemove;
	int btnRemoveX, btnRemoveWidth;
	
	Button btnEdit;
	int btnEditX, btnEditWidth;
	
	Button btnSettings;
	int btnSettingsX, btnSettingsWidth;
	
	/*
	 *  PASTE LIST:
	 *
	 */
	ItemList<Paste> itmPastes;
	
	
	@Override
	public void init() {
		server = new Server("http://35.176.212.251:3001/");
		
		pastes = server.getPastes(Minecraft.getMinecraft().player.getUniqueID().toString());
		hasSelected = false;
		
		main = new Layout(width, height);
		
		main.setBackground(new Background() {
			@Override
			public void render(Gui gui, Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, boolean windowActive) {
				gui.drawRect(x, y, x + width, y + 16, new Color(65, 65, 88).getRGB());
				gui.drawRect(x, y + 16, x + width, y + 17, new Color(73, 73, 80).getRGB());
			}
		});
		
		// txfUser
		txfUserWidth = 3 + Icons.USER.getGridWidth() + Minecraft.getMinecraft().fontRenderer.getStringWidth(Minecraft.getMinecraft().player.getName());
		txfUserWidth %= width/2;
		//txfUserWidth = (int)(Minecraft.getMinecraft().player.getName().length() * 9) % (width/2);
		txfUser = new TextField(0, 0, txfUserWidth); 
		txfUser.setEditable(false);
		txfUser.setIcon(Icons.USER);
		txfUser.setText(Minecraft.getMinecraft().player.getName());
		main.addComponent(txfUser);
	
		// btnRefresh
		btnRefreshWidth = 16;
		btnRefreshX = txfUserWidth  + 1;
		btnRefresh = new Button(btnRefreshX, 0, Icons.RELOAD);
		btnRefresh.setToolTip("Reload:", "Reloads the pastes list.");
		btnRefresh.setClickListener(new ClickListener() {
			@Override public void onClick(int mouseX, int mouseY, int mouseButton) { btnRefreshPress(mouseX, mouseY, mouseButton); }
		});
		main.addComponent(btnRefresh);
	
		// btnImport
		btnImportWidth = 16;
		btnImportX = btnRefreshX + btnRefreshWidth + 1;
		btnImport = new Button(btnImportX, 0, Icons.IMPORT);
		btnImport.setToolTip("Import:", "Download a file using a file code.");
		btnImport.setClickListener(new ClickListener() {
			@Override public void onClick(int mouseX, int mouseY, int mouseButton) { btnImportPress(mouseX, mouseY, mouseButton); }
		});
		main.addComponent(btnImport);
		
		// btnExport
		btnExportWidth = 16;
		btnExportX = btnImportX + btnImportWidth + 1;
		btnExport = new Button(btnExportX, 0, Icons.EXPORT);
		btnExport.setToolTip("Export:", "Upload a file.");
		btnExport.setClickListener(new ClickListener() {
			@Override public void onClick(int mouseX, int mouseY, int mouseButton) { btnExportPress(mouseX, mouseY, mouseButton); }
		});
		main.addComponent(btnExport);
		
		// btnSettings
		btnSettingsWidth = 16;
		btnSettingsX = width - btnSettingsWidth - 1;
		btnSettings = new Button(btnSettingsX, 0, Icons.WRENCH);
		btnSettings.setToolTip("Settings:", "Open the settings menu.");
		main.addComponent(btnSettings);
		
		// btnRemove
		btnRemoveWidth = 16;
		btnRemoveX = btnSettingsX - btnRemoveWidth - 1;
		btnRemove = new Button(btnRemoveX, 0, Icons.TRASH);
		btnRemove.setEnabled(false);
		btnRemove.setToolTip("Remove:", "Remove the selected paste.");
		btnRemove.setClickListener(new ClickListener() {
			@Override public void onClick(int mouseX, int mouseY, int mouseButton) { btnRemovePress(mouseX, mouseY, mouseButton); }
		});
		main.addComponent(btnRemove);
		
		// btnEdit
		btnEditWidth = 16;
		btnEditX = btnRemoveX - btnEditWidth - 1;
		btnEdit = new Button(btnEditX, 0, Icons.CUT);
		btnEdit.setEnabled(false);
		btnEdit.setToolTip("Edit:", "Replace the selected paste with a different file.");
		btnEdit.setClickListener(new ClickListener() {
			@Override public void onClick(int mouseX, int mouseY, int mouseButton) { btnEditPress(mouseX, mouseY, mouseButton); }
		});
		main.addComponent(btnEdit);

		// List of Pastes:
		if(!pastes.isEmpty()){
			itmPastes = new ItemList<Paste>(0, 17, width, 9, true);
			for (Paste paste : pastes) {
				itmPastes.addItem(paste);
			}
			itmPastes.setItemClickListener(new ItemClickListener<Paste>() {
				@Override
				public void onClick(Paste file, int index, int mouseButton) {
					hasSelected = true;
					btnRemove.setEnabled(true);
					btnEdit.setEnabled(true);
				}
			});
			main.addComponent(itmPastes);
		}
		
		this.setCurrentLayout(main);
	}
	
	private void btnRefreshPress(int mouseX, int mouseY, int mouseButton){
		if(!pastes.isEmpty()){
			pastes = server.getPastes(Minecraft.getMinecraft().player.getUniqueID().toString());
			itmPastes.setItems(pastes);
		}
	}
	
	private void btnEditPress(int mouseX, int mouseY, int mouseButton){
		if(hasSelected){
			Dialog.Input dialog = new Dialog.Input("Please enter a Paste ID: ");
			openDialog(dialog);
			dialog.setResponseHandler(new Dialog.ResponseHandler<String>() {
				@Override
				public boolean onResponse(boolean success, String str) {
					if(success) {
						if(str.length() == 8 && str.matches("[a-zA-Z0-9]*"))
						{
							Paste p = server.getPaste(str);
							if(p.getFile() != null){
								server.editPaste(itmPastes.getSelectedItem().getId(), p);
							}
						}
					}
					return true;
				}
			});
		}
	}
	
	private void btnRemovePress(int mouseX, int mouseY, int mouseButton){
		if(hasSelected){
			server.removePaste(itmPastes.getSelectedItem().user, itmPastes.getSelectedItem().getId());
			pastes.remove(itmPastes.getSelectedItem());
			itmPastes.removeItem(itmPastes.getSelectedIndex());
			hasSelected = false;
			btnRemove.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRefreshPress(mouseX, mouseY, mouseButton);
		}
	}
	
	private void btnImportPress(int mouseX, int mouseY, int mouseButton){
		Dialog.Input dialog = new Dialog.Input("Please enter a Paste ID: ");
		openDialog(dialog);
		dialog.setResponseHandler(new Dialog.ResponseHandler<String>() {
			@Override
			public boolean onResponse(boolean success, String str) {
				if(success) {
					if(str.length() == 8 && str.matches("[a-zA-Z0-9]*"))
					{
						Paste p = server.getPaste(str);
						if(p.getFile() != null){
							Dialog.SaveFile download = new Dialog.SaveFile(App.this, p.getFile());
							openDialog(download);
						}
					}
				}
				return true;
			}
		});
	}

	private void btnExportPress(int mouseX, int mouseY, int mouseButton){
		try{
			Dialog.OpenFile dialog = new Dialog.OpenFile(App.this);
			openDialog(dialog);
			dialog.setResponseHandler(new Dialog.ResponseHandler<File>() {
				@Override
				public boolean onResponse(boolean success, File file) {
					if(success){
						Dialog.Confirmation confirm = new Dialog.Confirmation("Are you sure you want to upload " + file.getName() + "?");
						openDialog(confirm);
						confirm.setPositiveText("Uploaded!");
						confirm.setPositiveListener(new ClickListener() {
							@Override
							public void onClick(int mouseX, int mouseY, int mouseButton) {
								Paste p = new Paste(Minecraft.getMinecraft().player.getUniqueID().toString(), file);
								p.setId(server.postPaste(p));
								Dialog.Message showID = new Dialog.Message("Your paste id is: " + p.getId());
								openDialog(showID);
								dialog.close();
							}
						});
					}
					return false;
				}
			});
		}catch(Exception e){
			System.out.println("PasteFile | an error occured.");
		}
	}
	
	@Override
	public void load(NBTTagCompound tagCompound) {
		
	}

	@Override
	public void save(NBTTagCompound tagCompound) {
		
	}

}
