package doob.controller;

import doob.App;
import doob.model.*;
import doob.model.Level.Builder;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Controller for games.
 */
public class GameController {

  @FXML
  Canvas canvas;

  @FXML
  private Canvas lives1;
  @FXML
  private Canvas lives2;
  @FXML
  private Label score1;
  @FXML
  private Label score2;
  @FXML
  private ProgressBar progressBar;

  /*
   * public GameController(Canvas canvas) { this.canvas = canvas; initialize(); }
   */

  /**
   * Initialization of the game pane.
   */
  @FXML
  public void initialize() {
    levelList = new ArrayList<String>();
    readLevels();
    gameState = GameState.RUNNING;
    level = new Level(canvas);
    level = new LevelFactory(levelList.get(currentLevel), canvas).build();
    gc = lives1.getGraphicsContext2D();
    createTimer();
    timer.start();
  }

  private GameState gameState;
  private ArrayList<String> levelList;
  private int currentLevel;
  private Level level;
  private AnimationTimer timer;
  private GraphicsContext gc;

  public void createTimer() {
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        level.update();
        updateScore();
        updateProgressBar();
        updateLives();
        checkCollisions();
        checkLevelComplete();
      }
    };
  }
  
  public void readLevels() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = factory.newDocumentBuilder();
      
      Document doc = dBuilder.parse(new File("src/main/resources/level/Levels.xml"));
      doc.getDocumentElement().normalize();
      
      NodeList levels = doc.getElementsByTagName("LevelName");
      for(int i = 0; i < levels.getLength(); i++) {
        Node n = levels.item(i);
        String s = "src/main/resources/level/" + n.getTextContent();
        levelList.add(s);
      }
      currentLevel = 0;
      
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  public void checkCollisions() {
    if (level.ballPlayerCollision()) {
      if (level.getPlayers().get(0).getLives() == 1) {
        level.drawText(new Image("/image/gameover.png"));
      } else {
        level.drawText(new Image("/image/crushed.png"));
      }
      createFreeze();
    }
  }

  public void checkLevelComplete() {
    if (level.getBalls().size() <= 0) {
      level.drawText(new Image("/image/levelcomplete.png"));
      createFreeze();
    }
  }

  public void updateScore() {
    int score = level.getScore();
    score1.setText(score + "");
  }

  public void updateProgressBar() {
    double progress = level.getCurrentTime() / level.getTime();
    if (progress <= 0) {
      createFreeze();
      return;
    }
    progressBar.setProgress(progress);
  }

  public void updateLives() {
    gc.clearRect(0, 0, lives1.getWidth(), lives2.getHeight());
    for (Player p : level.getPlayers()) {
      for (int i = 0; i < p.getLives(); i++) {
        gc.drawImage(new Image("/image/heart.png"), i * 40, 8);
      }
    }
  }

  public void newLevel() {
    int lives = level.getPlayers().get(0).getLives();
    level = new LevelFactory(levelList.get(currentLevel), canvas).build();
    level.getPlayers().get(0).setLives(lives);
  }

  public void createFreeze() {
    Task<Void> sleeper = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        return null;
      }
    };
    sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
      public void handle(WorkerStateEvent event) {
        if (level.getPlayers().get(0).getLives() == 1) {
          level.gameOver();
        } else if (level.getBalls().size() == 0) {
          if(currentLevel < levelList.size()) {
            currentLevel++;
            newLevel();
            timer.start();
          } else {
            App.loadScene("/fxml/menu.fxml");
          }
        } else {
          level.crushed();
          newLevel();
          timer.start();
        }
      }
    });
    new Thread(sleeper).start();
    timer.stop();

  }

  public Level getLevel() {
    return level;
  }

  public GameState getGameState() {
    return gameState;
  }

  /**
   * States the game can be in.
   */
  public enum GameState {
    PAUSED, RUNNING, WON, LOST
  }
}
