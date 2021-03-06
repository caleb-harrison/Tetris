import javax.swing.*;

public class TetrisViewer {

  public static final int FRAME_WIDTH = TetrominoComponent.BOX_SIZE * 10;
  public static final int FRAME_HEIGHT = TetrominoComponent.BOX_SIZE * 20;
  public static final int SCOREBOARD_WIDTH = 250;
  
  public static void main(String[] args) {
    
    // set up the JFrame
    JFrame frame = new TetrisFrame(1000, 754); // have to add extra 55 to
    frame.setTitle("Caleb's Tetris");					 // prevent JFrame size bug

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

}
