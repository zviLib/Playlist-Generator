package view;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Song;

/**
 * A pop-up screen displaying suggested playlist
 * 
 * @author Zvi
 *
 */
public class PlaylistScreen {

	private Scene scene; // the pop-up scene
	private String header; // the name of the playlist

	/**
	 * @param header
	 *            - the name of the playlist
	 * @param songs
	 *            - songs to display
	 */
	public PlaylistScreen(String header, List<Song> songs) {

		this.header = header;

		// set list pane
		VBox list = new VBox(10);
		list.setAlignment(Pos.TOP_CENTER);

		// set header
		Label head = new Label(header);
		head.setId("header");
		list.getChildren().add(head);

		// add songs
		int count = 1;
		for (Song song : songs) {
			list.getChildren().add(songRow(count++, song));
			// limit number of songs to fit display space
			if (count == 12)
				break;
		}

		// finalize
		scene = new Scene(list);
		scene.getStylesheets().add(ClassLoader.getSystemResource("PlaylistScreen.css").toString());
	}

	/**
	 * display the pop-up screen
	 * 
	 * @param primary
	 *            - the app's main stage
	 */
	public void display(Stage primary) {
		// set view as pop up screen
		Stage popMenu = new Stage();
		popMenu.setTitle(header);
		popMenu.setScene(scene);
		popMenu.setResizable(false);
		popMenu.initModality(Modality.APPLICATION_MODAL);
		popMenu.initOwner(primary);
		popMenu.toFront();
		popMenu.show();
	}

	/**
	 * @param rowNum
	 *            - the row's number
	 * @param s
	 *            - the song to display
	 * @return a row displaying the song's information
	 */
	private HBox songRow(int rowNum, Song s) {

		// set cursor change on point
		HBox row = new HBox();
		row.setCursor(Cursor.HAND);

		////// add row number //////
		TilePane numBox = new TilePane();
		numBox.setId("num_box");
		Label l = new Label(String.format("%2d", rowNum));
		l.setId("row_num");
		numBox.getChildren().add(l);
		////////////////////////

		///// add song info ////////
		VBox songInfo = new VBox();
		songInfo.setId("song_info");
		// song name
		l = new Label(s.name);
		l.setId("song_name");
		songInfo.getChildren().add(l);
		// artist name
		l = new Label(s.artist);
		l.setId("artist_name");
		songInfo.getChildren().add(l);
		//////////////////////////

		////// add song duration ///////
		TilePane dur = new TilePane();
		dur.setId("dur_pane");
		l = new Label(s.duration.toString());
		l.setId("duration");
		dur.getChildren().add(l);
		///////////////////////////

		///// finalize /////
		row.getChildren().addAll(numBox, songInfo, dur);

		// set mouse point handlers//
		row.setOnMouseEntered((e) -> {
			row.getStylesheets().add(ClassLoader.getSystemResource("rowPointed.css").toString());
		});
		row.setOnMouseExited((e) -> {
			row.getStylesheets().remove(ClassLoader.getSystemResource("rowPointed.css").toString());
		});

		return row;
	}
}
