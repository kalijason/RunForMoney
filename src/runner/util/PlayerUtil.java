package runner.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtil {

	static public void giveItem(Player player, Material type, int number) {
		ItemStack item = new ItemStack(type, number);
		player.getInventory().addItem(item);
		player.sendMessage(ChatColor.AQUA + "你得到物品: " + type.name());
	}

}
