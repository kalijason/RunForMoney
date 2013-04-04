package runner.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import runner.game.GameController;
import runner.game.GameController.GameStatus;
import runner.util.ChatUtil;

public class InventoryEventManager implements Listener {

	GameController gameController;

	public InventoryEventManager(GameController gameController) {
		this.gameController = gameController;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onCommand(InventoryClickEvent event) {
		String playerName = event.getWhoClicked().getName();
		Player player = (Bukkit.getServer().getPlayer(playerName));
		if (player != null
				&& (gameController.getHunter(player) != null || gameController
						.getRunner(player) != null)
				&& gameController.getGameStatus() == GameStatus.Running
				&& event.getSlotType() == SlotType.ARMOR) {
			ChatUtil.sendToSender(player, ChatColor.RED + "遊戲中不可以更換裝備!!");
			event.setCancelled(true);
		}

	}
}
