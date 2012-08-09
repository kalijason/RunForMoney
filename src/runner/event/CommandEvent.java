package runner.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import runner.game.GameController;

public class CommandEvent implements Listener {

	GameController gameController;

	public CommandEvent(GameController gameController) {
		this.gameController = gameController;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onCommand(PlayerCommandPreprocessEvent event) {

		Player player = event.getPlayer();
		if (player != null
				&& player instanceof Player
				&& ((gameController.getRunner(player) != null) || gameController
						.getHunter(player) != null)) {
			if (!player.hasPermission("runformoney.admin")) {
				if (event.getMessage().toLowerCase().indexOf("/login") != -1) {
					event.setCancelled(true);
					player.sendMessage(ChatColor.BLUE + "遊戲中不可以使用指令");
				}

			}

		}

	}
}
