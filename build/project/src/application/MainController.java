/*******************************************************************************
 * QSYS400.COM
 * Written by : Sumit goyal
 * 2016
 *******************************************************************************/
/**
 * Sample Skeleton for 'Main.fxml' Controller Class
 */

package application;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import ibmi.Result;
import ibmi.sqlcatalog.CatalogInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import qJob.QJob;
import qJob.qRemoteJob.QRemoteConnection;
import qJob.qlocalJob.qServers.QCurrentServer;
import qJob.qlocalJob.qServers.QServer;
import qStatus.ActionStatus;
import qStatus.QLastStatus;
import qutils.ResultSetToExcel;
import qutils.ResultSetToTable;

public class MainController {

	private ObservableList<QServer> servers = FXCollections
			.observableArrayList(QJob.getCurrentJob().getLocalJob().getqLocalServerDB().getServerList());

	// Image processing = new Image("file:resources/p2.gif", 16, 16, true,
	// true);
	// Image serverImage = new Image("file:resources/s1.png", 20, 20, true,
	// true);
	// Image saveImage = new Image("file:resources/Diskette.png", 20, 20, true,
	// true);

	ObservableList<String> objectTypes = FXCollections.observableArrayList("Table", "View", "Index", "Procedure",
			"Funtion", "Trigger");

	@FXML
	private Accordion accordionList;

	// ----------------------------------------------------------------

	@FXML // fx:id="paneServer"
	private TitledPane paneServer; // Value injected by FXMLLoader

	// ================ SERVER
	// LIST==============================================
	@FXML // fx:id="serverList"
	private TableView<QServer> serverList; // Value injected by FXMLLoader

	@FXML // fx:id="listServerName"
	private TableColumn<QServer, Integer> listServerId; // Value injected by
														// FXMLLoader

	@FXML // fx:id="listServerName"
	private TableColumn<QServer, String> listServerName; // Value injected by
															// FXMLLoader

	@FXML // fx:id="listServerIp"
	private TableColumn<QServer, String> listServerIp; // Value injected by
														// FXMLLoader

	@FXML // fx:id="listUserName"
	private TableColumn<QServer, String> listUserName; // Value injected by
														// FXMLLoader

	@FXML // fx:id="listServerPassword"
	private TableColumn<QServer, String> listServerNaming; // Value injected by
															// FXMLLoader

	@FXML
	private WebView helpPage;
	// =========================================================
	@FXML // fx:id="serverName"
	private TextField serverId;

	@FXML // fx:id="serverName"
	private TextField serverName; // Value injected by FXMLLoader

	@FXML // fx:id="serverIp"
	private TextField serverIp; // Value injected by FXMLLoader

	@FXML // fx:id="serverUser"
	private TextField serverUser; // Value injected by FXMLLoader

	@FXML // fx:id="serverPassword"
	private PasswordField serverPassword; // Value injected by FXMLLoader

	@FXML // fx:id="serverClear"
	private Button serverClear; // Value injected by FXMLLoader

	@FXML // fx:id="serverSave"
	private Button serverSave; // Value injected by FXMLLoader

	@FXML // fx:id="serverConnect"
	private Button serverConnect; // Value injected by FXMLLoader

	@FXML
	private RadioButton namingSQL;

	@FXML
	private ToggleGroup SQLnaming;

	@FXML
	private RadioButton namingSYSTEM;

	@FXML
	private ListView<String> Libl;

	// -----------------------------------------------------------------

	@FXML // fx:id="notification"
	private TextField notification; // Value injected by FXMLLoader

	// -------------------------------------------------XXXXXXXXXXXXXXXXX-----------------------

	@FXML // fx:id="paneSQL"
	private TitledPane paneSQL; // Value injected by FXMLLoader
	// ---------------------------------ABOUT---------------------------------------

	// @FXML // fx:id="sqlAbout" ??? add final

	private final TableView<?> sqlAbout = new TableView(); // Value injected by
															// FXMLLoader

