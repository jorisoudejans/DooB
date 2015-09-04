package doob.model;

public abstract class PowerUp {
	
	private int duration;
	private int timeOfDisappear;
	
	public PowerUp(int duration, int timeOfDisappear) {
		//super();
		this.duration = duration;
		this.timeOfDisappear = timeOfDisappear;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getTimeOfDisappear() {
		return timeOfDisappear;
	}

	public void setTimeOfDisappear(int timeOfDisappear) {
		this.timeOfDisappear = timeOfDisappear;
	}
	

}
