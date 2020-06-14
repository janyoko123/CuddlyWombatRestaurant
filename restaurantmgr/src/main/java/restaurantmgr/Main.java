package restaurantmgr;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*
 * 1/6: open multiple windows by using fxml
 * Done:
 * 		+ open multiple window
 * 		+ only work with current window
 * Problem to tackle:
 * 		- separate to OOP class
 *  	- need to close previous window?
 *  	- login only uses admin-admin
 * */

import javafx.application.Application;
import javafx.stage.Stage;
import restaurantmgr.component.Scene_InitialMenu;
import restaurantmgr.model.StatusDetail;
import restaurantmgr.service.ItemInterface;
import restaurantmgr.service.ItemService;
import restaurantmgr.service.StatusDetailService;


public class Main extends Application{

	// Main Method
	public static void main(String[] args) {	
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		primaryStage.setTitle("Restaurant Manager");
        primaryStage.setScene(Scene_InitialMenu.getScene(primaryStage));
        primaryStage.setResizable(false);
        primaryStage.show();
        
	}
}
