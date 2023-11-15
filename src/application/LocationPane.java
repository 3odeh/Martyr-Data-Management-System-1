package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.CharacterStringConverter;

// This class is a location screen that display the information of location
public class LocationPane extends BorderPane {

	// This constructor make a object and add the component and information of
	// selected location to the pane
	public LocationPane(MyDoubleLinkedList list, DoubleNode current, Stage stage) {

		// The center of the Screen (border pane)
		// Make a table view of martyr
		TableView<Martyr> tv = new TableView<Martyr>();

		// Make a column name of martyr
		TableColumn<Martyr, String> nameCol = new TableColumn<Martyr, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Martyr, String>("name"));
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setSortable(false);
		// This to handle update the name of martyr
		nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Martyr, String>>() {
			@Override
			public void handle(CellEditEvent<Martyr, String> event) {
				TablePosition<Martyr, String> pos = event.getTablePosition();
				String newName = event.getNewValue();
				if (newName != null && !newName.isEmpty()) {
					int row = pos.getRow();
					Martyr martyr = event.getTableView().getItems().get(row);
					martyr.setName(newName);
				} else {
					GeneralPanes.errorAlert("Please check the name");
					tv.refresh();
				}
			}
		});

		// Make a column age of martyr
		TableColumn<Martyr, String> ageCol = new TableColumn<Martyr, String>("Age");
		// This to customize the insert data to the column
		ageCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Martyr, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Martyr, String> data) {
						if (data.getValue().getAge() < 0)
							return new SimpleStringProperty("No Data");
						return new SimpleStringProperty("" + data.getValue().getAge());
					}
				});
		ageCol.setSortable(false);
		ageCol.setCellFactory(TextFieldTableCell.forTableColumn());
		// This to handle update the age of martyr
		ageCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Martyr, String>>() {
			@Override
			public void handle(CellEditEvent<Martyr, String> event) {
				TablePosition<Martyr, String> pos = event.getTablePosition();
				try {
					Byte newAge = Byte.parseByte(event.getNewValue());
					if (newAge >= 0) {
						int row = pos.getRow();
						Martyr martyr = event.getTableView().getItems().get(row);
						martyr.setAge(newAge);
					} else {
						GeneralPanes.errorAlert("Please check the age");
						tv.refresh();
					}
				} catch (Exception e) {
					GeneralPanes.errorAlert("Please check the age");
					tv.refresh();
				}

			}
		});

		// Make a date of death column
		TableColumn<Martyr, String> deathDateCol = new TableColumn<Martyr, String>("Date of death");
		deathDateCol.setCellValueFactory(new PropertyValueFactory<Martyr, String>("simpleDateOfDeath"));
		deathDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
		deathDateCol.setSortable(false);
		// This to handle update the date of death of martyr
		deathDateCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Martyr, String>>() {
			@Override
			public void handle(CellEditEvent<Martyr, String> event) {
				try {
					TablePosition<Martyr, String> pos = event.getTablePosition();
					String newDate = event.getNewValue();
					int row = pos.getRow();
					Martyr martyr = event.getTableView().getItems().get(row);
					martyr.setSimpleDateOfDeath(newDate);
					current.getStart().notifyChangeDate(martyr);
					tv.getItems().clear();
					current.getStart().addDataToTableView(tv);
				} catch (Exception e) {
					GeneralPanes
							.errorAlert("Please the be a date of death must a date in this format (month/day/year)");
					tv.refresh();
				}

			}

		});

		// Make a gender column
		TableColumn<Martyr, Character> genderCol = new TableColumn<Martyr, Character>("Gender");
		genderCol.setCellValueFactory(new PropertyValueFactory<Martyr, Character>("gender"));
		genderCol.setCellFactory(TextFieldTableCell.forTableColumn(new CharacterStringConverter()));
		genderCol.setSortable(false);
		// This to handle update the gender of martyr
		genderCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Martyr, Character>>() {

			@Override
			public void handle(CellEditEvent<Martyr, Character> event) {
				TablePosition<Martyr, Character> pos = event.getTablePosition();
				Character newGender = event.getNewValue();
				if (newGender == 'M' || newGender == 'F') {
					int row = pos.getRow();
					Martyr martyr = event.getTableView().getItems().get(row);
					martyr.setGender(newGender);
				} else {
					GeneralPanes.errorAlert("Please check the gender (Male : M , Female : F)");
					tv.refresh();
				}
			}
		});

		// Add all columns to the tabel view
		tv.getColumns().addAll(nameCol, ageCol, genderCol, deathDateCol);
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tv.setEditable(true);
		// Add all martyr in this location
		current.getStart().addDataToTableView(tv);

		// The top of the center of Screen, the search (martyr) section
		TextField searchTF = new TextField();
		Button searchBtn = new Button("Search");
		Button allBtn = new Button("All");
		HBox searchHbox = new HBox(allBtn, searchTF, searchBtn);
		searchHbox.setSpacing(1);
		searchHbox.setAlignment(Pos.CENTER);
		Label locationLabel = new Label(current.getLocation());
		locationLabel.setFont(new Font("Arial", 20));

		// The bottom of the center of Screen, the delete (selected martyr) section
		Button deleteMartyrBtn = new Button("Delete Martyr");
		deleteMartyrBtn.setPrefWidth(150);

		// Then add the top , mid and bottom sections to the center of screen
		VBox centerVBox = new VBox(locationLabel, searchHbox, tv, deleteMartyrBtn);
		centerVBox.setAlignment(Pos.TOP_CENTER);
		centerVBox.setSpacing(5);
		this.setMargin(centerVBox, new Insets(40));
		this.setCenter(centerVBox);
		// This action for search button, To search about the martyr name
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = searchTF.getText();
				if (text != null && !text.isEmpty()) {
					tv.getItems().clear();
					current.getStart().addDataToTableView(tv, text);
				} else {
					tv.getItems().clear();
					current.getStart().addDataToTableView(tv);
				}
			}
		});

		// This action for all button, to display all martyr on the table view
		allBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tv.getItems().clear();
				current.getStart().addDataToTableView(tv);
			}
		});
		// This action for delete button, To delete the selected martyr
		deleteMartyrBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Martyr martyr = tv.getSelectionModel().getSelectedItem();
				if (current.getStart().remove(martyr)) {
					tv.getItems().remove(martyr);
				} else {
					GeneralPanes.errorAlert("Please select a martyr to remove");
				}

			}
		});

		// This listener for label view to change the text of delete button
		tv.getFocusModel().focusedCellProperty().addListener(new ChangeListener<TablePosition>() {
			@Override
			public void changed(ObservableValue<? extends TablePosition> arg0, TablePosition arg1, TablePosition arg2) {
				Martyr martyr = tv.getSelectionModel().getSelectedItem();
				if (martyr != null) {
					deleteMartyrBtn.setText("Delete " + martyr.getName());
				} else {
					deleteMartyrBtn.setText("Delete Martyr");
				}
			}

		});

		// The left of the Screen (border pane)
		Button backBtn = new Button("Back");
		this.setMargin(backBtn, new Insets(30));
		this.setLeft(backBtn);
		// This action for back button, To return to the home page
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				HomePane homePane = new HomePane(list, stage);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
			}
		});

		// The right of the Screen (border pane)
		TextField updateTF = new TextField();
		updateTF.setMaxWidth(170);
		updateTF.setPrefWidth(170);
		Button updateBtn = new Button("update location");
		updateBtn.setMaxWidth(170);
		updateBtn.setPrefWidth(170);
		Label updateLabel = new Label();
		updateLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		VBox updateVBox = new VBox(updateTF, updateBtn, updateLabel);
		updateVBox.setSpacing(5);
		Button deleteBtn = new Button("delete location");
		deleteBtn.setMaxWidth(170);
		deleteBtn.setPrefWidth(170);
		VBox righVBox = new VBox(deleteBtn, updateVBox);
		righVBox.setSpacing(20);
		this.setMargin(righVBox, new Insets(30));
		this.setRight(righVBox);
		// This action for delete location button, To delete a current location
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				list.removeDoubleNode(current);
				HomePane homePane = new HomePane(list, stage);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
			}
		});

		// This action for update name button, To update the name of current location
		updateBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = updateTF.getText();
				if (text != null && !text.isEmpty()) {
					text = text.strip();
					if (text.equals(current.getLocation())) {
						updateLabel.setText("Error it the same location");
					} else if (list.changeName(text, current)) {
						locationLabel.setText(text);
					} else {
						updateLabel.setText("Error exist location");
					}
				} else {
					updateLabel.setText("Please enter the location");
				}
			}
		});

		// The bottom of the Screen (border pane) , Add new martyr section
		Label addErrorLabel = new Label();
		addErrorLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");

		Label nameLabel = new Label("Name");
		TextField nameTF = new TextField();
		VBox nameVBox = new VBox(nameLabel, nameTF);
		nameVBox.setAlignment(Pos.CENTER);
		nameVBox.setSpacing(5);

		Label ageLabel = new Label("Age");
		TextField ageTF = new TextField();
		VBox ageVBox = new VBox(ageLabel, ageTF);
		ageVBox.setAlignment(Pos.CENTER);
		ageVBox.setSpacing(5);

		Label genderLabel = new Label("Gender");
		ToggleGroup genderRBtnsTG = new ToggleGroup();
		RadioButton maleRBtn = new RadioButton("Male");
		maleRBtn.setToggleGroup(genderRBtnsTG);
		maleRBtn.setSelected(true);
		RadioButton femaleRBtn = new RadioButton("Female");
		femaleRBtn.setToggleGroup(genderRBtnsTG);
		HBox genderRBtnsHBox = new HBox(maleRBtn, femaleRBtn);
		genderRBtnsHBox.setSpacing(5);
		VBox genderVBox = new VBox(genderLabel, genderRBtnsHBox);
		genderVBox.setAlignment(Pos.CENTER);
		genderVBox.setSpacing(5);

		Label dateLabel = new Label("Date of death");
		DatePicker datePicker = new DatePicker();
		datePicker.setEditable(false);
		VBox dateVBox = new VBox(dateLabel, datePicker);
		dateVBox.setAlignment(Pos.CENTER);
		dateVBox.setSpacing(5);

		HBox bottomHBox = new HBox(nameVBox, ageVBox, dateVBox, genderVBox);
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.setSpacing(20);

		Button addBtn = new Button("Add");
		addBtn.setPrefWidth(150);
		VBox bottomVBox = new VBox(addErrorLabel, bottomHBox, addBtn);
		bottomVBox.setAlignment(Pos.CENTER);
		bottomVBox.setSpacing(10);
		this.setMargin(bottomVBox, new Insets(40));
		this.setBottom(bottomVBox);

		// This action for add martyr button, To add new martyr
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String name = nameTF.getText();
				if (name != null && !name.isEmpty()) {
					String ageString = ageTF.getText();
					if (ageString != null) {
						try {
							Byte age;
							if (ageString.isEmpty()) {
								if (GeneralPanes.warningMessage("No Data for Age"))
									age = -1;

								else {
									addErrorLabel.setText("The Martyr do not add");
									return;
								}
							} else
								age = Byte.parseByte(ageString);
							if (age >= 0) {
								String dateString = datePicker.getEditor().getText();
								if (dateString != null && !dateString.isEmpty()) {
									try {
										Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
										Martyr martyr = new Martyr(name, age, date, maleRBtn.isSelected());
										int place = current.getStart().add(martyr);
										if (place >= 0) {
											addErrorLabel
													.setText("The martyr " + (martyr.getName()) + " add successfully");
											tv.getItems().add(place, martyr);
										} else {
											addErrorLabel
													.setText("The martyr " + (martyr.getName()) + " already exits");
										}

									} catch (Exception e) {
										addErrorLabel.setText(
												"Please the be a date of death must a date in this format (month/day/year)");
									}
								} else {
									addErrorLabel.setText("Please enter the date of death");
								}
							} else {
								addErrorLabel.setText("Please check the age");
							}
						} catch (Exception e) {
							addErrorLabel.setText("Please check the age");
						}
					} else {
						addErrorLabel.setText("Please check the age");
					}
				} else {
					addErrorLabel.setText("Please enter the name");
				}
			}
		});
	}

	// This constructor make a object and add the component and information of
	// All locations to the pane
	public LocationPane(MyDoubleLinkedList list, Stage stage) {

		// The left of the Screen (border pane)
		Button backBtn = new Button("Back");
		this.setMargin(backBtn, new Insets(30));
		this.setLeft(backBtn);
		// This action for back button, To return to the home page
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				HomePane homePane = new HomePane(list, stage);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
			}
		});

		// The center of the Screen (border pane)
		// Make a table view of martyr
		TableView<AllData> tv = new TableView<AllData>();
		// Make a column name of martyr
		TableColumn<AllData, String> nameCol = new TableColumn<AllData, String>("Name");
		// This to customize the insert data to the name column
		nameCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData, String> data) {
						return new SimpleStringProperty(data.getValue().getMartyr().getName());
					}
				});
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setSortable(false);
		// This to handle update the age of martyr
		nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData, String>>() {
			@Override
			public void handle(CellEditEvent<AllData, String> event) {
				TablePosition<AllData, String> pos = event.getTablePosition();
				String newName = event.getNewValue();
				int row = pos.getRow();
				if (newName != null && !newName.isEmpty()) {
					Martyr martyr = event.getTableView().getItems().get(row).getMartyr();
					martyr.setName(newName);
				} else {
					GeneralPanes.errorAlert("Please check the name");
					tv.refresh();
				}
			}
		});

		// Make a column age of martyr
		TableColumn<AllData, String> ageCol = new TableColumn<AllData, String>("Age");
		// This to customize the insert data to the age column
		ageCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData, String> data) {
						if (data.getValue().getMartyr().getAge() < 0)
							return new SimpleStringProperty("No Data");
						return new SimpleStringProperty(data.getValue().getMartyr().getAge() + "");
					}
				});
		ageCol.setCellFactory(TextFieldTableCell.forTableColumn());
		ageCol.setSortable(false);
		// This to handle update the age of martyr
		ageCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData, String>>() {
			@Override
			public void handle(CellEditEvent<AllData, String> event) {
				TablePosition<AllData, String> pos = event.getTablePosition();
				try {
					byte newAge = Byte.valueOf(event.getNewValue() + "");
					if (newAge >= 0) {
						int row = pos.getRow();
						Martyr martyr = event.getTableView().getItems().get(row).getMartyr();
						martyr.setAge(newAge);
					} else {
						GeneralPanes.errorAlert("Please check the age");
						tv.refresh();
					}
				} catch (Exception e) {
					GeneralPanes.errorAlert("Please check the age");
					tv.refresh();
				}
			}
		});

		// Make a date of death column
		TableColumn<AllData, String> deathDateCol = new TableColumn<AllData, String>("Date of death");
		// This to customize the insert data to the date of death column
		deathDateCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData, String> data) {
						return new SimpleStringProperty(data.getValue().getMartyr().getSimpleDateOfDeath());
					}
				});
		// This to handle update the date of death of martyr
		deathDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
		deathDateCol.setSortable(false);
		deathDateCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData, String>>() {
			@Override
			public void handle(CellEditEvent<AllData, String> event) {
				try {
					TablePosition<AllData, String> pos = event.getTablePosition();
					String newDate = event.getNewValue();
					int row = pos.getRow();
					AllData allData = event.getTableView().getItems().get(row);
					DoubleNode doubleNode = list.getDoubleNode(allData.getLocation());
					allData.getMartyr().setSimpleDateOfDeath(newDate);
					doubleNode.getStart().notifyChangeDate(allData.getMartyr());
					tv.getItems().clear();
					list.addAllDataToTableView(tv);
				} catch (ParseException e) {
					GeneralPanes
							.errorAlert("Please the be a date of death must a date in this format (month/day/year)");
					tv.refresh();
				}
			}

		});

		// Make a gender column
		TableColumn<AllData, String> genderCol = new TableColumn<AllData, String>("Gender");
		genderCol.setCellFactory(TextFieldTableCell.forTableColumn());
		genderCol.setSortable(false);
		// This to customize the insert data to the gender column
		genderCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData, String> data) {
						return new SimpleStringProperty(data.getValue().getMartyr().getGender() + "");
					}
				});

		// This to handle update the gender of martyr
		genderCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData, String>>() {
			@Override
			public void handle(CellEditEvent<AllData, String> event) {
				TablePosition<AllData, String> pos = event.getTablePosition();
				Character newGender = event.getNewValue().charAt(0);
				if (newGender == 'M' || newGender == 'F') {
					int row = pos.getRow();
					Martyr martyr = event.getTableView().getItems().get(row).getMartyr();
					martyr.setGender(newGender);
				
				} else {
					GeneralPanes.errorAlert("Please check the gender (Male : M , Female : F)");
					tv.refresh();
				}
			}
		});

		// Make a location column
		TableColumn<AllData, String> locationCol = new TableColumn<AllData, String>("Location");
		locationCol.setCellFactory(TextFieldTableCell.forTableColumn());
		locationCol.setSortable(false);
		// This to customize the insert data to the location column
		locationCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData, String> data) {
						// TODO Auto-generated method stub
						return new SimpleStringProperty(data.getValue().getLocation().trim());
					}
				});

		// This to handle update the location of martyr
		locationCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData, String>>() {
			@Override
			public void handle(CellEditEvent<AllData, String> event) {
				TablePosition<AllData, String> pos = event.getTablePosition();
				String newLocation = event.getNewValue();
				int row = pos.getRow();
				if (newLocation != null && !newLocation.isEmpty()) {
					AllData allData = event.getTableView().getItems().get(row);
					boolean check = list.removeNode(allData.getLocation(), allData.getMartyr());
					if (check) {
						list.add(allData.getMartyr(), newLocation.trim());
						allData.setLocation(newLocation.trim());
						tv.getItems().clear();
						list.addAllDataToTableView(tv);
					}
				} else {
					GeneralPanes.errorAlert("Please check the name");
					tv.refresh();
				}
			}
		});

		// Add all columns to the tabel view
		tv.getColumns().addAll(nameCol, ageCol, genderCol, deathDateCol, locationCol);
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tv.setEditable(true);
		// Add all martyr
		list.addAllDataToTableView(tv);

		// The top of the center of Screen, the search (martyr) section
		TextField searchTF = new TextField();
		Button searchBtn = new Button("Search");
		Button allBtn = new Button("All");
		HBox searchHbox = new HBox(allBtn, searchTF, searchBtn);
		searchHbox.setSpacing(1);
		searchHbox.setAlignment(Pos.CENTER);
		Label locationLabel = new Label("All");
		locationLabel.setFont(new Font("Arial", 20));

		// The bottom of the center of Screen, the delete (selected martyr) section
		Button deleteMartyrBtn = new Button("Delete Martyr");
		deleteMartyrBtn.setPrefWidth(150);

		// Then add the top , mid and bottom sections to the center of screen
		VBox centerVBox = new VBox(locationLabel, searchHbox, tv, deleteMartyrBtn);
		centerVBox.setAlignment(Pos.TOP_CENTER);
		centerVBox.setSpacing(5);
		this.setMargin(centerVBox, new Insets(40));
		this.setCenter(centerVBox);

		// This action for search button, To search about the martyr name
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = searchTF.getText();
				if (text != null && !text.isEmpty()) {
					tv.getItems().clear();
					list.addAllDataToTableView(tv, text);
				} else {
					tv.getItems().clear();
					list.addAllDataToTableView(tv);
				}
			}
		});

		// This action for all button, to display all martyr on the table view
		allBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tv.getItems().clear();
				list.addAllDataToTableView(tv);
			}
		});

		// This action for delete button, To delete the selected martyr
		deleteMartyrBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				AllData allData = tv.getSelectionModel().getSelectedItem();
				if(allData != null) {
				DoubleNode doubleNode = list.getDoubleNode(allData.getLocation());
				if (doubleNode.getStart().remove(allData.getMartyr())) {

					tv.scrollTo(tv.selectionModelProperty().getValue().getSelectedIndex() - 16);
					tv.getItems().remove(allData);
				} else {
					GeneralPanes.errorAlert("Please select a martyr to remove");
				}
				}else {
					GeneralPanes.errorAlert("Please select a martyr to remove");
				}
			}
		});

		// This listener for label view to change the text of delete button
		tv.getFocusModel().focusedCellProperty().addListener(new ChangeListener<TablePosition>() {
			@Override
			public void changed(ObservableValue<? extends TablePosition> arg0, TablePosition arg1, TablePosition arg2) {
				AllData allData = tv.getSelectionModel().getSelectedItem();
				if (allData != null) {
					deleteMartyrBtn.setText("Delete " + allData.getMartyr().getName());
				} else {
					deleteMartyrBtn.setText("Delete Martyr");
				}
			}
		});

		// The bottom of the Screen (border pane) , Add new martyr section
		Label addErrorLabel = new Label();
		addErrorLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");

		Label nameLabel = new Label("Name");
		TextField nameTF = new TextField();
		VBox nameVBox = new VBox(nameLabel, nameTF);
		nameVBox.setAlignment(Pos.CENTER);
		nameVBox.setSpacing(5);

		Label ageLabel = new Label("Age");
		TextField ageTF = new TextField();
		VBox ageVBox = new VBox(ageLabel, ageTF);
		ageVBox.setAlignment(Pos.CENTER);
		ageVBox.setSpacing(5);

		Label locationAddLabel = new Label("Location");
		TextField locationAddTF = new TextField();
		VBox locationAddVBox = new VBox(locationAddLabel, locationAddTF);
		locationAddVBox.setAlignment(Pos.CENTER);
		locationAddVBox.setSpacing(5);

		Label genderLabel = new Label("Gender");
		ToggleGroup genderRBtnsTG = new ToggleGroup();
		RadioButton maleRBtn = new RadioButton("Male");
		maleRBtn.setToggleGroup(genderRBtnsTG);
		maleRBtn.setSelected(true);
		RadioButton femaleRBtn = new RadioButton("Female");
		femaleRBtn.setToggleGroup(genderRBtnsTG);
		HBox genderRBtnsHBox = new HBox(maleRBtn, femaleRBtn);
		genderRBtnsHBox.setSpacing(5);
		VBox genderVBox = new VBox(genderLabel, genderRBtnsHBox);
		genderVBox.setAlignment(Pos.CENTER);
		genderVBox.setSpacing(5);

		Label dateLabel = new Label("Date of death");
		DatePicker datePicker = new DatePicker();
		datePicker.setEditable(false);
		VBox dateVBox = new VBox(dateLabel, datePicker);
		dateVBox.setAlignment(Pos.CENTER);
		dateVBox.setSpacing(5);

		HBox bottomHBox = new HBox(nameVBox, ageVBox, locationAddVBox, dateVBox, genderVBox);
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.setSpacing(20);

		Button addBtn = new Button("Add");
		addBtn.setPrefWidth(150);
		VBox bottomVBox = new VBox(addErrorLabel, bottomHBox, addBtn);
		bottomVBox.setAlignment(Pos.CENTER);
		bottomVBox.setSpacing(10);
		this.setMargin(bottomVBox, new Insets(40));
		this.setBottom(bottomVBox);

		// This action for add martyr button, To add new martyr
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String name = nameTF.getText();
				if (name != null && !name.isEmpty()) {
					String ageString = ageTF.getText();
					if (ageString != null) {
						try {
							byte age;

							if (ageString.isEmpty()) {
								if (GeneralPanes.warningMessage("No Data for Age"))
									age = -1;
								else
									addErrorLabel.setText("The Martyr do not add");
								return;
							} else
								age = Byte.parseByte(ageString);
							if (age >= 0) {
								String locationString = locationAddTF.getText();
								if (locationString != null && !locationString.isEmpty()) {
									String dateString = datePicker.getEditor().getText();
									if (dateString != null && !dateString.isEmpty()) {
										try {
											Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
											Martyr martyr = new Martyr(name, age, date, maleRBtn.isSelected());
											list.add(martyr, locationString);
											tv.getItems().clear();
											list.addAllDataToTableView(tv);

										} catch (Exception e) {
											addErrorLabel.setText(
													"Please the be a date of death must a date in this format (month/day/year)");

										}
									} else {
										addErrorLabel.setText("Please enter the date of death");
									}
								} else {
									addErrorLabel.setText("Please enter the location");
								}
							} else
								addErrorLabel.setText("Please check the age");
						} catch (Exception e) {
							addErrorLabel.setText("Please check the age");
						}
					} else {
						addErrorLabel.setText("Please check the age");
					}
				} else {
					addErrorLabel.setText("Please enter the name");
				}
			}
		});
	}

}


