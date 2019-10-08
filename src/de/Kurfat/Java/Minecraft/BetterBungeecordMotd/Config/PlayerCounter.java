package de.Kurfat.Java.Minecraft.BetterBungeecordMotd.Config;

import java.util.Arrays;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing.PlayerInfo;
import net.md_5.bungee.api.ServerPing.Players;

public class PlayerCounter {

	private int max = 9999;
	private int fake = 0;
	private List<String> info = Arrays.asList("Wow that looks really good.", "I can even use several lines.");
	
	public PlayerCounter() {}
	
	public int getMax() {
		return max;
	}
	public int getFake() {
		return fake;
	}
	public PlayerInfo[] getInfo() {
		PlayerInfo[] infos = new PlayerInfo[this.info.size()];
		for (int i = 0; i < this.info.size(); i++) {
			String text = this.info.get(i);
			infos[i] = new PlayerInfo(ChatColor.translateAlternateColorCodes('&', text), "");
		}
		return infos;
	}
	public Players getPlayers(int online) {
		return new Players(max, online + fake, getInfo());
	}
	
}
