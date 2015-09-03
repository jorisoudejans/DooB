package doob.model;

public abstract class PowerUp {
	
	private double duration;
	private Sprite sprite;
	private double disappear;
	
	public PowerUp(double duration, Sprite sprite, double disappear) {
		//super();
		this.duration = duration;
		this.sprite = sprite;
		this.disappear = disappear;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public double getDisappear() {
		return disappear;
	}

	public void setDisappear(double disappear) {
		this.disappear = disappear;
	}
	

}
