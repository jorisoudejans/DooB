package doob.levelBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import doob.model.Ball;
import doob.model.Player;
import doob.model.Wall;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

/**
 * This class writes the created level to a suitable FXML file.
 *
 */
public class LevelWriter {
	
	private static final int PLAYER_HEIGHT = 72;
	private ArrayList<Wall> walls;
	private ArrayList<Ball> balls;
	private ArrayList<Player> players;
	private String time;
	private String name;

    private Document dom;
    private Element e = null;
	
	/**
	 * Basic constructor.
	 * @param ballList the balls to be added to the level
	 * @param wallList the walls to be added to the level
	 * @param playerList the players to be added to the level
	 * @param time the time available to complete the level.
	 * @param levelName the name of the level.
	 */
	public LevelWriter(ArrayList<Ball> ballList, ArrayList<Wall> wallList, 
			ArrayList<Player> playerList, int time, String levelName) {
		this.walls = wallList;
		this.balls = ballList;
		this.players = playerList;
		this.time = Integer.toString(time);
		this.name = levelName;
	}
	
	/**
	 * Writes the players to an element to be added to the xml file.
	 * @return the element containing all the players
	 */
	private Element writePlayers() {
		Element res = dom.createElement("playerList");
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			Element pe = dom.createElement("player");
			pe.setAttribute("id", Integer.toString(i + 1));
			Element px = dom.createElement("x");
			px.appendChild(dom.createTextNode(Integer.toString(p.getX())));
			Element py = dom.createElement("y");
			py.appendChild(dom.createTextNode(Integer.toString(p.getY())));
			Element pw = dom.createElement("width");
			pw.appendChild(dom.createTextNode(Integer.toString(p.getWidth())));
			Element ph = dom.createElement("height");
			ph.appendChild(dom.createTextNode(Integer.toString(PLAYER_HEIGHT)));
			pe.appendChild(px);
			pe.appendChild(py);
			pe.appendChild(pw);
			pe.appendChild(ph);
			res.appendChild(pe);
		}
		return res;
	}
	
	private Element writeBalls() {
		Element res = dom.createElement("ballList");
		for (int i = 0; i < balls.size(); i++) {
			Ball b = balls.get(i);			
			Element be = dom.createElement("ball");
			be.setAttribute("id", Integer.toString(i + 1));
			
			Element bx = dom.createElement("x");
			bx.appendChild(dom.createTextNode(Integer.toString((int) b.getX())));
			Element by = dom.createElement("y");
			by.appendChild(dom.createTextNode(Integer.toString((int) b.getY())));
			Element bsX = dom.createElement("speedX");
			bsX.appendChild(dom.createTextNode(Integer.toString((int) b.getSpeedX())));
			Element bsY = dom.createElement("speedY");
			bsY.appendChild(dom.createTextNode(Integer.toString((int) b.getSpeedY())));
			Element bs = dom.createElement("size");
			bs.appendChild(dom.createTextNode(Integer.toString(b.getSize())));
			
			be.appendChild(bx);
			be.appendChild(by);
			be.appendChild(bsX);
			be.appendChild(bsY);
			be.appendChild(bs);
			res.appendChild(be);
		}
		return res;
	}
	
	private Element writeWalls() {
		Element res = dom.createElement("wallList");
		for (int i = 0; i < walls.size(); i++) {
			Wall w = walls.get(i);			
			Element we = dom.createElement("wall");
			we.setAttribute("id", Integer.toString(i + 1));
			
			Element wx = dom.createElement("x");
			//wx.appendChild(dom.createTextNode(Integer.toString(w.getX()));
			wx.appendChild(dom.createTextNode(Integer.toString(w.getX())));
			Element wy = dom.createElement("y");
			wy.appendChild(dom.createTextNode(Integer.toString(w.getY())));
			Element ww = dom.createElement("width");
			ww.appendChild(dom.createTextNode(Integer.toString(w.getWidth())));
			Element wh = dom.createElement("height");
			wh.appendChild(dom.createTextNode(Integer.toString(w.getHeight())));
			Element wm = dom.createElement("moveable");
			if (w.isMoveable()) {
				wm.appendChild(dom.createTextNode("1"));
			} else {
				wm.appendChild(dom.createTextNode("0"));
			}
			we.appendChild(wx);
			we.appendChild(wy);
			we.appendChild(ww);
			we.appendChild(wh);
			we.appendChild(wm);
			res.appendChild(we);			
		}
		return res;
	}
	
	/**
	 * Save the created level to an fxml file.
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public void saveToFXML() throws FileNotFoundException, UnsupportedEncodingException {
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        dom = db.newDocument();
	        Element rootEle = dom.createElement("level");

	        e = dom.createElement("time");
	        e.appendChild(dom.createTextNode(time));
	        rootEle.appendChild(e);

	        rootEle.appendChild(writePlayers());
	        rootEle.appendChild(writeBalls());
	        rootEle.appendChild(writeWalls());

	        dom.appendChild(rootEle);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	            // send DOM to file
	            String path = "src/main/resources/level/" + name + ".xml";
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new File(path))
	                                		 /*new PrintWriter(path, "UTF-8"))*/);

	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
	}

}
