package de.Kurfat.Java.Minecraft.BetterBungeecordMotd;

import de.Kurfat.Java.Minecraft.BetterBungeecordMotd.Config.JsonConfig;
import de.Kurfat.Java.Minecraft.BetterBungeecordMotd.Config.Maintaince;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Command_Maintaince extends Command {

	public Command_Maintaince() {
		super("maintaince");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		JsonConfig config = JsonConfig.INSTANCE;
		if(sender.hasPermission(config.getMaintaince().getCommandPermission()) == false) return;
		Maintaince maintaince = config.getMaintaince();
		maintaince.setEnable(!maintaince.isEnable());
		if(maintaince.isEnable()) for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) if(player.hasPermission(maintaince.getKick().getPermission()) == false) player.disconnect(maintaince.getKick().getMessage());
		sender.sendMessage(new ComponentBuilder("Maintaince set: " + maintaince.isEnable()).create());
	}

}
