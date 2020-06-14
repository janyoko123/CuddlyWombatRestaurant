package restaurantmgr.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import restaurantmgr.handler.Authentication;

public class Scene_InitialMenu {
	private final static int width = 380;
	private final static int height = 400;
	private static Scene scene;
	private static Stage primaryStage;
	private static TilePane _pane;
	
	public static Scene getScene(Stage stage) {
		if(scene == null) {
			scene = initializeScene();
			primaryStage = stage;
		}
		refreshScene();
		return scene;
	}
	
	private static Scene initializeScene() {
		TilePane pane = new TilePane();
		_pane = pane;
		
		setupPane(pane);
		
		Scene scene = new Scene(pane, width, height);
		
		return scene;
	}
	
	private static void refreshScene() {
		_pane.getChildren().clear();
		setupPane(_pane);
	}
	
	private static void setupPane(TilePane pane) {
		Button btnBrowseMenu = new Button("Browse Menu");
		btnBrowseMenu.setPrefWidth(200);
		btnBrowseMenu.setPrefHeight(90);
		btnBrowseMenu.setOnAction(value ->  {
			Authentication.getInstance().logout();
			primaryStage.setScene(Scene_StaffMenu.getScene(primaryStage));
		});
		
		
		Button btnToLoginMenu = new Button("Staff Login");
		btnToLoginMenu.setPrefWidth(200);
		btnToLoginMenu.setPrefHeight(90);
		btnToLoginMenu.setOnAction(value ->  {
			primaryStage.setScene(Scene_StaffLogin.getScene(primaryStage));
		});
		
		pane.getChildren().addAll(btnBrowseMenu,btnToLoginMenu);
		pane.setAlignment(Pos.CENTER);
		TilePane.setMargin(btnBrowseMenu, new Insets(0,0,20,0));
	}
}
