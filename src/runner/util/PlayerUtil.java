package runner.util;

import org.bukkit.Bukkit;

public class PlayerUtil {
	static public void broadcastMultiMessage(String message) {
		if (message != null) {
			String[] s = message.split("\n");
			for (String m : s) {
				Bukkit.broadcastMessage("[°k¨«¤¤] " + m);
			}
		}
	}

}
