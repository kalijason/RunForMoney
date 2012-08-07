package runner.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtil {
	static public void broadcastMultiMessage(String message) {
		if (message != null) {
			String[] s = message.split("\n");
			for (String m : s) {
				Bukkit.broadcastMessage("[�k����] " + m);
			}
		}
	}

	static public String secToString(long secs) {
		StringBuffer sb = new StringBuffer();
		sb.append(ChatColor.YELLOW);
		long sec = secs % 60;
		long min = ((secs - sec) / 60);

		sb.append(min + " �� " + sec + " ��");
		sb.append(ChatColor.RESET);
		return sb.toString();
	}

	static public void sendMultiMessage(CommandSender sender, String message) {
		if (message != null) {
			String[] s = message.split("\n");
			for (String m : s) {
				sender.sendMessage("[�k����] " + m);
			}
		}
	}

}
