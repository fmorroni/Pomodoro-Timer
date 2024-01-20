package frontend;

import java.util.function.Function;
import javafx.util.StringConverter;

public class IntSlider extends TitledLabeledSlider<Integer> {
  private static final int TICK_UNITS = 1;
  private static final Function<Integer, Double> toDouble =
      (Integer round) -> Double.valueOf(round);
  private static final Function<Double, Integer> fromDouble =
      (Double round) -> (int) round.doubleValue();
  private static final StringConverter<Number> converter =
      new StringConverter<Number>() {
        @Override
        public Number fromString(String string) {
          return Double.parseDouble(string);
        }

        @Override
        public String toString(Number seconds) {
          Integer secs = seconds.intValue() + (TICK_UNITS - seconds.intValue() % TICK_UNITS) % TICK_UNITS;
          return secs.toString();
        }
      };

  public IntSlider(String title, int min, int max, int initVal, String color) {
    super(title, min, max, initVal, TICK_UNITS, color, toDouble, fromDouble, converter);
  }
}
