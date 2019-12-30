package de.Kurfat.Java.Minecraft.BB.Motd;

import de.Kurfat.Java.Minecraft.BB.Motd.Config.Maintaince;
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
		Maintaince maintaince = BetterBungeecordMotd.CONFIG.getMaintaince();
		if(sender.hasPermission(maintaince.getCommandPermission()) == false) return;
		maintaince.setEnable(!maintaince.isEnable());
		if(maintaince.isEnable()) for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) if(player.hasPermission(maintaince.getKick().getPermission()) == false) player.disconnect(maintaince.getKick().getMessage());
		sender.sendMessage(new ComponentBuilder("Maintaince set: " + maintaince.isEnable()).create());
		BetterBungeecordMotd.CONFIG.save();
	}

}
