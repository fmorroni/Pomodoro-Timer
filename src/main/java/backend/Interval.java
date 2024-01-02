package backend;

public enum Interval {
  Work("Work"),
  ShortBreak("Short Break"),
  LongBreak("Long Break");

  private final String description;

  Interval(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return description;
  }
}
