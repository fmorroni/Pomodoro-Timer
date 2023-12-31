import static org.junit.jupiter.api.Assertions.*;

import backend.Time;
import org.junit.jupiter.api.Test;

public class TimeTest {

  private void assertConversionToComponents(
      Time t, long H, long M, long S, long Ms, boolean isNeg, String from) {
    assertAll(
        "Time class " + from + " conversion",
        () -> {
          if (isNeg) assertTrue(t.isNegative(), "Negative?");
          else assertFalse(t.isNegative(), "Negative?");
        },
        () -> assertEquals(H, t.getHours(), "Hours"),
        () -> assertEquals(M, t.getMinutes(), "Minutes"),
        () -> assertEquals(S, t.getSeconds(), "Seconds"),
        () -> assertEquals(Ms, t.getMs(), "Milliseconds"));
  }

  @Test
  public void testConversionToComponents() {
    Time t = new Time(98625340);
    assertConversionToComponents(t, 27, 23, 45, 340, false, "milliseconds");

    t = Time.fromSeconds(98625.340);
    assertConversionToComponents(t, 27, 23, 45, 340, false, "seconds");

    t = Time.fromMinutes(98625.340 / 60);
    assertConversionToComponents(t, 27, 23, 45, 340, false, "minutes");

    t = Time.fromHours(98625.340 / 3600);
    assertConversionToComponents(t, 27, 23, 45, 340, false, "hours");

    t = new Time(-98625340);
    assertConversionToComponents(t, 27, 23, 45, 340, true, "milliseconds negative");
  }

  @Test
  public void testConversionToTotals() {
    long ms = 98625340L;
    Time t = new Time(ms);
    assertEquals(ms, t.getInMs(), "Milliseconds");
    assertEquals(ms / 1000.0, t.getInSeconds(), 1e-5, "Seconds");
    assertEquals(ms / 60000.0, t.getInMinutes(), 1e-5, "Minutes");
    assertEquals(ms / 3600000.0, t.getInHours(), 1e-5, "Hours");

    t = new Time(-1000);
    assertEquals(-1, t.getInSeconds(), 1e-5, "Seconds");
  }

  @Test
  public void testEquals() {
    Time t1 = new Time(100020);
    Time t2 = Time.fromSeconds(100.020);
    Time t3 = new Time(2000);

    assertEquals(t1, t2, "Equal times should be equal");
    assertNotEquals(t1, t3, "Different times should not be equal");
  }

  @Test
  public void testCompareTo() {
    Time t1 = new Time(100020);
    Time t2 = new Time(1000200000);
    Time t3 = new Time(10);
    Time t4 = Time.fromSeconds(100.020);

    assertTrue(t1.compareTo(t2) < 0, "%s should be smaller than %s".formatted(t1, t2));
    assertTrue(t1.compareTo(t3) > 0, "%s should be larger than %s".formatted(t1, t3));
    assertTrue(t1.compareTo(t4) == 0, "%s should be equal to %s".formatted(t1, t4));
  }

  @Test
  public void testRangeExceptions() {
    assertThrows(
        IllegalArgumentException.class,
        () -> Time.fromHours(Long.MAX_VALUE / 3600000.0),
        "Hours out of max range");
    assertDoesNotThrow(() -> Time.fromHours(Long.MAX_VALUE / 3600000.0 - 1), "Hours in max range");
    assertThrows(
        IllegalArgumentException.class,
        () -> Time.fromHours(Long.MIN_VALUE / 3600000.0),
        "Hours out of min range");
    assertDoesNotThrow(() -> Time.fromHours(Long.MIN_VALUE / 3600000.0 + 1), "Hours in min range");

    assertThrows(
        IllegalArgumentException.class,
        () -> Time.fromMinutes(Long.MAX_VALUE / 60000.0),
        "Minutes out of max range");
    assertDoesNotThrow(
        () -> Time.fromMinutes(Long.MAX_VALUE / 60000.0 - 1), "Minutes in max range");
    assertThrows(
        IllegalArgumentException.class,
        () -> Time.fromMinutes(Long.MIN_VALUE / 60000.0),
        "Minutes out of min range");
    assertDoesNotThrow(
        () -> Time.fromMinutes(Long.MIN_VALUE / 60000.0 + 1), "Minutes in min range");

    assertThrows(
        IllegalArgumentException.class,
        () -> Time.fromSeconds(Long.MAX_VALUE / 1000.0),
        "Seconds out of max range");
    assertDoesNotThrow(() -> Time.fromSeconds(Long.MAX_VALUE / 1000.0 - 2), "Seconds in max range");
    assertThrows(
        IllegalArgumentException.class,
        () -> Time.fromSeconds(Long.MIN_VALUE / 1000.0),
        "Seconds out of min range");
    assertDoesNotThrow(() -> Time.fromSeconds(Long.MIN_VALUE / 1000.0 + 2), "Seconds in min range");
  }

  @Test
  public void testMath() {
    Time t1 = new Time(12345);
    Time t2 = new Time(1234);
    Time t3 = new Time(123456);
    Time t4 = new Time(-t3.getInMs());

    assertEquals(
        new Time(t1.getInMs() - t2.getInMs()),
        t1.subtract(t2),
        "Subtraction positive");

    assertEquals(
        new Time(t1.getInMs() - t3.getInMs()),
        t1.subtract(t3),
        "Subtraction negative");

    assertEquals(
        new Time(t1.getInMs() + t3.getInMs()),
        t1.add(t3),
        "Addition");

    assertEquals(
        new Time(t1.getInMs() - t3.getInMs()),
        t1.add(t4),
        "Addition with negative");
  }
}
