package de.Kurfat.Java.Minecraft.BB.Motd;

import net.md_5.bungee.api.plugin.Command;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.Kurfat.Java.Minecraft.BB.Motd.Cache.PlayerCache;
import de.Kurfat.Java.Minecraft.BB.Motd.Config.Config;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class BetterBungeecordMotd extends Plugin {
	
	public static final Gson GSON = new GsonBuilder()
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.excludeFieldsWithoutExposeAnnotation()
			.registerTypeAdapter(Favicon.class, Favicon.getFaviconTypeAdapter())
			.create();
	
	public static BetterBungeecordMotd PLUGIN;
	public static File PLUGIN_DIR;
	public static Command COMMAND;
	public static PlayerCache PLAYERCACHE;
	public static Config CONFIG;
	
	public void onEnable() {
		PLUGIN = this;
		PLUGIN_DIR = new File("plugins/BB/Motd/");
		COMMAND = new Command_Maintaince();
		CONFIG = Config.load();
		PLAYERCACHE = new PlayerCache();
		
		PluginManager manager = ProxyServer.getInstance().getPluginManager();
		manager.registerListener(this, PLAYERCACHE);
		manager.registerCommand(this, COMMAND);
	}
	@Override
	public void onDisable() {
		PluginManager manager = ProxyServer.getInstance().getPluginManager();
		manager.unregisterCommand(COMMAND);
		manager.unregisterListener(PLAYERCACHE);
		
		CONFIG.save();
		PLAYERCACHE.save();
	}
	public static void printMessage(String message) {
		System.out.println(CONFIG.getPrefix() + AnsiColor.RESET + " " + AnsiColor.WHITE + message + AnsiColor.RESET);
	}
	
}
