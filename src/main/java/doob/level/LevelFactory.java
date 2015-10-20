package doob.level;

import doob.model.Ball;
import doob.model.Level;
import doob.model.Player;
import doob.model.Wall;
import doob.util.BoundsTuple;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Class for parsing levels.
 */
public class LevelFactory {

    public static final int NUMBER_OF_PLAYER_IMAGES = 3;

    private File file;
    private BoundsTuple bounds;
    private int time;
    private ArrayList<Player> playerList;
    private ArrayList<Ball> ballList;
    private ArrayList<Wall> wallList;
    private Image[] playerImages;

    /**
     * Creates a LevelFactory object which all that is needed to build a level.
     *
     * @param path path to the XML-file
     * @param bounds javaFX bounds
     */
    public LevelFactory(String path, BoundsTuple bounds) {
        this.file = new File(path);
        this.bounds = bounds;
        this.playerList = new ArrayList<Player>();
        this.ballList = new ArrayList<Ball>();
        this.wallList = new ArrayList<Wall>();

        playerImages = new Image[1];
    }

    public void setPlayerImages(Image[] playerImages) {
        this.playerImages = playerImages;
    }

    /**
     * parses an int out of an xml line.
     *
     * @param s The XML-tag to parse from
     * @param eElement The XML-Element to parse from
     * @return The parsed int
     */
    public int parseInt(String s, Element eElement) {
        return Integer.parseInt(eElement.getElementsByTagName(s)
                .item(0)
                .getTextContent());

    }

    /**
     * Parses and adds players in the XML to the playerList.
     *
     * @param doc Document build from the XML
     */
    public void parsePlayers(Document doc) {
        NodeList nListPlayer = doc.getElementsByTagName("player");
        for (int i = 0; i < nListPlayer.getLength(); i++) {
            Node nNode = nListPlayer.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int x = parseInt("x", eElement);
                int y = parseInt("y", eElement);
                int width = parseInt("width", eElement);
                int height = parseInt("height", eElement);

                setPlayerImages(i);

                Player pl = new Player(
                        x,
                        y,
                        width,
                        height,
                        playerImages[0],
                        playerImages[1],
                        playerImages[2]
                );
                playerList.add(pl);
            }
        }
    }

    /**
     * Sets the playerImages to the corresponding images.
     * @param i Images for the i-th player
     */
    public void setPlayerImages(int i) {
            playerImages = new Image[] { new Image("/image/character" + i + "_stand.png"),
                    new Image("/image/character" + i + "_left.gif"),
                    new Image("/image/character" + i + "_right.gif") };
    }

    /**
     * Parses and adds balls in the XML to the ballList.
     *
     * @param doc Document build from the XML
     */
    public void parseBalls(Document doc) {
        NodeList nListBall = doc.getElementsByTagName("ball");
        for (int i = 0; i < nListBall.getLength(); i++) {
            Node nNode = nListBall.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int x = parseInt("x", eElement);
                int y = parseInt("y", eElement);
                int speedX = parseInt("speedX", eElement);
                int speedY = parseInt("speedY", eElement);
                int size = parseInt("size", eElement);

                Ball ball = new Ball(x, y, speedX, speedY, size);
                ballList.add(ball);
            }
        }
    }

    /**
     * Parses and adds walls in the XML to the wallList.
     *
     * @param doc Document build from the XML
     */
    public void parseWalls(Document doc) {
        NodeList nListBall = doc.getElementsByTagName("wall");
        for (int i = 0; i < nListBall.getLength(); i++) {
            Node nNode = nListBall.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int x = parseInt("x", eElement);
                int y = parseInt("y", eElement);
                int width = parseInt("width", eElement);
                int height = parseInt("height", eElement);
                if (parseInt("moveable", eElement) == 1) {
                    int endx = parseInt("endx", eElement);
                    int endy = parseInt("endy", eElement);
                    int duration = parseInt("duration", eElement);
                    int speed = parseInt("speed", eElement);
                    String condition = eElement.getElementsByTagName("condition")
                    		.item(0).getTextContent();
                    Wall wall = new Wall(x, y, width, height, endx, endy, 
                    		duration, speed, condition);
                    wallList.add(wall);
                } else {
                    Wall wall = new Wall(x, y, width, height);
                    wallList.add(wall);
                }
            }
        }
    }
    
    /**
     * Parse the level from a document.
     * @param doc Document to be read.
     */
    public void parseLevel(Document doc) {
    	NodeList nListBall = doc.getElementsByTagName("time");	
        Node nNode = nListBall.item(0);
        time = Integer.parseInt(nNode.getTextContent());
    }

    /**
     * Parses the XML and adds all parsed objects to their respective lists.
     */
    public void parseXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            
            parseLevel(doc);
            parsePlayers(doc);
            parseBalls(doc);
            parseWalls(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Builds the level from the parsed XML.
     * @return The build level
     */
    public Level build() {

        //XML parsing
        parseXML();

        /*Level.Builder builder = new Level.Builder();
        builder.setBalls(ballList);
        builder.setCanvas(bounds);
        builder.setPlayers(playerList);
        builder.setWalls(wallList);
        builder.setTime(time);*/

        return new Level.Builder()
                .setBalls(ballList)
                .setBounds(bounds)
                .setPlayers(playerList)
                .setWalls(wallList)
                .setTime(time)
                .build();

    }

}