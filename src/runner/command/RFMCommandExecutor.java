package runner.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import runner.game.GameController;
import runner.util.ChatUtil;

public class RFMCommandExecutor implements CommandExecutor {

	private GameController gameController;

	public RFMCommandExecutor(GameController gameController) {
		this.gameController = gameController;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		// check if it's a valid command
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Not enough arguments!");
			return false;
		}

		if (args[0].equalsIgnoreCase("addrunner")) {
			if (isValidArgNumber(args, 2)) {
				Player other = (Bukkit.getServer().getPlayer(args[1]));
				if (other == null) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " is not online!");
					return false;
				}
				// add runner to game controller
				gameController.addRunner(other);
				return true;
			}
		} else if (args[0].equalsIgnoreCase("addhunter")) {
			if (isValidArgNumber(args, 2)) {
				Player other = (Bukkit.getServer().getPlayer(args[1]));
				if (other == null) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " is not online!");
					return false;
				}
				// add runner to game controller
				gameController.addHunter(other);
				return true;
			}
		} else if (args[0].equalsIgnoreCase("start")) {
			if (isValidArgNumber(args, 1)) {
				gameController.start();
				return true;
			}
		} else if (args[0].equalsIgnoreCase("quest")) {
			if (isValidArgNumber(args, 1)) {
				gameController.quest();
				return true;
			}
		} else if (args[0].equalsIgnoreCase("stop")) {
			if (isValidArgNumber(args, 1)) {
				gameController.gameover();
				return true;
			}
		} else if (args[0].equalsIgnoreCase("status")) {
			if (isValidArgNumber(args, 1)) {
				// ChatUtil.broadcastMultiMessage(gameController.getStatus());
				ChatUtil.sendMultiMessage(sender, gameController.getStatus());
				return true;
			}

		} else if (args[0].equalsIgnoreCase("broadcast")) {
			if (isValidArgNumber(args, 1)) {
				ChatUtil.broadcastMultiMessage(gameController.getStatus());
				return true;
			}
		} else if (args[0].equalsIgnoreCase("reset")) {
			if (isValidArgNumber(args, 1)) {
				gameController.reset();
				return true;
			}
		} else if (args[0].equalsIgnoreCase("settime")) {
			if (isValidArgNumber(args, 2)) {
				gameController.setTime(Integer.parseInt(args[1]));
				return true;
			}
		}

		// invalid
		return false;

	}

	private boolean isValidArgNumber(String args[], int number) {
		if (args.length == number) {
			return true;
		}
		return false;

	}
}
