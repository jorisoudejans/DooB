package doob.model;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Class for parsing levels.
 */
public class LevelFactory {

    public static final int NUMBER_OF_PLAYER_IMAGES = 3;

    private File file;
    private Canvas canvas;
    private ArrayList<Player> playerList;
    private ArrayList<Ball> ballList;
    private Image[] playerImages;

    /**
     * Creates a LevelFactory object which all that is needed to build a level.
     *
     * @param path path to the XML-file
     * @param canvas javaFX canvas
     */
    public LevelFactory(String path, Canvas canvas) {
        this.file = new File(path);
        this.canvas = canvas;
        this.playerList = new ArrayList<Player>();
        this.ballList = new ArrayList<Ball>();

        playerImages = new Image[1];
    }

    public void setPlayerImages(Image[] images) {
        this.playerImages = images;
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

                if (playerImages.length < NUMBER_OF_PLAYER_IMAGES) {
                    playerImages = new Image[] { new Image("/image/character1_stand.png"),
                            new Image("/image/character1_left.gif"),
                            new Image("/image/character1_right.gif") };
                }

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
     * Parses the XML and adds all parsed objects to their respective lists.
     */
    public void parseXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            parsePlayers(doc);
            parseBalls(doc);

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

        Level.Builder builder = new Level.Builder();
        builder.setBalls(ballList);
        builder.setCanvas(canvas);
        builder.setPlayers(playerList);

        return builder.build();
    }

}