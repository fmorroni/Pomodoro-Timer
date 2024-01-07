package frontend;

import backend.Time;
import java.util.function.Function;
import javafx.util.StringConverter;

public class TimeSlider extends LabeledSlider<Time> {
  private static final Function<Time, Double> toDouble = (Time t) -> t.getInMinutes();
  private static final StringConverter<Number> converter =
      new StringConverter<Number>() {
        @Override
        public Number fromString(String string) {
          return Double.parseDouble(string);
        }

        @Override
        public String toString(Number minutes) {
          return Time.fromMinutes(minutes.doubleValue()).toString();
        }
      };

  public TimeSlider(Time min, Time max, String color) {
    super(min, max, color, toDouble, converter);
  }
}
