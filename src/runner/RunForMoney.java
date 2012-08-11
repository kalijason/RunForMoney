package runner;

import org.bukkit.plugin.java.JavaPlugin;

import runner.command.JoinCommandExecutor;
import runner.command.RFMCommandExecutor;
import runner.game.GameController;
import runner.game.Teleporter;

public class RunForMoney extends JavaPlugin {

	private RFMCommandExecutor myExecutor;
	private JoinCommandExecutor joinExecutor;
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

		// main command executor for RFM
		myExecutor = new RFMCommandExecutor(gameController);
		getCommand("rfm").setExecutor(myExecutor);

		// join command
		joinExecutor = new JoinCommandExecutor(gameController);
		getCommand("join").setExecutor(joinExecutor);

		teleporter = new Teleporter(this);

	}
}
