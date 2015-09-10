package doob.model;

import static org.junit.Assert.*;

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
	private Ball b1;
	
	@Before
	public void setup() {
		p1 = new Spike(220, 200, 10);
	}
	
	@Test
	public void ProjectileBallCollisiontest() {
		when(b1.getX()).thenReturn((double) 220);
		when(b1.getY()).thenReturn((double) 200);
		when(b1.getSize()).thenReturn((double) 30);
		assertTrue(p1.collides(b1));
	}

}
