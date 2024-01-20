package frontend;

import backend.Interval;
import backend.Time;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class SettingsList implements CustomNode {
  private final VBox list = new VBox();
  private final Button saveBtn = new Button("Save");
  private Runnable extraSaveBtnAction;

  private static final Time workMin = Time.fromMinutes(10);
  private static final Time workMax = Time.fromMinutes(40);
  private static final Time shortBreakMin = Time.fromMinutes(1);
  private static final Time shortBreakMax = Time.fromMinutes(10);
  private static final Time longBreakMin = Time.fromMinutes(5);
  private static final Time longBreakMax = Time.fromMinutes(40);
  private static final Time reminderIntervalMin = Time.fromMinutes(0);
  private static final Time reminderIntervalMax = Time.fromMinutes(5);
  private static final int roundsMin = 1;
  private static final int roundsMax = 10;

  // private static final Time workMin = Time.fromSeconds(1);
  // private static final Time workMax = Time.fromSeconds(10);
  // private static final Time shortBreakMin = Time.fromSeconds(1);
  // private static final Time shortBreakMax = Time.fromSeconds(10);
  // private static final Time longBreakMin = Time.fromSeconds(1);
  // private static final Time longBreakMax = Time.fromSeconds(10);

  public SettingsList(PomodoroTimer pomodoro) {
    TimeSlider workSlider =
        new TimeSlider(
            "Work Interval Duration",
            workMin,
            workMax,
            pomodoro.getWorkDuration(),
            PomodoroTimer.getIntervalColor(Interval.Work));

    TimeSlider shortBreakSlider =
        new TimeSlider(
            "Short Break Interval Duration",
            shortBreakMin,
            shortBreakMax,
            pomodoro.getShortBreakDuration(),
            PomodoroTimer.getIntervalColor(Interval.ShortBreak));

    TimeSlider longBreakSlider =
        new TimeSlider(
            "Long Break Interval Duration",
            longBreakMin,
            longBreakMax,
            pomodoro.getLongBreakDuration(),
            PomodoroTimer.getIntervalColor(Interval.LongBreak));

    IntSlider roundsSlider =
        new IntSlider("Rounds", roundsMin, roundsMax, pomodoro.getRounds(), "#848b98");

    addAllCustomNodes(workSlider, shortBreakSlider, longBreakSlider, roundsSlider);

    CheckBox automativIntervals = new CheckBox("Automatic next interval");
    automativIntervals.getStyleClass().add("setting-name");
    automativIntervals.setSelected(pomodoro.getAutomaticIntervals());
    list.getChildren().add(automativIntervals);
    TimeSlider reminderIntervalSlider =
        new TimeSlider(
            "Reminder (0 to disable)",
            reminderIntervalMin,
            reminderIntervalMax,
            pomodoro.getReminderInterval(),
            "#848b98");
    addCustomNode(reminderIntervalSlider);
    automativIntervals
        .selectedProperty()
        .addListener(
            (ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean selected) -> {
              if (selected) reminderIntervalSlider.disable();
              else reminderIntervalSlider.enable();
            });

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
          pomodoro.saveReminderInterval(reminderIntervalSlider.getValue());
          if (reminderIntervalSlider.getValue().toMs() == 0) {
            pomodoro.saveReminderEnabled(false);
          } else {
            pomodoro.saveReminderEnabled(true);
          }
          pomodoro.loadSettings();
          extraSaveBtnAction.run();
        });

    list.getChildren().addAll(enableNotification, saveBtn);
  }

  private void addCustomNode(CustomNode customNode) {
    list.getChildren().add(customNode.getNode());
  }

  private <T> void addAllCustomNodes(CustomNode... customNodes) {
    for (CustomNode customNode : customNodes) {
      addCustomNode(customNode);
    }
  }

  // private <T> void disableSlider(LabeledSlider<T> slider) {

  // }

  public Node getNode() {
    return list;
  }

  public void extraSaveBtnAction(Runnable action) {
    extraSaveBtnAction = action;
  }
}
