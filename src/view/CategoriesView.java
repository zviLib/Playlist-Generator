package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Displays categories that the user selects as the theme of the wanted playlist
 * 
 * @author Zvi
 *
 */
public class CategoriesView implements View {

	private VBox back; // the view's base
	private VBox[] columns; // columns displaying available categories

	private List<Label> catButtons; // stores the categories selected\not
									// selected
	private Button select;

	/**
	 * @param categories
	 *            - the categories to display
	 */
	public CategoriesView(List<String> categories) {
		setDisplay();
		setCategories(categories);
	}

	/**
	 * create the view's display
	 */
	private void setDisplay() {
		back = new VBox(15);
		back.setId("back");
		back.setAlignment(Pos.CENTER);
		HBox columnsPane = new HBox();
		catButtons = new ArrayList<>();

		// set the button's columns
		columns = new VBox[2];
		columns[0] = new VBox(7);
		columns[1] = new VBox(7);

		// set columns margin
		HBox.setMargin(columns[0], new Insets(10, 10, 0, 20));
		HBox.setMargin(columns[1], new Insets(10, 20, 0, 10));

		// add columns to display
		columnsPane.getChildren().addAll(columns);

		// initialize select button
		select = new Button("Select");
		back.getChildren().addAll(columnsPane, select);
	}

	/**
	 * @param categories
	 *            - the categories to display
	 */
	private void setCategories(List<String> categories) {

		// make a button for each category
		for (int i = 0; i < categories.size(); i++) {
			Label b = new Label(categories.get(i));
			b.setId("cat_label");
			b.setMinSize(150, 50);
			// set event handler
			b.setOnMouseClicked(buttonHandler());
			// add to list
			catButtons.add(b);

			//// set initial pick /////
			if (i == 0)
				b.setId("selected");
			else
				b.setId("not_select");
			///////////////////////////

			// add to display
			columns[i % 2].getChildren().add(b);
		}
	}

	/**
	 * @return Handler that marks selected button
	 */
	private EventHandler<MouseEvent> buttonHandler() {
		return e -> {
			for (Label b : catButtons)
				if (e.getSource().equals(b))
					b.setId("selected");
				else
					b.setId("not_selected");
		};
	}

	public void setSelectHandler(EventHandler<ActionEvent> handler) {
		select.setOnAction(handler);
	}

	/**
	 * @return the id of the selected category
	 */
	public int getSelectedCategory() {
		for (int i = 0; i < catButtons.size(); i++) {
			if (catButtons.get(i).getId().equals("selected"))
				return i;
		}

		return -1;
	}

	/**
	 * @return the selected category's name
	 */
	public String getCatName() {
		for (Label b : catButtons) {
			if (b.getId().equals("selected"))
				return b.getText();
		}

		return "";
	}

	@Override
	public Pane getDisplay() {
		return back;
	}
}
