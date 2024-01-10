package backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TimerTest {
  private final Timer tmr;
  private final Time start = new Time(3040);
  private final Time end = new Time(93743042);

  TimerTest() {
    tmr = new Timer(start);
  }

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
}
