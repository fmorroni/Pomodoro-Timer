package frontend;

import backend.Time;
import java.util.HashMap;
import java.util.Map;

public class TimeDisplay extends TextAreaWithBorder {
  private static final String format = "%c%02d:%02d ";
  private static final int charCount = format.formatted(' ', 0, 0).length();
  private String prevTimeStr = " ".repeat(charCount);

  private static final Map<Character, PrettyChar> charMap = new HashMap<>();

  static {
    charMap.put('0', PrettyChar.Zero);
    charMap.put('1', PrettyChar.One);
    charMap.put('2', PrettyChar.Two);
    charMap.put('3', PrettyChar.Three);
    charMap.put('4', PrettyChar.Four);
    charMap.put('5', PrettyChar.Five);
    charMap.put('6', PrettyChar.Six);
    charMap.put('7', PrettyChar.Seven);
    charMap.put('8', PrettyChar.Eight);
    charMap.put('9', PrettyChar.Nine);
    charMap.put(':', PrettyChar.Dots);
    charMap.put(' ', PrettyChar.Space);
    charMap.put('-', PrettyChar.Minus);
  }

  public TimeDisplay() {
    super(PrettyChar.height, PrettyChar.width * charCount, 1, 3);
    System.out.println(PrettyChar.width * charCount);
  }

  private void setChar(int col, PrettyChar c) {
    for (int i = 0; i < PrettyChar.height; ++i) {
      for (int j = 0; j < PrettyChar.width; ++j) {
        super.setChar(i, j + col * PrettyChar.width, c.representation[i][j]);
      }
    }
  }

  public void setDisplay(Time t) {
    String s = format.formatted(t.isNegative() ? '-' : ' ', t.getMinutes(), t.getSeconds());
    for (int i = 0; i < s.length(); ++i) {
      if (s.charAt(i) != prevTimeStr.charAt(i)) setChar(i, charMap.get(s.charAt(i)));
    }
    prevTimeStr = s;
  }

  private static enum PrettyChar {
    Zero("╔═══╗║╔═╗║║║ ║║║║ ║║║╚═╝║╚═══╝"),
    One("  ╔╗  ╔╝║  ╚╗║   ║║  ╔╝╚╗ ╚══╝"),
    Two("╔═══╗║╔═╗║╚╝╔╝║╔═╝╔╝║║╚═╗╚═══╝"),
    Three("╔═══╗║╔═╗║╚╝╔╝║╔╗╚╗║║╚═╝║╚═══╝"),
    Four("╔╗ ╔╗║║ ║║║╚═╝║╚══╗║   ║║   ╚╝"),
    Five("╔═══╗║╔══╝║╚══╗╚══╗║╔══╝║╚═══╝"),
    Six("╔═══╗║╔══╝║╚══╗║╔═╗║║╚═╝║╚═══╝"),
    Seven("╔═══╗║╔═╗║╚╝╔╝║  ║╔╝  ║║   ╚╝ "),
    Eight("╔═══╗║╔═╗║║╚═╝║║╔═╗║║╚═╝║╚═══╝"),
    Nine("╔═══╗║╔═╗║║╚═╝║╚══╗║╔══╝║╚═══╝"),
    Dots("           ╔═╗  ╚═╝  ╔═╗  ╚═╝ "),
    Space("                              "),
    Minus("          ╔═══╗╚═══╝          ");
    private static final int height = 6, width = 5;
    private final char[][] representation = new char[height][width];

    PrettyChar(String linearRepresentation) {
      for (int i = 0; i < height; ++i) {
        for (int j = 0; j < width; ++j) {
          representation[i][j] = linearRepresentation.charAt(i * width + j);
        }
      }
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < height; ++i) {
        for (int j = 0; j < width; ++j) {
          sb.append(representation[i][j]);
        }
        sb.append('\n');
      }
      return sb.toString();
    }
  }
}

// ╔═══╗     ╔╗     ╔═══╗    ╔═══╗    ╔╗ ╔╗    ╔═══╗    ╔═══╗    ╔═══╗    ╔═══╗    ╔═══╗
// ║╔═╗║    ╔╝║     ║╔═╗║    ║╔═╗║    ║║ ║║    ║╔══╝    ║╔══╝    ║╔═╗║    ║╔═╗║    ║╔═╗║
// ║║ ║║    ╚╗║     ╚╝╔╝║    ╚╝╔╝║    ║╚═╝║    ║╚══╗    ║╚══╗    ╚╝╔╝║    ║╚═╝║    ║╚═╝║   ╔═╗
// ║║ ║║     ║║     ╔═╝╔╝    ╔╗╚╗║    ╚══╗║    ╚══╗║    ║╔═╗║      ║╔╝    ║╔═╗║    ╚══╗║   ╚═╝
// ║╚═╝║    ╔╝╚╗    ║║╚═╗    ║╚═╝║       ║║    ╔══╝║    ║╚═╝║      ║║     ║╚═╝║    ╔══╝║   ╔═╗
// ╚═══╝    ╚══╝    ╚═══╝    ╚═══╝       ╚╝    ╚═══╝    ╚═══╝      ╚╝     ╚═══╝    ╚═══╝   ╚═╝

// ╔═══╗╔═══╗     ╔═══╗╔═══╗     ╔═══╗╔═══╗       ╔╗ ╔═══╗  ╔╗
// ║╔═╗║║╔═╗║     ║╔═╗║║╔══╝     ║╔═╗║║╔═╗║      ╔╝║ ║╔═╗║ ╔╝║
// ║║ ║║║║ ║║ ╔═╗ ╚╝╔╝║║╚══╗ ╔═╗ ║║ ║║╚╝╔╝║ ╔═╗  ╚╗║ ╚╝╔╝║ ╚╗║
// ║║ ║║║║ ║║ ╚═╝ ╔═╝╔╝╚══╗║ ╚═╝ ║║ ║║╔╗╚╗║ ╚═╝   ║║ ╔═╝╔╝  ║║
// ║╚═╝║║╚═╝║ ╔═╗ ║║╚═╗╔══╝║ ╔═╗ ║╚═╝║║╚═╝║ ╔═╗  ╔╝╚╗║║╚═╗ ╔╝╚╗
// ╚═══╝╚═══╝ ╚═╝ ╚═══╝╚═══╝ ╚═╝ ╚═══╝╚═══╝ ╚═╝  ╚══╝╚═══╝ ╚══╝
