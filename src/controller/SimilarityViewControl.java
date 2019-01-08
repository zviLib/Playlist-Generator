package controller;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Song;
import model.server.Client;
import model.server.Message;
import view.PlaylistScreen;
import view.SimilarityView;

/**
 * Controls the view where the user can select a song and get a playlist
 * according the a specific attribute of the selected song.
 * 
 * @author Zvi
 *
 */
public class SimilarityViewControl implements ViewControl {

	private SimilarityView view;
	private Client client;
	private Stage primaryStage;

	/**
	 * @param client
	 *            - communication method with the user
	 * @param stage
	 *            - the application's main stage
	 */
	public SimilarityViewControl(Client client, Stage stage) {

		this.primaryStage = stage;
		this.client = client;

		// initialize the view
		view = new SimilarityView();

		// set button's event handlers
		setRefreshHandler();
		setDisplayHandler();
		setSelectHandler();

		// set the first suggested songs
		view.forceRefresh();
	}

	/**
	 * reads songs from the server and displays them
	 */
	public void setRefreshHandler() {
		view.setRefreshHandler((e) -> {

			// send refresh message to server
			client.sendMessage(Message.refresh, String.valueOf(0));

			// read list of songs from server and them on view
			view.setSongs(client.getInfo());

			// empty previous suggested categories
			view.emptyCategories();
		});
	}

	/**
	 * sends selected song and displays received categories
	 */
	public void setSelectHandler() {
		view.setSelectHandler((e) -> {

			// get selected id from view
			int id = view.getSelectedSong();
			if (id == -1)
				return;

			// send selected song number to server
			client.sendMessage(Message.songId, String.valueOf(id));

			// switch panes
			view.switchPanes();

			// set available categories
			view.setCategories(client.getInfo());
		});
	}

	/**
	 * sends selected category and displays received playlist
	 */
	public void setDisplayHandler() {
		view.setDisplayHandler((e) -> {

			// get selected option from view
			int id = view.getSelectedCat();
			// if nothing was selected
			if (id == -1)
				return;

			// send selected id to server
			client.sendMessage(Message.categorieId, String.valueOf(id));

			// read input and create song objects
			List<Song> songList = client.getSongs();

			PlaylistScreen ps = new PlaylistScreen(view.getCatName(), songList);
			ps.display(primaryStage);
		});
	}

	@Override
	public Pane getDisplay() {
		return view.getDisplay();
	}

}
