package backend;

import java.util.HashMap;
import java.util.Map;

public class PomodoroTimer {
  private int workIntervals = 4;
  private int currentInterval = 0;
  private Interval currentIntervalType = Interval.Work;
  private final Map<Interval, Double> intervalDurations = new HashMap<>();
  private final Timer timer;

  public PomodoroTimer() {
    this(System.currentTimeMillis());
  }

  public PomodoroTimer(long startTimeMs) {
    timer = new Timer(startTimeMs);
    intervalDurations.put(Interval.Work, 25.0);
    intervalDurations.put(Interval.ShortBreak, 5.0);
    intervalDurations.put(Interval.LongBreak, 15.0);
  }

  private double currentDuration() {
    return intervalDurations.get(currentIntervalType);
  }

  public double getRemainingTime() {
    timer.takeLap();
    return currentDuration() - timer.getMinutes();
  }

  public double getRemainingTime(double elapsedMins) {
    long elapsedMs = (long) (elapsedMins * 60 * 1000);
    timer.takeLap(elapsedMs);
    return currentDuration() - timer.getMinutes();
  }

  public PomodoroTimer setWorkIntervalsAmount(int intervals) {
    this.workIntervals = intervals;
    return this;
  }

  private PomodoroTimer setIntervalDuration(Interval type, double duration) {
    if (duration <= 0) {
      throw new IllegalArgumentException("Interval duration must be greater than 0");
    }
    intervalDurations.put(type, duration);
    return this;
  }

  public PomodoroTimer setWorkInterval(double duration) {
    return setIntervalDuration(Interval.Work, duration);
  }

  public PomodoroTimer setShortBreakInterval(double duration) {
    return setIntervalDuration(Interval.ShortBreak, duration);
  }

  public PomodoroTimer setLongBreakInterval(double duration) {
    return setIntervalDuration(Interval.LongBreak, duration);
  }

  private double getIntervalDuration(Interval type) {
    return intervalDurations.get(type);
  }

  public double getWorkInterval() {
    return getIntervalDuration(Interval.Work);
  }

  public double getShortBreakInterval() {
    return getIntervalDuration(Interval.ShortBreak);
  }

  public double getLongBreakInterval() {
    return getIntervalDuration(Interval.LongBreak);
  }

  public double getCurrentInterval() {
    return getIntervalDuration(currentIntervalType);
  }

  public Interval getCurrentIntervalType() {
    return currentIntervalType;
  }

  private String intervalDurationsString() {
    StringBuilder sb = new StringBuilder("{\n");
    intervalDurations.forEach((key, value) -> sb.append("\t%s: %.1f,\n".formatted(key, value)));
    sb.append("}");
    return sb.toString();
  }

  public void nextInterval() {
    ++currentInterval;
    if (currentInterval == 2 * workIntervals) {
      currentIntervalType = Interval.LongBreak;
    } else if (currentInterval % 2 == 0) {
      currentIntervalType = Interval.Work;
    } else {
      currentIntervalType = Interval.ShortBreak;
    }
    timer.restart();
  }

  @Override
  public String toString() {
    return ("Pomodoro Timer:\n"
            + "{\n"
            + "\tworkIntervals: %d,\n"
            + "\tcurrentInterval: %d,\n"
            + "\tcurrentIntervalType: %s,\n"
            + "\tIntervals: %s,\n"
            // + "\tremainingTime: %.1f mins\n"
            + "\n}")
        .formatted(
            workIntervals,
            currentInterval,
            currentIntervalType,
            intervalDurationsString().replace("\t", "\t\t").replace("}", "\t}"));
  }
}
