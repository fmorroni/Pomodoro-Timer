package backend;

public class Timer {
  private Time start;
  private Time timeAtPause;
  private boolean isPaused = false;

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
    if (isPaused) return timeAtPause;
    else return endTime.subtract(start);
  }

  public void pause() {
    pause(Time.now());
  }

  protected void pause(Time endTime) {
    timeAtPause = getTime(endTime);
    isPaused = true;
  }

  public void play() {
    play(Time.now());
  }

  protected void play(Time unpauseTime) {
    start = unpauseTime.subtract(timeAtPause);
    isPaused = false;
  }

  public void togglePlayPause() {
    if (isPaused) play();
    else pause();
  }

  public boolean isPaused() {
    return isPaused;
  }
}
