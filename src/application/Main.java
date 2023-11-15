package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			MyDoubleLinkedList data = new MyDoubleLinkedList();
			HomePane homePane = new HomePane(data,stage);
			Scene homeScene = new Scene(homePane);
			stage.setScene(homeScene);
			stage.setHeight(700);
			stage.setWidth(1400);
			stage.setTitle("Martyr Program");
			stage.setResizable(false);
			stage.show();			
		} catch(Exception e) {
			e.printStackTrace();
			GeneralPanes.errorAlert("Error!");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
