package net.mitl_gr.minomb_excavator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

		Scene scene = new Scene(root, 720, 480);
		
		//垂直方向にレイアウトするコンテナ
		VBox vbox = new VBox(10);
		vbox.setLayoutY(10);
		
		//水平方向にレイアウトするコンテナ
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);
		
		//テキスト入力
		TextField field = new TextField();
		field.setPrefColumnCount(20);
		hbox.getChildren().add(field);
		
		//ボタン
		Button button = new Button("検索");
		button.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				/* テキストボックスから取得した文字列を
				 * WebEngineでロードする
				 */
				String url = field.getText();
				webview.getEngine().load(url);
			}
		});
		hbox.getChildren().add(button);
		
		//HBoxをVBoxに貼る
		vbox.getChildren().add(hbox);
		
		//WebViewをVBoxに貼る
		vbox.getChildren().add(webview);
		
		//VBoxをルートに貼る
		root.getChildren().add(vbox);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
