package frontend;

import backend.PomodoroTimer;
import backend.Time;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Supplier;

public class PomodoroCli {
  private static PomodoroTimer pomodoro = new PomodoroTimer();
  private static Scanner scan = new Scanner(System.in);
  private static boolean exit = false;
  private static TimeDisplay timeDisplay = new TimeDisplay();
  private static TextAreaWithBorder intervalTypeArea = new TextAreaWithBorder(1, 30);
  private static final Option[] options = {
    new Option("Exit", () -> exit = true),
    new Option("Print time", () -> System.out.println(pomodoro.getRemainingTime())),
    new Option("Print info", () -> System.out.println(pomodoro)),
    new Option("Reset interval", () -> pomodoro.resetInterval()),
    new Option("Next interval", () -> pomodoro.nextInterval()),
    new Option(
        "Set amount of work intervals",
        () -> {
          System.out.printf("How many work intervals do you want?\nIntervals: ");
          Integer n = handleInput(() -> scan.nextInt());
          if (n == null) return;
          pomodoro.setWorkIntervalsAmount(n);
        }),
    new Option(
        "Set work interval duration",
        () -> {
          System.out.printf("How long should the work intervals be?\nMinutes: ");
          Double duration = handleIntervalDurationInput(() -> scan.nextDouble());
          if (duration == null) return;
          pomodoro.setWorkInterval(Time.fromMinutes(duration));
        }),
    new Option(
        "Set short break interval duration",
        () -> {
          System.out.printf("How long should the short breaks be?\nMinutes: ");
          Double duration = handleIntervalDurationInput(() -> scan.nextDouble());
          if (duration == null) return;
          pomodoro.setShortBreakInterval(Time.fromMinutes(duration));
        }),
    new Option(
        "Set long break interval duration",
        () -> {
          System.out.printf("How long should the long breaks be?\nMinutes: ");
          Double duration = handleIntervalDurationInput(() -> scan.nextDouble());
          if (duration == null) return;
          pomodoro.setLongBreakInterval(Time.fromMinutes(duration));
        }),
    new Option("Reset timer", () -> pomodoro.resetTimer()),
  };

  private static TextAreaWithBorder optionsArea = new TextAreaWithBorder(options.length, 40);

  static {
    for (int i = 0; i < options.length; ++i) {
      optionsArea.setString(i, 0, "%d - %s".formatted(i, options[i]));
    }
  }

  private static AnsiColor timerColor;

  public static void main(String[] args) throws InterruptedException {
    pomodoro.setWorkInterval(Time.fromSeconds(5)).setShortBreakInterval(Time.fromSeconds(3));

    StackContainer container = new StackContainer();
    container.addAll(intervalTypeArea, timeDisplay, optionsArea);

    updateIntervalTypeArea();
    updateTimeDisplayColor();

    while (true) {
      cls();
      Time remaining = pomodoro.getRemainingTime();
      if (remaining.isNegative()) {
        pomodoro.nextInterval();
        updateIntervalTypeArea();
        updateTimeDisplayColor();
        remaining = pomodoro.getRemainingTime();
      }
      timeDisplay.setDisplay(remaining);
      System.out.println(container);
      Thread.sleep(1000);
      System.out.flush();
    }
    // askOptions();
  }

  private static void updateTimeDisplayColor() {
    switch (pomodoro.getCurrentIntervalType()) {
      case Work:
        timerColor = AnsiColor.AnsiRed;
        break;
      case ShortBreak:
        timerColor = AnsiColor.AnsiGreen;
        break;
      case LongBreak:
        timerColor = AnsiColor.AnsiBlue;
        break;
    }
    timeDisplay.setColor(timerColor);
  }

  private static void updateIntervalTypeArea() {
    intervalTypeArea.reset();
    intervalTypeArea.setString(
        0, 0, "Current Interval: %s".formatted(pomodoro.getCurrentIntervalType().toString()));
  }

  private static <T> T handleInput(Supplier<T> scannerAction) {
    try {
      return scannerAction.get();
    } catch (InputMismatchException e) {
      scan.next();
      System.out.println("Invalid value\n");
      return null;
    }
  }

  private static Double handleIntervalDurationInput(Supplier<Double> scannerAction) {
    Double value = handleInput(scannerAction);
    if (value >= 60) {
      System.out.println("Invalid value: Duration should be lower than 60 minutes\n");
      return null;
    }
    return value;
  }

  private static void cls() {
    System.out.printf("\033[H\033[J");
  }

  private static void printOptions() {
    for (int i = 0; i < options.length; ++i) {
      System.out.printf("%d - %s\n", i, options[i]);
    }
  }

  private static void askOptions() {
    if (exit) return;
    int i = 0;
    boolean invalidOption = true;
    do {
      printOptions();
      System.out.printf("\nSelect option: ");
      try {
        i = scan.nextInt();
        invalidOption = i < 0 || i >= options.length;
      } catch (InputMismatchException e) {
        scan.next();
        invalidOption = true;
      }
      if (invalidOption) {
        System.out.println("Invalid option\n");
      }
    } while (invalidOption);
    System.out.println();
    options[i].run();
    System.out.println();
    askOptions();
  }

  private static class Option implements Runnable {
    private final String description;
    private final Runnable action;

    public Option(String description, Runnable action) {
      this.description = description;
      this.action = action;
    }

    @Override
    public void run() {
      action.run();
    }

    @Override
    public String toString() {
      return description;
    }
  }
}
