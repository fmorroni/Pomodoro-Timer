package frontend;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;

public class MainView {

  public View getView() {
    try {
      View view =
          FXMLLoader.load(
              MainView.class.getResource("/main.fxml"),
              //  This loads the main.properties as a resource. (See notes)
              ResourceBundle.getBundle("main"));
      return view;
    } catch (IOException e) {
      System.out.println("IOException: " + e);
      return new View();
    }
  }
}
