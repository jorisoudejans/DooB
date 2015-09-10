package doob.model;

import static org.junit.Assert.*;
import javafx.scene.image.Image;

import org.mockito.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProjectileTest {
	
	private Projectile p1;
	
	private Ball b1;
	
	@Before
	public void setup() {
		p1 = new Spike(220, 200, -1);
		b1 = new Ball(220, 200, 30, 30, 30);
	}
	
	@Test
	public void ProjectileBallCollisiontest() {
		assertTrue(p1.collides(b1));
	}
	
	/*@Test
	public void getterSetterTest() {
		assertSame(p1.getX(), 220.0);
		assertSame(p1.getY(), 200.0);
		assertSame(p1.getShootSpeed(), -1);
	}*/

}
