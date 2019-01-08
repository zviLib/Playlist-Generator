package view;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Let the user select the emphasis of the playlist
 * 
 * @author Zvi
 *
 */
public class CustomView implements View {

	private VBox back; // the view's base
	private Button submit;

	private List<TextField> scores; // saves the rank of each attribute
	private final String[] attributes;

	public CustomView() {
		attributes = new String[] { "Loudness", "Danceability", "Hotness", "Tempo" };

		scores = new ArrayList<>();
		setDisplay();

	}

	/**
	 * initializes display
	 */
	private void setDisplay() {
		// set background
		back = new VBox(15);
		back.setId("back");
		back.setAlignment(Pos.CENTER);

		// add slider for each attribute
		for (String s : attributes)
			back.getChildren().add(slideRow(s));

		// add submit button
		submit = new Button("Submit");
		back.getChildren().add(submit);
	}

	/**
	 * @return a slider in range 1-10
	 */
	private Slider createSlider() {
		Slider slider = new Slider(1, 10, 5);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMinorTickCount(1);
		slider.setBlockIncrement(1);
		return slider;
	}

	/**
	 * @param name
	 *            - the name of the slider
	 * @return a row with the slider's name, the slider and a field displaying
	 *         the selection
	 */
	private HBox slideRow(String name) {
		HBox row = new HBox(5);
		// display name of property
		Label header = new Label(name);
		header.setId("slider_header");
		// get new slider
		Slider slider = createSlider();
		// display selected value
		TextField score = new TextField(String.valueOf((int) slider.getValue()));
		score.setDisable(true);

		// bind slider with textView
		slider.valueProperty().addListener((a, b, c) -> score.setText(String.valueOf(c.intValue())));

		// add slider to scores list
		scores.add(score);

		// add all
		row.getChildren().addAll(header, slider, score);

		return row;
	}

	public void setSubmitHandler(EventHandler<ActionEvent> handler) {
		submit.setOnAction(handler);
	}

	/**
	 * @return the rank of each attribute coded with spaces between them
	 */
	public String getRanks() {
		StringBuilder sb = new StringBuilder();

		// add all scores to string
		/// Loudness
		sb.append(normalizeValue(scores.get(0).getText(), (x) -> x * -5.2f) + " ");
		/// Danceability
		sb.append(normalizeValue(scores.get(1).getText(), (x) -> x * 0.1f) + " ");
		/// Hotness
		sb.append(normalizeValue(scores.get(2).getText(), (x) -> x * 0.1f) + " ");
		/// Tempo
		sb.append(normalizeValue(scores.get(3).getText(), (x) -> x * 26.3f));

		return sb.toString();
	}

	@Override
	public Pane getDisplay() {
		return back;
	}

	private String normalizeValue(String value, Function<Integer, Float> func) {

		int val = Integer.parseInt(value);
		float res = func.apply(val);
		return String.valueOf(res);
	}
}