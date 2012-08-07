package runner.game;

public class RFMPlayer {
	private String name;
	private int type;
	private boolean isAlive;
	private int kills;

	public RFMPlayer(String name, int type) {
		super();
		this.name = name;
		this.type = type;
		isAlive = true;
		kills = 0;
	}

	public void addKills() {
		kills++;
	}

	public int getKills() {
		return kills;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(int type) {
		this.type = type;
	}
}
