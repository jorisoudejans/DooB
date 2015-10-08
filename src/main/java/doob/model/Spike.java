package doob.model;

import javafx.scene.image.Image;

public class Spike extends Projectile {
	
	public Spike(Player player, double x, double y, double shootSpeed) {
		super(player, x, y);
		if (shootSpeed != -1) {
			this.setImg(new Image("/image/Spike.png"));
		}
	}

}
