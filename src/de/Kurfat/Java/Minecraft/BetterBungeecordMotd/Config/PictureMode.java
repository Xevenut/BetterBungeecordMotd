package de.Kurfat.Java.Minecraft.BetterBungeecordMotd.Config;

public class PictureMode {
	
	private Mode mode = Mode.AVATAR;
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
			if(this == CUSTOM) return JsonConfig.INSTANCE.getPictureMode().getCustomAddress();
			return url;
		}
	}
}
