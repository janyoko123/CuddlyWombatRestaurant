package restaurantmgr.component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.mysql.jdbc.StringUtils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import restaurantmgr.handler.Authentication;
import restaurantmgr.model.Chef;
import restaurantmgr.model.Customer;
import restaurantmgr.model.Invoice;
import restaurantmgr.model.Item;
import restaurantmgr.model.Order;
import restaurantmgr.model.OrderDetail;
import restaurantmgr.model.Reservation;
import restaurantmgr.model.Staff;
import restaurantmgr.model.StatusDetail;
import restaurantmgr.model.Waiter;
import restaurantmgr.service.CustomerInterface;
import restaurantmgr.service.CustomerService;
import restaurantmgr.service.InvoiceInterface;
import restaurantmgr.service.InvoiceService;
import restaurantmgr.service.ItemInterface;
import restaurantmgr.service.ItemService;
import restaurantmgr.service.OrderInterface;
import restaurantmgr.service.OrderService;
import restaurantmgr.service.ReportInterface;
import restaurantmgr.service.ReportService;
import restaurantmgr.service.ReservationInterface;
import restaurantmgr.service.ReservationService;
import restaurantmgr.service.StaffInterface;
import restaurantmgr.service.StaffService;
import restaurantmgr.service.StatusDetailInterface;
import restaurantmgr.service.StatusDetailService;

public class Scene_StaffMenu {
	private final static int width = 1100;
	private final static int height = 500;
	private static Scene scene;
	private static Stage primaryStage;
	private static BorderPane _pane;
	private static final CustomerInterface customerService = CustomerService.getInstance();
	private static final InvoiceInterface invoiceService = InvoiceService.getInstance();
	private static final ItemInterface itemService = ItemService.getInstance();
	private static final OrderInterface orderService = OrderService.getInstance();
	private static final ReportInterface reportService = ReportService.getInstance();
	private static final ReservationInterface reservationService = ReservationService.getInstance();
	private static final StaffInterface staffService = StaffService.getInstance();
	private static final StatusDetailInterface statusDetailService = StatusDetailService.getInstance();
	
	public static Scene getScene(Stage stage) {
		if(scene == null) {
			scene = initializeScene();
			scene.getStylesheets().add(Scene_StaffMenu.class.getResource("basic.css").toExternalForm());
			primaryStage = stage;
		}
		refreshScene();
		return scene;
	}
	
	private static Scene initializeScene() {
		BorderPane pane = new BorderPane();
		_pane = pane;
		
		setupPane(pane);
		
		Scene scene = new Scene(pane, width, height);
		
		
		return scene;
	}
	
	private static void refreshScene() {
		_pane.getChildren().clear();
		setupPane(_pane);
	}
	
	private static void setupPane(BorderPane pane) {
		
		TilePane tpLeft = new TilePane();
		tpLeft.setAlignment(Pos.CENTER);
		tpLeft.getStyleClass().add("toolpanel");
		
		
		Button btnItemMenu = new Button("Item Catalog");
		btnItemMenu.getStyleClass().add("buttontoolpanel");
		btnItemMenu.setOnAction(value ->  {
			pane.setCenter(formMenuPane(1));
		});
		
		Button btnInstoreMenu = new Button("Dine-In Menu");
		btnInstoreMenu.getStyleClass().add("buttontoolpanel");
		btnInstoreMenu.setOnAction(value ->  {
			pane.setCenter(formMenuPane(2));
		});
		
		Button btnTakeoutMenu = new Button("Take-Out Menu");
		btnTakeoutMenu.getStyleClass().add("buttontoolpanel");
		btnTakeoutMenu.setOnAction(value ->  {
			pane.setCenter(formMenuPane(3));
		});
		
		Button btnOrder = new Button("Order");
		btnOrder.getStyleClass().add("buttontoolpanel");
		btnOrder.setOnAction(value ->  {
			pane.setCenter(formOrderPane());
		});
		
		Button btnReservation = new Button("Reservation");
		btnReservation.getStyleClass().add("buttontoolpanel");
		btnReservation.setOnAction(value ->  {
			pane.setCenter(formReservationPane());
		});
		
		Button btnReport = new Button("Report");
		btnReport.getStyleClass().add("buttontoolpanel");
		btnReport.setOnAction(value ->  {
			pane.setCenter(formReportPane());
		});
		
		Button btnManagement = new Button("Management");
		btnManagement.getStyleClass().add("buttontoolpanel");
		btnManagement.setOnAction(value ->  {
			pane.setCenter(formManagementPane());
		});
		
		Button btnSignOut = new Button("Return");
		btnSignOut.getStyleClass().add("buttontoolpanel");
		btnSignOut.setOnAction(value ->  {
			Authentication.getInstance().logout();
			primaryStage.setScene(Scene_InitialMenu.getScene(primaryStage));
		});
		
		
		tpLeft.getChildren().addAll(btnInstoreMenu, btnTakeoutMenu);
		
		if(Authentication.getInstance().isAuthenticated()) {
			tpLeft.getChildren().addAll(btnOrder, btnItemMenu);
			if(Authentication.getInstance().getAuthenticatedUser() != null) {
				if(Authentication.getInstance().getAuthenticatedUser().getRole().equals("waiter")) {
					tpLeft.getChildren().add(btnReservation);
				}
				if(Authentication.getInstance().getAuthenticatedUser().getRole().equals("owner")) {
					tpLeft.getChildren().addAll(btnReservation,btnReport,btnManagement);
				}
			}
		}
		
		tpLeft.getChildren().addAll(btnSignOut);
		
		
		
		pane.setLeft(tpLeft);
		pane.setCenter(formMenuPane(2));
		if(Authentication.getInstance().isAuthenticated() 
				&& Authentication.getInstance().getAuthenticatedUser() != null 
				&& Authentication.getInstance().getAuthenticatedUser().getRole().equals("owner")) {
			pane.setBottom(formBottomPane());
		}
	}
	
