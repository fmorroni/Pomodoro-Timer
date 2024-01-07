package frontend;

import backend.Time;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.NavigationDrawer;

public class DrawerManager {

  public static void buildDrawer(AppManager app) {
    NavigationDrawer drawer = app.getDrawer();
    // drawer.setSide(Side.RIGHT);
    // drawer.getStylesheets().add(PomodoroTimer.class.getResource("drawer.css").toExternalForm());
    // drawer.setStyle("-fx-background-color: blue");
    // drawer.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

    NavigationDrawer.Header header = new NavigationDrawer.Header("Settings");
    // header.setStyle("-fx-background-color: blue;");
    drawer.setHeader(header);
    // new NavigationDrawer.Header(
    //     "Gluon Application",
    //     "Pomodoro Timer",
    //     new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/icon.png"))));

    // final Item mainItem =
    //     new ViewItem(
    //         "Primary",
    //         MaterialDesignIcon.HOME.graphic(),
    //         PomodoroTimer.MAIN_VIEW,
    //         ViewStackPolicy.SKIP);
    // drawer.getItems().addAll(mainItem);

    SettingsList sl = new SettingsList();
    sl.add("Work Interval Duration", Time.fromMinutes(10), Time.fromHours(2), "#fe4d4c");
    sl.add("Short Break Interval Duration", Time.fromMinutes(2), Time.fromMinutes(30), "#05eb8b");
    sl.add("Long Break Interval Duration", Time.fromMinutes(5), Time.fromHours(2), "#0bbcda");
    drawer.getItems().add(sl.getNode());

    // if (Platform.isDesktop()) {
    //   final Item quitItem = new Item("X", MaterialDesignIcon.EXIT_TO_APP.graphic());
    //   quitItem
    //       .selectedProperty()
    //       .addListener(
    //           (obs, ov, nv) -> {
    //             if (nv) {
    //               Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
    //             }
    //           });
    //   drawer.getItems().add(quitItem);
    // }
  }
}
