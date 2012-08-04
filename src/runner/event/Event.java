package runner.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.block.BlockBreakEvent;

public class Event implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(final PlayerJoinEvent event) {

	}

	@EventHandler
	public void normalLogin(PlayerLoginEvent event) {
		event.getPlayer()
				.getServer()
				.broadcastMessage(
						"player " + event.getPlayer().getDisplayName()
								+ " just login.");
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		String playername = event.getPlayer().getDisplayName();
		String blockname = event.getBlock().getType().name();
		event.getPlayer()
				.getServer()
				.broadcastMessage(
						"player " + playername + " just break " + blockname);
	}
}
