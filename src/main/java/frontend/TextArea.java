package frontend;

public class TextArea {
  protected final int rows, cols;
  private final char[][] area;

  private AnsiColor color = AnsiColor.Default;

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

  protected void setString(int totRows, int totCols, int row, int col, String s) {
    boolean wrap = false;
    int k = 0;
    for (int i = row; i < totRows && k < s.length(); ++i) {
      int offset = wrap ? 0 : col;
      for (int j = 0; j + offset < totCols && k < s.length(); ++j, ++k) {
        setChar(i, j + offset, s.charAt(k));
      }
      wrap = true;
    }
  }

  public void setString(int row, int col, String s) {
    setString(rows, cols, row, col, s);
  }

  public void setColor(AnsiColor color) {
    this.color = color;
  }

  public int getWidth() {
      return cols;
  }

  public int getHeight() {
      return rows;
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
    return color.convert(sb.toString());
  }
}
