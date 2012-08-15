package runner.game;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import runner.RunForMoney;
import runner.util.PlayerUtil;

public class PrizeDispatcher {
	private RunForMoney runForMoney;
	Random random;

	public PrizeDispatcher(RunForMoney runForMoney) {
		this.runForMoney = runForMoney;
		this.random = new Random();
	}

	public void dispatchToHunters(List<RFMPlayer> hunterList) {
		// 抓到玩家大於一人則有一個獎品，再來每抓三人就有一個獎品
		for (RFMPlayer p : hunterList) {
			int defaultPrize = 1;
			if (p.getKills() > 0 && p.isAlive()) {
				defaultPrize += (p.getKills() / 3);
			}
			givePrize(p, 1, defaultPrize);
		}
	}

	public void dispatchToRunners(List<RFMPlayer> runnerList) {
		for (RFMPlayer p : runnerList) {
			if (p.isAlive()) {
				givePrize(p, 1, 3);
			} else {
				givePrize(p, 1, 1);
			}
		}
	}

	private Material getRandomPrizeByLevel(int level) {
		final Material[] priceList = { Material.DIAMOND, Material.GLOWSTONE,
				Material.SLIME_BALL };
		// get random between
		int r = (int) random.nextInt(priceList.length);

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
