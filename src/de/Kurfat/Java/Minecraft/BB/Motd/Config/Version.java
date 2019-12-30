package de.Kurfat.Java.Minecraft.BB.Motd.Config;

import com.google.gson.annotations.Expose;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing.Protocol;

public class Version {

	@Expose
	private int id;
	@Expose
	private String text;

	public Version(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public int getID() {
		return id;
	}
	public String getText() {
		return text;
	}
	public Protocol getProtocol() {
		return new Protocol(ChatColor.translateAlternateColorCodes('&', text), id);
	}
	
}
