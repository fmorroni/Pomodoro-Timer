package frontend;

import backend.Interval;
import backend.Time;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
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
  @FXML private Button resetIntervalBtn;

  private Runnable printPomodoroBtnAction = () -> {};
  private Runnable nextIntervalBtnAction = () -> {};
  private Runnable resetIntervalBtnAction = () -> {};

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
    // label.setText(String.format(resources.getString("label.text"), "JavaFX", javafxVersion));
  }

  public void setTimerLabel(Time t) {
    timerLabel.setText(t.toStringMinutesAndSeconds());
  }

  public void setIntervalLabel(Interval interval) {
    intervalLabel.setText(interval.toString());
  }

  public void setPrintPomodoroBtnAction(Runnable action) {
    printPomodoroBtnAction = action;
  }

  public void setNextIntervalBtnAction(Runnable action) {
    nextIntervalBtnAction = action;
  }

  public void setResetIntervalBtnAction(Runnable action) {
    resetIntervalBtnAction = action;
  }

  @FXML
  private void printPomodoro() {
    printPomodoroBtnAction.run();
  }

  @FXML
  private void nextInterval() {
    nextIntervalBtnAction.run();
  }

  @FXML
  private void resetInterval() {
    resetIntervalBtnAction.run();
  }

  // @FXML
  // private void updateColor() {
  //   Random random = new Random();
  //   // this.color.setTextFill(
  //   //     Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
  // }
}
