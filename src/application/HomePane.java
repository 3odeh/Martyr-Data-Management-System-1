package application;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

// This class is a home screen
public class HomePane extends BorderPane {

	// This constructor make a object and add the component to the pane
	public HomePane(MyDoubleLinkedList list, Stage stage) {

		// The top of the Screen (border pane)
		TextField searchTF = new TextField();
		Button searchBtn = new Button("Search");
		Button allBtn = new Button("All");
		HBox searchHbox = new HBox(allBtn, searchTF, searchBtn);
		searchHbox.setSpacing(1);
		Button loadBtn = new Button("load");
		Button saveBtn = new Button("save");
		HBox topHbox = new HBox(loadBtn, searchHbox, saveBtn);
		topHbox.setAlignment(Pos.CENTER);
		topHbox.setSpacing(30);
		this.setMargin(topHbox, new Insets(30));
		this.setTop(topHbox);

		// The center of the Screen (border pane)
		GridPane gp = new GridPane();
		fillData(list, gp, stage);
		ScrollPane sp = new ScrollPane(gp);
		this.setCenter(sp);

		// This file chooser to select file by user
		FileChooser loadFileChooser = new FileChooser();
		loadFileChooser.setTitle("Select file");
		loadFileChooser.getExtensionFilters().addAll(new ExtensionFilter("All", "*.csv", "*.txt"),
				new ExtensionFilter("Text File", "*.txt"), new ExtensionFilter("Excel File", "*.csv"));

		// This action for load button, To let user select file and load the data then
		// display the location
		loadBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File file = loadFileChooser.showOpenDialog(stage);
				if (file != null && file.canRead()) {
					loadBtn.setText(file.getName());
					try {
						if (list.read(file)) {
							StatisticData.isUpdatedData = true;
							fillData(list, gp, stage);
						}
					} catch (Exception e) {
						GeneralPanes.errorAlert("Error");
					}
				} else {
					GeneralPanes.warningMessage("No selected file");
				}

			}
		});

		// This file chooser to save the file in the selected place
		FileChooser saveFileChooser = new FileChooser();
		saveFileChooser.setTitle("Save File");
		saveFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text File", "*.txt"),
				new ExtensionFilter("Excel File", "*.csv"));

		// This action for save button, To let user select file and save the data
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File file = saveFileChooser.showSaveDialog(stage);
				if (file != null) {
					try {
						if (!file.exists())
							file.createNewFile();
					} catch (Exception e) {
						GeneralPanes.errorAlert(e.getMessage());
					}
				}
			}
		});

		// This action for search button, To search about the location name
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = searchTF.getText();
				if (text != null && !text.isEmpty()) {
					text = text.strip();
					fillDataWithSearch(list, gp, text, stage);
				} else {
					fillData(list, gp, stage);
				}

			}
		});

		// This action for all button, to display all location on the center of screen
		allBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				fillData(list, gp, stage);
			}
		});
	}

	// This method to display all location in the center of screen
	private void fillData(MyDoubleLinkedList list, GridPane gp, Stage stage) {

		// To remove all old component
		gp.getChildren().clear();

		// This for Add section (Add new location)
		Button addBtn = new Button("Add");
		TextField addTF = new TextField();
		Label errorLabel = new Label();
		errorLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		VBox addVBox = new VBox(addTF, addBtn, errorLabel);
		addVBox.setSpacing(5);
		addBtn.setPrefHeight(40);
		addBtn.setPrefWidth(170);
		addTF.setPrefWidth(170);
		addTF.setMaxWidth(170);
		gp.setMargin(addVBox, new Insets(45, 0, 0, 45));
		gp.add(addVBox, 0, 0);

		if (!list.isEmpty()) {
			// Add all location
			list.addLocationToGP(gp, stage);
		} else {
			// no data for location
			Label emptyLabel = new Label("Empty List");
			gp.add(emptyLabel, 1, 0);
			gp.setMargin(emptyLabel, new Insets(45));
		}

		// This action for add button, To add new location
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = addTF.getText();
				if (text != null && !text.isEmpty()) {
					text = text.strip();
					if (list.add(text)) {
						fillData(list, gp, stage);
					} else {
						errorLabel.setText("Error exist location");
					}
				} else {
					errorLabel.setText("Please enter the location");
				}
			}
		});

	}

	// This method to search input name then display all location that start of that
	// name
	private void fillDataWithSearch(MyDoubleLinkedList list, GridPane gp, String search, Stage stage) {

		// To remove all old component
		gp.getChildren().clear();

		// This for Add section (Add new location)
		Button addBtn = new Button("Add");
		TextField addTF = new TextField();
		Label errorLabel = new Label();
		errorLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		VBox addVBox = new VBox(addTF, addBtn, errorLabel);
		addVBox.setSpacing(5);
		addBtn.setPrefHeight(40);
		addBtn.setPrefWidth(170);
		addTF.setPrefWidth(170);
		addTF.setMaxWidth(170);
		gp.setMargin(addVBox, new Insets(45, 0, 0, 45));
		gp.add(addVBox, 0, 0);

		if (!list.isEmpty()) {
			// Add all searched location
			list.addLocationToGP(gp, search, stage);
		} else {
			// no data for locations
			Label emptyLabel = new Label("Empty List");
			gp.add(emptyLabel, 1, 0);
			gp.setMargin(emptyLabel, new Insets(45));
		}

		// This action for add button, To add new location
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = addTF.getText();
				if (text != null && !text.isEmpty()) {
					if (list.add(text)) {
						text = text.strip();
						fillData(list, gp, stage);
					} else {
						errorLabel.setText("Error exist location");
					}

				} else {
					errorLabel.setText("Please enter the location");
				}
			}
		});

	}

}
