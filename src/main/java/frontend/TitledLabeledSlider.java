package frontend;

import java.util.function.Function;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class TitledLabeledSlider<T> extends LabeledSlider<T> {
  private final VBox vbox = new VBox();
  private final Label titleLabel;

  public TitledLabeledSlider(
      String title,
      T min,
      T max,
      T val,
      double tickUnits,
      String color,
      Function<T, Double> toDouble,
      Function<Double, T> fromDouble,
      StringConverter<Number> converter) {
    super(min, max, val, tickUnits, color, toDouble, fromDouble, converter);
    vbox.setAlignment(Pos.CENTER_LEFT);
    titleLabel = new Label(title);
    titleLabel.getStyleClass().add("setting-name");
    vbox.getChildren().addAll(titleLabel, super.getNode());
    vbox.setSpacing(13);
    vbox.setPadding(new Insets(5));
  }

  @Override
  public void disable() {
    super.disable();
    titleLabel.getStyleClass().add("disabled");
  }

  @Override
  public void enable() {
    super.enable();
    titleLabel.getStyleClass().remove("disabled");
  }

  @Override
  public Node getNode() {
    return this.vbox;
  }
}
