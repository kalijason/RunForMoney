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
			sender.sendMessage("�Цb�C������J�����O!");
			return false;
		} else {
			if (gameController.getGameStatus() == GameStatus.Standy) {
				ChatUtil.sendToSender(sender, ChatColor.RED
						+ "�ثe�S���C���Ұ�!�е��ݥD���H�}�C��!");
			} else if (gameController.getGameStatus() == GameStatus.Running) {
				ChatUtil.sendToSender(sender, ChatColor.RED
						+ "�ثe�C�����b�i�椤!�е��ݤU�@���a!");
			} else {
				if (gameController.join(player)) {
					ChatUtil.sendToSender(sender, ChatColor.AQUA
							+ "�[�J���\!�е��ݹC���}�l!���y�H��a�W���M�p�۸˳ơA�аk�`�̳��汼���W���˳�!");
				} else {
					ChatUtil.sendToSender(sender, ChatColor.RED + "�A�w�b�C����!");
				}
			}

			return true;
		}

	}
}
