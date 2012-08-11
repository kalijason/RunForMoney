package runner.game;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import runner.RunForMoney;
import runner.util.PlayerUtil;

public class PrizeDispatcher {
	private RunForMoney runForMoney;

	public PrizeDispatcher(RunForMoney runForMoney) {
		this.runForMoney = runForMoney;
	}

	public void dispatchToHunters(List<RFMPlayer> hunterList) {
		// 抓到玩家大於一人則有一個獎品，再來每抓三人就有一個獎品
		for (RFMPlayer p : hunterList) {
			if (p.getKills() > 0 && p.isAlive()) {
				givePrize(p, 1, (p.getKills() / 3) + 1);
			}

		}
	}

	public void dispatchToRunners(List<RFMPlayer> runnerList) {
		for (RFMPlayer p : runnerList) {
			if (p.isAlive()) {
				givePrize(p, 1, 3);
			}else{
				givePrize(p, 1, 1);
			}
		}
	}

	private Material getRandomPrizeByLevel(int level) {
		final Material[] priceList = { Material.DIAMOND, Material.GLOWSTONE,
				Material.SLIME_BALL };
		// get random between
		int r = (int) (Math.random() * priceList.length);

		return priceList[r];
	}

	private void givePrize(RFMPlayer rfmPlayer, int level, int number) {
		Player player = runForMoney.getServer().getPlayer(rfmPlayer.getName());
		if (player != null) {

			for (int i = 0; i < number; i++) {
				Material type = getRandomPrizeByLevel(level);
				PlayerUtil.giveItem(player, type, 1);
			}

		}
	}

}
