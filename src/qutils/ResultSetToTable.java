package qutils;

import java.sql.ResultSet;
import java.sql.SQLException;

import ibmi.Result;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ResultSetToTable {

	public static void loadTable(Result[] catalogResult, TableView[] tableView) {
		for (int i = 0; i <= tableView.length; i++) {
			loadTable(catalogResult[i], tableView[i]);
		}
	}

	public static void loadTable(Result catalogResult, TableView tableView) {
		loadTable(catalogResult.getRs(), tableView);
		catalogResult.reset();
		tableView.refresh();
	}

	public static void loadTable(ResultSet rs, TableView tableView) {
		if (rs == null || tableView == null)
			return;

		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
		ObservableList<ObservableList<String>> data2 = FXCollections.observableArrayList();
		loadData(rs, data);

		if (data.size() == 1) {
			if (loadColumn(tableView)) {
				loadDataSingle(rs, data, data2);
				tableView.setItems(data2);
			}
		} else {
			if (loadColumns(rs, tableView))
				tableView.setItems(data);
		}

	}

	private static boolean loadData(ResultSet rs, ObservableList<ObservableList<String>> data) {
		boolean output = true;

		try {

			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add((rs.getString(i) == null ? "" : rs.getString(i)));
				}

				data.add(row);
			}
			// FINALLY ADDED TO TableView
			// tableView.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return output;
	}

	private static boolean loadColumns(ResultSet rs, TableView tableView) {
		boolean output = true;
		try {
			int i = 0;
			for (i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
				// System.out.println(rs.getMetaData().getColumnTypeName(i+1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				tableView.getColumns().addAll(col);

			}
			output = (i == 0 ? false : true);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			output = false;
		}
		return output;
	}

	private static boolean loadColumn(TableView tableView) {
		boolean output = true;

		int i = 0;
		for (i = 0; i < 2; i++) {
			// We are using non property style for making dynamic table
			final int j = i;
			TableColumn col = new TableColumn();

			col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
					return new SimpleStringProperty(param.getValue().get(j).toString());
				}
			});

			tableView.getColumns().addAll(col);

		}
		output = (i == 0 ? false : true);

		return output;
	}

	private static boolean loadDataSingle(ResultSet rs, ObservableList<ObservableList<String>> data,
			ObservableList<ObservableList<String>> data2) {

		boolean output = true;

		try {

			// Iterate Row

			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				ObservableList<String> row = FXCollections.observableArrayList();
				// Iterate Column
				row.add(rs.getMetaData().getColumnName(i));

				String tempString = data.get(0).get(i - 1);

				row.add((tempString == null) ? "" : tempString);

				data2.add(row);
			}

			// FINALLY ADDED TO TableView
			// tableView.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return output;
	}

}
