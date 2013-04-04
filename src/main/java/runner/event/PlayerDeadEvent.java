package runner.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import runner.game.GameController;
import runner.game.RFMPlayer;

public class PlayerDeadEvent implements Listener {

	GameController gameController;

	public PlayerDeadEvent(GameController gameController) {
		this.gameController = gameController;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDeath(PlayerDeathEvent event) {

		final Player player = event.getEntity();

		// check if it's a player in game
		if (player != null && player instanceof Player && ((gameController.getRunner(player) != null) || gameController.getHunter(player) != null)) {

			// keep exp
			event.setKeepLevel(true);

			// keep armor
			final ItemStack[] armor = player.getInventory().getArmorContents();

			Bukkit.getScheduler().scheduleSyncDelayedTask(gameController.getRunForMoney(), new Runnable() {

				public void run() {
					player.getInventory().setArmorContents(armor);
				}

			});

			for (ItemStack is : armor) {
				event.getDrops().remove(is);
			}

			// keep Items
			final ItemStack[] inventory = player.getInventory().getContents();
			for (int i = 0; i < inventory.length; i++) {
				ItemStack is = inventory[i];

				if (is != null)
					event.getDrops().remove(is);
				else
					inventory[i] = null;
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(gameController.getRunForMoney(), new Runnable() {

				public void run() {
					player.getInventory().setContents(inventory);

					// check if player should be dead
					gameController.checkPlayerDeath(player);

				}

			});

		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onRespawn(PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		// check if it's a player in game and death, teleport back to observer

		if (player != null && player instanceof Player && ((gameController.getRunner(player) != null) || gameController.getHunter(player) != null)) {
			RFMPlayer rfmPlayer = gameController.getRFMPlayer(player);
			if (rfmPlayer != null && !rfmPlayer.isAlive()) {

				Bukkit.getScheduler().scheduleSyncDelayedTask(gameController.getRunForMoney(), new Runnable() {

					public void run() {
						gameController.getRunForMoney().getTeleporter().moveToPortal(player, "RFMobserver");

					}

				});

			}
		}

	}
}
