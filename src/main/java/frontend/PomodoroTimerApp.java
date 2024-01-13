package frontend;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

import backend.PomodoroTimer;
import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PomodoroTimerApp extends Application {

  public static final String MAIN_VIEW = HOME_VIEW;

  private final AppManager appManager = AppManager.initialize(this::postInit);

  private final PomodoroTimer pomodoro = new PomodoroTimer();
  private final Settings settings = new Settings(pomodoro);

  @Override
  public void init() {
    appManager.addViewFactory(MAIN_VIEW, () -> new MainView().getView());

    settings.load();

    SettingsList sl = new SettingsList(pomodoro, settings);
    DrawerManager.buildDrawer(appManager, sl);
  }

  @Override
  public void start(Stage stage) {
    appManager.start(stage);
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
