package frontend;

import backend.Interval;
import backend.Time;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PomodoroTimer extends backend.PomodoroTimer {
  public static final String WORK_DURATION_KEY = "workDuration";
  public static final String SHORT_BREAK_DURATION_KEY = "shortBreakDuration";
  public static final String LONG_BREAK_DURATION_KEY = "longBreakDuration";
  public static final String ROUNDS_KEY = "rounds";
  public static final String AUTOMATIC_INTERVALS_KEY = "autoInterval";
  public static final String NOTIFICATION_ENABLED_KEY = "notificationEnabled";

  public static final double DEFAULT_WORK_DURATION = 25 * 60;
  public static final double DEFAULT_SHORT_BREAK_DURATION = 5 * 60;
  public static final double DEFAULT_LONG_BREAK_DURATION = 15 * 60;
  public static final double DEFAULT_ROUNDS = 4;
  public static final boolean DEFAULT_AUTOMATIC_INTERVALS = false;
  public static final boolean DEFAULT_NOTIFICATION_ENABLED = true;

  private static final Map<Interval, String> intervalColors = new HashMap<>();

  static {
    intervalColors.put(Interval.Work, "#fe4d4c");
    intervalColors.put(Interval.ShortBreak, "#05eb8b");
    intervalColors.put(Interval.LongBreak, "#0bbcda");
  }

  public static final ImageView playIcon =
      new ImageView(
          new Image(
              PomodoroTimer.class.getResourceAsStream(
                  "/playIcon.png"))); // Replace with the actual path to your image
  public static final ImageView pauseIcon =
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

  private boolean notificationEnabled = true;
  private static final String notificationResourceName = "/notification.mp3";
  private Media notificationMedia = null;

  // private MediaPlayer notificationPlayer = null;

  public PomodoroTimer() {
    super();

    URL resourceUrl = getClass().getResource(notificationResourceName);
    if (resourceUrl != null) {
      notificationMedia = new Media(resourceUrl.toExternalForm());
    } else {
      System.out.println("Resource `%s` not found".formatted(notificationResourceName));
    }
  }

  public static String getIntervalColor(Interval interval) {
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

  public void setAutomaticIntervals(boolean isAuto) {
    automaticIntervals = isAuto;
  }

  public void setNotificationEnabled(boolean enabled) {
    notificationEnabled = enabled;
  }

  public boolean notificationEnabled() {
    return notificationEnabled;
  }

  public void loadSettings() {
    setWorkDuration(
        Time.fromSeconds(preferences.getDouble(WORK_DURATION_KEY, DEFAULT_WORK_DURATION)));
    setShortBreakDuration(
        Time.fromSeconds(
            preferences.getDouble(SHORT_BREAK_DURATION_KEY, DEFAULT_SHORT_BREAK_DURATION)));
    setLongBreakDuration(
        Time.fromSeconds(
            preferences.getDouble(LONG_BREAK_DURATION_KEY, DEFAULT_LONG_BREAK_DURATION)));
    setRounds(preferences.getInt(ROUNDS_KEY, 4));
    setAutomaticIntervals(
        preferences.getBoolean(AUTOMATIC_INTERVALS_KEY, DEFAULT_AUTOMATIC_INTERVALS));
    setNotificationEnabled(
        preferences.getBoolean(NOTIFICATION_ENABLED_KEY, DEFAULT_NOTIFICATION_ENABLED));
  }

  public void saveNotificationEnabled(boolean enabled) {
    preferences.putBoolean(NOTIFICATION_ENABLED_KEY, enabled);
  }

  @Override
  public void nextInterval() {
    super.nextInterval();
    if (notificationEnabled) {
      try {
        MediaPlayer notificationPlayer = new MediaPlayer(notificationMedia);
        notificationPlayer.play();
      } catch (Exception e) {
        System.out.println("MediaPlayer exception: " + e.getMessage());
      }
    }

    // For some reason when restarting the player it then misses some ms from the start
    // of the audio file so I need to create a new MediaPlayer instance each time...
    // if (notificationPlayer != null) {
    //   notificationPlayer.stop();
    //   notificationPlayer.play();
    // }
  }

  @Override
  public String toString() {
    return super.toString() + "automaticIntervals: " + automaticIntervals;
  }
}
