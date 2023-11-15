package application;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

//This class is a location screen that display the statistic of location
public class StatisticPane extends BorderPane {

	// This constructor make a object and add the component and statistic of
	// selected location to the pane
	public StatisticPane(MyDoubleLinkedList list, DoubleNode current, Stage stage) {

		// The top of the Screen (border pane)
		Button backBtn = new Button("Back");
		this.setMargin(backBtn, new Insets(30));
		this.setTop(backBtn);
		// This action for back button, To return to the home page
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				HomePane homePane = new HomePane(list, stage);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
			}
		});

		// The left of the Screen (border pane)
		Button leftBtn = new Button("Left");
		leftBtn.setPrefSize(50, 50);
		this.setMargin(leftBtn, new Insets(50));
		this.setAlignment(leftBtn, Pos.CENTER);
		this.setLeft(leftBtn);
		// This action for left button, To display the previous location
		leftBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// When the previous is no location (null) display all location
				if (current.getPre() != null) {
					StatisticPane sp = new StatisticPane(list, current.getPre(), stage);
					Scene scene = new Scene(sp);
					stage.setScene(scene);
				} else {
					StatisticPane sp = new StatisticPane(list, stage);
					Scene scene = new Scene(sp);
					stage.setScene(scene);
				}
			}
		});
		
		// The right of the Screen (border pane)
		Button rightBtn = new Button("Right");
		rightBtn.setPrefSize(50, 50);
		this.setMargin(rightBtn, new Insets(50));
		this.setAlignment(rightBtn, Pos.CENTER);
		this.setRight(rightBtn);

		// Disable the right button when no next location (null) 
		rightBtn.setDisable(current.getNext() == null);
		// This action for right button, To display the next location
		rightBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				StatisticPane sp = new StatisticPane(list, current.getNext(), stage);
				Scene scene = new Scene(sp);
				stage.setScene(scene);
			}
		});

		// The center of the Screen (border pane)
		Label locationLabel = new Label(current.getLocation());
		locationLabel.setFont(new Font("Arial", 26));

		
		if (!current.getStart().isEmpty()) {
			// When their is data (martyr) in current location
			
			// Get statistic of current location
			StatisticData statisticData = new StatisticData(current);

			// Put statistic data in the labels and table view
			Label maleLabel = new Label("The number of male : " + statisticData.getNumberOfMale());
			maleLabel.setFont(new Font("Arial", 20));

			Label femaleLabel = new Label("The number of female : " + statisticData.getNumberOfFemale());
			femaleLabel.setFont(new Font("Arial", 20));

			Label avaAgeLabel = new Label("The avaerage of age : " + statisticData.getAverageOfAge());
			avaAgeLabel.setFont(new Font("Arial", 20));

			Label percentageLabel = new Label("The percentage of martyrs : " + statisticData.getThePercentage() + "%");
			percentageLabel.setFont(new Font("Arial", 20));

			Label maxDateLabel = new Label("The date that has the maximum number of martyrs : "
					+ new SimpleDateFormat("MM/dd/yyyy").format(statisticData.getMaxDate()));
			maxDateLabel.setFont(new Font("Arial", 20));

			VBox statisticVBox = new VBox(maleLabel, femaleLabel, avaAgeLabel, percentageLabel, maxDateLabel);
			statisticVBox.setSpacing(15);
			statisticVBox.setAlignment(Pos.CENTER_LEFT);

			// Make a table view for age
			TableView<CountNode<Byte>> tv = new TableView();

			// Make a column age of martyr
			TableColumn<CountNode<Byte>, String> ageColumn = new TableColumn<CountNode<Byte>, String>("Age");
			// This to customize the insert data to the age column
			ageColumn.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<CountNode<Byte>, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<CountNode<Byte>, String> data) {
							if (data.getValue().getData() < 0)
								return new SimpleStringProperty("No Data");
							return new SimpleStringProperty(data.getValue().getData() + "");
						}
					});
			ageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			ageColumn.setSortable(true);

			// Make a column age count of martyr
			TableColumn<CountNode<Byte>, String> countColumn = new TableColumn<CountNode<Byte>, String>(
					"Numbers of Martyrs");
			// This to customize the insert data to the age count column
			countColumn.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<CountNode<Byte>, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<CountNode<Byte>, String> data) {
							return new SimpleStringProperty(data.getValue().getCount() + "");
						}
					});
			countColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			countColumn.setSortable(true);

			// Add all columns to the tabel view
			tv.getColumns().addAll(ageColumn, countColumn);
			tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tv.setEditable(false);
			tv.setPrefWidth(500);
			// Add age and age count of current location to the table view
			statisticData.setAgeData(tv);

			HBox statisticHBox = new HBox(tv, statisticVBox);
			statisticHBox.setSpacing(15);
			statisticHBox.setAlignment(Pos.CENTER);

			VBox centerVBox = new VBox(locationLabel, statisticHBox);
			centerVBox.setSpacing(80);
			centerVBox.setAlignment(Pos.CENTER);

			this.setCenter(centerVBox);
		} else {
			// When their is no data (martyr) in current location
			Label noData = new Label("No martye added");
			noData.setFont(new Font("Arial", 20));
			noData.setTextFill(Color.RED);
			VBox centerVBox = new VBox(locationLabel, noData);
			centerVBox.setSpacing(80);
			centerVBox.setAlignment(Pos.CENTER);
			this.setCenter(centerVBox);
		}

	}

	// This constructor make a object and add the component and statistic of
	// all locations to the pane
	public StatisticPane(MyDoubleLinkedList list, Stage stage ) {

		// The top of the Screen (border pane)
		Button backBtn = new Button("Back");
		this.setMargin(backBtn, new Insets(30));
		this.setTop(backBtn);
		// This action for back button, To return to the home page
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				HomePane homePane = new HomePane(list, stage);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
			}
		});

		// The left of the Screen (border pane),here this button is useless
		Button leftBtn = new Button("Left");
		leftBtn.setPrefSize(50, 50);
		this.setMargin(leftBtn, new Insets(50));
		this.setAlignment(leftBtn, Pos.CENTER);
		this.setLeft(leftBtn);
		leftBtn.setDisable(true);
	
		// The right of the Screen (border pane)
		Button rightBtn = new Button("Right");
		rightBtn.setPrefSize(50, 50);
		this.setMargin(rightBtn, new Insets(50));
		this.setAlignment(rightBtn, Pos.CENTER);
		this.setRight(rightBtn);
		// Disable the right button when no next location (null) 
		rightBtn.setDisable(list.getFirstDoubleNode() == null);
		// This action for right button, To display the next location
		rightBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				StatisticPane sp = new StatisticPane(list, list.getFirstDoubleNode(), stage);
				Scene scene = new Scene(sp);
				stage.setScene(scene);

			}
		});
	
		// The center of the Screen (border pane)
		Label locationLabel = new Label("All");
		locationLabel.setFont(new Font("Arial", 26));
		// Get statistic of all location
		StatisticData statisticData = new StatisticData(list);
		if (StatisticData.getAllMartrCount() != 0) {
			// When their is data (martyr) in all location
			
			// Put statistic data in the labels and table view
			Label maleLabel = new Label("The number of male : " + statisticData.getNumberOfMale());
			maleLabel.setFont(new Font("Arial", 20));

			Label femaleLabel = new Label("The number of female : " + statisticData.getNumberOfFemale());
			femaleLabel.setFont(new Font("Arial", 20));

			Label avaAgeLabel = new Label("The avaerage of age : " + statisticData.getAverageOfAge());
			avaAgeLabel.setFont(new Font("Arial", 20));

			Label percentageLabel = new Label("The percentage of martyrs : " + statisticData.getThePercentage()+"%");
			percentageLabel.setFont(new Font("Arial", 20));

			Label maxDateLabel = new Label("The date that has the maximum number of martyrs : "
					+ new SimpleDateFormat("MM/dd/yyyy").format(statisticData.getMaxDate()));
			maxDateLabel.setFont(new Font("Arial", 20));

			VBox statisticVBox = new VBox(maleLabel, femaleLabel, avaAgeLabel, percentageLabel, maxDateLabel);
			statisticVBox.setSpacing(15);
			statisticVBox.setAlignment(Pos.CENTER_LEFT);

			// Make a table view for age
			TableView<CountNode<Byte>> tv = new TableView();

			// Make a column age of martyr
			TableColumn<CountNode<Byte>, String> ageColumn = new TableColumn<CountNode<Byte>, String>("Age");
			// This to customize the insert data to the age column
			ageColumn.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<CountNode<Byte>, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<CountNode<Byte>, String> data) {
							if (data.getValue().getData() < 0)
								return new SimpleStringProperty("No Data");
							return new SimpleStringProperty(data.getValue().getData() + "");
						}
					});
			ageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			ageColumn.setSortable(true);

			// Make a column age count of martyr
			TableColumn<CountNode<Byte>, String> countColumn = new TableColumn<CountNode<Byte>, String>(
					"Numbers of Martyrs");
			// This to customize the insert data to the age count column
			countColumn.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<CountNode<Byte>, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<CountNode<Byte>, String> data) {
							return new SimpleStringProperty(data.getValue().getCount() + "");
						}
					});
			countColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			countColumn.setSortable(true);

			// Add all columns to the tabel view
			tv.getColumns().addAll(ageColumn, countColumn);
			tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tv.setEditable(false);
			tv.setPrefWidth(500);
			// Add age and age count to the table view
			statisticData.setAgeData(tv);

			HBox statisticHBox = new HBox(tv, statisticVBox);
			statisticHBox.setSpacing(15);
			statisticHBox.setAlignment(Pos.CENTER);

			VBox centerVBox = new VBox(locationLabel, statisticHBox);
			centerVBox.setSpacing(80);
			centerVBox.setAlignment(Pos.CENTER);

			this.setCenter(centerVBox);
		} else {
			// When their is no data (martyr) in all locations
			Label noData = new Label("No martye added");
			noData.setFont(new Font("Arial", 20));
			noData.setTextFill(Color.RED);
			VBox centerVBox = new VBox(locationLabel, noData);
			centerVBox.setSpacing(80);
			centerVBox.setAlignment(Pos.CENTER);
			this.setCenter(centerVBox);
		}

	}

}
