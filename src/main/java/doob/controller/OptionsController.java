package doob.controller;

import java.io.File;

import javafx.scene.input.KeyCode;

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

/**
 * Class to manage options.
 */
public class OptionsController {

	private String file;

	private KeyCode left;
	private KeyCode right;
	private KeyCode shoot;

	private int sound;

	/**
	 * Constructor.
	 *
	 * @param file
	 *            the filepath where the highscores are saved.
	 */
	public OptionsController(String file) {
		this.file = file;
	}

	/**
	 * Reads the XML file.
	 */
	public void read() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();

			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();

			parseControls(doc);
			parseSound(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses the controls from the XML.
	 * @param doc The document to be parsed.
	 */
	public void parseControls(Document doc) {
		NodeList nList = doc.getElementsByTagName("controls");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				this.left = KeyCode.getKeyCode(eElement.getElementsByTagName("left").item(0)
						.getTextContent());
				this.right = KeyCode.getKeyCode(eElement.getElementsByTagName("right").item(0)
						.getTextContent());
				this.shoot = KeyCode.getKeyCode(eElement.getElementsByTagName("shoot").item(0)
						.getTextContent());
			}
		}

	}

	/**
	 * Parses the sound level from the XML.
	 * @param doc The document to be parsed.
	 */
	public void parseSound(Document doc) {
		NodeList nListBall = doc.getElementsByTagName("sound");
		Node nNode = nListBall.item(0);
		this.sound = Integer.parseInt(nNode.getTextContent());
	}

	/**
	 * Writes the options to the file.
	 */
	public void write() {
		Document doc = appendElements();

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
	 * Append options elements to the document.
	 * @return The document.
	 */
	private Document appendElements() {
		Document doc = createDocument();
		Element rootElement = doc.createElement("options");
		doc.appendChild(rootElement);
		
		Element sound = doc.createElement("sound");
		sound.appendChild(doc.createTextNode(Integer.toString(this.sound)));
		rootElement.appendChild(sound);

		Element controls = doc.createElement("controls");
		rootElement.appendChild(controls);

		Element left = doc.createElement("left");
		left.appendChild(doc.createTextNode(this.left.getName()));
		controls.appendChild(left);

		Element right = doc.createElement("right");
		right.appendChild(doc.createTextNode(this.right.getName()));
		controls.appendChild(right);

		Element shoot = doc.createElement("shoot");
		shoot.appendChild(doc.createTextNode(this.shoot.getName()));
		controls.appendChild(shoot);
		
		return doc;
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

	public KeyCode getLeft() {
		return left;
	}

	public KeyCode getRight() {
		return right;
	}

	public KeyCode getShoot() {
		return shoot;
	}

	public int getSound() {
		return sound;
	}

	public void setLeft(KeyCode left) {
		this.left = left;
	}

	public void setRight(KeyCode right) {
		this.right = right;
	}

	public void setShoot(KeyCode shoot) {
		this.shoot = shoot;
	}

	public void setSound(int sound) {
		this.sound = sound;
	}
}
