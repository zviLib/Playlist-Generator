package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Lets the user select a song and get a playlist according the a specific
 * attribute of the selected song.
 * 
 * @author Zvi
 *
 */
public class SimilarityView implements View {

	private VBox back; // the view's base
	private VBox songsPane; // displays songs the select
	private VBox categorie; // displays the attribute to select
	private Button refresh, select, display;

	private List<RadioButton> songButtons; // saves the selected song
	private List<RadioButton> catButtons; // saves the selected category
	private String catName;

	/**
	 * initializes the view
	 */
	public SimilarityView() {
		setDisplay();
	}

	private void setDisplay() {
		back = new VBox();

		///// songs box //////
		songsPane = new VBox(7);
		// place song's box header and buttons
		HBox headerRow = new HBox(7);
		Label l = new Label("Choose song to complete into playlists: ");
		l.setMinWidth(350);
		l.setId("subHeader");

		refresh = new Button("Refresh");
		refresh.setId("simButton");

		select = new Button("Select");
		select.setId("simButton");
		
		// finalize
		headerRow.getChildren().addAll(l, refresh, select);
		songsPane.getChildren().add(headerRow);
		songsPane.setId("back");
		/////////////////////

		///// categories pane /////
		categorie = new VBox(7);
		// set categories box header and button
		headerRow = new HBox(7);
		l = new Label("Choose categorie to display: ");
		l.setId("subHeader");
		l.setMinWidth(350);

		display = new Button("Display");
		Button prev = new Button(" Back ");
		// set back handler
		prev.setOnAction(e -> switchPanes());
		// finalize
		headerRow.getChildren().addAll(l, prev, display);
		categorie.getChildren().add(headerRow);
		categorie.setId("back");
		//////////////////////

		//// Set Boxes margin ////
		Insets boxMargin = new Insets(10, 30, 0, 30);
		VBox.setMargin(songsPane, boxMargin);
		VBox.setMargin(categorie, boxMargin);
		/////////////////////

		// put songs pane on display
		back.getChildren().add(songsPane);
	}

	public void setRefreshHandler(EventHandler<ActionEvent> handler) {
		refresh.setOnAction(handler);
	}

	public void setSelectHandler(EventHandler<ActionEvent> handler) {
		select.setOnAction(handler);
	}

	public void setDisplayHandler(EventHandler<ActionEvent> handler) {
		display.setOnAction(handler);
	}

	/**
	 * Adds available songs for selection
	 * @param songs
	 */
	public void setSongs(List<String> songs) {
		// delete old songs
		while (songsPane.getChildren().size() > 1)
			songsPane.getChildren().remove(1);

		songButtons = new ArrayList<>();
		ToggleGroup songsGroup = new ToggleGroup();

		// add all received songs
		String[] split;
		int count = 0;
		for (String s : songs) {
			split = s.split(";");
			RadioButton rb = new RadioButton(String.format("%s - %s", split[0], split[1]));
			rb.setToggleGroup(songsGroup);
			songButtons.add(rb);
			songsPane.getChildren().add(rb);
			// limit number of song to display room
			if (++count == 12)
				break;
		}

		// make sure an option is selected
		songButtons.get(0).setSelected(true);
	}

	public void setCategories(List<String> cat) {
		// delete previous categories
		emptyCategories();

		catButtons = new ArrayList<>();
		ToggleGroup catGroup = new ToggleGroup();

		// add all received categories
		for (String s : cat) {
			RadioButton rb = new RadioButton(s);
			rb.setToggleGroup(catGroup);
			catButtons.add(rb);
			categorie.getChildren().add(rb);
		}

		// make sure an option is selected
		catButtons.get(0).setSelected(true);

	}

	/**
	 * @return the id of the selected song
	 */
	public int getSelectedSong() {

		if (songButtons == null)
			return -1;

		int size = songButtons.size();
		for (int i = 0; i < size; i++) {
			if (songButtons.get(i).isSelected())
				return i;
		}

		return -1;

	}
	
	/**
	 * @return the id of the selected category
	 */
	public int getSelectedCat() {

		if (catButtons == null)
			return -1;

		int size = catButtons.size();
		for (int i = 0; i < size; i++) {
			if (catButtons.get(i).isSelected()) {
				catName = catButtons.get(i).getText();
				return i;
			}
		}

		return -1;

	}

	/**
	 * Erase previous categories
	 */
	public void emptyCategories() {
		// leave only the header row
		while (categorie.getChildren().size() > 1)
			categorie.getChildren().remove(1);
	}

	/**
	 * force the view to get new songs
	 */
	public void forceRefresh() {
		refresh.fire();
	}

	/**
	 * @return the selected category's name
	 */
	public String getCatName() {
		return catName;
	}

	@Override
	public Pane getDisplay() {
		return back;
	}

	/**
	 * switches between the two sub-menues
	 */
	public void switchPanes() {
		if (back.getChildren().contains(songsPane)) {
			back.getChildren().remove(songsPane);
			back.getChildren().add(categorie);
		} else if (back.getChildren().contains(categorie)) {
			back.getChildren().remove(categorie);
			back.getChildren().add(songsPane);
		}
	}
}
