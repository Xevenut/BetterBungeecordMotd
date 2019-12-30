package de.Kurfat.Java.Minecraft.BB.Motd.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.gson.annotations.Expose;

import de.Kurfat.Java.Minecraft.BB.Motd.AnsiColor;
import de.Kurfat.Java.Minecraft.BB.Motd.BetterBungeecordMotd;
import de.Kurfat.Java.Minecraft.BB.Motd.JsonFileUtil;

public class Config {

	public static final JsonFileUtil<Config> JSON_FILE_UTIL = new JsonFileUtil<Config>(Config.class);
	private static final File FILE = new File(BetterBungeecordMotd.PLUGIN_DIR.getPath() + "/config.json");
	public static Config load() {
		try {
			Config config = JSON_FILE_UTIL.load(FILE);
			System.out.println(config.getPrefix() + AnsiColor.RESET + " " + AnsiColor.WHITE + "Config was loaded" + AnsiColor.RESET);
			return config;
		} catch (FileNotFoundException e) {
			BetterBungeecordMotd.printMessage("Config not found. Create default");
			return new Config();
		} catch (IOException e) {
			BetterBungeecordMotd.printMessage("Can't load! Please check this file: config.json");
			System.exit(-1);
			return null;
		}
	}
	
	@Expose
	private String prefix = AnsiColor.RED + "[" + AnsiColor.WHITE + "Motd" + AnsiColor.RED + "]" + AnsiColor.RESET;
	@Expose
	private Maintaince maintaince = new Maintaince();
	@Expose
	private PlayerCounter playercounter = new PlayerCounter();
	@Expose
	private PictureMode picturemode = new PictureMode();
	@Expose
	private Motd motd = new Motd();
	@Expose
	private Version version = new Version(575, "&aKurfat 1.15.1");
	
	public Config() {}
	
	public String getPrefix() {
		return prefix;
	}
	public Maintaince getMaintaince() {
		return maintaince;
	}
	public PlayerCounter getPlayerCounter() {
		return playercounter;
	}
	public PictureMode getPictureMode() {
		return picturemode;
	}
	public Motd getMotd() {
		return motd;
	}
	public Version getVersion() {
		return version;
	}
	
	public void save() {
		try {
			JSON_FILE_UTIL.save(this, FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
