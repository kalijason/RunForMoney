package runner.game;

import org.bukkit.entity.Player;

import runner.RunForMoney;

import com.onarandombox.MultiverseCore.api.SafeTTeleporter;
import com.onarandombox.MultiversePortals.MVPortal;
import com.onarandombox.MultiversePortals.MultiversePortals;

public class Teleporter {
	RunForMoney runforMoney;
	private MultiversePortals multiversePortals;

	public Teleporter(RunForMoney runforMoney) {
		checkForMultiVerse(runforMoney);
	}

	private void checkForMultiVerse(RunForMoney runforMoney) {
		if (runforMoney.getServer().getPluginManager()
				.getPlugin("Multiverse-Core") != null) {
			this.multiversePortals = (MultiversePortals) runforMoney
					.getServer().getPluginManager()
					.getPlugin("Multiverse-Portals");
			System.out.println("MVC Founded");
		} else {
			System.out.println("MVC Not Founded");
		}
	}

	/**
	 * after catching player, move him to the jail
	 * 
	 * @param player
	 */
	public void moveToPortal(Player player, String portalName) {
		try {
			MVPortal p = multiversePortals.getPortalManager().getPortal(
					portalName);
			SafeTTeleporter t = multiversePortals.getCore()
					.getSafeTTeleporter();
			t.safelyTeleport(player, player, p.getDestination());
		} catch (Exception ignore) {
			ignore.printStackTrace();
		}

	}
}
