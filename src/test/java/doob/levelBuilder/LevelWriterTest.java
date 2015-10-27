package doob.levelBuilder;

import doob.model.Ball;
import doob.model.Player;
import doob.model.Wall;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Shane on 27-10-2015.
 */
public class LevelWriterTest {

    private ArrayList<Wall> walls;
    private ArrayList<Ball> balls;
    private ArrayList<Player> players;
    private String time;
    private String name;

    @Before
    public void setUp() {
        walls = new ArrayList<Wall>();
        balls = new ArrayList<Ball>();
        players = new ArrayList<Player>();
        name = "Tests/";
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Paths.get("src/main/resources/level/Custom/" + name + ".xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element getElement(String file, String tag) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nListBall = doc.getElementsByTagName(tag);
            Node nNode = nListBall.item(0);

            return (Element) nNode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testWritePlayers() {
        Player pl1 = new Player(420, 575, 50, 72, null, null, null);
        Player pl2 = new Player(500, 600, 100, 20, null, null, null);

        players.add(pl1);
        players.add(pl2);

        name = name + "playerTest";

        LevelWriter lw = new LevelWriter(balls, walls, players, 0, name);
        try {
            lw.saveToXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
