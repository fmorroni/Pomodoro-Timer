package backend;

public class Timer {
  private Time start;

  public Timer() {
    this(Time.now());
  }

  protected Timer(Time start) {
    if (start.isNegative()) {
      throw new IllegalArgumentException("start can't be negative: start = %s".formatted(start));
    }
    this.start = start;
  }

  public void restart() {
    restart(Time.now());
  }

  protected void restart(Time start) {
    this.start = start;
  }

  public Time getTime() {
    return getTime(Time.now());
  }

  protected Time getTime(Time endTime) {
    if (endTime.compareTo(start) < 0) {
      throw new IllegalArgumentException("End time can't be smaller than start time");
    }
    return endTime.subtract(start);
  }
}
