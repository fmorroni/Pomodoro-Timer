package frontend;

import backend.PomodoroTimer;
import backend.Time;
import java.util.prefs.Preferences;

public class Settings {
  public static final String WORK_DURATION_KEY = "workDuration";
  public static final String SHORT_BREAK_DURATION_KEY = "shortBreakDuration";
  public static final String LONG_BREAK_DURATION_KEY = "longBreakDuration";
  public static final String ROUNDS_KEY = "rounds";

  private final Preferences preferences =
      Preferences.userRoot().node("personal-projects/pomodoro-timer");
  private final PomodoroTimer pomodoro;

  public Settings(PomodoroTimer pomodoro) {
    this.pomodoro = pomodoro;
  }

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

  public void load() {
    System.out.println("Pomodoro before: ");
    System.out.println(pomodoro);

    pomodoro
        .setWorkDuration(Time.fromSeconds(preferences.getDouble(WORK_DURATION_KEY, 25 * 60)))
        .setShortBreakDuration(
            Time.fromSeconds(preferences.getDouble(SHORT_BREAK_DURATION_KEY, 5 * 60)))
        .setLongBreakDuration(
            Time.fromSeconds(preferences.getDouble(LONG_BREAK_DURATION_KEY, 15 * 60)))
        .setRounds(preferences.getInt(ROUNDS_KEY, 4));
    pomodoro.resetTimer();

    System.out.println("Pomodoro after: ");
    System.out.println(pomodoro);
  }

  // @Override
  // public String toString() {
  //   return "%s: %s\n%s: %d\n%s: %.2f"
  //       .formatted(USERNAME_KEY, username, AGE_KEY, age, HEIGHT_KEY, height);
  // }
}
