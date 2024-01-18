package frontend;

import backend.Interval;
import backend.Time;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SettingsList {
  private final VBox list = new VBox();
  private final Button saveBtn = new Button("Save");
  private Runnable extraSaveBtnAction;

  // private static final Time workMin = Time.fromMinutes(10);
  // private static final Time workMax = Time.fromMinutes(40);
  // private static final Time shortBreakMin = Time.fromMinutes(1);
  // private static final Time shortBreakMax = Time.fromMinutes(10);
  // private static final Time longBreakMin = Time.fromMinutes(5);
  // private static final Time longBreakMax = Time.fromMinutes(40);
  private static final int roundsMin = 1;
  private static final int roundsMax = 10;

  private static final Time workMin = Time.fromSeconds(1);
  private static final Time workMax = Time.fromSeconds(10);
  private static final Time shortBreakMin = Time.fromSeconds(1);
  private static final Time shortBreakMax = Time.fromSeconds(10);
  private static final Time longBreakMin = Time.fromSeconds(1);
  private static final Time longBreakMax = Time.fromSeconds(10);

  public SettingsList(PomodoroTimer pomodoro) {
    TimeSlider workSlider =
        new TimeSlider(
            workMin,
            workMax,
            pomodoro.getWorkDuration(),
            PomodoroTimer.getIntervalColor(Interval.Work));
    addSlider("Work Interval Duration", workSlider);

    TimeSlider shortBreakSlider =
        new TimeSlider(
            shortBreakMin,
            shortBreakMax,
            pomodoro.getShortBreakDuration(),
            PomodoroTimer.getIntervalColor(Interval.ShortBreak));
    addSlider("Short Break Interval Duration", shortBreakSlider);

    TimeSlider longBreakSlider =
        new TimeSlider(
            longBreakMin,
            longBreakMax,
            pomodoro.getLongBreakDuration(),
            PomodoroTimer.getIntervalColor(Interval.LongBreak));
    addSlider("Long Break Interval Duration", longBreakSlider);

    IntSlider roundsSlider = new IntSlider(roundsMin, roundsMax, pomodoro.getRounds(), "#848b98");
    addSlider("Rounds", roundsSlider);

    CheckBox automativIntervals = new CheckBox("Automatic next interval");
    automativIntervals.getStyleClass().add("setting-name");
    automativIntervals.setSelected(pomodoro.getAutomaticIntervals());

    CheckBox enableNotification = new CheckBox("Sound notification");
    enableNotification.getStyleClass().add("setting-name");
    enableNotification.setSelected(pomodoro.notificationEnabled());

    saveBtn.setOnAction(
        (ev) -> {
          pomodoro.saveWorkDuration(workSlider.getValue());
          pomodoro.saveShortBrakeDuration(shortBreakSlider.getValue());
          pomodoro.saveLongBrakeDuration(longBreakSlider.getValue());
          pomodoro.saveRounds(roundsSlider.getValue());
          pomodoro.saveAutomaticIntervals(automativIntervals.isSelected());
          pomodoro.saveNotificationEnabled(enableNotification.isSelected());
          pomodoro.loadSettings();
          extraSaveBtnAction.run();
        });

    list.getChildren().addAll(automativIntervals, enableNotification, saveBtn);
  }

  private <T> void addSlider(String title, LabeledSlider<T> slider) {
    VBox vbox = new VBox();
    // setSpacing(10);
    vbox.setAlignment(Pos.CENTER_LEFT);
    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("setting-name");
    vbox.getChildren().addAll(titleLabel, slider);
    vbox.setSpacing(13);
    vbox.setPadding(new Insets(10));
    list.getChildren().add(vbox);
  }

  public Node getNode() {
    return list;
  }

  public void extraSaveBtnAction(Runnable action) {
    extraSaveBtnAction = action;
  }
}
