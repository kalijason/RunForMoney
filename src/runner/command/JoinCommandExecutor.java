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
			sender.sendMessage("請在遊戲內輸入此指令!");
			return false;
		} else {
			if (gameController.getGameStatus() != GameStatus.Waiting) {
				ChatUtil.sendToSender(sender, ChatColor.RED + "目前沒有遊戲正在等待玩家!");
			} else if (gameController.getGameStatus() != GameStatus.Waiting) {
				ChatUtil.sendToSender(sender, ChatColor.RED + "目前沒有遊戲正在等待玩家!");
			} else {
				if (gameController.join(player)) {
					ChatUtil.sendToSender(sender, ChatColor.AQUA + "加入成功!請等待遊戲開始!");
				} else {
					ChatUtil.sendToSender(sender, ChatColor.RED + "你已在遊戲中!");
				}
			}

			return true;
		}

	}
}
