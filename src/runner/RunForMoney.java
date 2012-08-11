package runner;

import org.bukkit.plugin.java.JavaPlugin;

import runner.command.RFMCommandExecutor;
import runner.game.GameController;
import runner.game.Teleporter;

public class RunForMoney extends JavaPlugin {

	private RFMCommandExecutor myExecutor;
	private GameController gameController;

	private Teleporter teleporter;

	public Teleporter getTeleporter() {
		return teleporter;
	}

	@Override
	public void onDisable() {

		System.out.println("Disable");
	}

	@Override
	public void onEnable() {
		System.out.println("Enable");

		gameController = new GameController(this);

		// main command excutor for RFM
		myExecutor = new RFMCommandExecutor(gameController);

		getCommand("rfm").setExecutor(myExecutor);

		teleporter = new Teleporter(this);

	}
}
