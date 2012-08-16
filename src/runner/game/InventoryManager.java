package runner.game;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryManager {
	HashMap<Player, ItemStack[]> invBank;
	GameController controller;

	public InventoryManager(GameController controller) {
		invBank = new HashMap<Player, ItemStack[]>();
		this.controller = controller;
	}

	public void withDraw(Player player) {
		if (player != null) {
			ItemStack[] i = invBank.get(player);
			if (i != null) {
				player.getInventory().setArmorContents(i);
				invBank.remove(i);
			}
		}
	}

	public void store(Player player) {
		if (player != null) {
			invBank.put(player, player.getInventory().getArmorContents());
			clear(player);
		}
	}

	public void setHunter(Player player) {
		if (player != null && player.getInventory() != null) {
			// prepare hunter items
			player.getInventory().setHelmet(new ItemStack(Material.GLASS));
			player.getInventory().setLeggings(
					new ItemStack(Material.DIAMOND_LEGGINGS));
			player.getInventory().setChestplate(
					new ItemStack(Material.DIAMOND_CHESTPLATE));
			player.getInventory().setBoots(
					new ItemStack(Material.DIAMOND_BOOTS));
		}
	}

	private void clear(Player player) {
		if (player != null && player.getInventory() != null) {
			player.getInventory().setHelmet(new ItemStack(Material.AIR));
			player.getInventory().setLeggings(new ItemStack(Material.AIR));
			player.getInventory().setChestplate(new ItemStack(Material.AIR));
			player.getInventory().setBoots(new ItemStack(Material.AIR));

		}

	}

}
