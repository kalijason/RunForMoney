package runner.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.onarandombox.MultiversePortals.MultiversePortals;

import runner.Constants;
import runner.game.GameController;
import runner.util.ChatUtil;

public class RFMCommandExecutor implements CommandExecutor {

	private GameController gameController;

	public RFMCommandExecutor(GameController gameController) {
		this.gameController = gameController;
	}

	private boolean isValidArgNumber(String args[], int number) {
		if (args.length == number) {
			return true;
		}
		return false;

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
				String playerName = args[1];
				Player player = (Bukkit.getServer().getPlayer(playerName));
				if (player == null) {
					sender.sendMessage(ChatColor.RED + playerName + " 目前不在線上!");
					return false;
				}

				if (gameController.getRunner(player) != null) {
					sender.sendMessage(ChatColor.RED + playerName + " 已經在隊伍裡!");
					return false;
				}
				// add runner to game controller
				gameController.addRunner(player);
				sender.sendMessage(ChatColor.YELLOW + playerName + " 加入成功!");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("addrunnerall")) {
			if (isValidArgNumber(args, 1)) {
				World world = sender.getServer().getWorld(
						Constants.ARENA_WORLDNAME);
				List<Player> playerList = world.getPlayers();

				for (Player player : playerList) {
					// add runner to game controller
					gameController.addRunner(player);
				}

				sender.sendMessage(ChatColor.YELLOW + " 加入了 "
						+ playerList.size() + " 個玩家!");

				return true;
			}
		} else if (args[0].equalsIgnoreCase("addhunter")) {
			if (isValidArgNumber(args, 2)) {
				String playerName = args[1];
				Player player = (Bukkit.getServer().getPlayer(playerName));
				if (player == null) {
					sender.sendMessage(ChatColor.RED + playerName + " 目前不在線上!");
					return false;
				}
				if (gameController.getHunter(player) != null) {
					sender.sendMessage(ChatColor.RED + playerName + " 已經在隊伍裡!");
					return false;
				}
				// add runner to game controller
				gameController.addHunter(player);
				sender.sendMessage(ChatColor.YELLOW + playerName + " 加入成功!");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("start")) {
			if (isValidArgNumber(args, 1)) {
				if (gameController.isStarted()) {
					sender.sendMessage(ChatColor.YELLOW
							+ "遊戲正在進行中，欲重新開始遊戲請先輸入 /stop 停止遊戲!");
				} else {
					gameController.start();
				}

				return true;
			}
		} else if (args[0].equalsIgnoreCase("quest")) {
			if (isValidArgNumber(args, 1)) {
				gameController.quest();
				return true;
			}
		} else if (args[0].equalsIgnoreCase("stop")) {
			if (isValidArgNumber(args, 1)) {

				if (!gameController.isStarted()) {
					sender.sendMessage(ChatColor.YELLOW + "沒有遊戲正在進行中!");
				} else {
					gameController.stop();
				}

				return true;
			}
		} else if (args[0].equalsIgnoreCase("status")) {
			if (isValidArgNumber(args, 1)) {
				ChatUtil.sendToSender(sender, gameController.getStatus());
				return true;
			}

		} else if (args[0].equalsIgnoreCase("broadcast")) {
			if (isValidArgNumber(args, 1)) {
				ChatUtil.broadcast(gameController.getStatus());
				return true;
			}
		} else if (args[0].equalsIgnoreCase("reset")) {
			if (isValidArgNumber(args, 1)) {
				gameController.reset();
				sender.sendMessage(ChatColor.YELLOW + "遊戲已重置!");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("settime")) {
			if (isValidArgNumber(args, 2)) {
				gameController.setTime(Integer.parseInt(args[1]));
				sender.sendMessage(ChatColor.YELLOW + "時間已設定!");
				return true;
			}

		}

		// invalid
		return false;

	}
}
