package frontend;

import backend.Interval;
import backend.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PomodoroTimer extends backend.PomodoroTimer {
  public static final String WORK_DURATION_KEY = "workDuration";
  public static final String SHORT_BREAK_DURATION_KEY = "shortBreakDuration";
  public static final String LONG_BREAK_DURATION_KEY = "longBreakDuration";
  public static final String ROUNDS_KEY = "rounds";
  public static final String AUTOMATIC_INTERVALS_KEY = "autoInterval";

  public static final double DEFAULT_WORK_DURATION = 25 * 60;
  public static final double DEFAULT_SHORT_BREAK_DURATION = 5 * 60;
  public static final double DEFAULT_LONG_BREAK_DURATION = 15 * 60;
  public static final double DEFAULT_ROUNDS = 4;
  public static final boolean DEFAULT_AUTOMATIC_INTERVALS = false;

  private static final Map<Interval, String> intervalColors = new HashMap<>();

  static {
    intervalColors.put(Interval.Work, "#fe4d4c");
    intervalColors.put(Interval.ShortBreak, "#05eb8b");
    intervalColors.put(Interval.LongBreak, "#0bbcda");
  }

  // public static final String playIcon = "▶";
  // public static final String pauseIcon = "⏸";
  public static ImageView playIcon =
      new ImageView(
          new Image(
              PomodoroTimer.class.getResourceAsStream(
                  "/playIcon.png"))); // Replace with the actual path to your image
  public static ImageView pauseIcon =
      new ImageView(
          new Image(
              PomodoroTimer.class.getResourceAsStream(
                  "/pauseIcon.png"))); // Replace with the actual path to your image

  static {
    playIcon.setFitWidth(75);
    playIcon.setFitHeight(75);
    pauseIcon.setFitWidth(75);
    pauseIcon.setFitHeight(75);
  }

  public static String getIntervalColor(Interval interval) {
    // Image image = new Image("path/to/your/image.png"); // Replace with the actual path to your
    // image

    // // Create an ImageView with the loaded image
    // ImageView imageView = new ImageView(image);

    // // Create a button with the ImageView as its graphic
    // Button imageButton = new Button();
    // imageButton.setGraphic(imageView);
    return intervalColors.get(interval);
  }

  private final Preferences preferences =
      Preferences.userRoot().node("personal-projects/pomodoro-timer");

  private boolean automaticIntervals;

  public void saveWorkDuration(Time t) {
    preferences.putDouble(WORK_DURATION_KEY, t.toSeconds());
  }

  public void saveShortBrakeDuration(Time t) {
    preferences.putDouble(SHORT_BREAK_DURATION_KEY, t.toSeconds());
  }

  public void saveLongBrakeDuration(Time t) {
    preferences.putDouble(LONG_BREAK_DURATION_KEY, t.toSeconds());
  }

  public void saveRounds(int rounds) {
    preferences.putInt(ROUNDS_KEY, rounds);
  }

  public void saveAutomaticIntervals(boolean isAuto) {
    preferences.putBoolean(AUTOMATIC_INTERVALS_KEY, isAuto);
  }

  public boolean getAutomaticIntervals() {
    return automaticIntervals;
  }

  public PomodoroTimer setAutomaticIntervals(boolean isAuto) {
    automaticIntervals = isAuto;
    return this;
  }

  public void loadSettings() {
    this.setWorkDuration(
            Time.fromSeconds(preferences.getDouble(WORK_DURATION_KEY, DEFAULT_WORK_DURATION)))
        .setShortBreakDuration(
            Time.fromSeconds(
                preferences.getDouble(SHORT_BREAK_DURATION_KEY, DEFAULT_SHORT_BREAK_DURATION)))
        .setLongBreakDuration(
            Time.fromSeconds(
                preferences.getDouble(LONG_BREAK_DURATION_KEY, DEFAULT_LONG_BREAK_DURATION)))
        .setRounds(preferences.getInt(ROUNDS_KEY, 4))
        .resetTimer();

    setAutomaticIntervals(
        preferences.getBoolean(AUTOMATIC_INTERVALS_KEY, DEFAULT_AUTOMATIC_INTERVALS));
  }

  @Override
  public String toString() {
    return super.toString() + "automaticIntervals: " + automaticIntervals;
  }
}
