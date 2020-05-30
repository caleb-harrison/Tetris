import java.awt.Color;
import java.awt.Point;

public class Tetromino extends TetrominoComponent {
  
  private Point position;
  private Color lightcolor;
  private Color darkcolor;
  private char shapeName;
  private int[][] shapeArray;

  private static final int[][] I_SHAPE = {{1},
                                          {1},
                                          {1},
                                          {1}};

  private static final int[][] J_SHAPE = {{0, 1}, 
                                          {0, 1},
                                          {1, 1}};

  private static final int[][] L_SHAPE = {{1, 0}, 
                                          {1, 0},
                                          {1, 1}};
  
  private static final int[][] T_SHAPE = {{1, 1, 1}, 
                                          {0, 1, 0}};

  private static final int[][] S_SHAPE = {{0, 1, 1}, 
                                          {1, 1, 0}};

  private static final int[][] Z_SHAPE = {{1, 1, 0}, 
                                          {0, 1, 1}};
                                                                                  
  private static final int[][] O_SHAPE = {{1, 1},
                                          {1, 1}};
  
  // 3-argument constructor
  public Tetromino(char shapeName, Color lightcolor, Color darkcolor) {
    // call the 4-argument constructor with this info
    this(shapeName, lightcolor, darkcolor, new Point(0, 0));
  }

  // 4-argument constructor
  public Tetromino(char shapeName, Color lightcolor, Color darkcolor, Point position) {
    this.shapeName = shapeName;
    this.lightcolor = lightcolor;
    this.darkcolor = darkcolor;
    this.position = position;
    
    if (shapeName == 'I') {
      this.shapeArray = I_SHAPE;
    } else if (shapeName == 'J') {
      this.shapeArray = J_SHAPE;
    } else if (shapeName == 'L') {
      this.shapeArray = L_SHAPE;
    } else if (shapeName == 'T') {
      this.shapeArray = T_SHAPE;
    } else if (shapeName == 'S') {
      this.shapeArray = S_SHAPE;
    } else if (shapeName == 'Z') {
      this.shapeArray = Z_SHAPE;
    } else if (shapeName == 'O') {
      this.shapeArray = O_SHAPE;
    } else {
      throw new RuntimeException("unknown shape name!");
    }
    
  }

  
  public char getShapeName() {
    return this.shapeName;
  }

  public int[][] getShapeArray() {
    return this.shapeArray;
  }

  public void setShapeArray(int[][] matrix) {
    this.shapeArray = matrix;
  }

  public void rotate(Tetromino tetromino) {
    int[][] matrix = null;
    // get the transpose of current matrix
    matrix = getTranspose(this.getShapeArray());
    // reverse that matrix
    matrix = getReverse(matrix);

    int[][] test = matrix;
    // get width and height of new matrix
    int tetrominoWidth = getMatrixWidthInBoxes(test) * TetrominoComponent.BOX_SIZE;
    int tetrominoHeight = getMatrixHeightInBoxes(test) * TetrominoComponent.BOX_SIZE;

    // if it would go outside the box, dont rotate it
    if (tetrominoWidth + tetromino.getPosition().x > TetrisViewer.FRAME_WIDTH ||
        tetrominoHeight + tetromino.getPosition().y > TetrisViewer.FRAME_HEIGHT) {
      System.out.println("cant rotate here");
    } else {
      // otherwise set the new matrix as the current one
      tetromino.setShapeArray(matrix);
      System.out.println(tetrominoWidth);
      System.out.println("rotated");
      // repaint it
      repaint();
    }
  }

  public int[][] getTranspose(int[][] matrix) {
    int[][] newMatrix = new int[matrix[0].length][matrix.length];

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        newMatrix[j][i] = matrix[i][j];
      }
    }
    return newMatrix;
  }

  public int[][] getReverse(int[][] matrix) {
    int middle = matrix.length / 2;

    for(int i = 0; i < middle; i++) {
      int[] m = matrix[i];
      matrix[i] = matrix[matrix.length - i - 1];
      matrix[matrix.length - i - 1] = m;
    }
    return matrix;

  }
  
  public Color getLightColor() {
    return this.lightcolor;
  }

  public Color getDarkColor() {
    return this.darkcolor;
  }
  
  public Point getPosition() {
    return this.position;
  }

  public void setPosition(Point position) {
    this.position = position;
    repaint();
  }
  
  public int getWidthInBoxes() {
    int width = 0;
    for (int[] row : shapeArray) {
      for (int j = 0; j < row.length; j++) {
        int b = row[j];
        if ((b > 0) && ((j + 1) > width)) {
          width = j + 1;
        }
      }
    }
    return width;
  }

  public int getMatrixWidthInBoxes(int[][] matrix) {
    int width = 0;
    for (int[] row : matrix) {
      for (int j = 0; j < row.length; j++) {
        int b = row[j];
        if ((b > 0) && ((j + 1) > width)) {
          width = j + 1;
        }
      }
    }
    return width;
  }
  
  public int getHeightInBoxes() {
    int height = 0;
    for (int[] row : shapeArray) {
      if (blocksInRow(row) > 0) {
        height = height + 1;
      }
    }
    return height;
  }

  public int getMatrixHeightInBoxes(int[][] matrix) {
    int height = 0;
    for (int[] row : matrix) {
      if (blocksInRow(row) > 0) {
        height = height + 1;
      }
    }
    return height;
  }
  
  private int blocksInRow(int[] row) {
    int blocks = 0;
    for (int b : row) {
      if (b > 0) {
        blocks = blocks + 1;
      }
    }
    return blocks;
  }
  
}

/* Old versions of tetromino shapes


private static final int[][] I_SHAPE = {{1, 0, 0, 0},
                                        {1, 0, 0, 0},
                                        {1, 0, 0, 0},
                                        {1, 0, 0, 0}};

  private static final int[][] J_SHAPE = {{0, 1, 0, 0}, 
                                          {0, 1, 0, 0},
                                          {1, 1, 0, 0},
                                          {0, 0, 0, 0}};

  private static final int[][] L_SHAPE = {{1, 0, 0, 0}, 
                                          {1, 0, 0, 0},
                                          {1, 1, 0, 0},
                                          {0, 0, 0, 0}};
  
  private static final int[][] T_SHAPE = {{1, 1, 1, 0}, 
                                          {0, 1, 0, 0},
                                          {0, 0, 0, 0},
                                          {0, 0, 0, 0}};

  private static final int[][] S_SHAPE = {{0, 1, 1, 0}, 
                                          {1, 1, 0, 0},
                                          {0, 0, 0, 0},
                                          {0, 0, 0, 0}};

  private static final int[][] Z_SHAPE = {{1, 1, 0, 0}, 
                                          {0, 1, 1, 0},
                                          {0, 0, 0, 0},
                                          {0, 0, 0, 0}};
                                                                                  
  private static final int[][] O_SHAPE = {{1, 1, 0, 0},
                                          {1, 1, 0, 0},
                                          {0, 0, 0, 0},
                                          {0, 0, 0, 0}};

*/
