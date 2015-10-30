package doob.model.levelbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import doob.model.Ball;
import doob.model.Player;
import doob.model.Wall;

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
	private Element we;

    private Document dom;

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
			px.appendChild(dom.createTextNode(Integer.toString(p.getXCoord())));
			Element py = dom.createElement("y");
			py.appendChild(dom.createTextNode(Integer.toString(p.getYCoord())));
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
			res.appendChild(b.getDomRepresentation(dom, i));
		}
		return res;
	}
	
	/**
	 * Helper for the writeWalls() method. This method contains the shared data of both walls.
	 * @param i wall id
	 */
	private void walls(int i) {	
		Wall w = walls.get(i);
		we = dom.createElement("wall");
		we.setAttribute("id", Integer.toString(i + 1));			
		Element wx = dom.createElement("x");
		wx.appendChild(dom.createTextNode(Integer.toString(w.getXCoord())));
		Element wy = dom.createElement("y");
		wy.appendChild(dom.createTextNode(Integer.toString(w.getYCoord())));
		Element ww = dom.createElement("width");
		ww.appendChild(dom.createTextNode(Integer.toString(w.getWidth())));
		Element wh = dom.createElement("height");
		wh.appendChild(dom.createTextNode(Integer.toString(w.getHeight())));
		we.appendChild(wx);
		we.appendChild(wy);
		we.appendChild(ww);
		we.appendChild(wh);
	}
	
	/**
	 * Writes the walls to the xml file.
	 * @return walls element
	 */
	private Element writeWalls() {
		Element res = dom.createElement("wallList");

		for (int i = 0; i < walls.size(); i++) {
			Wall w = walls.get(i);
			walls(i);
			Element wm = dom.createElement("moveable");
			if (w.isMoveable()) {
				wm.appendChild(dom.createTextNode("1"));
				we.appendChild(wm);
				Element wendx = dom.createElement("endx");
				wendx.appendChild(dom.createTextNode(Integer.toString(w.getEndx())));
				Element wendy = dom.createElement("endy");
				wendy.appendChild(dom.createTextNode(Integer.toString(w.getEndy())));
				Element wdur = dom.createElement("duration");
				wdur.appendChild(dom.createTextNode(Integer.toString(w.getDuration())));
				Element wspeed = dom.createElement("speed");
				wspeed.appendChild(dom.createTextNode(Integer.toString(w.getSpeed())));
				we.appendChild(wendx);
				we.appendChild(wendy);
				we.appendChild(wdur);
				we.appendChild(wspeed);
			} else {
				wm.appendChild(dom.createTextNode("0")); 
				we.appendChild(wm); }
			res.appendChild(we);			
		}
		return res;
	}
	
	/**
	 * Save the created level to an xml file.
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public void saveToXML() throws FileNotFoundException, UnsupportedEncodingException {
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        dom = db.newDocument();
	        Element rootEle = dom.createElement("level");
	        Element e = dom.createElement("time");
	        e.appendChild(dom.createTextNode(time));
	        rootEle.appendChild(e);
	        if (players.size() > 0) {
	        rootEle.appendChild(writePlayers()); }
	        if (balls.size() > 0) {
	        rootEle.appendChild(writeBalls()); }
	        if (walls.size() > 0) {
	        rootEle.appendChild(writeWalls()); }
	        dom.appendChild(rootEle);
	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	            // send DOM to file
	            String path = "src/main/resources/level/Custom/" + name + ".xml";
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new File(path))); }
	        catch (TransformerException te) {
	            System.out.println(te.getMessage()); }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce); }
	}
	
	/**
	 * Used by the LevelReader to make a list of all custom levels.
	 * @param names the names of the custom levels.
	 */
	public static void writeCustomLevels(String[] names) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document dom = db.newDocument();
	        Element rootEle = dom.createElement("Levels");
	        for (int i = 0; i < names.length; i++) {
		        Element e = dom.createElement("LevelName");
		        e.appendChild(dom.createTextNode(names[i]));
		        rootEle.appendChild(e);	        	
	        }
	        dom.appendChild(rootEle);
	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");	
	            String path = "src/main/resources/level/CustomSPLevels.xml";
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new File(path))); }
	        catch (TransformerException te) {
	            System.out.println(te.getMessage()); }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce); }
			
		}

}