	// @FXML // fx:id="sqlAboutColumn"
	// private TableColumn<?, ?> sqlAboutColumn; // Value injected by FXMLLoader
	//
	// @FXML // fx:id="sqlAboutType"
	// private TableColumn<?, ?> sqlAboutType; // Value injected by FXMLLoader

	@FXML
	private GridPane aboutGrid;

	@FXML // fx:id="sqlObjectType"
	private ComboBox<String> sqlObjectType; // Value injected by FXMLLoader

	@FXML // fx:id="loadClear"
	private Button loadClear; // Value injected by FXMLLoader

	@FXML // fx:id="loadDetails"
	private Button loadDetails; // Value injected by FXMLLoader

	@FXML // fx:id="sqlLib"
	private TextField sqlLib; // Value injected by FXMLLoader

	@FXML // fx:id="sqlObject"
	private TextField sqlObject; // Value injected by FXMLLoader

	@FXML
	private VBox aboutVbox;
	// ------------------------------------------------------------------------
	// RESULTS
	@FXML // fx:id="resultLog"
	private TitledPane resultLog; // Value injected by FXMLLoader

	// @FXML // fx:id="sqlAbout"
	private TableView<?> resultTable = new TableView();; // Value injected by
															// FXMLLoader

	// @FXML // fx:id="resultData"
	private TitledPane resultData = new TitledPane("Result", resultTable); // Value
																			// injected
																			// by
																			// FXMLLoader

	@FXML
	private TextArea resultMessage;

	@FXML
	private Accordion resultAccordion;

	// ----------------------------------------------------------------------------

	@FXML // fx:id="sqlDownloadXLS"
	private Button sqlDownloadXLS; // Value injected by FXMLLoader

	@FXML
	private Hyperlink xlsDownloadLink;

	// @FXML // fx:id="sqlDownloadCSV"
	// private Button sqlDownloadCSV; // Value injected by FXMLLoader

	@FXML // fx:id="sqlClear"
	private Button sqlClear; // Value injected by FXMLLoader

	@FXML // fx:id="sqlRun"
	private Button sqlRun; // Value injected by FXMLLoader

	@FXML
	private TextArea sqlText;

	@FXML
	void loadClear(ActionEvent event) {
		//// sqlAbout.getItems().clear();
		// sqlAbout.getColumns().clear();
		// sqlAbout.refresh();
		aboutVbox.getChildren().clear();
		sqlLib.clear();
		sqlObject.clear();
	}

	@FXML
	void loadDetails(ActionEvent event) {

		aboutVbox.getChildren().clear();

		// loadDetails.setGraphic(new ImageView(processing));
		loadDetails.setDisable(true);

		QLastStatus.clear();
		QLastStatus.get().setMessage("Loading...");
		// final CatalogInfo cInfo =new CatalogInfo();
		CatalogInfo cInfo = new CatalogInfo();
		Task<Boolean> loadDetailData = new Task<Boolean>() {
			@Override
			public Boolean call() {
				cInfo.loadDetails(QJob.getCurrentJob(), sqlObjectType.getSelectionModel().getSelectedItem(),
						sqlLib.getText(), sqlObject.getText(), false);
				return true;
			}
		};

		loadDetailData.setOnSucceeded((e) -> {

			int heightFacctor = cInfo.getResult().size();
			for (Result result : cInfo.getResult()) {
				TableView tempTable = new TableView();
				tempTable.setPrefHeight(aboutVbox.getHeight() / heightFacctor);
				ResultSetToTable.loadTable(result, tempTable);
				aboutVbox.getChildren().add(tempTable);
			}
			loadDetails.setDisable(false);
		});

		new Thread(loadDetailData).start();

	}

	// ============================================================================================================================================

	@FXML
	void serverClear(ActionEvent event) {
		QCurrentServer.clear();
	}

