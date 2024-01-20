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
  public static final String REMINDER_INTERVAL_KEY = "reminderInterval";
  public static final String REMINDER_ENABLED_KEY = "reminderEnabled";

  public static final Time DEFAULT_WORK_DURATION = Time.fromMinutes(25);
  public static final Time DEFAULT_SHORT_BREAK_DURATION = Time.fromMinutes(5);
  public static final Time DEFAULT_LONG_BREAK_DURATION = Time.fromMinutes(15);
  public static final double DEFAULT_ROUNDS = 4;
  public static final boolean DEFAULT_AUTOMATIC_INTERVALS = false;
  public static final boolean DEFAULT_NOTIFICATION_ENABLED = true;
  public static final Time DEFAULT_REMINDER_INTERVAL = Time.fromMinutes(1);
  public static final boolean DEFAULT_REMINDER_ENABLED = true;

  private final Preferences preferences =
      Preferences.userRoot().node("personal-projects/pomodoro-timer");

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

  private boolean automaticIntervals;
  private boolean notificationEnabled = true;
  private static final String notificationResourceName = "/notification.mp3";
  private Media notificationMedia = null;
  private Time reminderInterval = Time.fromMinutes(1);
  private boolean reminderEnabled = true;

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

  public void putTime(String key, Time time) {
    preferences.putDouble(key, time.toSeconds());
  }

  public Time getTime(String key, Time defaultTime) {
    return Time.fromSeconds(preferences.getDouble(key, defaultTime.toSeconds()));
  }

  public void saveWorkDuration(Time t) {
    putTime(WORK_DURATION_KEY, t);
  }

  public void saveShortBrakeDuration(Time t) {
    putTime(SHORT_BREAK_DURATION_KEY, t);
  }

  public void saveLongBrakeDuration(Time t) {
    putTime(LONG_BREAK_DURATION_KEY, t);
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

  public void saveNotificationEnabled(boolean enabled) {
    preferences.putBoolean(NOTIFICATION_ENABLED_KEY, enabled);
  }

  public boolean notificationEnabled() {
    return notificationEnabled;
  }

  public void setNotificationEnabled(boolean enabled) {
    notificationEnabled = enabled;
  }

  public void saveReminderInterval(Time t) {
    putTime(REMINDER_INTERVAL_KEY, t);
  }

  public Time getReminderInterval() {
    return reminderInterval;
  }

  public void setReminderInterval(Time t) {
    reminderInterval = t;
  }

  public void saveReminderEnabled(boolean enabled) {
    preferences.putBoolean(REMINDER_ENABLED_KEY, enabled);
  }

  public boolean reminderEnabled() {
    return reminderEnabled;
  }

  public void setReminderEnabled(boolean enabled) {
    reminderEnabled = enabled;
  }

  public void loadSettings() {
    setWorkDuration(getTime(WORK_DURATION_KEY, DEFAULT_WORK_DURATION));
    setShortBreakDuration(getTime(SHORT_BREAK_DURATION_KEY, DEFAULT_SHORT_BREAK_DURATION));
    setLongBreakDuration(getTime(LONG_BREAK_DURATION_KEY, DEFAULT_LONG_BREAK_DURATION));
    setRounds(preferences.getInt(ROUNDS_KEY, 4));
    setAutomaticIntervals(
        preferences.getBoolean(AUTOMATIC_INTERVALS_KEY, DEFAULT_AUTOMATIC_INTERVALS));
    setNotificationEnabled(
        preferences.getBoolean(NOTIFICATION_ENABLED_KEY, DEFAULT_NOTIFICATION_ENABLED));
    setReminderInterval(getTime(REMINDER_INTERVAL_KEY, DEFAULT_REMINDER_INTERVAL));
    setReminderEnabled(preferences.getBoolean(REMINDER_ENABLED_KEY, DEFAULT_REMINDER_ENABLED));
  }

  public void playNotification() {
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
  public void nextInterval() {
    super.nextInterval();
    playNotification();
  }

  @Override
  public String toString() {
    return "%s\nautomaticIntervals: %s\nreminder(interval|enabled): %s | %s"
        .formatted(super.toString(), automaticIntervals, reminderInterval, reminderEnabled);
  }
}
