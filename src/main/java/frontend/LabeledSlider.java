package frontend;

import java.util.function.Function;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public abstract class LabeledSlider<T extends Comparable<? super T>> extends HBox {
  private final Slider slider;
  private final Label valueLabel;

  public LabeledSlider(
      T min, T max, String color, Function<T, Double> toDouble, StringConverter<Number> converter) {
    Double minVal = toDouble.apply(min);
    Double maxVal = toDouble.apply(max);
    slider = new Slider(minVal, maxVal, minVal);
    // slider.setBlockIncrement(1);
    // slider.setMajorTickUnit(1);
    // slider.setMinorTickCount(0);
    // slider.setSnapToTicks(true);

    slider.setStyle(String.format("-thumb-color: %s;", color));

    valueLabel = new Label(minVal.toString());
    valueLabel.setMinWidth(50);
    valueLabel.getStyleClass().add("slider-label");

    slider
        .valueProperty()
        .addListener(
            (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
              double val = (new_val.doubleValue() - minVal) * 100 / (maxVal - minVal);
              slider.setStyle(
                  String.format(
                      "-track-color: linear-gradient(to right, %s %.2f%%, #2f384b %.2f%%);"
                          + "-thumb-color: %s;",
                      color, val, val, color));
              // valueLabel.setText("hola: " + new_val);
            });

    // valueLabel = new Label(Integer.valueOf(minVal).toString());
    // valueLabel = new Label();
    // valueLabel.setMinWidth(50);
    // valueLabel.getStyleClass().add("slider-label");

    valueLabel.textProperty().bindBidirectional(slider.valueProperty(), converter);

    getChildren().addAll(slider, valueLabel);

    setHgrow(slider, javafx.scene.layout.Priority.ALWAYS);
    setHgrow(valueLabel, javafx.scene.layout.Priority.NEVER);
    setSpacing(20);
  }
}
