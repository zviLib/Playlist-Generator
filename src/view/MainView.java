package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * The main view of the application.
 * Also controls the scene
 * @author Zvi
 *
 */
public class MainView {

	private Scene scene; // the display
	private VBox midBox; // displays buttons\menu names
	private GridPane lowBox; // displays the sub-menus
	private Button popular, explore, similarity, custom;
	private ImageView home; // the home button

	/**
	 * generates the main view
	 */
	public MainView() {
		setScene();
	}

	private void setScene() {

		///// main pane background /////
		VBox back = new VBox();
		back.setAlignment(Pos.TOP_CENTER);
		back.getStylesheets().add(ClassLoader.getSystemResource("root.css").toString());
		/////////////////////

		// add home button
		home = new ImageView(ClassLoader.getSystemResource("home.png").toString());
		VBox homePane = new VBox();
		homePane.setMinWidth(100);
		homePane.setAlignment(Pos.CENTER_LEFT);
		homePane.getChildren().add(home);
		VBox.setMargin(home, new Insets(0, 0, 0, 20));

		// set css
		homePane.getStylesheets().add(ClassLoader.getSystemResource("header_row.css").toString());
		////////////////////

		///// buttons box ////
		midBox = new VBox(40);
		midBox.setId("mid_box");
		midBox.setMinWidth(550);
		// initialize buttons
		popular = new Button("Popular");
		explore = new Button("Explore");
		similarity = new Button("Choose by similarity");
		similarity.setId("similarity");
		custom = new Button("Custom");
		// finalize
		setMidToButtons();
		///////////////////////

		// initialize low box
		lowBox = new GridPane();
		lowBox.setAlignment(Pos.TOP_CENTER);
		lowBox.setId("lowBox");

		///// Finalize Scene ////
		back.getChildren().addAll(homePane, midBox, lowBox);

		// set css
		midBox.getStylesheets().add(ClassLoader.getSystemResource("mid_box.css").toString());
		lowBox.getStylesheets().add(ClassLoader.getSystemResource("low_box.css").toString());

		scene = new Scene(back, 575, 500);
		//////////////////////
	}

	public Scene getScene() {
		return scene;
	}

	public void setSimilarityHandler(EventHandler<ActionEvent> handler) {
		similarity.setOnAction(handler);
	}

	public void setPopularHandler(EventHandler<ActionEvent> handler) {
		popular.setOnAction(handler);
	}

	public void setHomeHandler(EventHandler<MouseEvent> handler) {
		home.setOnMouseClicked(handler);
	}

	public void setExploreHandler(EventHandler<ActionEvent> handler) {
		explore.setOnAction(handler);
	}

	public void setCustomHandler(EventHandler<ActionEvent> handler) {
		custom.setOnAction(handler);
	}

	/**
	 * set the middle pane to display the main menu
	 */
	public void setMidToButtons() {
		// remove previous view
		while (midBox.getChildren().size() > 0)
			midBox.getChildren().remove(0);

		midBox.getChildren().addAll(popular, explore, custom, similarity);
		// make buttons at center
		midBox.setAlignment(Pos.CENTER);
		midBox.setMinHeight(350);
	}

	/**
	 * set middle pane to display sub-menu name
	 * @param l - the sub-menu's name
	 */
	public void setMidToLabel(Label l) {
		// remove previous view
		while (midBox.getChildren().size() > 0)
			midBox.getChildren().remove(0);

		midBox.getChildren().add(l);
		// make label stick to the top
		midBox.setAlignment(Pos.TOP_CENTER);
		midBox.setMinHeight(Region.USE_COMPUTED_SIZE);
	}

	/**
	 * set low box to display sub-menu
	 * @param interior - the sub-menu's display
	 */
	public void setLowBox(Node interior) {
		// remove previous view
		while (lowBox.getChildren().size() > 0)
			lowBox.getChildren().remove(0);

		lowBox.getChildren().add(interior);
	}
}
