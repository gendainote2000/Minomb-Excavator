package net.mitl_gr.minomb_excavator;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args){
		Application.launch(Main.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Minomb Excavator");

		WebView webView = new WebView();
		webView.getEngine().load("http://google.co.jp/");
		webView.setZoom(1.2);

		StackPane root = new StackPane();
		root.getChildren().add(webView);

		Scene scene = new Scene(root, 1080, 720);

		//垂直方向にレイアウトするコンテナ
		VBox vbox = new VBox();
		vbox.setLayoutY(10);

		//水平方向にレイアウトするコンテナ
		HBox hbox = new HBox(10);
		hbox.setMinHeight(60);
		hbox.setAlignment(Pos.BOTTOM_LEFT);

		//テキスト入力
		TextField field = new TextField();
		field.setFont(Font.font(15));
		field.setMinHeight(30);
		field.setPrefColumnCount(40);

		Worker<Void> worker = webView.getEngine().getLoadWorker();
		worker.stateProperty().addListener(new ChangeListener<State>(){
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue ov, State oldState, State newState){
				if (newState == State.SUCCEEDED){
					String url = webView.getEngine().getLocation();
					field.setText(url);
				}
			}
		});

		hbox.getChildren().add(field);

		//ボタン
		Button button = new Button("検索");
		button.setMinHeight(30);
		button.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				/* テキストボックスから取得した文字列を
				 * WebEngineでロードする
				 */
				String url = field.getText();
				webView.getEngine().load(url);
			}
		});
		hbox.getChildren().add(button);
		
		//WebViewをSceneの大きさに合わせて変更
		webView.minHeightProperty().bind(scene.heightProperty());

		//HBoxをVBoxに貼る
		vbox.getChildren().add(hbox);

		//WebViewをVBoxに貼る
		vbox.getChildren().add(webView);

		//VBoxをルートに貼る
		root.getChildren().add(vbox);



		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
