import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import lili.Lili;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Lili lili;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image liliImage = new Image(this.getClass().getResourceAsStream("/images/lili.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        if (lili != null) {
            String welcomeMessage = lili.getWelcomeMessage();
            dialogContainer.getChildren().add(
                    DialogBox.getLiliDialog(welcomeMessage, liliImage)
            );
        }
    }

    /** Injects the Lili instance */
    public void setLili(Lili l) {
        lili = l;

        if (dialogContainer != null) {
            String welcomeMessage = lili.getWelcomeMessage();
            dialogContainer.getChildren().add(
                    DialogBox.getLiliDialog(welcomeMessage, liliImage)
            );
        }
    }

    private void exitApplication() {
        Label exitingLabel = new Label("Exiting");
        dialogContainer.getChildren().add(DialogBox.getLiliDialog("Exiting", liliImage));

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        pauseTransition.setOnFinished(event -> {
            exitingLabel.setText("Exiting.");
            dialogContainer.getChildren().add(DialogBox.getLiliDialog("Exiting.", liliImage));

            PauseTransition pause2 = new PauseTransition(Duration.seconds(0.2));
            pause2.setOnFinished(event2 -> {
                exitingLabel.setText("Exiting..");
                dialogContainer.getChildren().add(DialogBox.getLiliDialog("Exiting..", liliImage));

                PauseTransition pause3 = new PauseTransition(Duration.seconds(0.2));
                pause3.setOnFinished(event3 -> {
                    exitingLabel.setText("Exiting...");
                    dialogContainer.getChildren().add(DialogBox.getLiliDialog("Exiting...", liliImage));

                    PauseTransition finalPause = new PauseTransition(Duration.seconds(0.2));
                    finalPause.setOnFinished(event4 -> Platform.exit());
                    finalPause.play();
                });
                pause3.play();
            });
            pause2.play();
        });
        pauseTransition.play();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isEmpty()) {
            return;
        }

        String response = lili.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getLiliDialog(response, liliImage)
        );

        userInput.clear();

        if (lili.isExitCommand(input)) {
            exitApplication();
        }
    }
}
