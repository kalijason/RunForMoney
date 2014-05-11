package runner.game;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {
	HashMap<String, ItemStack[]> invBank;
	GameController controller;

	public InventoryManager(GameController controller) {
		invBank = new HashMap<String, ItemStack[]>();
		this.controller = controller;
	}

	private void clear(Player player) {
		if (player != null && player.getInventory() != null) {
			player.getInventory().setHelmet(new ItemStack(Material.AIR));
			player.getInventory().setLeggings(new ItemStack(Material.AIR));
			player.getInventory().setChestplate(new ItemStack(Material.AIR));
			player.getInventory().setBoots(new ItemStack(Material.AIR));

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

	public void store(Player player) {
		if (player != null) {
			invBank.put(player.getName(), player.getInventory()
					.getArmorContents());
			clear(player);
		}
	}

	public void withDraw(Player player) {
		if (player != null) {
			ItemStack[] i = invBank.get(player.getName());
			if (i != null) {
				player.getInventory().setArmorContents(i);
				invBank.remove(i);
			}
		}
	}

}
