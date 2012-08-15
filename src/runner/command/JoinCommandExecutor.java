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
			if (gameController.getGameStatus() == GameStatus.Standy) {
				ChatUtil.sendToSender(sender, ChatColor.RED
						+ "目前沒有遊戲啟動!請等待主持人開遊戲!");
			} else if (gameController.getGameStatus() == GameStatus.Running) {
				ChatUtil.sendToSender(sender, ChatColor.RED
						+ "目前遊戲正在進行中!請等待下一場吧!");
			} else {
				if (gameController.join(player)) {
					ChatUtil.sendToSender(sender, ChatColor.AQUA
							+ "加入成功!請等待遊戲開始!請獵人穿帶上全套鑽石裝備，請逃亡者都脫掉身上的裝備!");
				} else {
					ChatUtil.sendToSender(sender, ChatColor.RED + "你已在遊戲中!");
				}
			}

			return true;
		}

	}
}
