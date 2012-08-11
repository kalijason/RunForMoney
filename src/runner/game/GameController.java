package runner.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import runner.RunForMoney;
import runner.event.AttackEvent;
import runner.event.CommandEvent;
import runner.event.QuitEvent;
import runner.util.ChatUtil;

/**
 * @author Jason
 * 
 */
public class GameController {
	private final int TPS = 20;
	private long totalTime = 60 * 2; // 2 mins at default
	private long startTime;
	private boolean isStarted;
	private List<RFMPlayer> runnerList;
	private List<RFMPlayer> hunterList;
	RunForMoney runForMoney;
	private PrizeDispatcher prizeDispatcher;
	private InventoryManager invManager;

	public GameController(RunForMoney runForMoney) {
		runnerList = new ArrayList<RFMPlayer>();
		hunterList = new ArrayList<RFMPlayer>();
		this.runForMoney = runForMoney;
		this.prizeDispatcher = new PrizeDispatcher(runForMoney);
		this.invManager = new InventoryManager(this);
	}

	public void addHunter(Player player) {
		if (getRunner(player) != null) {
			removeRunner(player);
		}
		hunterList.add(new RFMPlayer(player.getName(), PlayerType.HUNTER));
	}

	public void addRunner(Player player) {
		runnerList.add(new RFMPlayer(player.getName(), PlayerType.RUNNER));
	}

