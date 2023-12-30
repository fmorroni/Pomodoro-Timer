package backend;

public class Timer {
  private long start;
  private long ms;
  private double seconds, minutes;

  public Timer() {
    this(System.currentTimeMillis());
  }

  public Timer(long start) {
    if (start < 0) {
      throw new IllegalArgumentException("start can't be less than 0: start = %d".formatted(start));
    }
    this.start = start;
  }

  public void restart() {
    restart(System.currentTimeMillis());
  }

  public void restart(long start) {
    this.start = start;
    ms = 0;
    seconds = 0;
    minutes = 0;
  }

  public void takeLap() {
    takeLap(System.currentTimeMillis());
  }

  public void takeLap(long lapMs) {
    if (lapMs < start) {
      throw new IllegalArgumentException(
          "start can't larger than end: %d > %d".formatted(start, lapMs));
    }
    ms = lapMs - start;
    seconds = ms / 1000.0;
    minutes = seconds / 60;
  }

  public long getMs() {
    return ms;
  }

  public double getSeconds() {
    return seconds;
  }

  public double getMinutes() {
    return minutes;
  }

  public long getStart() {
    return start;
  }
}
