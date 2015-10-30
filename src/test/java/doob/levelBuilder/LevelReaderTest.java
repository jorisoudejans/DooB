package doob.levelBuilder;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Shane on 30-10-2015.
 * Tests for the LevelReader class.
 */
public class LevelReaderTest {

    @Test
    public void testMakeCustomLevelList() throws Exception{
        String pathOut = "src/main/resources/level/CustomSPLevels.xml";
        String pathExp = "src/test/resources/level/CustomSPLevelsOutR.xml";

        String tempPath = "src/main/resources/level/CustomSPLevelsCopy2.xml";


        //copy current customLevels file to switch back later
        Files.copy(Paths.get(pathOut), Paths.get(tempPath));

        LevelReader lr =  new LevelReader();
        lr.setPath("src/test/resources/level/Custom/");

        lr.makeCustomLevelList();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setIgnoringComments(true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc1 = db.parse(new File(pathExp));
        doc1.normalizeDocument();

        Document doc2 = db.parse(new File(pathOut));
        doc2.normalizeDocument();

        Assert.assertTrue(doc1.isEqualNode(doc2));

        //switch back to the right customLevels
        Files.deleteIfExists(Paths.get(pathOut));
        Files.copy(Paths.get(tempPath), Paths.get(pathOut));
        Files.deleteIfExists(Paths.get(tempPath));
    }

}
