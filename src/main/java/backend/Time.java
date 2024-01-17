package backend;

public class Time implements Comparable<Time> {
  private final int ms, seconds, minutes;
  private final long hours;
  private final long totalMs;
  private final boolean isNegative;

  private static final int hoursToMsRatio = 60 * 60 * 1000;
  private static final int minutesToMsRatio = 60 * 1000;
  private static final int secondsToMsRatio = 1000;

  private static final long maxHours = Long.MAX_VALUE / hoursToMsRatio;
  private static final long minHours = Long.MIN_VALUE / hoursToMsRatio;
  private static final long maxMinutes = Long.MAX_VALUE / minutesToMsRatio;
  private static final long minMinutes = Long.MIN_VALUE / minutesToMsRatio;
  private static final long maxSeconds = Long.MAX_VALUE / secondsToMsRatio;
  private static final long minSeconds = Long.MIN_VALUE / secondsToMsRatio;

  public Time(long totalMilliseconds) {
    if (totalMilliseconds < 0) {
      isNegative = true;
      totalMilliseconds = -totalMilliseconds;
    } else {
      isNegative = false;
    }
    totalMs = totalMilliseconds;
    hours = totalMilliseconds / hoursToMsRatio;
    long minutesTot = totalMilliseconds / minutesToMsRatio;
    minutes = (int) (minutesTot - hours * 60);
    long secondsTot = totalMilliseconds / secondsToMsRatio;
    seconds = (int) (secondsTot - minutesTot * 60);
    ms = (int) (totalMilliseconds - secondsTot * 1000);
  }

  private static void throwIfOutOfRange(double val, long min, long max, String name) {
    if ((long) val <= min || (long) val >= max) {
      throw new IllegalArgumentException("%s = %f ∉ (%d, %d)".formatted(name, val, min, max));
    }
  }

  public static Time fromSeconds(double seconds) {
    throwIfOutOfRange(seconds, minSeconds, maxSeconds, "Seconds");
    return new Time((long) (seconds * secondsToMsRatio));
  }

  public static Time fromMinutes(double minutes) {
    throwIfOutOfRange(minutes, minMinutes, maxMinutes, "Minutes");
    return new Time((long) (minutes * minutesToMsRatio));
  }

  public static Time fromHours(double hours) {
    throwIfOutOfRange(hours, minHours, maxHours, "Hours");
    return new Time((long) (hours * hoursToMsRatio));
  }

  public boolean isNegative() {
    return isNegative;
  }

  public int getMs() {
    return ms;
  }

  public int getSeconds() {
    return seconds;
  }

  public int getMinutes() {
    return minutes;
  }

  public long getHours() {
    return hours;
  }

  public long toMs() {
    return (isNegative ? -1 : 1) * totalMs;
  }

  public double toSeconds() {
    return (double) toMs() / secondsToMsRatio;
  }

  public double toMinutes() {
    return (double) toMs() / minutesToMsRatio;
  }

  public double toHours() {
    return (double) toMs() / hoursToMsRatio;
  }

  public Time subtract(Time other) {
    return new Time(toMs() - other.toMs());
  }

  public Time add(Time other) {
    return new Time(toMs() + other.toMs());
  }

  public static Time now() {
    return new Time(System.currentTimeMillis());
  }

  @Override
  public String toString() {
    return "%s%02d:%02d:%02d:%02d".formatted(isNegative ? "-" : "", hours, minutes, seconds, ms);
  }

  public String toStringMinutesAndSeconds() {
    long mins = hours * 60 + minutes;
    long secs = Math.round(seconds + ms / 1000.0);
    return "%s%02d:%02d".formatted(isNegative ? "-" : "", mins, secs);
  }

  @Override
  public boolean equals(Object obj) {
    return obj == this
        || (obj instanceof Time other
            && totalMs == other.totalMs
            && isNegative == other.isNegative);
  }

  @Override
  public int compareTo(Time other) {
    if (this == other) return 0;
    return Long.valueOf(toMs()).compareTo(other.toMs());
  }
}
