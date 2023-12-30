package frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class PomodoroTimerApp extends Application {

  private StackPane centerPane;

  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 300, 400);

    // Hamburger menu button
    Button menuButton = new Button("☰"); // You can replace this with appropriate icon/image
    menuButton.setOnAction(e -> handleMenuButtonClick());

    // Timer display
    StackPane timerPane = new StackPane();
    timerPane
        .getChildren()
        .add(new Button("00:00")); // You can replace this with a dynamic timer display

    Shape progressCircle = createProgressCircle(50, 10, 120, Color.BLUE);
    progressCircle.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
    Shape progressCircleBackground = createProgressCircle(50, 10, 360, Color.GRAY);
    // StrokeTransition strokeTransition = createStrokeTransition(progressCircle);
    // strokeTransition.setCycleCount(StrokeTransition.INDEFINITE);

    // StackPane for centering
    centerPane = new StackPane();
    centerPane.getChildren().addAll(progressCircleBackground, progressCircle, timerPane);
    centerPane.setStyle("-fx-border-color: red; -fx-border-width: 3px;");

    // Add components to the layout
    root.setTop(menuButton);
    root.setCenter(centerPane);

    primaryStage.setTitle("Pomodoro Timer");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private Shape createProgressCircle(
      double radius, double borderWidth, double progress, Color color) {
    Arc slice = new Arc(0, 0, radius, radius, 0, progress);
    slice.setType(ArcType.OPEN);
    slice.setStroke(color);
    slice.setStrokeWidth(borderWidth);
    slice.setFill(Color.TRANSPARENT);
    return slice;
  }

  // private StrokeTransition createStrokeTransition(Circle circle) {
  //   StrokeTransition transition = new StrokeTransition(Duration.seconds(2), circle);
  //   transition.setFromValue(Color.TRANSPARENT);
  //   transition.setToValue(Color.BLACK);
  //   return transition;
  // }

  private void handleMenuButtonClick() {
    // Handle menu button click event (e.g., show settings, options, etc.)
    System.out.println("Menu button clicked!");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
