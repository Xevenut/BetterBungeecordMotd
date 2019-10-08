package de.Kurfat.Java.Minecraft.BetterBungeecordMotd.Config;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing.Protocol;

public class Version {

	private int id;
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
