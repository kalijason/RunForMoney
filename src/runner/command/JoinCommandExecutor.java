package runner.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import runner.game.GameController;

public class JoinCommandExecutor implements CommandExecutor {

	private GameController gameController;

	public JoinCommandExecutor(GameController gameController) {
		this.gameController = gameController;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		Player player = (Bukkit.getServer().getPlayer(sender.getName()));
		if (player == null) {
			sender.sendMessage("�Цb�C������J�����O!");
			return false;
		} else {
			boolean success = gameController.join(player);

			if (success) {
				sender.sendMessage("�[�J���\!");
			} else {
				sender.sendMessage("�A�w�b�C����!");
			}
			return true;
		}

	}
}
