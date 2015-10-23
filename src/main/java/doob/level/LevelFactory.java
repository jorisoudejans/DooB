package doob.level;

import doob.controller.LevelController;
import doob.model.*;
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
import java.util.Collections;
import java.util.Comparator;

/**
 * Class for parsing levels.
 */
public class LevelFactory {

    public static final int NUMBER_OF_PLAYER_IMAGES = 3;

    private File file;
    private Canvas canvas;
    private BoundsTuple bounds;
    private int time;
    private ArrayList<Player> playerList;
    private ArrayList<Ball> ballList;
    private ArrayList<Wall> wallList;
    private Image[] playerImages;
    private String type;

    /**
     * Creates a LevelFactory object which all that is needed to build a level.
     *
     * @param path path to the XML-file
     * @param canvas javaFX bounds
     */
    public LevelFactory(String path, Canvas canvas, String type) {
        this.file = new File(path);
        this.canvas = canvas;
        this.playerList = new ArrayList<Player>();
        this.ballList = new ArrayList<Ball>();
        this.wallList = new ArrayList<Wall>();
        this.type = type;
        this.bounds = new BoundsTuple(canvas.getWidth(), canvas.getHeight());

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

        parseXML();

        Level level = null;
        if (type.equals("coop") || type.equals("duel") || type.equals("single")) {
            level = new CampaignLevel(bounds);
        } else if (type.equals("survival")) {
            level = new SurvivalLevel(bounds);
        }

        return setup(level);
    }

    /**
     * Builds up the given level.
     * @param level the level to build
     * @return the built level
     */
    private Level setup(Level level) {
        LevelController levelController = new LevelController(level);
        LevelView levelView = new LevelView(canvas.getGraphicsContext2D(), level);
        levelView.setLevelController(levelController);
        level.addObserver(levelView);
        level.addObserver(levelController);

        Wall right = new Wall(bounds.getWidth().intValue(), 0, 1, bounds.getHeight().intValue());
        Wall left = new Wall(0, 0, 1, bounds.getHeight().intValue());
        Wall ceiling = new Wall(0, 0, bounds.getWidth().intValue(), 1);
        Wall floor = new Wall(0, bounds.getHeight().intValue(), bounds.getWidth().intValue(), 1);

        level.setLeft(left);
        level.setRight(right);
        level.setCeiling(ceiling);
        level.setFloor(floor);

        Collections.sort(wallList, new Comparator<Wall>() {
            @Override
            public int compare(Wall w1, Wall w2) {
                return Integer.compare(w1.getX(), w2.getX());
            }
        });

        wallList.add(right);
        wallList.add(0, left);
        wallList.add(floor);
        wallList.add(ceiling);
        level.setBalls(ballList);
        level.setPlayers(playerList);
        level.setWalls(wallList);
        level.setTime(time);
        level.setCurrentTime(time);
        return level;
    }

}