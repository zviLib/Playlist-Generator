package controller;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.server.Client;
import model.server.DemoClient;
import model.server.Message;
import model.server.TCPClient;
import view.MainView;

/**
 * Controls the main view, including the scene
 * 
 * @author Zvi
 *
 */
public class MainViewControl {

	private Client client;
	private Stage stage;
	private MainView view;

	/**
	 * @param demo
	 *            - whether we are simulating the server or not
	 */
	public MainViewControl(boolean demo) {
		this.view = new MainView();

		// connect to server
		if (demo)
			client = new DemoClient();
		else
			client = new TCPClient();

		// set button handlers
		setSimilarityHandler();
		setPopularHandler();
		setExploreHandler();
		setCustomHandler();
		setHomeHandler();

	}

	/**
	 * display the application's scene
	 * 
	 * @param prim
	 *            - the main stage
	 */
	public void display(Stage prim) {
		this.stage = prim;
		prim.setResizable(false);
		prim.setTitle("Playlist Generator");
		prim.setScene(view.getScene());
		prim.show();
	}

	/**
	 * display the similarity menu
	 */
	private void setSimilarityHandler() {
		view.setSimilarityHandler(e -> {
			SimilarityViewControl svc = new SimilarityViewControl(client, stage);

			// replace selection buttons with header
			view.setMidToLabel(new Label("Choose by similarity"));

			// Display view
			view.setLowBox(svc.getDisplay());
		});
	}

	/**
	 * display the custom menu.
	 */
	private void setCustomHandler() {
		view.setCustomHandler(e -> {
			CustomViewControl cvc = new CustomViewControl(client, stage);

			// replace selection buttons with header
			view.setMidToLabel(new Label("Custom Selection"));

			// display view
			view.setLowBox(cvc.getDisplay());
		});
	}

	/**
	 * display the popular menu
	 */
	private void setPopularHandler() {
		view.setPopularHandler(e -> {

			// send popular message to server
			client.sendMessage(Message.popular, String.valueOf(0));

			// read suggested categories
			List<String> cat = client.getInfo();

			// replace selection buttons with header
			view.setMidToLabel(new Label("Popular"));

			// show categories
			showCategories(cat);
		});
	}

	/**
	 * display the explore menu
	 */
	private void setExploreHandler() {
		view.setExploreHandler(e -> {

			// send popular message to server
			client.sendMessage(Message.explore, String.valueOf(0));

			// read suggested categories
			List<String> cat = client.getInfo();

			// replace selection buttons with header
			view.setMidToLabel(new Label("Explore"));

			// show categories
			showCategories(cat);
		});
	}

	/**
	 * display the main menu
	 */
	private void setHomeHandler() {
		view.setHomeHandler(e -> {
			view.setMidToButtons();
			view.setLowBox(new GridPane());
		});
	}

	/**
	 * show the category selection after the user choose Popular or Explore
	 * 
	 * @param catrgories
	 *            - the categories to select from
	 */
	private void showCategories(List<String> catrgories) {
		// initialize view
		CategoriesViewControl cvc = new CategoriesViewControl(client, stage, catrgories);
		// display
		view.setLowBox(cvc.getDisplay());
	}
}
