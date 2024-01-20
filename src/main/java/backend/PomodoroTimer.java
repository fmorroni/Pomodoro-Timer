package backend;

import java.util.HashMap;
import java.util.Map;

public class PomodoroTimer {
  private int rounds = 4;
  private int currentInterval = 0;
  private Interval currentIntervalType = Interval.Work;
  private final Map<Interval, Time> intervalDurations = new HashMap<>();
  private final Timer timer;

  public PomodoroTimer() {
    this(Time.now());
  }

  protected PomodoroTimer(Time start) {
    timer = new Timer(start);
    intervalDurations.put(Interval.Work, Time.fromMinutes(25));
    intervalDurations.put(Interval.ShortBreak, Time.fromMinutes(5));
    intervalDurations.put(Interval.LongBreak, Time.fromMinutes(15));
  }

  private Time currentDuration() {
    return intervalDurations.get(currentIntervalType);
  }

  public Time getRemainingTime() {
    return getRemainingTime(Time.now());
  }

  protected Time getRemainingTime(Time elapsed) {
    return currentDuration().subtract(timer.getTime(elapsed));
  }

  public PomodoroTimer setRounds(int rounds) {
    this.rounds = rounds;
    return this;
  }

  public int getRounds() {
    return rounds;
  }

  private PomodoroTimer setIntervalDuration(Interval type, Time duration) {
    if (duration.isNegative()) {
      throw new IllegalArgumentException("Interval duration must be greater than 0");
    }
    intervalDurations.put(type, duration);
    return this;
  }

  public PomodoroTimer setWorkDuration(Time duration) {
    return setIntervalDuration(Interval.Work, duration);
  }

  public PomodoroTimer setShortBreakDuration(Time duration) {
    return setIntervalDuration(Interval.ShortBreak, duration);
  }

  public PomodoroTimer setLongBreakDuration(Time duration) {
    return setIntervalDuration(Interval.LongBreak, duration);
  }

  private Time getIntervalDuration(Interval type) {
    return intervalDurations.get(type);
  }

  public Time getWorkDuration() {
    return getIntervalDuration(Interval.Work);
  }

  public Time getShortBreakDuration() {
    return getIntervalDuration(Interval.ShortBreak);
  }

  public Time getLongBreakDuration() {
    return getIntervalDuration(Interval.LongBreak);
  }

  public Time getCurrentIntervalDuration() {
    return getIntervalDuration(currentIntervalType);
  }

  public Interval getCurrentIntervalType() {
    return currentIntervalType;
  }

  private String intervalDurationsString() {
    StringBuilder sb = new StringBuilder("{\n");
    intervalDurations.forEach(
        (intervalType, duration) -> sb.append("\t%s: %s,\n".formatted(intervalType, duration)));
    sb.append("}");
    return sb.toString();
  }

  public void nextInterval() {
    nextInterval(Time.now());
  }

  protected void nextInterval(Time intervalStart) {
    currentInterval = (currentInterval + 1) % (2 * rounds);
    if (currentInterval == 2 * rounds - 1) {
      currentIntervalType = Interval.LongBreak;
    } else if (currentInterval % 2 == 0) {
      currentIntervalType = Interval.Work;
    } else {
      currentIntervalType = Interval.ShortBreak;
    }
    timer.restart(intervalStart);
  }

  public void resetInterval() {
    timer.restart();
    pause();
  }

  public void resetTimer() {
    currentInterval = 0;
    currentIntervalType = Interval.Work;
    timer.restart();
    pause();
  }

  public void pause() {
    timer.pause();
  }

  public void play() {
    timer.play();
  }

  public boolean isPaused() {
    return timer.isPaused();
  }

  @Override
  public String toString() {
    return ("rounds: %d\n"
            + "currentInterval: %d\n"
            + "currentIntervalType: %s\n"
            + "Intervals: %s")
        .formatted(
            rounds,
            currentInterval,
            currentIntervalType,
            intervalDurationsString().replace("\t", "\t\t").replace("}", "\t}"));
  }

  public int getCurrentRound() {
    return currentInterval / 2 + 1;
  }
}
