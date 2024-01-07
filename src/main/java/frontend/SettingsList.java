package frontend;

import backend.Time;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SettingsList {
  private final VBox list = new VBox();

  public void add(String title, Time sliderMin, Time sliderMax, String color) {
    list.getChildren().add(new Item<Time>(title, new TimeSlider(sliderMin, sliderMax, color)));
  }

  public Node getNode() {
    return list;
  }

  private class Item<T extends Comparable<? super T>> extends VBox {
    // private final Label title;
    // private final LabeledSlider<T> slider;

    public Item(String title, LabeledSlider<T> slider) {
      // this.title = new Label(title);
      // this.slider = slider;

      // setSpacing(10); // Adjust spacing as needed
      setAlignment(Pos.CENTER_LEFT);
      getChildren().addAll(new Label(title), slider);
      setSpacing(13);
      setPadding(new Insets(10));
    }
  }
}
