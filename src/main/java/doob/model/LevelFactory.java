package doob.model;
import javafx.scene.canvas.Canvas;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Shane on 8-9-2015.
 */
public class LevelFactory {

    String file;
    Canvas canvas;
    ArrayList<Player> playerList;
    ArrayList<Ball> ballList;


    public LevelFactory(String file, Canvas canvas){
        this.file = file;
        this.canvas = canvas;
        this.playerList = new ArrayList<Player>();
        this.ballList = new ArrayList<Ball>();
    }



    public void build() {

        //XML parsing
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            //Loop through all players and add them to the playerList
            NodeList nListPlayer = doc.getElementsByTagName("player");
            for(int i = 0; i < nListPlayer.getLength(); i++){
                Node nNode = nListPlayer.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int x = Integer.parseInt(eElement.getElementsByTagName("x")
                            .item(0)
                            .getTextContent());
                    int y = Integer.parseInt(eElement.getElementsByTagName("y")
                            .item(0)
                            .getTextContent());
                    int width = Integer.parseInt(eElement.getElementsByTagName("width")
                            .item(0)
                            .getTextContent());
                    int height = Integer.parseInt(eElement.getElementsByTagName("height")
                            .item(0)
                            .getTextContent());

                    Player pl = new Player(x, y, width, height);
                    playerList.add(pl);
                }
            }

            //Loop through all balls and add them to the ballList
            NodeList nListBall = doc.getElementsByTagName("ball");
            for(int i = 0; i < nListBall.getLength(); i++){
                Node nNode = nListBall.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int x = Integer.parseInt(eElement.getElementsByTagName("x")
                            .item(0)
                            .getTextContent());
                    int y = Integer.parseInt(eElement.getElementsByTagName("y")
                            .item(0)
                            .getTextContent());
                    int speedX = Integer.parseInt(eElement.getElementsByTagName("speedX")
                            .item(0)
                            .getTextContent());
                    int speedY = Integer.parseInt(eElement.getElementsByTagName("speedY")
                            .item(0)
                            .getTextContent());
                    int size = Integer.parseInt(eElement.getElementsByTagName("size")
                            .item(0)
                            .getTextContent());

                    Ball ball = new Ball(x, y, speedX, speedY, size, (int) canvas.getWidth(), (int) canvas.getHeight());
                    ballList.add(ball);
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        Level.Builder builder = new Level.Builder();
        builder.setBalls(ballList);
        builder.setCanvas(canvas);
        builder.setPlayers(playerList);

        builder.build();


    }

}