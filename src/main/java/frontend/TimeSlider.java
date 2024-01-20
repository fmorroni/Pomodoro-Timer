package frontend;

import backend.Time;
import java.util.function.Function;
import javafx.util.StringConverter;

public class TimeSlider extends TitledLabeledSlider<Time> {
  // private static final int TICK_UNITS = 30;
  private static final int TICK_UNITS = 1;
  private static final Function<Time, Double> toDouble = (Time t) -> t.toSeconds();
  private static final Function<Double, Time> fromDouble =
      (Double seconds) -> Time.fromSeconds(seconds);
  private static final StringConverter<Number> converter =
      new StringConverter<Number>() {
        @Override
        public Number fromString(String string) {
          return Double.parseDouble(string);
        }

        @Override
        public String toString(Number seconds) {
          int secs =
              seconds.intValue() + (TICK_UNITS - seconds.intValue() % TICK_UNITS) % TICK_UNITS;
          return Time.fromSeconds(secs).toStringMinutesAndSeconds();
        }
      };

  public TimeSlider(String title, Time min, Time max, Time initVal, String color) {
    super(title, min, max, initVal, TICK_UNITS, color, toDouble, fromDouble, converter);
  }
}
