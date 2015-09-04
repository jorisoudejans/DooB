package doob.model;

public abstract class PowerUp {
	
	private int duration;
	private Sprite sprite;
	private int timeOfDisappear;
	
	public PowerUp(int duration, Sprite sprite, int timeOfDisappear) {
		//super();
		this.duration = duration;
		this.sprite = sprite;
		this.timeOfDisappear = timeOfDisappear;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public double getTimeOfDisappear() {
		return timeOfDisappear;
	}

	public void setTimeOfDisappear(int timeOfDisappear) {
		this.timeOfDisappear = timeOfDisappear;
	}
	

}
