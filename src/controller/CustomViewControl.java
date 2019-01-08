package controller;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Song;
import model.server.Client;
import model.server.Message;
import view.CustomView;
import view.PlaylistScreen;

/**
 * Controls the view where the user can select playlist according the his
 * preferences
 * 
 * @author Zvi
 *
 */
public class CustomViewControl implements ViewControl {

	private Client client;
	private Stage primaryStage;
	private CustomView cv;

	/**
	 * @param client
	 *            - communication method with the user
	 * @param stage
	 *            - the application's main stage
	 */
	public CustomViewControl(Client client, Stage stage) {
		this.client = client;
		this.primaryStage = stage;

		// initialize the view
		cv = new CustomView();

		// set the submit button event handler
		setSubmitButton();
	}

	/**
	 * On selection, the client sends to the server the selected user
	 * preferences, reads a list of songs from the server and displays them on
	 * different screen
	 */
	private void setSubmitButton() {
		cv.setSubmitHandler(e -> {

			// send selected values to server
			client.sendMessage(Message.custom, cv.getRanks());

			// read input and create song objects
			List<Song> songList = client.getSongs();

			// show suggested playlist
			PlaylistScreen ps = new PlaylistScreen("Custom Playlist", songList);
			ps.display(primaryStage);
		});
	}

	@Override
	public Pane getDisplay() {
		return cv.getDisplay();
	}
}
