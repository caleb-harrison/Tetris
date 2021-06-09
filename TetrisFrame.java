import javax.swing.JFrame;
import javax.swing.KeyStroke;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.Timer;

/*
 * Ideas clarified and corrected by Reading Big Java, Ch 10.9, 10.10, and special topic 10.5
 */
public class TetrisFrame extends JFrame {
  
  public static final int MAX_COLOR_VALUE = 256;
  public static final int AMOUNT_TO_MOVE_EACH_CYCLE = TetrominoComponent.BOX_SIZE;  // this is the drop rate
  public static final int DELAY_IN_MILLISEC = 300;  // set the delay between timer events
  
  private Tetromino tetromino;
  private TetrominoComponent component;
  private Timer timer;
  private boolean paused = false;
  private int boardWidth;
  private int boardHeight;
  
  /*
   * This class implements the timer listener. Every time the timer ticks,
   * we want our piece to move
   */
  class TimerListener implements ActionListener{
    public void actionPerformed(ActionEvent event) {
      // every time the timer ticks, do the following:

      // if the tetromino is falling..
      if (isFalling(tetromino)) {
        Point p = new Point(tetromino.getPosition().x,
                            tetromino.getPosition().y + AMOUNT_TO_MOVE_EACH_CYCLE);
        //System.out.println(p);
        if (withinBounds(tetromino, p)) {
          tetromino.setPosition(p);
        }
        repaint();
      } else {
        // save current tetromino
        

        // drop new tetromino
        tetromino = createRandomTetromino();
        component.setComponent(tetromino);
      }

    }
  } 
  
  /**
   * This class listens for keyboard inputs, and moves the piece accordingly
   */
  class KeyBoardListener implements KeyListener{
    public void keyPressed(KeyEvent event) {
      String key = KeyStroke.getKeyStrokeForEvent(event).toString();

      System.out.println("key pressed: " + key);
      
      key = key.replace("pressed ",  "");   // get rid of the first part
      if (key.contentEquals("SPACE")) { // pause or unpause
        if (paused == true) {
          timer.start();
          paused = false;
          System.out.println("unpaused!\n");
        } else {
          timer.stop();
          paused = true;
          System.out.println("paused!\n");
        }
      } else if (paused == false) {
        if (key.contentEquals("LEFT")) {  // move one block left
          Point p = new Point(tetromino.getPosition().x - TetrominoComponent.BOX_SIZE,
                              tetromino.getPosition().y);
          if (withinBounds(tetromino, p) && isFalling(tetromino)) {
            tetromino.setPosition(p);
            System.out.println(p);
          }
        }
        if (key.contentEquals("RIGHT")) {  // move one block right
          Point p = new Point(tetromino.getPosition().x + TetrominoComponent.BOX_SIZE,
                              tetromino.getPosition().y);
          if (withinBounds(tetromino, p) && isFalling(tetromino)) {
            tetromino.setPosition(p);
            System.out.println(p);
          }
        }
        if (key.contentEquals("DOWN")) {  // drop to the bottom
          Point p = new Point(tetromino.getPosition().x,
                              TetrisViewer.FRAME_HEIGHT - (tetromino.getHeightInBoxes() * TetrominoComponent.BOX_SIZE) - 1);
          if (withinBounds(tetromino, p) && isFalling(tetromino)) {
            tetromino.setPosition(p);
            System.out.println(p);
          }
        }
        if (key.contentEquals("R")) {  // reset
          tetromino = createRandomTetromino();
          component.setComponent(tetromino);
        }

        if (key.contentEquals("UP")) {  // rotate
          Point p = new Point(tetromino.getPosition().x,
                              tetromino.getPosition().y);
          if (withinBounds(tetromino, p) && isFalling(tetromino)) {
            tetromino.rotate(tetromino);
          }
        }
        repaint();
      }
    }
    
    // we don't care about these, but we're bound by the interface to implement them
    public void keyReleased(KeyEvent event) {}
    public void keyTyped(KeyEvent event) {}
  }

