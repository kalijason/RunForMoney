package runner;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import runner.command.RFMCommandExecutor;
import runner.game.GameController;

public class RunForMoney extends JavaPlugin {

	private RFMCommandExecutor myExecutor;
	private GameController gameController;

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

	}

}
