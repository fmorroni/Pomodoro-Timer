package frontend;

import backend.Interval;
import backend.Time;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainPresenter {

  // @FXML private Label color;
  // @FXML private Label label;
  @FXML private View main;
  @FXML private Label intervalLabel;
  @FXML private Label timerLabel;
  @FXML private Button printBtn;
  @FXML private Button nextIntervalBtn;
  @FXML private Label roundsLabel;
  @FXML private Button resetIntervalBtn;
  @FXML private Button playBtn;

  private Runnable nextIntervalBtnAction = () -> {};
  private Runnable resetIntervalBtnAction = () -> {};
  private Runnable playBtnAction = () -> {};

  public void initialize() {
    main.showingProperty()
        .addListener(
            (obs, oldValue, newValue) -> {
              if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(
                    MaterialDesignIcon.SETTINGS.button(
                        e -> AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Pomodoro Timer");
              }
            });
  }

  public void setRoundsLabel(int current, int max) {
    roundsLabel.setText("%d/%d".formatted(current, max));
  }

  public void setTimerLabel(Time t) {
    timerLabel.setText(t.toStringMinutesAndSeconds());
  }


  public void setTimerLabelColor(String color) {
    timerLabel.setStyle("-fx-text-fill: " + color + ";");
  }

  public void setIntervalLabel(Interval interval) {
    intervalLabel.setText(interval.toString());
  }

  public void setNextIntervalBtnAction(Runnable action) {
    nextIntervalBtnAction = action;
  }

  public void setResetIntervalBtnAction(Runnable action) {
    resetIntervalBtnAction = action;
  }

  public void setPlayBtnImageGraphic(Node n) {
    playBtn.setGraphic(n);
  }

  public void setPlayBtnAction(Runnable action) {
    playBtnAction = action;
  }

  @FXML
  private void nextInterval() {
    nextIntervalBtnAction.run();
  }

  @FXML
  private void resetInterval() {
    resetIntervalBtnAction.run();
  }

  @FXML
  private void togglePlayPause() {
    playBtnAction.run();
  }

  // @FXML
  // private void updateColor() {
  //   Random random = new Random();
  //   // this.color.setTextFill(
  //   //     Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
  // }
}
