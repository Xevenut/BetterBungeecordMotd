package de.Kurfat.Java.Minecraft.BetterBungeecordMotd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.Kurfat.Java.Minecraft.BetterBungeecordMotd.Config.JsonConfig;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerCache {
	
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	public static final File FILE = new File("plugins/BetterBungeecordMotd/playercache.json");
	public static PlayerCache INSTANCE;
	public static boolean load() {
		try {
			FileReader reader = new FileReader(FILE);
			INSTANCE = GSON.fromJson(reader, PlayerCache.class);
			System.out.println(JsonConfig.INSTANCE.getPrefix() + " " + AnsiColor.GREEN + "The file was successfully loaded:" + AnsiColor.RESET + " " + AnsiColor.YELLOW + "playercache.json" + AnsiColor.RESET);
			return true;
		} catch (FileNotFoundException e) {
			INSTANCE = new PlayerCache();
			System.out.println(JsonConfig.INSTANCE.getPrefix() + " " + AnsiColor.YELLOW + "Default file was created:" + AnsiColor.RESET + " " + AnsiColor.YELLOW + "playercache.json" + AnsiColor.RESET);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(JsonConfig.INSTANCE.getPrefix() + " " + AnsiColor.RED + "The file has an error. Please check the file before starting the server again: playercache.json" + AnsiColor.RESET + " " + AnsiColor.YELLOW + "config.json" + AnsiColor.RESET);
			return false;
		}
		
	}
	public static boolean save() {
		try {
			FILE.getParentFile().mkdirs();
			FILE.createNewFile();
			String json = GSON.toJson(INSTANCE);
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE));
			writer.write(json);
			writer.flush();
			writer.close();
			System.out.println(JsonConfig.INSTANCE.getPrefix() + " " + AnsiColor.GREEN + "The file has been saved:" + AnsiColor.RESET + " " + AnsiColor.YELLOW + "playercache.json" + AnsiColor.RESET);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(JsonConfig.INSTANCE.getPrefix() + " " + AnsiColor.RED + "The file has an save error. Please send the error code to the developer: playercache.json" + AnsiColor.RESET);
			return false;
		}
	}
	
	private Map<UUID, Cache> cache = new HashMap<UUID, PlayerCache.Cache>();
	
	public PlayerCache() {}
	
	public UUID getUUIDfromName(String name) {
		for(Entry<UUID, Cache> entry : new ArrayList<>(cache.entrySet())) if(entry.getValue().getName().equals(name)) return entry.getKey();
		return null;
	}
	public UUID getUUIDfromAddress(InetAddress address) {
		for(Entry<UUID, Cache> entry : new ArrayList<>(cache.entrySet())) if(entry.getValue().containceAddress(address)) return entry.getKey();
		return null;
	}
	public Cache getCache(UUID uuid) {
		return cache.containsKey(uuid) ? cache.get(uuid) : null;
	}
	
	public boolean updateNameFromMojang(String name) {
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
			URLConnection connection = url.openConnection();
			InputStreamReader reader = new InputStreamReader(connection.getInputStream());
			Gson gson = new GsonBuilder().create();
			Data data = gson.fromJson(reader, Data.class);
			name = data.getName();
			UUID uuid = data.getUUID();
			reader.close();
			cache.put(uuid, new Cache(name, null));
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void updateLocal(ProxiedPlayer player) {
		if(this.cache.containsKey(player.getUniqueId())) {
			Cache cache = this.cache.get(player.getUniqueId());
			cache.setName(player.getName());
			cache.addAddress(player.getAddress().getAddress());
		}
		else this.cache.put(player.getUniqueId(), new Cache(player.getName(), player.getAddress().getAddress()));
	}
	
	public static class Data{
		
		private String id;
		private String name;
		
		public Data() {}
		
		public UUID getUUID() {
			return UUID.fromString(id);
		}
		public String getName() {
			return name;
		}
		
	}
	public static class Cache{
		
		private String name;
		private Map<String, Date> addresses = new HashMap<String, Date>();
		
		public Cache(String name, InetAddress address) {
			this.name = name;
			if(address != null) addAddress(address);
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public void addAddress(InetAddress address) {
			long current = new Date().getTime();
			for(Entry<String, Date> entry : new ArrayList<>(this.addresses.entrySet())) {
				long sum = current - entry.getValue().getTime();
				if(sum >= 2592000000L) this.addresses.remove(entry.getKey());
			}
			this.addresses.put(address.getHostAddress(), new Date());
		}
		public boolean containceAddress(InetAddress address) {
			return addresses.containsKey(address.getHostAddress());
		}
		
	}
}
