package backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PomodoroTimerTest {
  @Test
  public void test() {
    PomodoroTimer pt =
        new PomodoroTimer(new Time(0))
            .setWorkInterval(Time.fromMinutes(32))
            .setWorkIntervalsAmount(5)
            .setShortBreakInterval(Time.fromMinutes(3.5));

    Time elapsedTime = Time.fromMinutes(10.4);
    assertEquals(
        pt.getCurrentInterval().subtract(elapsedTime), pt.getRemainingTime(elapsedTime));

    pt.nextInterval();
  }
}
