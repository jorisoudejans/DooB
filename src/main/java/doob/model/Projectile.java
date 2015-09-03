package doob.model;

import javafx.scene.image.Image;

public abstract class Projectile {

	private Image img;
	private double x;
	private double y;
	private double shootSpeed;
	
	public Projectile(double x, double y, double shootSpeed) {
		this.x = x;
		this.y = y;
		this.shootSpeed = shootSpeed;
	}
	
	public double getShootSpeed() {
		return shootSpeed;
	}

	public void setShootSpeed(double shootSpeed) {
		this.shootSpeed = shootSpeed;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void shoot() {
		this.y = y - shootSpeed;
	}

}