	/* 
	 * 3 modes.
	 * 1: FULL menu
	 * 2: Dine in menu
	 * 3: Take out menu
	 */
	private static TilePane formMenuPane(int mode) {
		TilePane pane = new TilePane();
		pane.setHgap(5);
		pane.setVgap(5);
		List<Item> items = new ArrayList<Item>();
		switch(mode) {
			case 1:
				items.addAll(itemService.findItems().get());
				break;
			case 2:
				items.addAll(itemService.findItemsDineIn().get());
				break;
			case 3:
				items.addAll(itemService.findItemsTakeOut().get());
				break;
			default:
				items.addAll(itemService.findItems().get());
				break;
		}
		
		for(Item item : items) {
			TilePane tpItem = new TilePane();
			tpItem.setAlignment(Pos.CENTER);
			tpItem.setPrefSize(100, 100);
			tpItem.getStyleClass().add("itemcard");
			
			Text txtName = new Text(item.getItemName());
			Text txtItemId = new Text("Id: " + item.getItemId());
			String price = String.format("%.2f", item.getPrice());
			Text txtPrice = new Text("Price: $" + price);
			
			tpItem.getChildren().addAll(txtName,txtItemId,txtPrice);
			pane.getChildren().add(tpItem);
		}
		
		return pane;
	}
	
