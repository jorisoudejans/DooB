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
	@Mock
	private Wall w1;
	private Ball b1;
	private double d1 = 220;
	private double d2 = 200;
	private double d3 = -1;
	
	@Before
	public void setup() {
		p1 = new Spike(d1, d2, d3);
		b1 = new Ball(220, 200, 30, 30, 30);
	}
	
	@Test
	public void ProjectileBallCollisiontest() {
		assertTrue(p1.collides(b1));
	}
	
	@Test
	public void getterSetterTest() {
		assertEquals(p1.getX(), d1, 0.001);
		assertEquals(p1.getY(), d2, 0.001);
		assertEquals(p1.getShootSpeed(), d3, 0.001);
		p1.setX(20);
		assertEquals(p1.getX(), 20, 0.01);
		p1.setY(40);
		assertEquals(p1.getY(), 40, 0.01);
		p1.setShootSpeed(10);
		assertEquals(p1.getShootSpeed(), 10, 0.01);
	}
	
	@Test
	public void ProjectileWallCollisionTest() {
		assertFalse(p1.collides(w1));
	}
	
	@Test
	public void moveTest() {
		assertEquals(p1.getY(), d2, 0.01);
		p1.move();
		assertEquals(p1.getY(), d2 - d3, 0.01);
	}
}
