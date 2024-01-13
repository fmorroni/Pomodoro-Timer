package frontend;

import backend.PomodoroTimer;
import backend.Time;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SettingsList {
  private final VBox list = new VBox();
  private final Button saveBtn = new Button("Save");

  public SettingsList(PomodoroTimer pomodoro, Settings settings) {
    TimeSlider workSlider =
        new TimeSlider(
            Time.fromMinutes(10), Time.fromHours(1), pomodoro.getWorkDuration(), "#fe4d4c");
    addSlider("Work Interval Duration", workSlider);

    TimeSlider shortBreakSlider =
        new TimeSlider(
            Time.fromMinutes(1), Time.fromMinutes(10), pomodoro.getShortBreakDuration(), "#05eb8b");

    addSlider("Short Break Interval Duration", shortBreakSlider);

    TimeSlider longBreakSlider =
        new TimeSlider(
            Time.fromMinutes(5), Time.fromMinutes(40), pomodoro.getLongBreakDuration(), "#0bbcda");
    addSlider("Long Break Interval Duration", longBreakSlider);

    IntSlider roundsSlider = new IntSlider(1, 10, pomodoro.getRounds(), "#848b98");
    addSlider("Rounds", roundsSlider);

    list.getChildren().add(saveBtn);

    saveBtn.setOnAction(
        (ev) -> {
          settings.saveWorkDuration(workSlider.getValue());
          settings.saveShortBrakeDuration(shortBreakSlider.getValue());
          settings.saveLongBrakeDuration(longBreakSlider.getValue());
          settings.saveRounds(roundsSlider.getValue());
          settings.load();
        });
  }

  private <T> void addSlider(String title, LabeledSlider<T> slider) {
    VBox vbox = new VBox();
    // setSpacing(10);
    vbox.setAlignment(Pos.CENTER_LEFT);
    vbox.getChildren().addAll(new Label(title), slider);
    vbox.setSpacing(13);
    vbox.setPadding(new Insets(10));
    list.getChildren().add(vbox);
  }

  public Node getNode() {
    return list;
  }
}
