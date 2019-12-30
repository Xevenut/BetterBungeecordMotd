package de.Kurfat.Java.Minecraft.BB.Motd.Cache;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import com.google.gson.annotations.Expose;

import de.Kurfat.Java.Minecraft.BB.Motd.BetterBungeecordMotd;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Cache {

	@Expose
	private UUID uuid;
	@Expose
	private String name;
	@Expose
	private HashMap<String, Date> addresses = new HashMap<String, Date>();
	@Expose
	private Favicon favicon;
	
	public Cache(ProxiedPlayer player) {
		this.uuid = player.getUniqueId();
		this.name = player.getName();
		updateFavicon();
	}
	
	public UUID getUUID() {
		return uuid;
	}
	public String getName() {
		return name;
	}
	public void addAddress(InetAddress address) {
		long current = new Date().getTime();
		for(Entry<String, Date> entry : new ArrayList<>(addresses.entrySet())) {
			long sum = current - entry.getValue().getTime();
			if(sum >= 2592000000L) this.addresses.remove(entry.getKey());
		}
		addresses.put(address.getHostAddress(), new Date());
	}
	public boolean containceAddress(InetAddress address) {
		String hostaddress = address.getHostAddress();
		if(addresses.containsKey(hostaddress) == false) return false;
		Date date = addresses.get(hostaddress);
		long sum = new Date().getTime() - date.getTime();
		if(sum >= 2592000000L) {
			this.addresses.remove(hostaddress);
			return false;
		}
		return true;
	}
	
	public boolean updateFavicon() {
		String path = BetterBungeecordMotd.CONFIG.getPictureMode().getMode().getURL().replace("%player_uuid%", uuid.toString());
		try {
			BufferedImage image = ImageIO.read(new URL(path));
			this.favicon = Favicon.create(image);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't read this file: " + path);
			return false;
		}
	}
	public Favicon getFavicon() {
		return favicon;
	}
	
}
