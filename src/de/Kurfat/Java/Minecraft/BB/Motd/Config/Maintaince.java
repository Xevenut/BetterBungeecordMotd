package de.Kurfat.Java.Minecraft.BB.Motd.Config;

import com.google.gson.annotations.Expose;

public class Maintaince {

	@Expose
	private boolean enable = false;
	@Expose
	private Version version = new Version(999, "&cMaintaince");
	@Expose
	private Kick kick = new Kick();
	@Expose
	private String commandpermission = "kurfat.command.maintaince";
	
	public Maintaince() {}
	
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public Version getVersion() {
		return version;
	}
	public Kick getKick() {
		return kick;
	}
	public String getCommandPermission() {
		return commandpermission;
	}
	
}
