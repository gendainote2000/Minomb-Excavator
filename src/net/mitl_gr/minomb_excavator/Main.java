package net.mitl_gr.minomb_excavator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args){
		Application.launch(Main.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Minomb Excavator");

		WebView webview = new WebView();
		webview.getEngine().load("http://google.co.jp/");

		StackPane root = new StackPane();
		root.getChildren().add(webview);

		Scene scene = new Scene(root, 500, 250);

	    primaryStage.setScene(scene);
		primaryStage.show();
	}
}
