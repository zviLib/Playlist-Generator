import controller.MainViewControl;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static MainViewControl vc;

	public static void main(String[] args) {

		// create the main view
		vc = new MainViewControl(true);

		// launch application
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		vc.display(primaryStage);
	}

}
