package runner;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiversePortals.MultiversePortals;

import runner.command.RFMCommandExecutor;
import runner.game.GameController;

public class RunForMoney extends JavaPlugin {

	private RFMCommandExecutor myExecutor;
	private GameController gameController;
	private MultiversePortals multiversePortals;

	private void checkForMultiVerse() {

		if (this.getServer().getPluginManager().getPlugin("Multiverse-Core") != null) {
			this.multiversePortals = (MultiversePortals) this.getServer()
					.getPluginManager().getPlugin("Multiverse-Portals");
			System.out.println("MVC Founded");
		} else {
			System.out.println("MVC Not Founded");
		}
	}

	public MultiversePortals getMultiversePortals() {
		return multiversePortals;
	}

	@Override
	public void onDisable() {

		System.out.println("Disable");
	}

	@Override
	public void onEnable() {
		System.out.println("Enable");

		gameController = new GameController(this);

		PluginManager pm = getServer().getPluginManager();

		// Chat Event
		// pm.registerEvents(new ChatEvent(), this);

		// main command excutor for RFM
		myExecutor = new RFMCommandExecutor(gameController);

		getCommand("rfm").setExecutor(myExecutor);

		checkForMultiVerse();
	}
}
