package frontend.cli;

import backend.PomodoroTimer;
import backend.Time;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Supplier;

public class PomodoroCli {
  private static PomodoroTimer pomodoro = new PomodoroTimer();
  private static Scanner scan = new Scanner(System.in);
  private static boolean exit = false;

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
          Double duration = handleInput(() -> scan.nextDouble());
          if (duration == null) return;
          pomodoro.setWorkInterval(Time.fromMinutes(duration));
        }),
    new Option(
        "Set short break interval duration",
        () -> {
          System.out.printf("How long should the short breaks be?\nMinutes: ");
          Double duration = handleInput(() -> scan.nextDouble());
          if (duration == null) return;
          pomodoro.setShortBreakInterval(Time.fromMinutes(duration));
        }),
    new Option(
        "Set long break interval duration",
        () -> {
          System.out.printf("How long should the long breaks be?\nMinutes: ");
          Double duration = handleInput(() -> scan.nextDouble());
          if (duration == null) return;
          pomodoro.setLongBreakInterval(Time.fromMinutes(duration));
        }),
    new Option("Reset timer", () -> pomodoro.resetTimer()),
  };

  private static <T> T handleInput(Supplier<T> scannerAction) {
    try {
      return scannerAction.get();
    } catch (InputMismatchException e) {
      scan.next();
      System.out.println("Invalid value\n");
      return null;
    }
  }

  public static void main(String[] args) {
    askOptions();
  }

  public static void printOptions() {
    for (int i = 0; i < options.length; ++i) {
      System.out.printf("%d - %s\n", i, options[i]);
    }
  }

  public static void askOptions() {
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
