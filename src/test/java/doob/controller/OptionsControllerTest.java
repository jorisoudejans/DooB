package doob.controller;

import javafx.scene.input.KeyCode;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Shane on 13-10-2015.
 */
public class OptionsControllerTest {

    @Test
    public void testControls(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse("src/test/resources/Options/Options_ControlsTest.xml");
            doc.getDocumentElement().normalize();


            OptionsController oc = new OptionsController("test");

            assertTrue(oc.getLeft() == null);
            assertTrue(oc.getRight() == null);
            assertTrue(oc.getShoot() == null);

            oc.parseControls(doc);

            assertEquals(KeyCode.A, oc.getLeft());
            assertEquals(KeyCode.D, oc.getRight());
            assertEquals(KeyCode.SPACE, oc.getShoot());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSound(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse("src/test/resources/Options/Options_SoundTest.xml");
            doc.getDocumentElement().normalize();

            OptionsController oc = new OptionsController("test");

            oc.parseSound(doc);

            assertEquals(87, oc.getSound());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testRead(){
        OptionsController oc  = new OptionsController("src/test/resources/Options/Options_ReadTest.xml");

        assertTrue(oc.getLeft() == null);
        assertTrue(oc.getRight() == null);
        assertTrue(oc.getShoot() == null);

        oc.read();

        assertEquals(KeyCode.J, oc.getLeft());
        assertEquals(KeyCode.L, oc.getRight());
        assertEquals(KeyCode.I, oc.getShoot());
        assertEquals(45, oc.getSound());

    }
}
