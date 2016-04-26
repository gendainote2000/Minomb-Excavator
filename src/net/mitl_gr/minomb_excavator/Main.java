package net.mitl_gr.minomb_excavator;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
import javafx.scene.web.WebEngine;
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
		WebEngine engine = webView.getEngine();
		engine.load("http://google.co.jp/");

		StackPane root = new StackPane();
		root.getChildren().add(webView);

		Scene scene = new Scene(root, 1080, 720);

		//垂直方向にレイアウトするコンテナ
		VBox vbox = new VBox();
		vbox.setLayoutY(10);

		//水平方向にレイアウトするコンテナ
		HBox hbox = new HBox(2);
		hbox.setMinHeight(60);
		hbox.setStyle("-fx-background-color: #cccccc;");
		hbox.setAlignment(Pos.BOTTOM_LEFT);

		//戻るボタン
		Button backButton = new Button("←");
		backButton.setOnAction(event ->{
			if(engine.getHistory().getCurrentIndex() > 0){
			engine.getHistory().go(-1);
			}
		});
		backButton.disabledProperty().and(
				Bindings.equal(0, engine.getHistory().currentIndexProperty())
		);
		backButton.setMinHeight(30);
		hbox.getChildren().add(backButton);

		//進むボタン
		Button goButton = new Button("→");
		goButton.setOnAction(event ->{
			if ((engine.getHistory().getEntries().size() - 1) > engine.getHistory().getCurrentIndex()){
			engine.getHistory().go(1);
			}
		});
		goButton.disabledProperty().and(
				Bindings.equal(0, engine.getHistory().currentIndexProperty())
		);
		goButton.setMinHeight(30);
		hbox.getChildren().add(goButton);

		//ボタン
		Button loadButton = new Button("LOAD");
		loadButton.setMinHeight(30);
		hbox.getChildren().add(loadButton);

		//ホームボタン
		Button homeButton = new Button("HOME");
		homeButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				engine.load("http://google.co.jp/");
			}
		});
		homeButton.setMinHeight(30);
		hbox.getChildren().add(homeButton);

		//テキスト入力
		TextField field = new TextField();
		field.setFont(Font.font(15));
		field.setMinHeight(30);
		field.setPrefColumnCount(40);

		Worker<Void> worker = engine.getLoadWorker();
		worker.stateProperty().addListener(new ChangeListener<State>(){
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue ov, State oldState, State newState){
				if (newState == State.SUCCEEDED){
					String url = engine.getLocation();
					field.setText(url);
				}
			}
		});

		hbox.getChildren().add(field);

		loadButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				/* テキストボックスから取得した文字列を
				 * WebEngineでロードする
				 */
				String url = field.getText();
				engine.load(url);
			}
		});

		//設定ボタン
		Button settingButton = new Button("SETTING");
		settingButton.setMinHeight(30);
		hbox.getChildren().add(settingButton);

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
