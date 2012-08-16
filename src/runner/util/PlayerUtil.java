package runner.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerUtil {

	static public void giveItem(Player player, Material type, int number) {
		ItemStack item = new ItemStack(type, number);
		player.getInventory().addItem(item);
		player.sendMessage(ChatColor.AQUA + "你得到物品: " + type.name());
	}

	static public void setAir(Player player) {
		PlayerInventory inv = player.getInventory();
		ItemStack[] diamondList = { new ItemStack(Material.AIR),
				new ItemStack(Material.AIR), new ItemStack(Material.AIR),
				new ItemStack(Material.AIR) };
		inv.setArmorContents(diamondList);
	}

	static public Player getPlayer(String playerName) {
		if (playerName != null) {
			Player player = (Bukkit.getServer().getPlayer(playerName));
			return player;
		} else {
			return null;
		}

	}

}
