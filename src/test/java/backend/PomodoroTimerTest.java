package backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PomodoroTimerTest {
  @Test
  public void test() {
    PomodoroTimer pt =
        new PomodoroTimer(new Time(0))
            .setWorkDuration(Time.fromMinutes(32))
            .setRounds(5)
            .setShortBreakDuration(Time.fromMinutes(3.5));

    Time elapsedTime = Time.fromMinutes(10.4);
    assertEquals(
        pt.getCurrentIntervalDuration().subtract(elapsedTime), pt.getRemainingTime(elapsedTime));

    pt.nextInterval();
  }
}
