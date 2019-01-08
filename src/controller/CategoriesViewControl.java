package controller;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Song;
import model.server.Client;
import model.server.Message;
import view.CategoriesView;
import view.PlaylistScreen;

/**
 * Controls the view when the user selected Popular or Explore options
 * 
 * @author Zvi
 *
 */
public class CategoriesViewControl implements ViewControl {

	private CategoriesView view;
	private Client client;
	private Stage primaryStage;

	/**
	 * @param client
	 *            - communication method with the user
	 * @param stage
	 *            - the application's main stage
	 * @param categories
	 *            - list of categories to display for selection
	 */
	public CategoriesViewControl(Client client, Stage stage, List<String> categories) {

		this.primaryStage = stage;
		this.client = client;

		// initialize the view
		view = new CategoriesView(categories);

		// set the select button event handler
		setSelectHandler();

	}

	/**
	 * On selection, the client sends to the server the selected category, reads
	 * a list of songs from the server and displays them on different screen
	 */
	public void setSelectHandler() {
		view.setSelectHandler((e) -> {

			// get selected option from view
			int id = view.getSelectedCategory();
			// if nothing was selected - do nothing
			if (id == -1)
				return;

			// send selected category id to server
			client.sendMessage(Message.categorieId, String.valueOf(id));

			// read songs list from server
			List<Song> songList = client.getSongs();

			// show suggested playlist
			PlaylistScreen ps = new PlaylistScreen(view.getCatName(), songList);
			ps.display(primaryStage);
		});
	}

	@Override
	public Pane getDisplay() {
		return view.getDisplay();
	}
}
