package de.Kurfat.Java.Minecraft.BB.Motd.Cache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import de.Kurfat.Java.Minecraft.BB.Motd.AnsiColor;
import de.Kurfat.Java.Minecraft.BB.Motd.BetterBungeecordMotd;
import de.Kurfat.Java.Minecraft.BB.Motd.JsonFileUtil;
import de.Kurfat.Java.Minecraft.BB.Motd.Config.Config;
import de.Kurfat.Java.Minecraft.BB.Motd.Config.Maintaince;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerCache implements Listener{

	public static final String CACHE_PATH = BetterBungeecordMotd.PLUGIN_DIR.getPath() + "/cache/";
	public static final JsonFileUtil<Cache> JSON_FILE_UTIL = new JsonFileUtil<Cache>(Cache.class);
	
	private HashMap<UUID, Cache> all = new HashMap<UUID, Cache>();
	
	public PlayerCache() {
		File directory = new File(CACHE_PATH);
		if(directory.exists() == false) directory.mkdirs();
		BetterBungeecordMotd.printMessage("Load playercache:");
		int count = 0;
		for(File file : directory.listFiles()) if(file.getName().endsWith(".json")){
			UUID uuid;
			try {
				uuid = UUID.fromString(file.getName().replace(".json", ""));
			} catch (Exception e) {
				continue;
			}
			try {
				Cache cache = JSON_FILE_UTIL.load(file);
				all.put(uuid, cache);
				BetterBungeecordMotd.printMessage("- " + uuid);
				count++;
			}catch (Exception ex) {
				BetterBungeecordMotd.printMessage("- " + uuid + ": " + AnsiColor.RED + ex.getMessage());
			}
		}
		BetterBungeecordMotd.printMessage(AnsiColor.GREEN + count + AnsiColor.RESET + " caches were loaded");
	}
	public void save() {
		BetterBungeecordMotd.printMessage("Save playercache:");
		int count = 0;
		for(Entry<UUID, Cache> e : all.entrySet())
			try {
				JSON_FILE_UTIL.save(e.getValue(), new File(CACHE_PATH + e.getKey() + ".json"));
				BetterBungeecordMotd.printMessage("- " + e.getKey());
				count++;
			} catch (IOException ex) {
				BetterBungeecordMotd.printMessage("- " + e.getKey() + ": " + AnsiColor.RED + ex.getMessage());
			}
		BetterBungeecordMotd.printMessage(AnsiColor.GREEN + count + AnsiColor.RESET + " caches were loaded");
	}
	
	@EventHandler
	public void onJoin(PostLoginEvent event) {
		Maintaince config = BetterBungeecordMotd.CONFIG.getMaintaince();
		ProxiedPlayer player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		
		if(all.containsKey(uuid) == false) {
			Cache cache = new Cache(player);
			all.put(uuid, cache);
		}
		Cache cache = all.get(uuid);
		cache.updateFavicon();
		cache.addAddress(event.getPlayer().getAddress().getAddress());

		if(config.isEnable() == false || player.hasPermission(config.getKick().getPermission())) return;
		player.disconnect(config.getKick().getMessage());
	}
	
	@EventHandler
	public void onPing(ProxyPingEvent event) {
		Config config = BetterBungeecordMotd.CONFIG;
		ServerPing ping = event.getResponse();
		
		// IS PLAYER
		for(Entry<UUID, Cache> entry : new ArrayList<>(all.entrySet())) if(entry.getValue().containceAddress(event.getConnection().getAddress().getAddress())) {
			ping.setDescriptionComponent(config.getMotd().getMessage(entry.getKey(), entry.getValue().getName())[0]);
			ping.setFavicon(entry.getValue().getFavicon());
			return;
		}
		
		// DEFAULT STTINGS
		ping.setDescriptionComponent(config.getMotd().getMessage(null, null)[0]);
		ping.setPlayers(config.getPlayerCounter().getPlayers(ping.getPlayers().getOnline()));
		if(config.getMaintaince().isEnable()) ping.setVersion(config.getMaintaince().getVersion().getProtocol());
		else ping.setVersion(config.getVersion().getProtocol());
	}
	
}
