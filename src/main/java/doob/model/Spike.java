package doob.model;

import javafx.scene.image.Image;

public class Spike extends Projectile {
	
	public Spike(double x, double y, double shootSpeed, Image im) {
		super(x, y);
		if (shootSpeed != -1) {
			this.setImg(im);
		}
	}

}