	@FXML
	void serverConnect(ActionEvent event) {

		// serverConnect.setGraphic(new ImageView(processing));
		xlsDownloadLink.setText("");

		QLastStatus.clear();
		QLastStatus.get().setMessage("Connecting...");

		paneServer.setDisable(true);
		paneSQL.setDisable(true);
		paneSQL.setText("Run SQL:[NOT CONNECTED]");

		Task<Boolean> makeConnection = new Task<Boolean>() {
			@Override
			public Boolean call() {
				QJob.getCurrentJob().getRemoteJob().setConnectedServer(QCurrentServer.get());
				if (QJob.getCurrentJob().getRemoteJob().getConnection() == null)
					return false;
				else
					return true;
			}
		};

		makeConnection.setOnSucceeded((e) -> {
			if (makeConnection.getValue()) {
				QLastStatus.get().setMessage("Connected.");
				paneSQL.setDisable(false);
				paneSQL.setText(
						"Run SQL: [CONNECTED] "
								+ (QCurrentServer.get().getServerName().trim().isEmpty()
										? QCurrentServer.get().getServerIp() : QCurrentServer.get().getServerName())
								+ "@" + QCurrentServer.get().getUserName());
				accordionList.setExpandedPane(paneSQL);
			}

			paneServer.setDisable(false);
			// serverConnect.setGraphic(new ImageView(serverImage));
		});

		new Thread(makeConnection).start();

	}

	@FXML
	void serverSave(ActionEvent event) {
		QLastStatus.clear();
		if (QCurrentServer.get().getServerIp() == null || QCurrentServer.get().getServerIp().isEmpty()) {
			QLastStatus.get().update(ActionStatus.Error, "Save Server", "Server IP is required to save.");
			return;
		}

		QCurrentServer.get().setChanged(true);
		QJob.getCurrentJob().getLocalJob().getqLocalServerDB().saveServer(QCurrentServer.get());
		QServer x = (QServer) QCurrentServer.get().clone();
		QCurrentServer.clear();
		if (servers.contains(x))
			servers.remove(x);
		servers.add(x);
		serverList.refresh();
		serverList.getSortOrder().add(this.listServerId);
		// serverList.so

	}

	@FXML
	void sqlClear(ActionEvent event) {
		QLastStatus.clear();
		resultTable.getItems().clear();
		resultTable.getColumns().clear();
		resultTable.refresh();
		resultAccordion.getPanes().clear();
		resultAccordion.getPanes().add(resultLog);
		resultMessage.clear();
	}

	//
	// @FXML
	// void sqlDownloadCSV(ActionEvent event) {
	//
	// }
	@FXML
	void openXLS(ActionEvent event) {
		File file = new File(xlsDownloadLink.getText());
		try {
			java.awt.Desktop.getDesktop().open(file);
		} catch (Exception e) {
			QLastStatus.get().update(ActionStatus.Error, "Open Excel", e.getMessage());
		}
	}

	@FXML
	void sqlDownloadXLS(ActionEvent event) {

		String sql = (!(sqlText.getSelectedText() == null) && (!sqlText.getSelectedText().trim().isEmpty())
				? sqlText.getSelectedText().trim() : sqlText.getText().trim());
		if (!sql.isEmpty()) {
			QJob.getCurrentJob().setLastQuery(sql);

			ArrayList<Result> results = new ArrayList<Result>();
			QJob.getCurrentJob().getQueryProcessor().run(new StringBuilder(sql), results);
			ResultSetToExcel excel = new ResultSetToExcel();

			for (Result result : results)
				excel.loadTable(result);
			if (!excel.errorMessage.isEmpty())
				QLastStatus.get().update(ActionStatus.Error, "Download Excel", excel.errorMessage);
			if (!excel.name.isEmpty()) {
				xlsDownloadLink.setText(excel.name);

				QLastStatus.get().update(ActionStatus.Completed, "Download Excel", "Download complete : " + excel.name);
			}
		}

	}

