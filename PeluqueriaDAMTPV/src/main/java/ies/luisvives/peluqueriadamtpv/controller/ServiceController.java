package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.model.Service;
import ies.luisvives.peluqueriadamtpv.restcontroller.APIRestConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.security.auth.callback.Callback;
import java.net.URL;
import java.util.ResourceBundle;

public class ServiceController implements Initializable, Callback {
    @FXML
    TableView<Service> listService;
    private final TableColumn<Service, String> name = new TableColumn("name");
    private final TableColumn<Service, String> description = new TableColumn("description");
    private final TableColumn<Service, String> price = new TableColumn("price");
    private final TableColumn<Service, String> stock = new TableColumn("stock");
    private final TableColumn<Service, String> image = new TableColumn("image");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listService.getColumns().addAll(name, description, price, stock, image);
    }

    @FXML
    public void onTableItemService (ActionEvent event) {
        try {
            ObservableList<Service> services =
                    FXCollections.observableArrayList(
                            APIRestConfig.getServicesService().serviceGetAll().execute().body()
                    );
            listService.setItems(services);
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            image.setCellValueFactory(new PropertyValueFactory<>("image"));

            name.setSortType(TableColumn.SortType.DESCENDING);
            price.setSortType(TableColumn.SortType.DESCENDING);
            stock.setSortType(TableColumn.SortType.DESCENDING);
            listService.setItems(services);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
