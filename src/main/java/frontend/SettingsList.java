package frontend;

import backend.PomodoroTimer;
import backend.Time;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SettingsList {
  private final VBox list = new VBox();
  private final Button saveBtn = new Button("Save");
  private final Map<String, LabeledSlider<Time>> timeItems = new HashMap<>();

  // private final Map<String, LabeledSlider<Number>> numericItems = new HashMap<>();

  // private final PomodoroTimer pomodoro;
  // private final Settings settings;

  public SettingsList(PomodoroTimer pomodoro, Settings settings) {
    // this.pomodoro = pomodoro;
    // this.settings = settings;

    addTimeSlider(
        Settings.WORK_DURATION_KEY,
        "Work Interval Duration",
        Time.fromMinutes(10),
        Time.fromHours(1),
        pomodoro.getWorkDuration(),
        "#fe4d4c");
    addTimeSlider(
        Settings.SHORT_BREAK_DURATION_KEY,
        "Short Break Interval Duration",
        Time.fromSeconds(0),
        Time.fromSeconds(60),
        pomodoro.getShortBreakDuration(),
        "#05eb8b");
    addTimeSlider(
        Settings.LONG_BREAK_DURATION_KEY,
        "Long Break Interval Duration",
        Time.fromMinutes(5),
        Time.fromMinutes(40),
        pomodoro.getLongBreakDuration(),
        "#0bbcda");
    list.getChildren().add(saveBtn);

    saveBtn.setOnAction(
        (ev) -> {
          System.out.println(
              "%s: %s"
                  .formatted(
                      Settings.WORK_DURATION_KEY,
                      timeItems.get(Settings.WORK_DURATION_KEY).getValue()));
          System.out.println(
              "%s: %s"
                  .formatted(
                      Settings.SHORT_BREAK_DURATION_KEY,
                      timeItems.get(Settings.SHORT_BREAK_DURATION_KEY).getValue()));
          System.out.println(
              "%s: %s"
                  .formatted(
                      Settings.LONG_BREAK_DURATION_KEY,
                      timeItems.get(Settings.LONG_BREAK_DURATION_KEY).getValue()));

          settings.saveWorkDuration(timeItems.get(Settings.WORK_DURATION_KEY).getValue());
          settings.saveShortBrakeDuration(
              timeItems.get(Settings.SHORT_BREAK_DURATION_KEY).getValue());
          settings.saveLongBrakeDuration(
              timeItems.get(Settings.LONG_BREAK_DURATION_KEY).getValue());
          settings.load();
        });
  }

  private <T> void addSlider(String key, String title, LabeledSlider<T> slider) {
    VBox vbox = new VBox();
    // setSpacing(10);
    vbox.setAlignment(Pos.CENTER_LEFT);
    vbox.getChildren().addAll(new Label(title), slider);
    vbox.setSpacing(13);
    vbox.setPadding(new Insets(10));
    list.getChildren().add(vbox);
  }

  private void addTimeSlider(
      String key, String title, Time sliderMin, Time sliderMax, Time value, String color) {
    TimeSlider slider = new TimeSlider(sliderMin, sliderMax, value, color);
    timeItems.put(key, slider);
    addSlider(key, title, slider);
  }

  // public void add(String title, Integer sliderMin, Integer sliderMax, String color) {
  //   list.getChildren().add(new Item<Integer>(title, new LabeledSlider<Integer>(sliderMin,
  // sliderMax, color)));
  // }

  public Node getNode() {
    return list;
  }
}
