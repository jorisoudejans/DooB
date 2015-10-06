package doob.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import doob.model.Score;

/**
 * Class to manage highscores.
 */
public class HighscoreController {

	private String file;
	private ArrayList<Score> highscores;
	public static final int MAX_SIZE = 10;

	/**
	 * Constructor.
	 * 
	 * @param file
	 *            the filepath where the highscores are saved.
	 */
	public HighscoreController(String file) {
		highscores = new ArrayList<Score>();
		this.file = file;
	}

	/**
	 * Read the highscores from the file.
	 * 
	 * @return A list of highscores.
	 */
	public ArrayList<Score> read() {
		Document doc = parseDocument();
		NodeList nListScores = doc.getElementsByTagName("highscore");
		for (int i = 0; i < nListScores.getLength(); i++) {
			Node nNode = nListScores.item(i);
			Element element = (Element) nNode;
			String name = element.getElementsByTagName("name").item(0)
					.getTextContent();
			int score = Integer.parseInt(element.getElementsByTagName("score")
					.item(0).getTextContent());
			highscores.add(new Score(name, score));
		}
		return highscores;
	}

	/**
	 * Writes the highscores to the file.
	 */
	public void write() {
		Document doc = createDocument();
		Element rootElement = doc.createElement("highscorelist");
		doc.appendChild(rootElement);
		for (Score score : highscores) {
			Element staff = doc.createElement("highscore");
			rootElement.appendChild(staff);

			Element firstname = doc.createElement("name");
			firstname.appendChild(doc.createTextNode(score.getName()));
			staff.appendChild(firstname);

			Element lastname = doc.createElement("score");
			lastname.appendChild(doc.createTextNode(score.getScore() + ""));
			staff.appendChild(lastname);
		}

		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(file));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Parses the file to a XML Document.
	 * 
	 * @return XML Document.
	 */
	private Document parseDocument() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();

			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a new XML Document ready to be written in.
	 * 
	 * @return new XML Document.
	 */
	private Document createDocument() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();

			Document doc = dBuilder.newDocument();
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the index where the parameter score should be placed in the
	 * highscorelist.
	 * 
	 * @param score
	 *            the score of which the index in the highscorelist is returned.
	 * @return index of the score.
	 */
	public int highScoreIndex(int score) {
		if (score <= highscores.get(highscores.size() - 1).getScore()) {
			return -1;
		}
		for (int i = 0; i < highscores.size(); i++) {
			if (score > highscores.get(i).getScore()) {
				return i;
			}
		}
		return highscores.size() - 1;
	}

	/**
	 * Add a score object on a given index in the highscorelist. List always has
	 * a maximum size of 10.
	 * 
	 * @param score
	 *            the score to add to the list.
	 * @param index
	 *            the index where to add the score.
	 */
	public void addScore(Score score, int index) {
		if (index >= 0 && index < highscores.size()) {
			highscores.add(index, score);
			if (highscores.size() > MAX_SIZE) {
				highscores.remove(0);
			}
		}
	}

	public ArrayList<Score> getHighscores() {
		return highscores;
	}

	public void setHighscores(ArrayList<Score> highscores) {
		this.highscores = highscores;
	}

}
