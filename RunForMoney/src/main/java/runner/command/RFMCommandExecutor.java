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

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// check if it's a valid command
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Not enough arguments!");
			return false;
		}

		if (args[0].equalsIgnoreCase("help")) {
			if (isValidArgNumber(args, 1)) {
				sender.sendMessage(ChatColor.AQUA + "/rfm wait - �}�йC���ۦ����a");
				sender.sendMessage(ChatColor.AQUA + "/rfm settime 600 - �]�w�C���ɶ��A��쬰��A600�Y��10����");
				sender.sendMessage(ChatColor.AQUA + "/rfm hunter <id> - �]�w���a���y�H");
				sender.sendMessage(ChatColor.AQUA + "/rfm status - �[�ݥثe�C�����A(�u���A�ݱo��)");
				sender.sendMessage(ChatColor.AQUA + "/rfm broadcast - �s���ثe�C�����A");
				sender.sendMessage(ChatColor.AQUA + "/rfm start - �C���}�l");
				sender.sendMessage(ChatColor.AQUA + "/rfm stop - �C���j���");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("runner")) {
			if (isValidArgNumber(args, 2)) {
				String playerName = args[1];
				Player player = (Bukkit.getServer().getPlayer(playerName));
				if (player == null) {
					sender.sendMessage(ChatColor.RED + playerName + " �ثe���b�u�W!");
					return false;
				}

				if (gameController.getRunner(player) != null) {
					sender.sendMessage(ChatColor.RED + playerName + " �w�g�b�����!");
					return false;
				}
				// add runner to game controller
				gameController.addRunner(player);
				sender.sendMessage(ChatColor.YELLOW + playerName + " �[�J�k�`�̦��\!");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("hunter")) {
			if (isValidArgNumber(args, 2)) {
				String playerName = args[1];
				Player player = (Bukkit.getServer().getPlayer(playerName));
				if (player == null) {
					sender.sendMessage(ChatColor.RED + playerName + " �ثe���b�u�W!");
					return false;
				}
				if (gameController.getHunter(player) != null) {
					sender.sendMessage(ChatColor.RED + playerName + " �w�g�b�y�H�����!");
					return false;
				}
				// add runner to game controller
				gameController.addHunter(player);
				sender.sendMessage(ChatColor.YELLOW + playerName + " �[�J�y�H���\!");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("start")) {
			if (isValidArgNumber(args, 1)) {
				if (gameController.getGameStatus() == GameStatus.Running) {
					sender.sendMessage(ChatColor.YELLOW + "�C�����b�i�椤�A�����s�}�l�C���Х���J /rfm stop ����C��!");
				} else if (gameController.getGameStatus() == GameStatus.Standy) {
					sender.sendMessage(ChatColor.YELLOW + "�C���|���إߡA�Х���J /rfm wait �ө��󪱮a!");
				} else {
					if (gameController.getRunnerList().size() == 0) {
						sender.sendMessage(ChatColor.YELLOW + "�|�L�k�`�̡A�Х���J /rfm wait �ө��󪱮a!");
					} else if (gameController.getHunterList().size() == 0) {
						sender.sendMessage(ChatColor.YELLOW + "�|�L�y�H�A�Х���J /rfm hunter id �Ӥ�ʳ]�w�y�H!");
					} else {
						gameController.start();
					}

				}

				return true;
			}
		} else if (args[0].equalsIgnoreCase("stop")) {
			if (isValidArgNumber(args, 1)) {

				if (gameController.getGameStatus() == GameStatus.Standy) {
					sender.sendMessage(ChatColor.YELLOW + "�S���C�����b�i�椤!");
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
				sender.sendMessage(ChatColor.YELLOW + "�C���w���m!");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("settime")) {
			if (isValidArgNumber(args, 2)) {
				if (gameController.getGameStatus() == GameStatus.Running) {
					sender.sendMessage(ChatColor.YELLOW + "�C�����L�k���ɶ��A�Х��j���C��!");
				} else {
					gameController.setTime(Integer.parseInt(args[1]));
					sender.sendMessage(ChatColor.YELLOW + "�ɶ��w�]�w!");
				}

				return true;
			}

		} else if (args[0].equalsIgnoreCase("wait")) {
			if (isValidArgNumber(args, 1)) {
				if (gameController.getGameStatus() != GameStatus.Standy) {
					sender.sendMessage(ChatColor.YELLOW + "�C�����b�i��Τw�b���ݤ�!");
				} else {
					gameController.reset();
					gameController.setGameStatus(GameStatus.Waiting);

					ChatUtil.broadcast(ChatColor.YELLOW + "�����k�����Y�N�}�l�A���ѥ[�����a�п�J/join�[�J�C��!");
					ChatUtil.sendToSender(sender, ChatColor.AQUA + "���a�}�l�[�J��A�п�J/rfm status���[�ݰk�`�̪��a\n�ϥ�/rfm hunter id �Ӥ�ʬD���y�H");
				}

				return true;
			}

		} else if (args[0].equalsIgnoreCase("test")) {
			/*
			 * Player player = (Bukkit.getServer().getPlayer("kalijason"));
			 * gameController.reset();
			 * gameController.setGameStatus(GameStatus.Waiting);
			 * gameController.addHunter(player);
			 * gameController.addRunner(player); gameController.start();
			 */
			return true;
		}

		// invalid
		return false;

	}
}
