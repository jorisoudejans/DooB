package doob.levelBuilder;

import doob.model.Ball;
import doob.model.Player;
import doob.model.Wall;
import doob.model.levelbuilder.LevelWriter;
import org.junit.*;
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

import static org.junit.Assume.assumeTrue;

/**
 * Created by Shane on 27-10-2015.
 * Tests the LevelWriter class.
 */
public class LevelWriterTest {

    private ArrayList<Wall> walls;
    private ArrayList<Ball> balls;
    private ArrayList<Player> players;
    private String name;


    @BeforeClass
    public static void setUpClass() throws Exception {
        assumeTrue(System.getenv("TRAVIS") == null);
    }
    @Before
    public void setUp() {
        walls = new ArrayList<Wall>();
        balls = new ArrayList<Ball>();
        players = new ArrayList<Player>();
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Paths.get("src/main/resources/level/Custom/" + name + ".xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean equalXML(String path1, String path2) throws Exception{

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setIgnoringComments(true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc1 = db.parse(new File(path1));
        doc1.normalizeDocument();

        Document doc2 = db.parse(new File(path2));
        doc2.normalizeDocument();

        return doc1.isEqualNode(doc2);
    }


    @Test
    public void testWritePlayers() throws Exception {
        Player pl1 = new Player(420, 575, 50, 72, null, null, null);
        Player pl2 = new Player(500, 600, 100, 20, null, null, null);

        players.add(pl1);
        players.add(pl2);

        name = "playerTest";

        String pathOut = "src/main/resources/level/Custom/playerTest.xml";
        String pathExp = "src/test/resources/level/Custom/playerTestOut.xml";


        LevelWriter lw = new LevelWriter(balls, walls, players, 0, name);

        lw.saveToXML();
        Assert.assertTrue(equalXML(pathOut, pathExp));

    }

    @Test
    public void testWriteWalls() throws Exception{
        Wall wall1 = new Wall(400, 500, 45, 90);
        Wall wall2 = new Wall(100, 541, 468, 132,200, 100, 2000, 5);

        walls.add(wall1);
        walls.add(wall2);

        name = "wallTest";

        String pathOut = "src/main/resources/level/Custom/wallTest.xml";
        String pathExp = "src/test/resources/level/Custom/wallTestOut.xml";


        LevelWriter lw = new LevelWriter(balls, walls, players, 0, name);

        lw.saveToXML();
        Assert.assertTrue(equalXML(pathOut, pathExp));
    }

    @Test
    public void testWriteBalls() throws Exception{
        Ball ball1 = new Ball(400, 500, 4, 2, 64);
        Ball ball2 = new Ball(100, 100, -8, 0, 256);

        balls.add(ball1);
        balls.add(ball2);

        name = "ballTest";

        String pathOut = "src/main/resources/level/Custom/ballTest.xml";
        String pathExp = "src/test/resources/level/Custom/ballTestOut.xml";


        LevelWriter lw = new LevelWriter(balls, walls, players, 0, name);

        lw.saveToXML();
        Assert.assertTrue(equalXML(pathOut, pathExp));
    }

    @Test
    public void testWriteCustomLevels() throws Exception{
        String pathOut = "src/main/resources/level/CustomSPLevels.xml";
        String pathExp = "src/test/resources/level/CustomSPLevelsOut.xml";

        String tempPath = "src/main/resources/level/CustomSPLevelsCopy.xml";

        //copy current customLevels file to switch back later
        Files.copy(Paths.get(pathOut), Paths.get(tempPath));

        String[] levels = {"CustomLevel1", "MyLevel23"};
        LevelWriter.writeCustomLevels(levels);


        Assert.assertTrue(equalXML(pathOut, pathExp));

        //switch back to the right customLevels
        Files.deleteIfExists(Paths.get(pathOut));
        Files.copy(Paths.get(tempPath), Paths.get(pathOut));
        Files.deleteIfExists(Paths.get(tempPath));


    }

}
