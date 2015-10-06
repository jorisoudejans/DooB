package doob.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ProjectileTest {

	private Player player;
	private Projectile p1;
	@Mock
	private Wall w1;
	private Ball b1;
	private double d1 = 220;
	private double d2 = 200;
	private double d3 = -1;
	
	@Before
	public void setup() {
		player = mock(Player.class);
		p1 = new Spike(player, d1, d2, d3);
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
		p1.setX(20);
		assertEquals(p1.getX(), 20, 0.01);
		p1.setY(40);
		assertEquals(p1.getY(), 40, 0.01);
	}
	
	@Test
	public void ProjectileWallCollisionTest() {
		assertFalse(p1.collides(w1));
	}

	// TODO fix this test.
	/*
	@Test
	public void moveTest() {
		assertEquals(p1.getY(), d2, 0.01);
		p1.move();
		assertEquals(p1.getY(), d2 - d3, 0.01);
	}*/
}
