package doob.model;

import javafx.scene.image.Image;

public class Spike extends Projectile{
	
	public Spike(double x, double y, double shootSpeed) {
		super(x, y, shootSpeed);
		this.setImg(new Image("/image/Spike.png"));
	}

}
