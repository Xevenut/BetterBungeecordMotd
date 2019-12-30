package de.Kurfat.Java.Minecraft.BB.Motd.Config;

import com.google.gson.annotations.Expose;

import de.Kurfat.Java.Minecraft.BB.Motd.BetterBungeecordMotd;

public class PictureMode {
	
	@Expose
	private Mode mode = Mode.AVATAR;
	@Expose
	private String customAddress = "https://<ip>/<path>.png";
	
	public PictureMode() {}
	
	public Mode getMode() {
		return mode;
	}
	public String getCustomAddress() {
		return customAddress;
	}

	public static enum Mode {
		AVATAR("https://minotar.net/avatar/%player_uuid%/64.png"),
		BUST("https://minotar.net/bust/%player_uuid%/64.png"),
		CUBE("https://minotar.net/cube/%player_uuid%/64.png"),
		HELM("https://minotar.net/helm/%player_uuid%/64.png"),
		CUSTOM(null);
		
		private String url;
		
		private Mode(String url) {
			this.url = url;
		}
		
		public String getURL() {
			if(this == CUSTOM) return BetterBungeecordMotd.CONFIG.getPictureMode().getCustomAddress();
			return url;
		}
	}
}
