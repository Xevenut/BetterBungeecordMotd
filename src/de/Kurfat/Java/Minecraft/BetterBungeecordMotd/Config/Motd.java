package de.Kurfat.Java.Minecraft.BetterBungeecordMotd.Config;

import java.util.UUID;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Motd {

	private Noobies noobies = new Noobies();
	private Players players = new Players();
	
	public Motd() {}
	
	public BaseComponent[] getMessage(UUID uuid, String name) {
		if(uuid == null || name == null) return new ComponentBuilder(replace(noobies.first) + "\n" + replace(noobies.second)).create();
		else return new ComponentBuilder(replace(players.first, uuid, name) + "\n" + replace(players.second, uuid, name)).create();
	}
	private String replace(String text, UUID uuid, String name) {
		return ChatColor.translateAlternateColorCodes('&', text).replace("%player_uuid%", uuid.toString()).replace("%player_name%", name);
	}
	private String replace(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	public static class Rows {
		
		protected String first;
		protected String second;
		
		public Rows() {}
		
		public String getFirst() {
			return first;
		}
		public String getSecond() {
			return second;
		}
		
	}
	public static class Noobies extends Rows{
		
		public Noobies() {
			this.first = "&aWellcome on My-Server &cNoob";
			this.second = "Your Staff .....";
		}
		
	}
	public static class Players extends Rows{
		
		public Players() {
			this.first = "&aWellcome on My-Server &c%player_name%";
			this.second = "Your Staff .....";
		}
		
	}
}
