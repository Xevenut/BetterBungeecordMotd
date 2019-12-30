package de.Kurfat.Java.Minecraft.BB.Motd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileUtil<T> {

	private Class<T> clazz;

	public JsonFileUtil(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public T load(File file) throws FileNotFoundException, IOException {
		FileReader fR = new FileReader(file);
		BufferedReader reader = new BufferedReader(fR);
		String json = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			json += temp;
		}
		T object = BetterBungeecordMotd.GSON.fromJson(json, clazz);
		reader.close();
		fR.close();
		return object;
	}
	
	public void save(T object, File file) throws IOException {
		String json = BetterBungeecordMotd.GSON.toJson(object);
		if(file.exists()) file.delete();
		File directory = file.getParentFile();
		if(directory.exists() == false) directory.mkdirs();
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write(json);
		writer.flush();
		writer.close();
	}
	
}
