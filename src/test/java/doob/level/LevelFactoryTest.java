package doob.level;

import doob.level.LevelFactory;
import doob.model.Ball;
import doob.model.Level;
import doob.model.Player;
import doob.model.Wall;
import doob.util.BoundsTuple;
import org.w3c.dom.Document;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.mockito.Mockito.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Class to test LevelFactory
 *
 * Created by hidde on 9/10/15.
 */
public class LevelFactoryTest {

    private LevelFactory levelFactory;
    @Spy
    private Canvas canvas;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    public Document buildDoc(String file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Test
    public void testBuildLevelSimple() throws Exception {

        levelFactory = new LevelFactory(getClass().getResource("/level/simple.xml").getPath(), canvas, "single");
        levelFactory.setPlayerImages(new Image[] { null, null, null });
        Level level = levelFactory.build();

        assertNotNull(level);
    }

    @Test
    public void testBuildLevelBall1() throws Exception {

        levelFactory = new LevelFactory(getClass().getResource("/level/ball1.xml").getPath(), canvas, "single");
        levelFactory.setPlayerImages(new Image[] { null, null, null });
        Level level = levelFactory.build();

        assertNotNull(level);
    }

    @Test
    public void testParseLevel() {
        levelFactory = new LevelFactory(getClass().getResource("/level/parseLevel.xml").getPath(), canvas, "single");
        Document doc = buildDoc(getClass().getResource("/level/parseLevel.xml").getPath());

        levelFactory.parseLevel(doc);

        assertEquals(453, levelFactory.getTime());

    }

    @Test
    public void testParseBalls() {
        levelFactory = new LevelFactory(getClass().getResource("/level/parseBalls.xml").getPath(), canvas, "single");
        Document doc = buildDoc(getClass().getResource("/level/parseBalls.xml").getPath());

        levelFactory.parseBalls(doc);

        Ball ball1 = new Ball(400, 153, -2, 0, 32);
        Ball ball2 = new Ball(520, 546, 2, 2, 64);
        ArrayList<Ball> balls =  new ArrayList<Ball>();

        balls.add(ball1);
        balls.add(ball2);

        assertEquals(balls, levelFactory.getBallList());

    }

    @Test
    public void testParseWalls() {
        levelFactory = new LevelFactory(getClass().getResource("/level/parseWalls.xml").getPath(), canvas, "single");
        Document doc = buildDoc(getClass().getResource("/level/parseWalls.xml").getPath());

        levelFactory.parseWalls(doc);

        Wall wall1 = new Wall(0, 0, 960, 50, 0, 650, 1000, 1, "");
        Wall wall2 = new Wall(440, 0, 80, 650);
        ArrayList<Wall> walls =  new ArrayList<Wall>();

        walls.add(wall1);
        walls.add(wall2);

        assertEquals(walls, levelFactory.getWallList());

    }

    //TODO: Figure out a way to test parsePlayers without initializing graphics.
    /*
    @Test
    public void testParsePlayers() {

        levelFactory = new LevelFactory(getClass().getResource("/level/parsePlayers.xml").getPath(), canvas, "single");
        Document doc = buildDoc(getClass().getResource("/level/parsePlayers.xml").getPath());


        levelFactory.parsePlayers(doc);

        Player pl1 = new Player(420, 575, 50, 72, null, null, null);
        Player pl2 = new Player(500, 600, 100, 20, null, null, null);
        ArrayList<Player> pll = new ArrayList<Player>();

        pll.add(pl1);
        pll.add(pl2);

        assertEquals(pll, levelFactory.getPlayerList());

    }
    */

}