  private Tetromino createRandomTetromino() {
    Random rand = new Random();
    
    // randomly pick the color
    // rand.nextInt((max - min) + 1) + min
    int redColor = rand.nextInt((MAX_COLOR_VALUE - 75) + 1) + 74;
    int greenColor = rand.nextInt((MAX_COLOR_VALUE - 75) + 1) + 74;
    int blueColor = rand.nextInt((MAX_COLOR_VALUE - 75) + 1) + 74;

    // fill color of each box on the tetromino
    Color myColor = new Color(redColor, greenColor, blueColor);

    // outline color of each box on the tetromino
    Color myColorDarker = new Color(redColor - 49, greenColor - 49, blueColor - 49);
    
    // set the shape
    char[] allTheTetrominos = {'I', 'J', 'L', 'T', 'S', 'Z', 'O'};
    int pickThisTetrominoNumber = rand.nextInt(allTheTetrominos.length);
    Tetromino tetromino = new Tetromino(allTheTetrominos[pickThisTetrominoNumber], myColor, myColorDarker);
    
    // needs to pick x value between one box away from each edge
    // value must be in increments of boxsize(i.e. 30,60,90..)
    int boundary = TetrisViewer.FRAME_WIDTH - (tetromino.getWidthInBoxes() * TetrominoComponent.BOX_SIZE);
    ArrayList<Integer> goodXValues = new ArrayList<Integer>();

    int i = TetrominoComponent.BOX_SIZE; // array list starts one block away from edge
    while (i < boundary) {
      goodXValues.add(i);
      i += TetrominoComponent.BOX_SIZE;
    }
    int index = rand.nextInt(goodXValues.size());
    int x = goodXValues.get(index);

    int y = 0;
    Point myPoint = new Point(x, y);
    tetromino.setPosition(myPoint);

    return tetromino;
  }
  
  /**
   * Sets up a TetrisFrame that handles most of our tetromino control
   * @param width: takes an integer for the overall frame width
   * @param height: takes an integer for the overall frame height
   */
  public TetrisFrame(int width, int height) {

    // initialize variables
    this.boardWidth = width;
    this.boardHeight = height;
    this.setSize(width, height);

    // add the TetrominoComponent Component
    component = new TetrominoComponent();
    tetromino = createRandomTetromino();
    component.setComponent(tetromino);
    this.add(component);
    
    // add the timer listener
    ActionListener listener = new TimerListener();
    int delay = DELAY_IN_MILLISEC;
    timer = new Timer(delay, listener);
    timer.start();
    
    // add the keyboard listener
    KeyBoardListener kBListener = new KeyBoardListener();
    component.addKeyListener(kBListener);
    component.setFocusable(true);
  }
  
  /**
   * Determine if this tetromino is within the frame bounds if moved to location P
   * @param t: the Tetromino to be examined
   * @param p: the point to move it to
   * @return boolean, True if the tetromino will still be in bounds when moved to point P, false otherwise
   */
  public boolean withinBounds(Tetromino t, Point p) {
    
    int newXLeftPosition = p.x;
    int newXRightPosition = p.x + (t.getWidthInBoxes() * TetrominoComponent.BOX_SIZE);
    int newYTopPosition = p.y;
    int newYBottomPosition = p.y + (t.getHeightInBoxes() * TetrominoComponent.BOX_SIZE);
    
    if ((newXLeftPosition >= 0) &&
        (newXRightPosition <= TetrisViewer.FRAME_WIDTH) &&
        (newYTopPosition >= 0) &&
        (newYBottomPosition <= TetrisViewer.FRAME_HEIGHT)) {
      return true;
    } else {
      return false;
    }
  }

  // returns true if block is still falling
  public boolean isFalling(Tetromino t) {
    // this point represents the lowest the block can go
    //     if we want to later make it stop when it hits the top
    //     of a tetromino, this point p will be anywhere there is a 1
    //     in the grid
    Point p = new Point(t.getPosition().x,
                        t.getPosition().y + AMOUNT_TO_MOVE_EACH_CYCLE);
    //*
    if (!withinBounds(t, p)) {
      System.out.println("not falling");
      return false;
    } else {
      System.out.println("falling..");
      return true;
    }
  }  
  

}
