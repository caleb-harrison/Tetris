import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;

public class TetrominoComponent extends JComponent {

  public static final int BOX_SIZE = 33;

  private Tetromino tetromino;
  
  public void paintComponent(Graphics g) {

    if (tetromino == null) {
      return;
    }
      
    // Recover Graphics2D
    Graphics2D g2 = (Graphics2D) g;

    // draw background image
    // taken from https://tetris.com/play-tetris
    Image img = Toolkit.getDefaultToolkit().getImage("assets/tetrisbg.png");
    g.drawImage(img, 0, 0, this);

    // draw everything inside the image container
    g2.translate(336, 47);

    // draw border
    Rectangle border = new Rectangle(-1,
                                     -1,
                                     TetrisViewer.FRAME_WIDTH + 2,
                                     TetrisViewer.FRAME_HEIGHT + 1);
    g2.setColor(Color.BLACK);
    g2.draw(border);

    // draw the grid rows
    int amtrows = TetrisViewer.FRAME_HEIGHT / BOX_SIZE;
    Color gridColor = new Color(0,0,0,30);
    g2.setColor(gridColor);
    for (int i = 0; i < amtrows; i++) {
      g2.drawLine(0, i * BOX_SIZE, TetrisViewer.FRAME_WIDTH, i * BOX_SIZE);
    }

    // draw the grid columns
    int amtcols = TetrisViewer.FRAME_WIDTH / BOX_SIZE;
    for (int i = 0; i < amtcols; i++) {
      g2.drawLine(i * BOX_SIZE, 0, i * BOX_SIZE, TetrisViewer.FRAME_HEIGHT);
    }

    // draw tetromino
    Color tetrominoLightColor = tetromino.getLightColor();
    Color tetrominoDarkColor = tetromino.getDarkColor();
    Point tetrominoPosition = tetromino.getPosition();
    int[][] shapeArray = tetromino.getShapeArray();
    
    // iterate over the rows (vertical)
    for (int i = 0; i < shapeArray.length; i++) {
      int[] row = shapeArray[i];
      // iterate over the columns (horizontal) for a single row
      for (int j = 0; j < row.length; j++) {
        int b = row[j];
        if (b > 0) {
          Rectangle r = new Rectangle(tetrominoPosition.x,
                                      tetrominoPosition.y,
                                      BOX_SIZE,
                                      BOX_SIZE);
          r.translate(j * BOX_SIZE, i * BOX_SIZE);
          g2.setColor(tetrominoLightColor);
          g2.fill(r);
          g2.setColor(tetrominoDarkColor);
          g2.draw(r);
        }
      }
    }
    
  }
  
  public void setComponent(Tetromino tetromino) {
    this.tetromino = tetromino;
  }
  
}
