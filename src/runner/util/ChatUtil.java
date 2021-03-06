package runner.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtil {
	static public void broadcast(String message) {
		if (message != null) {
			String[] s = message.split("\n");
			for (String m : s) {
				Bukkit.broadcastMessage("[逃走中] " + m);
			}
		}
	}

	static public String secToString(long secs) {
		StringBuffer sb = new StringBuffer();
		sb.append(ChatColor.YELLOW);
		long sec = secs % 60;
		long min = ((secs - sec) / 60);

		sb.append(min + " 分 " + sec + " 秒");
		sb.append(ChatColor.RESET);
		return sb.toString();
	}

	static public void sendToSender(CommandSender sender, String message) {
		if (message != null) {
			String[] s = message.split("\n");
			for (String m : s) {
				sender.sendMessage("[逃走中] " + m);
			}
		}
	}

	static public void sendToSender(Player player, String message) {
		if (message != null && player != null) {
			String[] s = message.split("\n");
			for (String m : s) {
				player.sendMessage("[逃走中] " + m);
			}
		}
	}

}
