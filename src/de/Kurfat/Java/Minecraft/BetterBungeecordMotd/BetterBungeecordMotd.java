package de.Kurfat.Java.Minecraft.BetterBungeecordMotd;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.imageio.ImageIO;

import de.Kurfat.Java.Minecraft.BetterBungeecordMotd.PlayerCache.Cache;
import de.Kurfat.Java.Minecraft.BetterBungeecordMotd.Config.JsonConfig;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

public class BetterBungeecordMotd extends Plugin implements Listener {
	
	private Command command = new Command_Maintaince();
	
	public void onEnable() {
		ProxyServer server = ProxyServer.getInstance();
		if(JsonConfig.load() == false) {
			server.stop("config.json has an error!");
			return;
		}
		if(PlayerCache.load() == false) {
			server.stop("playercache.json config has an error!");
			return;
		}
		JsonConfig.save();
		PluginManager manager = server.getPluginManager();
		manager.registerListener(this, this);
		manager.registerCommand(this, command);
	}
	@Override
	public void onDisable() {
		JsonConfig.save();
		PlayerCache.save();
	}
	
	@EventHandler
	public static void onJoin(PostLoginEvent event) {
		JsonConfig config = JsonConfig.INSTANCE;
		ProxiedPlayer player = event.getPlayer();
		if(config.getMaintaince().isEnable() && player.hasPermission(config.getMaintaince().getKick().getPermission()) == false) {
			player.disconnect(config.getMaintaince().getKick().getMessage());
			return;
		}
		PlayerCache.INSTANCE.updateLocal(event.getPlayer());
	}
	
	@EventHandler
	public void onPing(ProxyPingEvent event) {
		JsonConfig config = JsonConfig.INSTANCE;
		ServerPing ping = event.getResponse();
		
		ping.setPlayers(config.getPlayerCounter().getPlayers(ping.getPlayers().getOnline()));
		
		if(config.getMaintaince().isEnable()) ping.setVersion(config.getMaintaince().getVersion().getProtocol());
		else ping.setVersion(config.getVersion().getProtocol());
		
		
		PlayerCache playerCache = PlayerCache.INSTANCE;
		UUID uuid = playerCache.getUUIDfromAddress(event.getConnection().getAddress().getAddress());
		if(uuid == null) {
			ping.setDescriptionComponent(config.getMotd().getMessage(null, null)[0]);
			return;
		}
		Cache cache = playerCache.getCache(uuid);
		ping.setDescriptionComponent(config.getMotd().getMessage(uuid, cache.getName())[0]);
		
		try {
			URL url = new URL(config.getPictureMode().getMode().getURL().replace("%player_uuid%", uuid.toString()));
			BufferedImage Image = ImageIO.read(url);
	        ping.setFavicon(Favicon.create(Image));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
