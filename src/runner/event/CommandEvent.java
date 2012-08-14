package runner.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import runner.game.GameController;
import runner.game.RFMPlayer;

public class CommandEvent implements Listener {

	GameController gameController;

	public CommandEvent(GameController gameController) {
		this.gameController = gameController;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onCommand(PlayerCommandPreprocessEvent event) {

		// check if it's a player in game
		Player player = event.getPlayer();
		if (player != null
				&& player instanceof Player
				&& ((gameController.getRunner(player) != null) || gameController
						.getHunter(player) != null)

				&& !player.hasPermission("runformoney.admin")
				&& event.getMessage().toLowerCase().indexOf("/login") == -1) {
			RFMPlayer rfmPlayer = gameController.getRFMPlayer(player);
			if (rfmPlayer != null ) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.BLUE + "遊戲中不可以使用指令");
			}

		}

	}
}
