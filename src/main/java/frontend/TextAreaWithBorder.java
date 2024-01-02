package frontend;

public class TextAreaWithBorder extends TextArea {
  private final int rowsInner, colsInner, borderOffsetRow, borderOffsetCol;

  public TextAreaWithBorder(int rows, int cols) {
    this(rows, cols, 0);
  }

  public TextAreaWithBorder(int rowsInner, int colsInner, int borderOffset) {
    this(rowsInner, colsInner, borderOffset, borderOffset);
  }

  public TextAreaWithBorder(
      int rowsInner, int colsInner, int borderOffsetRow, int borderOffsetCol) {
    super(rowsInner + 2 * borderOffsetRow + 2, colsInner + 2 * borderOffsetCol + 2);
    if (borderOffsetRow < 0 || borderOffsetCol < 0) {
      throw new IllegalArgumentException("Border offset must be greater than 0");
    }
    this.rowsInner = rowsInner;
    this.colsInner = colsInner;
    this.borderOffsetRow = borderOffsetRow;
    this.borderOffsetCol = borderOffsetCol;
    super.reset();
    setBorder();
  }

  private void setBorder() {
    super.setChar(0, 0, Border.TopLeftCorner.getChar());
    super.setChar(0, cols - 1, Border.TopRightCorner.getChar());
    for (int i = 1; i < cols - 1; ++i) {
      super.setChar(0, i, Border.HorizontalLine.getChar());
    }
    super.setChar(rows - 1, 0, Border.BottomLeftCorner.getChar());
    super.setChar(rows - 1, cols - 1, Border.BottomRightCorner.getChar());
    for (int i = 1; i < cols - 1; ++i) {
      super.setChar(rows - 1, i, Border.HorizontalLine.getChar());
    }
    for (int i = 1; i < rows - 1; ++i) {
      super.setChar(i, 0, Border.VerticalLine.getChar());
      super.setChar(i, cols - 1, Border.VerticalLine.getChar());
    }
  }

  public void reset() {
    for (int i = 0; i < rowsInner; ++i) {
      for (int j = 0; j < colsInner; ++j) {
        setChar(i, j, ' ');
      }
    }
  }

  public void setChar(int row, int col, char c) {
    super.setChar(row + borderOffsetRow + 1, col + borderOffsetCol + 1, c);
  }

  public void setString(int row, int col, String s) {
    super.setString(rowsInner, colsInner, row, col, s);
  }

  private static enum Border {
    // ┌─────┐
    // │     │
    // └─────┘
    TopLeftCorner('┌'),
    TopRightCorner('┐'),
    BottomLeftCorner('└'),
    BottomRightCorner('┘'),
    HorizontalLine('─'),
    VerticalLine('│');

    private final char c;

    Border(char c) {
      this.c = c;
    }

    public char getChar() {
      return c;
    }
  }
}
