package fr.royalpha.bungeeannounce.util;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import fr.royalpha.bungeeannounce.BungeeAnnouncePlugin;
import fr.royalpha.bungeeannounce.handler.Executor;
import fr.royalpha.bungeeannounce.manager.AnnouncementManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Royalpha
 */
public class BAUtils {

	public static String separator = "::";
	public static String splittedSeparator = ":";

	public static BaseComponent[] parseNew(String input) {
		input = input.replace("[ln]", "\n");
		input = input.replaceAll("&+?(?=[abcdef0123456789])", "§");
		return TextComponent.fromLegacyText(input);
	}

	public static String colorise(String input) {
		return input.replaceAll("&+?(?=[abcdef0123456789])", "§");
	}

	private final static int CENTER_PX = 154;

	public static String getCenteredMessage(String message) {
		if(message == null || message.equals("")) return "";
		message = ChatColor.translateAlternateColorCodes('&', message);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for(char c : message.toCharArray()){
			if(c == '§'){
				previousCode = true;
				continue;
			}else if(previousCode == true){
				previousCode = false;
				if(c == 'l' || c == 'L'){
					isBold = true;
					continue;
				}else isBold = false;
			}else{
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while(compensated < toCompensate){
			sb.append(" ");
			compensated += spaceLength;
		}
		return(sb.toString() + message);
	}
	private static Boolean isNecessaryToParse(String s) {
		for (Executor exec : Executor.values()) {
			if (s.contains(separator + exec.getString() + separator))
				return true;
		}
		return false;
	}

	public static String colorizz(String uncolorizedString) {

		return colorise(uncolorizedString);

	}

	public static String translatePlaceholders(String input, CommandSender sender, ProxiedPlayer receiver,
			ProxiedPlayer player) {
		String output = input;
		if (receiver != null) {
			output = output.replaceAll("%RECEIVER_NAME%", receiver.getName());
			output = output.replaceAll("%RECEIVER_DISPLAY_NAME%", receiver.getDisplayName());
			output = output.replaceAll("%RECEIVER_PING%", receiver.getPing() + "");
			output = output.replaceAll("%RECEIVER_UUID%", receiver.getUniqueId().toString());

			if (receiver.getServer() != null && receiver.getServer().getInfo() != null) {
				ServerInfo server = receiver.getServer().getInfo();
				output = output.replaceAll("%RECEIVER_SERVER_NAME%", server.getName());
				output = output.replaceAll("%RECEIVER_SERVER_MOTD%", server.getMotd());
				output = output.replaceAll("%RECEIVER_SERVER_ONLINE_PLAYERS%", server.getPlayers().size() + "");
			}
			
			if (player == null) {
				player = receiver;
			}
		} else {
			output = output.replaceAll("%RECEIVER_NAME%", "unknown");
			output = output.replaceAll("%RECEIVER_DISPLAY_NAME%", "unknown");
			output = output.replaceAll("%RECEIVER_PING%", "-1");
			output = output.replaceAll("%RECEIVER_UUID%", "unknown");
			
			output = output.replaceAll("%RECEIVER_SERVER_NAME%", "unknown");
			output = output.replaceAll("%RECEIVER_SERVER_MOTD%", "unknown");
			output = output.replaceAll("%RECEIVER_SERVER_ONLINE_PLAYERS%", "-1");
		}
		
		if (player != null) {
			output = output.replaceAll("%PLAYER_NAME%", player.getName());
			output = output.replaceAll("%PLAYER_DISPLAY_NAME%", player.getDisplayName());
			output = output.replaceAll("%PLAYER_PING%", player.getPing() + "");
			output = output.replaceAll("%PLAYER_UUID%", player.getUniqueId().toString());

			if (player.getServer() != null && player.getServer().getInfo() != null) {
				ServerInfo server = player.getServer().getInfo();
				output = output.replaceAll("%PLAYER_SERVER_NAME%", server.getName());
				output = output.replaceAll("%PLAYER_SERVER_MOTD%", server.getMotd());
				output = output.replaceAll("%PLAYER_SERVER_ONLINE_PLAYERS%", server.getPlayers().size() + "");
				output = output.replaceAll("%SERVER_NAME%", server.getName());
				output = output.replaceAll("%SERVER_MOTD%", server.getMotd());
				output = output.replaceAll("%SERVER_ONLINE_PLAYERS%", server.getPlayers().size() + "");
			}
		} else {
			output = output.replaceAll("%PLAYER_NAME%", "unknown");
			output = output.replaceAll("%PLAYER_DISPLAY_NAME%", "unknown");
			output = output.replaceAll("%PLAYER_PING%", "-1");
			output = output.replaceAll("%PLAYER_UUID%", "unknown");
			
			output = output.replaceAll("%PLAYER_SERVER_NAME%", "unknown");
			output = output.replaceAll("%PLAYER_SERVER_MOTD%", "unknown");
			output = output.replaceAll("%PLAYER_SERVER_ONLINE_PLAYERS%", "-1");
			output = output.replaceAll("%SERVER_NAME%", "unknown");
			output = output.replaceAll("%SERVER_MOTD%", "unknown");
			output = output.replaceAll("%SERVER_ONLINE_PLAYERS%", "-1");
		}
		
		if (sender != null) {
			output = output.replaceAll("%SENDER_NAME%", sender.getName());
			
			if (sender instanceof ProxiedPlayer) {
				ProxiedPlayer senderPlayer = (ProxiedPlayer) sender;
				
				output = output.replaceAll("%SENDER_DISPLAY_NAME%", senderPlayer.getDisplayName());
				output = output.replaceAll("%SENDER_PING%", senderPlayer.getPing() + "");
				output = output.replaceAll("%SENDER_UUID%", senderPlayer.getUniqueId().toString());

				if (senderPlayer.getServer() != null && senderPlayer.getServer().getInfo() != null) {
					ServerInfo server = senderPlayer.getServer().getInfo();
					output = output.replaceAll("%SENDER_SERVER_NAME%", server.getName());
					output = output.replaceAll("%SENDER_SERVER_MOTD%", server.getMotd());
					output = output.replaceAll("%SENDER_SERVER_ONLINE_PLAYERS%", server.getPlayers().size() + "");
				}
			} else {
				output = output.replaceAll("%SENDER_DISPLAY_NAME%", "unknown");
				output = output.replaceAll("%SENDER_PING%", "-1");
				output = output.replaceAll("%SENDER_UUID%", "unknown");
				
				output = output.replaceAll("%SENDER_SERVER_NAME%", "unknown");
				output = output.replaceAll("%SENDER_SERVER_MOTD%", "unknown");
				output = output.replaceAll("%SENDER_SERVER_ONLINE_PLAYERS%", "-1");
			}
		}
		
		output = output.replaceAll("%BUNGEE_ONLINE_PLAYERS%", BungeeAnnouncePlugin.getProxyServer().getOnlineCount() + "");
		return output;
	}

	public static Integer[] getOptionalTitleArgsFromConfig(AnnouncementManager announcement, String rawType) {
		Integer[] emptyOutput = {};
		if (announcement == AnnouncementManager.TITLE || announcement == AnnouncementManager.SUBTITLE) {
			String[] splittedRawType = rawType.split("_");
			if (splittedRawType.length >= 4) {
				Integer fadeIn, stay, fadeOut;
				try {
					fadeIn = Integer.parseInt(splittedRawType[1]) * 20;
					stay = Integer.parseInt(splittedRawType[2]) * 20;
					fadeOut = Integer.parseInt(splittedRawType[3]) * 20;
					Integer[] filledOutput = { fadeIn, stay, fadeOut };
					return filledOutput;
				} catch (NumberFormatException e) {
					return emptyOutput;
				}
			}
		}
		return emptyOutput;
	}
}
