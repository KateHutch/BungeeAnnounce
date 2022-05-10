package fr.royalpha.bungeeannounce.announcement.announce;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Royalpha
 */
public class AnnounceAction implements fr.royalpha.bungeeannounce.handler.AnnounceAction {

	@Override
	public void onAction(ProxiedPlayer player, BaseComponent[] message, Integer... optionalTitleArgs) {
		player.sendMessage(message);
		//player.sendMessage();
		//ComponentBuilder builder = new ComponentBuilder("");
		//player.sendMessage(new ComponentBuilder("rgb").color(ChatColor.of("#FF0000")).create());

	}

}
