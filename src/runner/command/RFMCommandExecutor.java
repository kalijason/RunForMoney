package runner.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import runner.game.GameController;
import runner.game.GameController.GameStatus;
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

		if (args[0].equalsIgnoreCase("runner")) {
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
				sender.sendMessage(ChatColor.YELLOW + playerName + " 加入逃亡者成功!");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("hunter")) {
			if (isValidArgNumber(args, 2)) {
				String playerName = args[1];
				Player player = (Bukkit.getServer().getPlayer(playerName));
				if (player == null) {
					sender.sendMessage(ChatColor.RED + playerName + " 目前不在線上!");
					return false;
				}
				if (gameController.getHunter(player) != null) {
					sender.sendMessage(ChatColor.RED + playerName + " 已經在獵人隊伍裡!");
					return false;
				}
				// add runner to game controller
				gameController.addHunter(player);
				sender.sendMessage(ChatColor.YELLOW + playerName + " 加入獵人成功!");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("start")) {
			if (isValidArgNumber(args, 1)) {
				if (gameController.getGameStatus() == GameStatus.Running) {
					sender.sendMessage(ChatColor.YELLOW
							+ "遊戲正在進行中，欲重新開始遊戲請先輸入 /rfm stop 停止遊戲!");
				} else if (gameController.getGameStatus() == GameStatus.Standy) {
					sender.sendMessage(ChatColor.YELLOW
							+ "遊戲尚未建立，請先輸入 /rfm wait 來招攬玩家!");
				} else {
					if (gameController.getRunnerList().size() == 0) {
						sender.sendMessage(ChatColor.YELLOW
								+ "尚無逃亡者，請先輸入 /rfm wait 來招攬玩家!");
					} else if (gameController.getHunterList().size() == 0) {
						sender.sendMessage(ChatColor.YELLOW
								+ "尚無獵人，請先輸入 /rfm hunter id 來手動設定獵人!");
					} else {
						gameController.start();
					}

				}

				return true;
			}
		} else if (args[0].equalsIgnoreCase("stop")) {
			if (isValidArgNumber(args, 1)) {

				if (gameController.getGameStatus() == GameStatus.Standy) {
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

		} else if (args[0].equalsIgnoreCase("wait")) {
			if (isValidArgNumber(args, 1)) {
				if (gameController.getGameStatus() != GameStatus.Standy) {
					sender.sendMessage(ChatColor.YELLOW + "遊戲正在進行或已在等待中!");
				} else {
					gameController.reset();
					gameController.setGameStatus(GameStatus.Waiting);
					ChatUtil.broadcast(ChatColor.YELLOW
							+ "全員逃走中即將開始，欲參加的玩家請輸入/join加入遊戲!");
					ChatUtil.sendToSender(
							sender,
							ChatColor.AQUA
									+ "當玩家開始加入後，請輸入/rfm status來觀看逃亡者玩家\n使用/rfm hunter id 來手動挑選獵人");
				}

				return true;
			}

		} else if (args[0].equalsIgnoreCase("test")) {
			return true;
		}

		// invalid
		return false;

	}
}
