package application;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// This class contain pane methods to user it in other classes 
public class GeneralPanes {

	// This method return the location pane have the name of location and 2 buttons
	// for statistic and info of the location
	public static GridPane locationPane(DoubleNode current, Stage stage, MyDoubleLinkedList cu) {

		GridPane gp = new GridPane();
		Button infoBtn = new Button("Info");
		infoBtn.setPrefWidth(70);
		Button statBtn = new Button("Statistic");
		statBtn.setPrefWidth(70);
		Label name = new Label((current == null) ? "All" : current.getLocation());
		name.setStyle("-fx-font-weight: bold; -fx-font-size: 18;-fx-alignment:CENTER;");
		name.setMaxWidth(Double.MAX_VALUE);
		gp.add(name, 1, 1, 3, 1);
		gp.add(infoBtn, 3, 2);
		gp.add(statBtn, 1, 2);
		gp.setVgap(15);
		gp.setHgap(15);
		gp.setPadding(new Insets(30));
		infoBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				StatisticData.isUpdatedData = true;
				if (current == null) {
					LocationPane lp = new LocationPane(cu, stage);
					Scene scene = new Scene(lp);
					stage.setScene(scene);
				} else {
					LocationPane lp = new LocationPane(cu, current, stage);
					Scene scene = new Scene(lp);
					stage.setScene(scene);
				}
			}
		});
		statBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				if (current == null) {
					StatisticPane sp = new StatisticPane(cu, stage);
					Scene scene = new Scene(sp);
					stage.setScene(scene);
				} else {
					StatisticPane sp = new StatisticPane(cu, current, stage);
					Scene scene = new Scene(sp);
					stage.setScene(scene);
				}
			}
		});
		return gp;
	}

	// This method have alert stage to show error message to the user
	public static void errorAlert(String message) {
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText(message);
		a.showAndWait();
	}

	// This method have warning stage to show warning message to the user
	public static boolean warningMessage(String string) {
		Alert a = new Alert(AlertType.WARNING);
		a.setContentText(string);
		a.getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = a.showAndWait();
		return (result.get() == ButtonType.OK);

	}
}
