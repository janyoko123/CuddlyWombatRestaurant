package restaurantmgr.component;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import restaurantmgr.handler.Authentication;

//Wrapper class for Login button
public class Scene_StaffLogin {
	private final static int width = 300;
	private final static int height = 300;
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
		
		Label lblUsername = new Label("Username");
		TextField tfUsername = new TextField();
		tfUsername.setPrefWidth(200);
		tfUsername.setMaxWidth(200);
		
		Label lblPassword = new Label("Password");
		TextField tfPassword = new TextField();
		tfPassword.setPrefWidth(200);
		tfPassword.setMaxWidth(200);
		
		Text txtWarning = new Text("");
		txtWarning.setFill(Color.RED);
		
		Button btnLogin 	= new Button("Login");
		btnLogin.setPrefWidth(200);
		btnLogin.setOnAction(value ->  {
			tfUsername.getText();
			System.out.println("Logging in with username: " + tfUsername.getText());
			Authentication.getInstance().login(tfUsername.getText(), tfPassword.getText());
			if(!Authentication.getInstance().isAuthenticated()) {
				txtWarning.setText("Wrong username or password!");
			}
			else {
				if(Authentication.getInstance().getAuthenticatedUser().getRole().equals("owner")
						|| Authentication.getInstance().getAuthenticatedUser().getRole().equals("waiter")
						|| Authentication.getInstance().getAuthenticatedUser().getRole().equals("chef")) {
					txtWarning.setText("Login successful!");
					txtWarning.setFill(Color.BLACK);
					primaryStage.setScene(Scene_StaffMenu.getScene(primaryStage));
				} else {
					txtWarning.setText("You are not authorized to sign in");
				}
			}
		});
		
		Button btnCustomer = new Button("Return to start");
		btnCustomer.setPrefWidth(200);
		btnCustomer.setOnAction(value ->  {
			primaryStage.setScene(Scene_InitialMenu.getScene(primaryStage));
		});
		
		
		pane.getChildren().addAll(lblUsername, tfUsername, lblPassword, tfPassword, txtWarning, btnLogin, btnCustomer);
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setVgap(0);
	}
}
