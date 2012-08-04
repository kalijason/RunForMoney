package runner.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import runner.game.GameController;

public class AttackEvent implements Listener {

	GameController gameController;

	public AttackEvent(GameController gameController) {
		this.gameController = gameController;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onAttack(EntityDamageByEntityEvent event) {

		if (event.getDamager() instanceof Player
				&& event.getEntity() instanceof Player) {
			Player attacker = (Player) event.getDamager();
			Player defenser = (Player) event.getEntity();

			gameController.checkRunterKillsRunner(attacker, defenser);
		}
	}
}
