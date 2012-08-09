package runner.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import runner.game.GameController;
import runner.game.PlayerType;
import runner.game.RFMPlayer;
import runner.util.ChatUtil;

public class QuitEvent implements Listener {

	GameController gameController;

	public QuitEvent(GameController gameController) {
		this.gameController = gameController;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (player != null) {
			gameController.checkPlayerQuit(player);
		}
	}
}
