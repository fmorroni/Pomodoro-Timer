package frontend;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

import backend.Time;
import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.Swatch;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Dimension2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PomodoroTimerApp extends Application {

  public static final String MAIN_VIEW = HOME_VIEW;

  private final AppManager appManager = AppManager.initialize(this::postInit);

  private final PomodoroTimer pomodoro = new PomodoroTimer();

  private final View mainView;
  private final MainPresenter mainPresenter;
  private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

  public PomodoroTimerApp() {
    super();

    try {
      FXMLLoader loader =
          new FXMLLoader(
              getClass().getResource("/main.fxml"),
              // This loads the main.properties as a resource.
              ResourceBundle.getBundle("main"));
      mainView = loader.load();
      mainPresenter = loader.getController();
    } catch (IOException e) {
      throw new RuntimeException("IOException: " + e);
    }
    mainPresenter.setNextIntervalBtnAction(
        () -> {
          pomodoro.nextInterval();
          updateInterval();
          pauseTimer();
        });
    mainPresenter.setResetIntervalBtnAction(
        () -> {
          pomodoro.resetInterval();
          updateInterval();
          pauseTimer();
        });
    mainPresenter.setPlayBtnAction(
        () -> {
          if (pomodoro.isPaused()) playTimer();
          else pauseTimer();
        });

    pomodoro.loadSettings();
    pauseTimer();
    updateInterval();
    update();
  }

  @Override
  public void init() {
    appManager.addViewFactory(MAIN_VIEW, () -> mainView);

    scheduler.scheduleAtFixedRate(
        () -> javafx.application.Platform.runLater(this::update), 0, 1, TimeUnit.SECONDS);

    SettingsList sl = new SettingsList(pomodoro);
    sl.extraSaveBtnAction(
        () -> {
          pauseTimer();
          updateInterval();
        });

    DrawerManager.buildDrawer(appManager, sl);
  }

  private void pauseTimer() {
    pomodoro.pause();
    mainPresenter.setPlayBtnImageGraphic(PomodoroTimer.playIcon);
    update();
  }

  private void playTimer() {
    pomodoro.play();
    mainPresenter.setPlayBtnImageGraphic(PomodoroTimer.pauseIcon);
    update();
  }

  private void update() {
    Time remaining = pomodoro.getRemainingTime();
    if (pomodoro.getAutomaticIntervals() && remaining.toSeconds() < 1) {
      pomodoro.nextInterval();
      remaining = pomodoro.getRemainingTime();
      updateInterval();
    } else if (pomodoro.reminderEnabled()
        && remaining.toMs() < -1
        && (-remaining.toMs() % pomodoro.getReminderInterval().toMs()) < 1000) {
      pomodoro.playNotification();
    }
    mainPresenter.setTimerLabel(remaining);
  }

  private void updateInterval() {
    mainPresenter.setTimerLabelColor(
        PomodoroTimer.getIntervalColor(pomodoro.getCurrentIntervalType()));
    mainPresenter.setIntervalLabel(pomodoro.getCurrentIntervalType());
    mainPresenter.setRoundsLabel(pomodoro.getCurrentRound(), pomodoro.getRounds());
  }

  @Override
  public void start(Stage stage) {
    appManager.start(stage);
  }

  @Override
  public void stop() throws Exception {
    if (scheduler != null && !scheduler.isShutdown()) {
      scheduler.shutdown();
      // scheduler.awaitTermination(5, TimeUnit.SECONDS); // Optional: Wait for termination
    }
    super.stop();
  }

  private void postInit(Scene scene) {
    Swatch.BLUE_GREY.assignTo(scene);
    scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    ((Stage) scene.getWindow())
        .getIcons()
        .add(new Image(getClass().getResourceAsStream("/icon.png")));

    if (Platform.isDesktop()) {
      Dimension2D dimension2D =
          DisplayService.create()
              .map(DisplayService::getDefaultDimensions)
              .orElse(new Dimension2D(640, 480));
      scene.getWindow().setWidth(dimension2D.getWidth());
      scene.getWindow().setHeight(dimension2D.getHeight());
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