	// Create UI for bottom  panel in the staff menu
	private static HBox formBottomPane() {
		HBox pane = new HBox();
		
		Button btnAddItem = new Button("Add Item");
		btnAddItem.getStyleClass().add("buttonbottompanel");
		btnAddItem.setMinWidth(150);
		btnAddItem.setOnAction(value ->  {
			Stage stage = new Stage();
			stage.setTitle("Add new item");
			stage.setResizable(false);
			stage.setOnCloseRequest(value2-> {
				scene.getRoot().setDisable(false);
			});
			
			
			/* Add Item Menu */
			
			VBox vbAddItem = new VBox();
			vbAddItem.setAlignment(Pos.CENTER);
			
			Label lblName = new Label("Item Name");
			TextField tfName = new TextField();
			Label lblPrice = new Label("Price $(AUD)");
			TextField tfPrice = new TextField();
			Label lblDineIn = new Label("Dine-In");
			CheckBox cbDineIn = new CheckBox();
			Label lblTakeOut = new Label("Take-Out");
			CheckBox cbTakeOut = new CheckBox();
			Button btnSubmit = new Button("Create");
			btnSubmit.getStyleClass().add("buttonbottompanel");
			
			btnSubmit.setOnAction(value2 ->  {
				if(!StringUtils.isNullOrEmpty(tfName.getText()) && 
						!StringUtils.isNullOrEmpty(tfPrice.getText())) {
					if(Pattern.matches(
							"^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$",
							tfPrice.getText())) {
						System.out.println("Adding item: " + tfName.getText() + ", " + tfPrice.getText());
						itemService.addItem(new Item(tfName.getText(), 
								Float.parseFloat(tfPrice.getText()), 
								cbTakeOut.isSelected(), cbDineIn.isSelected()));
					}
				}
				stage.close();
				scene.getRoot().setDisable(false);
			});
			
			vbAddItem.getChildren().addAll(lblName, tfName, lblPrice, tfPrice, 
					lblDineIn, cbDineIn, lblTakeOut, cbTakeOut, btnSubmit);
	
			stage.setScene(new Scene(vbAddItem, 150,350));
			
			scene.getRoot().setDisable(true);

	        stage.show();
	        
		});
	
		Button btnEditItem = new Button("Edit Item");
		btnEditItem.getStyleClass().add("buttonbottompanel");
		btnEditItem.setMinWidth(150);
		btnEditItem.setOnAction(value ->  {
			Stage stage = new Stage();
			stage.setTitle("Edit item");
			stage.setResizable(true);
			stage.setOnCloseRequest(value2-> {
				scene.getRoot().setDisable(false);
			});
			
			/* Edit Item Menu */
			ObservableList<Item> items = 
			        FXCollections.observableArrayList(itemService.findItems().get());
			
			VBox vbEditItem = new VBox();
			vbEditItem.setAlignment(Pos.CENTER);
			Label lblEdit= new Label("Edit item");
			ComboBox<Item> cbItemList = new ComboBox<Item>(items);
			Label lblEditName = new Label("Item Name");
			TextField tfEditName = new TextField();
			Label lblEditPrice = new Label("Price $(AUD)");
			TextField tfEditPrice = new TextField();
			Label lblEditDineIn = new Label("Dine-In");
			CheckBox cbEditDineIn = new CheckBox();
			Label lblEditTakeOut = new Label("Take-Out");
			CheckBox cbEditTakeOut = new CheckBox();
			Label lblRemove = new Label("Check to remove this item");
			CheckBox cbRemove = new CheckBox();
			
			cbItemList.setOnAction(value2 -> {
				tfEditName.setText(cbItemList.getValue().getItemName());
				String price = String.format("%.2f", cbItemList.getValue().getPrice());
				tfEditPrice.setText(price);
				cbEditDineIn.setSelected(cbItemList.getValue().isDinein());
				cbEditTakeOut.setSelected(cbItemList.getValue().isTakeout());
			});
			
			Button btnEdit = new Button("Accept");
			btnEdit.getStyleClass().add("buttonbottompanel");
			btnEdit.setOnAction(value2 ->  {
				if(cbItemList.getValue() != null) {
					int itemId = cbItemList.getValue().getItemId();
					if(cbRemove.isSelected()) {
						System.out.println("Removing item id: " + itemId);
						itemService.removeItem(itemId);
					}
					else {
						if(Pattern.matches("^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$",
								tfEditPrice.getText())) {
							itemService.updateName(itemId, tfEditName.getText());
							itemService.updatePrice(itemId, Float.parseFloat(tfEditPrice.getText()));
							itemService.updateDineIn(itemId, cbEditDineIn.isSelected());
							itemService.updateTakeOut(itemId, cbEditTakeOut.isSelected());
						}
					}
				}
				stage.close();
				scene.getRoot().setDisable(false);
			});
			
			vbEditItem.getChildren().addAll(
				lblEdit, cbItemList,lblEditName, tfEditName, lblEditPrice, tfEditPrice,
				lblEditDineIn, cbEditDineIn, lblEditTakeOut, cbEditTakeOut, lblRemove, cbRemove, btnEdit
			);

			
			stage.setScene(new Scene(vbEditItem, 200,350));
			
			scene.getRoot().setDisable(true);

	        stage.show();
		});
		
		pane.getChildren().addAll(btnAddItem, btnEditItem);
		
		return pane;
	}
	
