package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.model.Service;
import ies.luisvives.peluqueriadamtpv.restcontroller.APIRestConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import retrofit2.Response;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;

public class ServiceController implements Initializable, Callback {
    @FXML
    TextField stockService;
    @FXML
    TextField priceService;
    @FXML
    TextField descriptionService;
    @FXML
    TextField nombreService;
    @FXML
    TableView<Service> listService;

    private final TableColumn<Service, String> name = new TableColumn("name");
    private final TableColumn<Service, String> description = new TableColumn("description");
    private final TableColumn<Service, Double> price = new TableColumn("price");
    private final TableColumn<Service, Integer> stock = new TableColumn("stock");
    private final TableColumn<Service, String> image = new TableColumn("image");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listService.getColumns().addAll(name, description, price, stock, image);
        onTableItemService();
    }

    @FXML
    public void onTableItemService() {
        try {
            Response<List<Service>> service = Objects.requireNonNull(APIRestConfig.getServicesService().serviceGetAll().execute());
            if (service.body() != null) {

                ObservableList<Service> services =
                        FXCollections.observableArrayList();
                services.addAll(service.body());
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                description.setCellValueFactory(new PropertyValueFactory<>("description"));
                price.setCellValueFactory(new PropertyValueFactory<>("price"));
                stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
                image.setCellValueFactory(new PropertyValueFactory<>("image"));

                name.setSortType(TableColumn.SortType.DESCENDING);
                price.setSortType(TableColumn.SortType.DESCENDING);
                stock.setSortType(TableColumn.SortType.DESCENDING);
                listService.setItems(services);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteService(ActionEvent event) {
        try {
            Service service = APIRestConfig.getServicesService().deleteService(listService.getSelectionModel().getSelectedItem().getId()).execute().body();
            Response<List<Service>> serviceList = Objects.requireNonNull(APIRestConfig.getServicesService().serviceGetAll().execute());
            ObservableList<Service> services =
                    FXCollections.observableArrayList();
            if (serviceList.body() != null) {
                services.addAll(serviceList.body());
            } else {
                services.remove(service);
            }
            listService.setItems(services);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertService() throws IOException {

        ObservableList<Service> services = FXCollections.observableArrayList();
        Service service = new Service();
        service.setId(UUID.randomUUID().toString());
        service.setName(nombreService.getText());
        service.setDescription(descriptionService.getText());
        service.setStock(Integer.valueOf(stockService.getText()));
        service.setPrice(Double.valueOf(priceService.getText()));

        APIRestConfig.getServicesService().insertService(service).execute();
        Response<List<Service>> serviceList = Objects.requireNonNull(APIRestConfig.getServicesService().serviceGetAll().execute());
        if (serviceList.body() != null) {
            services.addAll(serviceList.body());
            listService.setItems(services);
        }
    }
}
