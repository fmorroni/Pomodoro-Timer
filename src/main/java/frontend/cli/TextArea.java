package frontend.cli;

public class TextArea {
  protected final int rows, cols;
  private final char[][] area;

  public TextArea(int rows, int cols) {
    this.cols = cols;
    this.rows = rows;
    area = new char[rows][cols];
    reset();
  }

  public void reset() {
    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        area[i][j] = ' ';
      }
    }
  }

  public void setChar(int row, int col, char c) {
    area[row][col] = c;
  }

  public void setString(int row, int col, String s) {
    boolean wrap = false;
    int k = 0;
    for (int i = row; i < rows && k < s.length(); ++i) {
      int offset = wrap ? 0 : col;
      for (int j = 0; j + offset < cols && k < s.length(); ++j, ++k) {
        area[i][j + offset] = s.charAt(k);
      }
      wrap = true;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        sb.append(area[i][j]);
      }
      sb.append('\n');
    }
    return sb.toString();
  }
}
