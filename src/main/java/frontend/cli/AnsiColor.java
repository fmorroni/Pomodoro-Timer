package frontend.cli;

public enum AnsiColor {
  AnsiBlack("\u001B[30m"),
  AnsiRed("\u001B[31m"),
  AnsiGreen("\u001B[32m"),
  AnsiYellow("\u001B[33m"),
  AnsiBlue("\u001B[34m"),
  AnsiPurple("\u001B[35m"),
  AnsiCyan("\u001B[36m"),
  AnsiWhite("\u001B[37m"),
  Default("") {
    @Override
    public String convert(String s) {
      return s;
    }
  };

  private static final String reset = "\u001B[0m";
  private final String ansi;

  AnsiColor(String ansi) {
    this.ansi = ansi;
  }

  public String convert(String s) {
    return ansi + s + reset;
  }
}
