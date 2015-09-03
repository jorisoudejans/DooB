package doob.model;

import java.util.ArrayList;

public class Level {
	
	private ArrayList<Ball> balls;
	private double duration;
	private ArrayList<PowerUp> powerups;
	private long startTime;

	public Level(ArrayList<Ball> balls, double duration,
			ArrayList<PowerUp> powerups) {
		this.balls = balls;
		this.duration = duration;
		this.powerups = powerups;
		this.startTime = System.currentTimeMillis();
	}

	public ArrayList<Ball> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<Ball> balls) {
		this.balls = balls;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public ArrayList<PowerUp> getPowerups() {
		return powerups;
	}

	public void setPowerups(ArrayList<PowerUp> powerups) {
		this.powerups = powerups;
	}
	
	public long getStartTime() {
		return startTime;
	}
}
