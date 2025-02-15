import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lili.Lili;
import lili.LiliException;

/**
 * Main class for GUI.
 */
public class Main extends Application {

    private final Lili lili = new Lili();

    /**
     * Starts the GUI for the application.
     *
     * @param stage The stage object.
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setMinHeight(600);
            stage.setMinWidth(420);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setLili(lili);
            stage.show();
        } catch (IOException e) {
            throw new LiliException();
        }
    }
}
