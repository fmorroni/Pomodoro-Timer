package frontend;

import java.util.function.Function;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public class LabeledSlider<T> implements CustomNode {
  private final HBox hbox = new HBox();
  protected final Slider slider;
  private final Label valueLabel;
  private final double tickUnits;
  private final String color;
  private final Function<T, Double> toDouble;
  private final Function<Double, T> fromDouble;

  public LabeledSlider(
      T min,
      T max,
      T val,
      double tickUnits,
      String color,
      Function<T, Double> toDouble,
      Function<Double, T> fromDouble,
      StringConverter<Number> converter) {
    this.tickUnits = tickUnits;
    this.color = color;
    this.toDouble = toDouble;
    this.fromDouble = fromDouble;
    Double minVal = toDouble.apply(min);
    Double maxVal = toDouble.apply(max);
    Double initVal = toDouble.apply(val);
    slider = new Slider(minVal, maxVal, initVal);
    slider.setBlockIncrement(tickUnits);
    slider.setMajorTickUnit(tickUnits);
    slider.setMinorTickCount(0);
    slider.setSnapToTicks(true);

    setStyle(initVal);

    valueLabel = new Label(minVal.toString());
    valueLabel.setMinWidth(50);
    valueLabel.getStyleClass().add("slider-label");
    valueLabel.setAlignment(Pos.CENTER);

    slider
        .valueProperty()
        .addListener(
            (ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
              setStyle(newVal.doubleValue());
            });

    // valueLabel = new Label(Integer.valueOf(minVal).toString());
    // valueLabel = new Label();
    // valueLabel.setMinWidth(50);
    // valueLabel.getStyleClass().add("slider-label");

    valueLabel.textProperty().bindBidirectional(slider.valueProperty(), converter);

    hbox.getChildren().addAll(slider, valueLabel);

    HBox.setHgrow(slider, javafx.scene.layout.Priority.ALWAYS);
    HBox.setHgrow(valueLabel, javafx.scene.layout.Priority.NEVER);
    hbox.setSpacing(20);
  }

  private void setStyle(double val) {
    double actualVal = (val - slider.getMin()) * 100 / (slider.getMax() - slider.getMin());
    slider.setStyle(
        String.format(
            "-track-color: linear-gradient(to right, %s %.2f%%, #2f384b %.2f%%);"
                + "-thumb-color: %s;",
            color, actualVal, actualVal, color));
  }

  public String getColor() {
    return color;
  }

  public double getTickUnits() {
    return tickUnits;
  }

  public T getValue() {
    return fromDouble.apply(slider.getValue());
  }

  public void setValue(T value) {
    slider.setValue(toDouble.apply(value));
  }

  public void disable() {
    slider.setDisable(true);
    valueLabel.getStyleClass().add("disabled");
  }

  public void enable() {
    slider.setDisable(false);
    valueLabel.getStyleClass().remove("disabled");
  }

  public Node getNode() {
    return hbox;
  }
}
