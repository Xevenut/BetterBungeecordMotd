package de.Kurfat.Java.Minecraft.BB.Motd.Config;

import com.google.gson.annotations.Expose;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Kick {

	@Expose
	private String permission = "kurfat.bypass.maintaince";
	@Expose
	private String message = "&cSorry we have maintenance";
	
	public Kick() {}
	
	public String getPermission() {
		return permission;
	}
	public BaseComponent[] getMessage() {
		return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', message)).create();
	}
}