	@FXML
	void sqlRun(ActionEvent event) {

		this.sqlClear.fire();

		String sql = (!(sqlText.getSelectedText() == null) && (!sqlText.getSelectedText().trim().isEmpty())
				? sqlText.getSelectedText().trim() : sqlText.getText().trim());
		if (!sql.isEmpty()) {
			QJob.getCurrentJob().setLastQuery(sql);

			ArrayList<Result> results = new ArrayList<Result>();
			QJob.getCurrentJob().getQueryProcessor().run(new StringBuilder(sql), results);

			for (Result result : results) {
				TableView tempTable = new TableView();
				tempTable.setPlaceholder(new Text("No " + result.getHeading() + " found."));
				TitledPane titelPane = new TitledPane(result.getHeading(), tempTable);
				ResultSetToTable.loadTable(result, tempTable);

				resultAccordion.getPanes().add(titelPane);

			}
		}
		//
		if (resultAccordion.getPanes().size() >= 2)
			resultAccordion.getPanes().get(1).expandedProperty().set(true);
		else
			resultAccordion.getPanes().get(0).expandedProperty().set(true);

		// ???????

		// ------ ****** comment resultData from FXML
		// resultAccordion.getPanes().add(resultData);
		// ????????????????????????????????????????
		// and resulttable ResultSetToTable.loadTable(, resultTable);

		// ??????????

		// sqlRun.setGraphic(new ImageView(""));
	}

	// final KeyCombination keyForRunSQL = new KeyCodeCombination(KeyCode.R,
	// KeyCombination.CONTROL_DOWN);

	@FXML
	void serverPaneKeyPress(KeyEvent event) {

		if (event.isControlDown() && event.getCode() == KeyCode.S) {
			this.serverSave.fire();

		}
		if (event.isControlDown() && event.getCode() == KeyCode.C) {
			this.serverConnect.fire();

		}

		if (event.getCode() == KeyCode.F3) {
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();

			stage.close();

			Platform.exit();
			System.exit(0);
		}

	}

	@FXML
	void keyPress(KeyEvent event) {
		QLastStatus.clear();
		if (event.isControlDown() && event.getCode() == KeyCode.R) {
			this.sqlRun.fire();

		}
		if (event.isControlDown() && event.getCode() == KeyCode.C) {
			this.sqlClear.fire();

		}
		if (event.getCode() == KeyCode.F9) {
			String lastQry = QJob.getCurrentJob().getLastQuery();
			if (!lastQry.isEmpty())
				sqlText.setText(lastQry);

		}
		if (event.getCode() == KeyCode.F10) {
			String lastQry = QJob.getCurrentJob().getNextQuery();
			if (!lastQry.isEmpty())
				sqlText.setText(lastQry);

		}

		if (event.getCode() == KeyCode.F3) {
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			Platform.exit();
			System.exit(0);
		}
	}
	// =================================================

	@FXML
	public void initialize() {
		// serverConnect.setGraphic(new ImageView(serverImage));
		// serverSave.setGraphic(new ImageView(saveImage));

		paneSQL.setDisable(true);
		this.manageAccordian();
		WebEngine webEngine = helpPage.getEngine();
		webEngine.load("http://qsys400.com/qsql-help/#sqlheader");

		// aboutGrid.add(sqlAbout, 0, 2);
		aboutVbox.setPrefSize(aboutGrid.getWidth(), aboutGrid.getHeight());

		sqlObjectType.setItems(objectTypes);
		sqlObjectType.getSelectionModel().selectFirst();

		Libl.setItems(QCurrentServer.get().getLibl());
		Libl.setEditable(true);
		Libl.setCellFactory(TextFieldListCell.forListView());

		this.serverId.textProperty().bindBidirectional(QCurrentServer.get().serverIDProperty(),
				new NumberStringConverter());
		this.serverName.textProperty().bindBidirectional(QCurrentServer.get().serverNameProperty());
		this.serverIp.textProperty().bindBidirectional(QCurrentServer.get().serverIpProperty());
		this.serverUser.textProperty().bindBidirectional(QCurrentServer.get().userNameProperty());
		this.serverPassword.textProperty().bindBidirectional(QCurrentServer.get().passwordProperty());

		namingSQL.setUserData("SQL");
		namingSYSTEM.setUserData("SYSTEM"); // ??? spell check

		SQLnaming.selectedToggleProperty().addListener((obs, oldValue, newValue) -> {

			String oldVal = "";
			String newVal = "";
			if (oldValue != null && oldValue instanceof Toggle)
				oldVal = oldValue.getUserData().toString();
			if (newValue != null && newValue instanceof Toggle)
				newVal = newValue.getUserData().toString();
			if (!oldVal.equals(newVal)) {
				QCurrentServer.get().setNaming(newVal);
			}

		});
		QCurrentServer.get().namingProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue == null) {
				SQLnaming.selectToggle(namingSQL);
				return;
			}

