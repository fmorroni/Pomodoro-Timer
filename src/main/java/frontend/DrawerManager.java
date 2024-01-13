package frontend;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.NavigationDrawer;

public class DrawerManager {

  public static void buildDrawer(AppManager app, SettingsList settingsList) {
    NavigationDrawer drawer = app.getDrawer();
    // drawer.setSide(Side.RIGHT);
    // drawer.getStylesheets().add(PomodoroTimer.class.getResource("drawer.css").toExternalForm());
    // drawer.setStyle("-fx-background-color: blue");
    // drawer.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

    NavigationDrawer.Header header = new NavigationDrawer.Header("Settings");
    // header.setStyle("-fx-background-color: blue;");
    drawer.setHeader(header);

    drawer.getItems().add(settingsList.getNode());
  }
}
