package backend;

import java.util.HashMap;
import java.util.Map;

public class PomodoroTimer {
  private int workIntervals = 4;
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

  public PomodoroTimer setWorkIntervalsAmount(int intervals) {
    this.workIntervals = intervals;
    return this;
  }

  private PomodoroTimer setIntervalDuration(Interval type, Time duration) {
    if (duration.isNegative()) {
      throw new IllegalArgumentException("Interval duration must be greater than 0");
    }
    intervalDurations.put(type, duration);
    return this;
  }

  public PomodoroTimer setWorkInterval(Time duration) {
    return setIntervalDuration(Interval.Work, duration);
  }

  public PomodoroTimer setShortBreakInterval(Time duration) {
    return setIntervalDuration(Interval.ShortBreak, duration);
  }

  public PomodoroTimer setLongBreakInterval(Time duration) {
    return setIntervalDuration(Interval.LongBreak, duration);
  }

  private Time getIntervalDuration(Interval type) {
    return intervalDurations.get(type);
  }

  public Time getWorkInterval() {
    return getIntervalDuration(Interval.Work);
  }

  public Time getShortBreakInterval() {
    return getIntervalDuration(Interval.ShortBreak);
  }

  public Time getLongBreakInterval() {
    return getIntervalDuration(Interval.LongBreak);
  }

  public Time getCurrentInterval() {
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
    currentInterval = (currentInterval + 1) % (2 * workIntervals);
    if (currentInterval == 2 * workIntervals - 1) {
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
  }

  public void resetTimer() {
    currentInterval = 0;
    currentIntervalType = Interval.Work;
    timer.restart();
  }

  @Override
  public String toString() {
    return ("Pomodoro Timer:\n"
            + "{\n"
            + "\tworkIntervals: %d,\n"
            + "\tcurrentInterval: %d,\n"
            + "\tcurrentIntervalType: %s,\n"
            // + "\tremainingTime: %s\n"
            + "\tIntervals: %s,\n"
            + "\n}")
        .formatted(
            workIntervals,
            currentInterval,
            currentIntervalType,
            // getRemainingTime(),
            intervalDurationsString().replace("\t", "\t\t").replace("}", "\t}"));
  }
}
