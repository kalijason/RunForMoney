package runner.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import runner.game.GameController;

public class QuitJoinEvent implements Listener {

	GameController gameController;

	public QuitJoinEvent(GameController gameController) {
		this.gameController = gameController;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (player != null) {
			gameController.checkPlayerQuit(player);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (player != null) {
			gameController.checkPlayerQuit(player);
		}
	}
}
