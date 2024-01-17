package backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TimerTest {
  private final Time start = new Time(3040);
  private final Time end = new Time(93743042);
  private final Timer tmr = new Timer(start);

  @Test
  public void timeTest() {
    assertEquals(new Time(end.subtract(start).toMs()), tmr.getTime(end), "Assert time");
  }

  @Test
  public void exceptionsTest() {
    assertDoesNotThrow(
        () -> {
          Timer tmr = new Timer();
          tmr.getTime();
        });
    assertDoesNotThrow(
        () -> {
          Timer tmr = new Timer(new Time(1000));
          tmr.getTime(new Time(1001));
        });
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Timer tmr = new Timer(new Time(1000));
          tmr.getTime(new Time(0));
        },
        "Lap time can't be less than start time");
    assertThrows(IllegalArgumentException.class, () -> new Timer(new Time(-4)));
  }

  @Test
  public void restartTest() {
    tmr.restart();
    assertEquals(
        Time.fromMinutes(10.5),
        tmr.getTime(Time.now().add(Time.fromMinutes(10.5))),
        "Should restart to current time and get time after 10.5 mins");
  }

  @Test
  public void pausePlayTest() {
    Time t0 = new Time(0);
    Time t1 = Time.fromSeconds(10);
    Time t2 = Time.fromSeconds(17);
    Time t3 = Time.fromSeconds(20);
    Timer tmr = new Timer(t0);
    Time beforePause = tmr.getTime(t1);
    tmr.pause(t1);
    Time afterPause = tmr.getTime(t2);
    assertEquals(beforePause, afterPause, "Time should remain the same while timer is paused.");
    tmr.play(t2);
    assertEquals(
        beforePause, tmr.getTime(t2), "Time should still be the same right after resuming.");
    assertEquals(
        t1.add(t3.subtract(t2)),
        tmr.getTime(t3),
        "Time should have progressed only the amount since resuming.");
    tmr.pause(t3);
    tmr.restart(t1);
    assertEquals(t0, tmr.getTime(t1), "Timer should reset even if paused.");
    assertEquals(t2.subtract(t1), tmr.getTime(t2), "Timer should resume if reset when paused.");
  }
}
