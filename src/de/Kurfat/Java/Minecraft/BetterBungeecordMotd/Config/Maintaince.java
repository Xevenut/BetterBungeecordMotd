package de.Kurfat.Java.Minecraft.BetterBungeecordMotd.Config;

public class Maintaince {

	private boolean enable = false;
	private Version version = new Version(999, "&cMaintaince");
	private Kick kick = new Kick();
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
