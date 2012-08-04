package runner.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatEvent implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onChat(PlayerChatEvent event) {
		System.out.println(event.getFormat());
		System.out.println(event.getMessage());
		System.out.println(event.getPlayer().getName());

	}
}
