package com.cmdengineer.pastefile;

import java.awt.Color;

import com.cmdengineer.pastefile.utils.HTTPRequestAPI;
import com.cmdengineer.pastefile.utils.Paste;
import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.api.app.Dialog;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.Layout.Background;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.ComboBox.List;
import com.mrcrayfish.device.api.app.listener.ClickListener;
import com.mrcrayfish.device.api.app.component.Spinner;
import com.mrcrayfish.device.api.app.component.Text;
import com.mrcrayfish.device.api.app.component.TextField;
import com.mrcrayfish.device.api.io.File;
import com.mrcrayfish.device.core.Laptop;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;

public class App extends Application {

	int width = 180, height = 100;
	// txt -> Text
	// txf -> TextField
	// btn -> Button
	// spn -> Spinner
	
	// DATA
	Server server;
	
	// APP
	Layout main;
	
	Spinner spnLoad;
	
	TextField txfUser;
	int txfUserWidth;

	Button btnImport;
	int btnImportX, btnImportWidth;
	
	Button btnExport;
	int btnExportX, btnExportWidth;
	
	
	@Override
	public void init() {
		server = new Server("http://localhost:3001/");
		
		main = new Layout(width, height);
		
		main.setBackground(new Background() {
			@Override
			public void render(Gui gui, Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, boolean windowActive) {
				gui.drawRect(x, y, x + width, y + 16, new Color(65, 65, 88).getRGB());
				gui.drawRect(x, y + 16, x + width, y + 17, new Color(73, 73, 80).getRGB());
			}
		});
		
		// txfUser
		txfUserWidth = (int)(Minecraft.getMinecraft().player.getName().length() * 8.5) % (width/2);
		txfUser = new TextField(0, 0, txfUserWidth);
		txfUser.setEditable(false);
		txfUser.setIcon(Icons.USER);
		txfUser.setText(Minecraft.getMinecraft().player.getName());
		main.addComponent(txfUser);
		
		// btnImport
		btnImportWidth = 16;
		btnImportX = txfUserWidth + 1;
		btnImport = new Button(btnImportX, 0, Icons.IMPORT);
		btnImport.setToolTip("Import:", "Download a file using a file code.");
		main.addComponent(btnImport);
		
		// btnExport
		btnExportWidth = 16;
		btnExportX = txfUserWidth + btnImportWidth + 2;
		btnExport = new Button(btnExportX, 0, Icons.EXPORT);
		btnExport.setToolTip("Export:", "Upload a file.");
		main.addComponent(btnExport);
		
		this.setCurrentLayout(main);
	}
	
	public File openFile(){
		File f = null;
		Dialog.OpenFile dialog = new Dialog.OpenFile(App.this);
		openDialog(dialog);
		dialog.setResponseHandler(new Dialog.ResponseHandler<File>() {
			@Override
			public boolean onResponse(boolean success, File e) {
				Paste p = new Paste(Minecraft.getMinecraft().player.getName(), e);
				p.setId(server.postPaste(p));
				Dialog.Confirmation confirm = new Dialog.Confirmation("Are you sure you want to delete " + p.getId() + "?");
				openDialog(confirm);
				confirm.setPositiveText("Removed!");
				confirm.setPositiveListener(new ClickListener() {
					@Override
					public void onClick(Component c, int mouseButton) {
						server.removePaste(p.user, p.getId());
					}
				});
				return success;
			}
		});
		return f;
	}
	
	@Override
	public void load(NBTTagCompound tagCompound) {
		
	}

	@Override
	public void save(NBTTagCompound tagCompound) {
		
	}

}