			if (oldValue == null || !newValue.equals(oldValue)) {
				if (newValue.equalsIgnoreCase(namingSYSTEM.getUserData().toString()))
					SQLnaming.selectToggle(namingSYSTEM);
				else
					SQLnaming.selectToggle(namingSQL);
			}
		});

		serverList.setItems(servers);

		listServerId.setCellValueFactory(new PropertyValueFactory<QServer, Integer>("serverID"));
		listServerName.setCellValueFactory(new PropertyValueFactory<QServer, String>("serverName"));
		listServerIp.setCellValueFactory(new PropertyValueFactory<QServer, String>("serverIp"));
		listUserName.setCellValueFactory(new PropertyValueFactory<QServer, String>("userName"));
		listServerNaming.setCellValueFactory(new PropertyValueFactory<QServer, String>("naming"));
		listServerId.setCellFactory(callback -> {
			return new TableCell<QServer, Integer>() {
				@Override
				public void updateItem(Integer serverId, boolean empty) {
					if (serverId == null)
						return;
					super.updateItem(serverId, empty);
					this.setText(Integer.toString(serverId));
					this.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
						if (mouseEvent.getClickCount() > 1 && mouseEvent.getButton() == MouseButton.SECONDARY) {

							QServer x = new QServer();
							x.setServerID(serverId);
							x.setChanged(true);
							x.setDeleted(true);
							QJob.getCurrentJob().getLocalJob().getqLocalServerDB().saveServer(x);

							if (servers.contains(x))
								servers.remove(x);
							serverList.refresh();
							QLastStatus.get().update(ActionStatus.Completed, "Delete server",
									"Server details deleted.");

						}
					});
				}
			};
		});

		notification.textProperty().bind(QLastStatus.get().messageProperty());
		resultMessage.textProperty().bind(QLastStatus.get().longMessageProperty());

		serverList.getSelectionModel().selectedItemProperty().addListener((obsValue, oldValue, newValue) -> {
			if (newValue != null) {
				QCurrentServer.copy(newValue);
				Libl.refresh();
				// serverList.getSelectionModel().select(-1);
			}
		});

		serverList.setRowFactory(new Callback<TableView<QServer>, TableRow<QServer>>() {
			@Override
			public TableRow<QServer> call(TableView<QServer> tableView2) {
				final TableRow<QServer> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) // &&
																					// event.getClickCount()
																					// ==
																					// 2
					{

						QCurrentServer.copy(row.getItem());
						event.consume();

					}
				});
				return row;
			}
		});

	}

	public void manageAccordian() {

		accordionList.setExpandedPane(paneServer);
		accordionList.expandedPaneProperty().addListener((property, oldValue, newValue) -> {

			if (oldValue != null) {
				if (oldValue.equals(paneServer)) {
					if (paneSQL.isDisable())
						accordionList.setExpandedPane(paneServer);
					else
						accordionList.setExpandedPane(paneSQL);
				}
				if (oldValue.equals(paneSQL))
					accordionList.setExpandedPane(paneServer);
			}

		});
	}

}
