package de.Kurfat.Java.Minecraft.BetterBungeecordMotd.Config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.Kurfat.Java.Minecraft.BetterBungeecordMotd.AnsiColor;

public class JsonConfig {

	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	public static final File FILE = new File("plugins/BetterBungeecordMotd/config.json");
	public static JsonConfig INSTANCE;
	public static boolean load() {
		try {
			FileReader reader = new FileReader(FILE);
			INSTANCE = GSON.fromJson(reader, JsonConfig.class);
			System.out.println(INSTANCE.getPrefix() + " " + AnsiColor.GREEN + "The file was successfully loaded:" + AnsiColor.RESET + " " + AnsiColor.YELLOW + "config.json" + AnsiColor.RESET);
			return true;
		} catch (FileNotFoundException e) {
			INSTANCE = new JsonConfig();
			System.out.println(INSTANCE.getPrefix() + " " + AnsiColor.YELLOW + "Default file was created:" + AnsiColor.RESET + " " + AnsiColor.YELLOW + "config.json" + AnsiColor.RESET);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(INSTANCE.getPrefix() + " " + AnsiColor.RED + "The file has an error. Please check the file before starting the server again: config.json" + AnsiColor.RESET + " " + AnsiColor.YELLOW + "config.json" + AnsiColor.RESET);
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
			System.out.println(INSTANCE.getPrefix() + " " + AnsiColor.GREEN + "The file has been saved:" + AnsiColor.RESET + " " + AnsiColor.YELLOW + "config.json" + AnsiColor.RESET);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(INSTANCE.getPrefix() + " " + AnsiColor.RED + "The file has an save error. Please send the error code to the developer: config.json" + AnsiColor.RESET);
			return false;
		}
	}
	
	private String prefix = AnsiColor.RED + "[" + AnsiColor.WHITE + "Motd" + AnsiColor.RED + "]" + AnsiColor.RESET;
	private Maintaince maintaince = new Maintaince();
	private PlayerCounter playercounter = new PlayerCounter();
	private PictureMode picturemode = new PictureMode();
	private Motd motd = new Motd();
	private Version version = new Version(498, "&aKurfat 1.14");
	
	public JsonConfig() {}
	
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
}
