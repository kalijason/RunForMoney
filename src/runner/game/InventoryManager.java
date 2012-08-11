package runner.game;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

public class InventoryManager {
	HashMap<String, ItemStack[]> invBank;
	GameController controller;

	public InventoryManager(GameController controller) {
		invBank = new HashMap<String, ItemStack[]>();
		this.controller = controller;
	}

	public ItemStack[] get(String name) {
		return invBank.get(name);
	}

	public void put(String name, ItemStack[] itemAry) {
		invBank.put(name, itemAry);
	}
}