	private static TilePane formOrderPane() {
		
		TilePane pane = new TilePane();
	
		ObservableList<Order> orders = 
		        FXCollections.observableArrayList(orderService.findOrders().get());
		
		ObservableList<StatusDetail> statusDetails = statusDetailService.getList();
		
		// Periodically refresh the status details that keeps track of order waiting time.
		Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

		    @Override
		    public void handle(ActionEvent event) {
		        statusDetailService.updateList();
		    }
		}));
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();

		Button btnAddOrder = new Button("Add Order");
		btnAddOrder.getStyleClass().add("buttonbottompanel");
		btnAddOrder.setOnAction(value ->  {
			Stage stage = new Stage();
			stage.setTitle("Add Order");
			stage.setResizable(false);
			stage.setOnCloseRequest(value2-> {
				scene.getRoot().setDisable(false);
			});
			
			VBox vbAddOrder = new VBox();
			vbAddOrder.setAlignment(Pos.CENTER);
			Label lbl= new Label("Add new order");
			Label lblTable = new Label("Table");
			TextField tfTable = new TextField();

			Button btnSubmit = new Button("Submit");
			btnSubmit.getStyleClass().add("buttonbottompanel");
			btnSubmit.setOnAction(value2 ->  {
				Order order = new Order(tfTable.getText(), null, "New", Timestamp.valueOf(LocalDateTime.now()));
				orderService.addNewOrder(order);
				orders.clear();
				orders.addAll(orderService.findOrders().get());
				stage.close();
				scene.getRoot().setDisable(false);
			});
			
			vbAddOrder.getChildren().addAll(lbl,lblTable,tfTable,btnSubmit);
			
			stage.setScene(new Scene(vbAddOrder, 150,250));
			
			scene.getRoot().setDisable(true);

	        stage.show();
		});
		
		ListView<Order> lvOrders = new ListView<Order>();
		lvOrders.setItems(orders);
		lvOrders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		lvOrders.setCellFactory(param -> new ListCell<Order>() {
		    @Override
		    protected void updateItem(Order order, boolean empty) {
		        super.updateItem(order, empty);

		        if (empty || order == null) {
		            setText(null);
		            setStyle(null);
		        } else {
		            setText(order.toString());
		            if (order.getStatus().equals("Complete")) {
			            setStyle("-fx-background-color: #C0C0C0;");
		            }
		            else if(order.getStatus().equals("New")) {
		            	setStyle("-fx-background-color: #E5FFCC;");
		            }
		            else if(order.getStatus().equals("Ready to Serve")) {
		            	setStyle("-fx-background-color: #80FF00;");
		            }
		            else {
		            	setStyle("-fx-background-color: #FFFF99;");
		            }
		        }
		    }
		});
		
		ListView<StatusDetail> lvStatusDetails = new ListView<StatusDetail>();
		lvStatusDetails.setItems(statusDetails);
		lvStatusDetails.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		Button btnViewOrder = new Button("View Order");
		btnViewOrder.getStyleClass().add("buttonbottompanel");
		btnViewOrder.setOnAction(value ->  {
			Order order = lvOrders.getSelectionModel().getSelectedItem();
			if(order == null) return;
			Stage stage = new Stage();
			stage.setTitle("View Order");
			stage.setResizable(false);
			stage.setOnCloseRequest(value2-> {
				scene.getRoot().setDisable(false);
				orders.clear();
				orders.addAll(orderService.findOrders().get());
			});
			System.out.println("Opening order: " + order);
			stage.setScene(new Scene(formViewOrderPane(order), 500, 500));
			scene.getRoot().setDisable(true);
			stage.show();
		});
		
		VBox vbButtons = new VBox();
		vbButtons.setAlignment(Pos.CENTER);
		
		if(Authentication.getInstance().getAuthenticatedUser() != null
				&& Authentication.getInstance().getAuthenticatedUser().getRole().equals("chef")) {
			vbButtons.getChildren().add(btnViewOrder);
		} else {
			vbButtons.getChildren().addAll(btnAddOrder, btnViewOrder);
		}
		
		pane.getChildren().addAll(vbButtons ,lvOrders, lvStatusDetails);
		
		return pane;
	}
	
	private static TilePane formViewOrderPane(Order order) {
		TilePane pane = new TilePane();
		
		VBox vBox = new VBox();
		
		Label lbl= new Label("Add item to order");
		
		ObservableList<Item> items = 
		        FXCollections.observableArrayList(itemService.findItems().get());
		ComboBox<Item> cbItemList = new ComboBox<Item>(items);
		
		Label lblQuantity= new Label("Qty");
		
		TextField tfQuantity = new TextField();
		
		ObservableList<OrderDetail> olOrderDetails = 
		        FXCollections.observableArrayList(
		        		orderService.findOrderDetailsByOrderId(order.getOrderId()).get());
		
		Button btnAddItem = new Button("Add");
		btnAddItem.getStyleClass().add("buttonbottompanel");
		btnAddItem.setOnAction(value ->  {
			if(cbItemList.getValue() != null && !StringUtils.isNullOrEmpty(tfQuantity.getText())) {
				// Attempt to parse quantity
				try {
					int qty = Integer.parseInt(tfQuantity.getText());
					orderService.addItemToOrder(order.getOrderId(), cbItemList.getValue(), qty);
					olOrderDetails.clear();
					olOrderDetails.addAll(orderService.findOrderDetailsByOrderId(order.getOrderId()).get());
				} catch(NumberFormatException e) {
					return;
				}
			}
		});
		
		Label lblEmpty1 = new Label(" ");
		Label lblEmpty2 = new Label(" ");
		Label lblEmpty3 = new Label(" ");
		Label lblEmpty4 = new Label(" ");
		
		ListView<OrderDetail> lvOrderDetails = new ListView<OrderDetail>();
		lvOrderDetails.setItems(olOrderDetails);
		lvOrderDetails.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		Button btnRemoveItem = new Button("Remove Selected Item");
		btnRemoveItem.getStyleClass().add("buttonbottompanel");
		btnRemoveItem.setOnAction(value -> {
			if(lvOrderDetails.getSelectionModel().getSelectedItem() != null) {
				OrderDetail od = lvOrderDetails.getSelectionModel().getSelectedItem();
				orderService.removeItemFromOrder(od.getId());
				olOrderDetails.clear();
				olOrderDetails.addAll(orderService.findOrderDetailsByOrderId(order.getOrderId()).get());
			}
		});
		
		Button btnInProgress = new Button("Order in Progress");
		btnInProgress.getStyleClass().add("buttonbottompanel");
		btnInProgress.setOnAction(value -> {
			orderService.updateStatus(order.getOrderId(), "In Progress");
		});
		
		Button btnReadyServe = new Button("Ready to Serve");
		btnReadyServe.getStyleClass().add("buttonbottompanel");
		btnReadyServe.setOnAction(value -> {
			orderService.updateStatus(order.getOrderId(), "Ready to Serve");
		});
		
		Button btnComplete = new Button("Order Completed");
		btnComplete.getStyleClass().add("buttonbottompanel");
		btnComplete.setOnAction(value -> {
			orderService.updateStatus(order.getOrderId(), "Complete");
		});
		
		Label lblPrice = new Label("Price $(AUD): Not Totaled");
		Label lblPrice2 = new Label();
		Button btnCalcPrice = new Button("Calculate Price");
		btnCalcPrice.getStyleClass().add("buttonbottompanel");
		btnCalcPrice.setOnAction(value2 -> {
			int orderId = order.getOrderId();
			List<OrderDetail> orderDetails = orderService.findOrderDetailsByOrderId(orderId).get();
			List<Item> itemsList = itemService.findItems().get();
			
			float price = 0f;
			for(OrderDetail detail: orderDetails) {
				price += (detail.getQuantity() * priceHelper(itemsList, detail.getItemId()));
			}
			lblPrice.setText("Price $(AUD): ");
			lblPrice2.setText(price+"");
			
		});
		
		Button btnPay = new Button("Pay");
		btnPay.getStyleClass().add("buttonbottompanel");
		btnPay.setOnAction(value2 -> {
			try { 
				int orderId = order.getOrderId();
				float price = Float.parseFloat(lblPrice2.getText());
				String waiterId = Authentication.getInstance().getAuthenticatedUser().getId();
				String tableId = order.getTableId();
				
				Invoice invoice = new Invoice(price, waiterId, tableId, orderId);
				
				Stage stage = new Stage();
				stage.setTitle("Payment Menu");
				stage.setResizable(false);
				stage.setOnCloseRequest(value3-> {
					scene.getRoot().setDisable(false);

				});
				System.out.println("Opening payment pane");
				stage.setScene(new Scene(formPaymentPane(invoice), 500, 500));
				scene.getRoot().setDisable(true);
				stage.show();
				
				
			} catch (Exception e) {
				System.err.println("Need to calculate price before you can pay!");
			}
			
			
		});
		
		if(Authentication.getInstance().getAuthenticatedUser() != null 
				&& !Authentication.getInstance().getAuthenticatedUser().getRole().equals("chef")) {
			vBox.getChildren().addAll(lbl, cbItemList, lblQuantity, tfQuantity, btnAddItem, lblEmpty1,
					btnRemoveItem, lblEmpty2, lblEmpty3);
		}
		if(Authentication.getInstance().getAuthenticatedUser() != null 
				&& Authentication.getInstance().getAuthenticatedUser().getRole().equals("chef")) {
			vBox.getChildren().addAll(btnInProgress, btnReadyServe);
		}
		if(Authentication.getInstance().getAuthenticatedUser() != null 
				&& !Authentication.getInstance().getAuthenticatedUser().getRole().equals("chef")) {
			vBox.getChildren().addAll(btnComplete, 
					lblEmpty4, lblPrice, lblPrice2, btnCalcPrice, btnPay);
		}	
			
		pane.getChildren().addAll(vBox, lvOrderDetails);
		
		return pane;
	}
	
	private static float priceHelper(List<Item> items, int itemId) {
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getItemId() == itemId) {
				return items.get(i).getPrice();
			}
		}
		return 0f;
	}
	
	private static TilePane formReservationPane() {
		TilePane tpReservation = new TilePane();
		
		VBox vBox = new VBox();
		
		Label lblAddReservation = new Label("Add Reservation");
		Label lblName = new Label("Name:");
		TextField tfName = new TextField();
		Label lblNumPeople = new Label("Number of People:");
		TextField tfNumPeople = new TextField();
		Label lblTableType = new Label("Table Type:");
		TextField tfTableType = new TextField();
		Label lblDate= new Label("Date:");
		DatePicker tfDate = new DatePicker();
		
		ObservableList<Reservation> olReservations = 
		        FXCollections.observableArrayList(reservationService.findReservations().get());
		
		Button btnAddReservation = new Button("Create Reservation");
		btnAddReservation.getStyleClass().add("buttonbottompanel");
		btnAddReservation.setOnAction(value ->  {
			try {
				int numPeople = Integer.parseInt(tfNumPeople.getText());
				
				Customer customer = new Customer(tfName.getText());
				customer.setId(customerService.addCustomer(customer));
				
				Reservation reservation = new Reservation(
						customer.getId(),numPeople,tfTableType.getText(),
						Timestamp.valueOf(LocalDateTime.of(tfDate.getValue(), LocalTime.now())), true
				);
				reservationService.addNewReservation(reservation);
				olReservations.clear();
				olReservations.addAll(reservationService.findReservations().get());
			} catch(NumberFormatException e) {
				System.err.println("Invalid value for number of people");
			}
		});
		
		ListView<Reservation> lvReservations = new ListView<Reservation>();
		lvReservations.setMinWidth(400);
		lvReservations.setItems(olReservations);
		lvReservations.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		lvReservations.setCellFactory(param -> new ListCell<Reservation>() {
		    @Override
		    protected void updateItem(Reservation reservation, boolean empty) {
		        super.updateItem(reservation, empty);

		        if (empty || reservation == null) {
		            setText(null);
		            setStyle(null);
		        } else {
		            setText(reservation.toString());
		            if (reservation.getActive()) {
			            setStyle("-fx-background-color: #006600;");
		            }
		            else {	
		            	setStyle("-fx-background-color: #990000;");
		            }
		        }
		    }
		});
		
		Label lblFindReservation = new Label("Find reservation id:");
		TextField tfFindReservation = new TextField();
		
		Button btnFindReservation = new Button("Find Reservation");
		btnFindReservation.getStyleClass().add("buttonbottompanel");
		btnFindReservation.setOnAction(value ->  {
			try{
				int id = Integer.parseInt(tfFindReservation.getText());
				if(!reservationService.lookupReservation(id).isPresent()) {
					System.err.println("Reservation not found!");
					return;
				}
				Reservation reservation = reservationService.lookupReservation(id).get();
				
				olReservations.clear();
				olReservations.add(reservation);
			} catch(NumberFormatException e) {
				System.err.println("Reservation needs to be a number.");
			}
		});
		
		Button btnFindAllReservation = new Button("All Reservations");
		btnFindAllReservation.getStyleClass().add("buttonbottompanel");
		btnFindAllReservation.setOnAction(value ->  {
			olReservations.clear();
			olReservations.addAll(reservationService.findReservations().get());
		});
		
		Button btnCancelReservation = new Button("Cancel Selected");
		btnCancelReservation.getStyleClass().add("buttonbottompanel");
		btnCancelReservation.setOnAction(value ->  {
			Reservation reservation = lvReservations.getSelectionModel().getSelectedItem();
			if(reservation != null) {
				reservationService.cancelReservation(reservation.getReservationId());
				olReservations.clear();
				olReservations.addAll(reservationService.findReservations().get());
			} else {
				System.err.println("Select a reservation first.");
			}
		});
		
		vBox.getChildren().addAll(lblAddReservation,lblName,tfName,lblNumPeople,
				tfNumPeople,lblTableType,tfTableType,lblDate,tfDate, btnAddReservation,
				lblFindReservation,tfFindReservation,
				btnFindReservation,btnFindAllReservation,btnCancelReservation);
		
		tpReservation.getChildren().addAll(vBox, lvReservations);
		
		return tpReservation;
	}
	
	private static VBox formPaymentPane(Invoice invoice) {
		VBox pane = new VBox();
		
		Label lblBalance = new Label("Amount Due: " + invoice.getPrice());
		Label lblPaymentMethod = new Label("Payment Method");
		ComboBox<String> cbPaymentMethod = new ComboBox<String>();
		cbPaymentMethod.getItems().addAll("Cash", "Card", "Check");
		
		Label lblAmountPaid = new Label("Amount Paid");
		TextField tfAmountPaid = new TextField();
		Label lblChange = new Label();
		Button btnProcessPayment = new Button("Process Payment");
		btnProcessPayment.getStyleClass().add("buttonbottompanel");
		btnProcessPayment.setOnAction(value ->  {
			try {
				if(invoiceService.findInvoiceByOrderId(invoice.getOrderId()).isPresent()) {
					System.out.println("Customer has already paid!");
					lblChange.setText("Customer has already paid!");
					return;
				}
				float amtPaid = Float.parseFloat(tfAmountPaid.getText());
			
				invoice.setAmountPaid(amtPaid);
				invoice.setDate(Timestamp.valueOf(LocalDateTime.now()));
				invoice.setPaymentMethod(cbPaymentMethod.getSelectionModel().getSelectedItem());
				if( invoice.getAmountPaid() - invoice.getPrice() >= 0.0f) {
					
					String price = String.format("%.2f", (invoice.getAmountPaid() - invoice.getPrice()));
					// Update label for customer change
					lblChange.setText("Customer Change: $" + price);
					
					int invoiceNumber = invoiceService.addInvoice(invoice);
					invoice.setId(invoiceNumber);
					System.out.println("Invoice: " + invoice);
				
				} else {
					System.out.println("Customer needs to pay the correct amount");
					lblChange.setText("Amount paid is not enough");
				}
			} catch(NumberFormatException e) {
				System.err.println("Please enter a valid amount for Amount Paid.");
			}
		});
		
		
		pane.getChildren().addAll(lblBalance, lblPaymentMethod, cbPaymentMethod, 
				lblAmountPaid, tfAmountPaid, btnProcessPayment, lblChange);
		
		return pane;
	}

	private static VBox formReportPane() {
		VBox pane = new VBox();
		pane.setAlignment(Pos.CENTER);
		
		Label lblReport = new Label("File Monetary Report");
		Label lblEmpty = new Label("");
		Label lblFlowIn = new Label("Flow In");
		float flowIn = reportService.findDailyFlowIn().isPresent() ? 
				reportService.findDailyFlowIn().get(): 0;
		String flowInStr = String.format("%.2f", flowIn);
		Text txtFlowIn = new Text(flowInStr);
		Label lblFlowOut = new Label("Enter Flow Out");
		TextField txtFlowOut = new TextField();
		txtFlowOut.setPrefWidth(100);
		txtFlowOut.setMaxWidth(100);
		
		Button btnCalcProfit = new Button("Store Profit to Database");
		btnCalcProfit.getStyleClass().add("buttonbottompanel");
		btnCalcProfit.setPrefWidth(150);
		btnCalcProfit.setMinWidth(150);
		btnCalcProfit.setOnAction(value ->  {
			try {
				float flowOut = Float.parseFloat(txtFlowOut.getText());
				
				float profit = flowIn - flowOut;

				if(reportService.getTodayProfit().isPresent()) {
					// Update today's profit
					reportService.addProfit(false, profit);
				}
				else {
					// Insert profit since we dont have one for today
					reportService.addProfit(true, profit);
				}
				
			} catch(NumberFormatException e) {
				System.err.println("Please enter a valid amount.");
			}
		});
		
		Button btnPrintTodayProfit = new Button("Print Today's Profit");
		btnPrintTodayProfit.getStyleClass().add("buttonbottompanel");
		btnPrintTodayProfit.setPrefWidth(150);
		btnPrintTodayProfit.setMinWidth(150);
		btnPrintTodayProfit.setOnAction(value ->  {
				if(reportService.getTodayProfit().isPresent()) {
					String profit = String.format("%.2f",reportService.getTodayProfit().get());
					System.out.println("Today's profit is: $" + profit);
				}
		});
		
		Button btnPrintMonthlyProfit = new Button("Print Monthly Profit");
		btnPrintMonthlyProfit.getStyleClass().add("buttonbottompanel");
		btnPrintMonthlyProfit.setPrefWidth(150);
		btnPrintMonthlyProfit.setMinWidth(150);
		btnPrintMonthlyProfit.setOnAction(value ->  {
				if(reportService.getMonthlyProfit().isPresent()) {
					String profit = String.format("%.2f",reportService.getMonthlyProfit().get());
					System.out.println("Monthly profit is: $" + profit);
				}
		});
		
		Button btnPrintAnnualProfit = new Button("Print Annual Profit");
		btnPrintAnnualProfit.getStyleClass().add("buttonbottompanel");
		btnPrintAnnualProfit.setPrefWidth(150);
		btnPrintAnnualProfit.setMinWidth(150);
		btnPrintAnnualProfit.setOnAction(value ->  {
				if(reportService.getAnnualProfit().isPresent()) {
					String profit = String.format("%.2f",reportService.getAnnualProfit().get());
					System.out.println("Annual profit is: $" + profit);
				}
		});
		
		pane.getChildren().addAll(lblReport,lblEmpty,lblFlowIn,txtFlowIn,lblFlowOut,
				txtFlowOut,btnCalcProfit,btnPrintTodayProfit,btnPrintMonthlyProfit,
				btnPrintAnnualProfit);
		return pane;
	}
	
	private static VBox formManagementPane() {
		VBox pane = new VBox();
		pane.setAlignment(Pos.CENTER);
		
		Label lbl = new Label("Add Staff");
		Label lblStaffName = new Label("Staff Name");
		TextField tfStaffName = new TextField();
		tfStaffName.setPrefWidth(100);
		tfStaffName.setMaxWidth(100);
		Label lblStaffUsername = new Label("Username");
		TextField tfStaffUsername = new TextField();
		tfStaffUsername.setPrefWidth(100);
		tfStaffUsername.setMaxWidth(100);
		Label lblStaffPassword = new Label("Password");
		TextField tfStaffPassword = new TextField();
		tfStaffPassword.setPrefWidth(100);
		tfStaffPassword.setMaxWidth(100);
		Label lblStaffRole = new Label("Role");
		ComboBox<String> cbRole  = new ComboBox<String>();
		cbRole.setPrefWidth(100);
		cbRole.setMaxWidth(100);
		cbRole.getItems().addAll("chef", "waiter");
		
		Button btnAddStaff = new Button("Create Staff User");
		btnAddStaff.getStyleClass().add("buttonbottompanel");
		btnAddStaff.setPrefWidth(150);
		btnAddStaff.setMinWidth(150);
		btnAddStaff.setOnAction(value ->  {
				String name = tfStaffName.getText();
				String username = tfStaffUsername.getText();
				String pw = tfStaffPassword.getText();
				String role = cbRole.getSelectionModel().getSelectedItem();
				if(StringUtils.isNullOrEmpty(name) && StringUtils.isNullOrEmpty(username) 
						&& StringUtils.isNullOrEmpty(pw)
						&& StringUtils.isNullOrEmpty(role)) {
					System.err.println("Please fill in all the fields to create a user!");
				}
				
				Staff newStaff;
				switch(role) {
					case "chef":
						newStaff = new Chef(name, username, pw);
						staffService.addStaff(newStaff);
						break;
					case "waiter":
						newStaff = new Waiter(name, username, pw);
						staffService.addStaff(newStaff);
						break;
					case "default":
						newStaff = new Staff(name, username, pw, role);
						staffService.addStaff(newStaff);
						break;
				}

		});
		
		pane.getChildren().addAll(lbl,lblStaffName,tfStaffName,lblStaffUsername,
				tfStaffUsername, lblStaffPassword,
				tfStaffPassword,lblStaffRole,cbRole,btnAddStaff);
		
		return pane;
	}
}