	public void beginStatus() {
		ChatUtil.broadcast(ChatColor.GOLD + "遊戲初期，全場獵人速度減緩30秒！！！");
		for (RFMPlayer p : hunterList) {
			Player hunter = (Bukkit.getServer().getPlayer(p.getName()));
			if (hunter != null) {
				hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
						30 * TPS, 1));
			}
		}

		// set time out
		runForMoney.getServer().getScheduler()
				.scheduleSyncDelayedTask(runForMoney, new Runnable() {
					@Override
					public void run() {
						ChatUtil.broadcast(ChatColor.GOLD + "全場獵人速度回復！！！");

					}
				}, 30 * TPS);
	}

	public void checkIfGameover() {
		if (getRunnerAlive() <= 0) {
			ChatUtil.broadcast(ChatColor.RED + "所有逃亡者皆被補獲！！！");
			stop();
		}

		if (getHunterAlive() <= 0) {
			ChatUtil.broadcast(ChatColor.RED + "所有獵人放棄遊戲！！！");
			stop();
		}
	}

	public void checkPlayerQuit(Player player) {
		RFMPlayer rfmPlayer = getHunter(player);
		if (rfmPlayer == null)
			rfmPlayer = getRunner(player);
		if (rfmPlayer != null) {
			String type = null;
			if (rfmPlayer.getType() == PlayerType.HUNTER) {
				type = "獵人";
			} else {
				type = "逃亡者";
			}
			rfmPlayer.setAlive(false);
			ChatUtil.broadcast(ChatColor.AQUA + rfmPlayer.getName() + "("
					+ type + ")" + " 放棄了遊戲!" + ChatColor.RESET);
			checkIfGameover();

		}
	}

	public void checkRunterKillsRunner(Player hunter, Player runner) {
		RFMPlayer rfmRunner = getRunner(runner);
		RFMPlayer rfmHunter = getHunter(hunter);
		if (rfmRunner != null && rfmHunter != null && rfmRunner.isAlive()) {
			rfmRunner.setAlive(false);
			rfmHunter.addKills();
			runForMoney.getTeleporter().moveToPortal(runner, "observer");
			ChatUtil.broadcast(ChatColor.LIGHT_PURPLE + "獵人 "
					+ hunter.getName() + " 抓到了逃亡者  " + runner.getName()
					+ " ！！！ (存活人數剩" + getRunnerAlive() + " 人)\n" + getTime());
			checkIfGameover();
		}
	}

	public RFMPlayer getHunter(Player player) {
		for (RFMPlayer p : hunterList) {
			if (p.getName().equalsIgnoreCase(player.getName())) {
				return p;
			}
		}
		return null;
	}

	public int getHunterAlive() {
		int alives = 0;
		for (RFMPlayer p : hunterList) {
			if (p.isAlive()) {
				alives++;
			}
		}
		return alives;
	}

	public List<RFMPlayer> getHunterList() {
		return hunterList;
	}

	public RFMPlayer getRunner(Player player) {
		for (RFMPlayer p : runnerList) {
			if (p.getName().equalsIgnoreCase(player.getName())) {
				return p;
			}
		}
		return null;
	}

	public int getRunnerAlive() {
		int alives = 0;
		for (RFMPlayer p : runnerList) {
			if (p.isAlive()) {
				alives++;
			}
		}
		return alives;
	}

	public List<RFMPlayer> getRunnerList() {
		return runnerList;
	}

	public String getStatus() {
		StringBuffer statusString = new StringBuffer();

		// prepare runner
		statusString.append(ChatColor.AQUA + "參賽者 (共 " + runnerList.size()
				+ "人/存活人數" + getRunnerAlive() + ") ");

		for (RFMPlayer p : runnerList) {
			ChatColor color = ChatColor.RESET;
			if (p.isAlive()) {
				color = ChatColor.AQUA;
			} else {
				color = ChatColor.GRAY;
			}
			statusString.append(color + p.getName() + ChatColor.RESET + ", ");
		}

		statusString.append("\n");

		// prepare hunter

		statusString.append(ChatColor.GREEN + "獵人 (共 " + hunterList.size()
				+ "人) ");

		for (RFMPlayer p : hunterList) {
			ChatColor color = ChatColor.RESET;
			if (p.isAlive()) {
				color = ChatColor.AQUA;
			} else {
				color = ChatColor.GRAY;
			}
			statusString.append(color + p.getName() + "(" + p.getKills() + ") "
					+ ChatColor.RESET + ", ");
		}

		statusString.append("\n");

		// prepare time
		statusString.append(getTime());

		return statusString.toString();
	}

	public String getTime() {
		StringBuffer statusString = new StringBuffer();
		if (isStarted) {

			long gameTime = (System.currentTimeMillis() - startTime) / 1000;
			statusString.append("遊戲時間已經過: " + ChatUtil.secToString(gameTime)
					+ ", 尚有: " + ChatUtil.secToString(totalTime - gameTime));

		} else {
			statusString.append("遊戲時間設定為 " + ChatUtil.secToString(totalTime));
		}

		return statusString.toString();
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void quest() {
		ChatUtil.broadcast(ChatColor.GOLD + "玩家任務完成，全場獵人速度減緩30秒！！！");
		for (RFMPlayer p : hunterList) {
			Player hunter = (Bukkit.getServer().getPlayer(p.getName()));
			if (hunter != null) {
				hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
						30 * TPS, 1));
			}
		}
	}

	public void removeRunner(Player player) {
		for (RFMPlayer p : runnerList) {
			if (p.getName().equalsIgnoreCase(player.getName())) {
				runnerList.remove(p);
				return;
			}
		}
	}

	public void reset() {
		if (isStarted) {
			stop();
		}
		EntityDamageEvent.getHandlerList().unregister(runForMoney);
		runnerList.clear();
		hunterList.clear();
	}

	public void setTime(int secs) {
		ChatUtil.broadcast("遊戲時間設定為 " + ChatUtil.secToString(secs));
		totalTime = secs;
	}

	public void start() {

		ChatUtil.broadcast(ChatColor.GOLD + "==全員逃走中遊戲正式開始，死命逃吧！！！==");
		Bukkit.getPluginManager().registerEvents(new AttackEvent(this),
				runForMoney);

		Bukkit.getPluginManager().registerEvents(new CommandEvent(this),
				runForMoney);

		Bukkit.getPluginManager().registerEvents(new QuitEvent(this),
				runForMoney);

		isStarted = true;
		startTime = System.currentTimeMillis();

		// show status after 3 secs
		runForMoney.getServer().getScheduler()
				.scheduleSyncDelayedTask(runForMoney, new Runnable() {
					@Override
					public void run() {
						ChatUtil.broadcast(getStatus());
					}
				}, 3 * TPS);

		// set time out
		runForMoney.getServer().getScheduler()
				.scheduleSyncDelayedTask(runForMoney, new Runnable() {
					@Override
					public void run() {
						ChatUtil.broadcast(getStatus());
						stop();
					}
				}, totalTime * TPS);

		beginStatus();
	}

	public void stop() {
		ChatUtil.broadcast("遊戲結束！！！");
		EntityDamageEvent.getHandlerList().unregister(runForMoney);
		PlayerCommandPreprocessEvent.getHandlerList().unregister(runForMoney);
		PlayerQuitEvent.getHandlerList().unregister(runForMoney);

		runForMoney.getServer().getScheduler().cancelTasks(runForMoney);
		isStarted = false;

		prizeDispatcher.dispatchToRunners(runnerList);
		prizeDispatcher.dispatchToHunters(hunterList);
		ChatUtil.broadcast("獎品發送結束！！！");
	}
}
