package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.model.UserDTO;
import ies.luisvives.peluqueriadamtpv.model.UserGender;
import ies.luisvives.peluqueriadamtpv.restcontroller.APIRestConfig;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import retrofit2.Response;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class UsersController implements Initializable, Callback {

    @FXML
    TextField usernameTextField, nameTextField, surnameTextField, passwordTextField, telephoneTextField, emailTextField, imageTextField;
    @FXML
    ChoiceBox<String> gender_choice_box;
    @FXML
    TableView<UserDTO> listUsers;

    private final TableColumn<UserDTO, String> username = new TableColumn<>("username");
    private final TableColumn<UserDTO, String> name = new TableColumn<>("name");
    private final TableColumn<UserDTO, String> surname = new TableColumn<>("surname");
    private final TableColumn<UserDTO, String> telephone = new TableColumn<>("telephone");
    private final TableColumn<UserDTO, String> email = new TableColumn<>("email");
    private final TableColumn<UserDTO, String> gender = new TableColumn<>("gender");
    private final TableColumn<UserDTO, String> image = new TableColumn<>("image");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listUsers.getColumns().addAll(username,name,surname,telephone,email,gender,image);
        ObservableList<UserGender> genders = new SimpleListProperty<>(UserGender.Female.name(), UserGender.Male.name());
        gender_choice_box.getItems().addAll(UserGender.Male.name(), UserGender.Female.name());
        gender_choice_box.setValue(UserGender.Male.name());
        onTableItemUser();
    }

    @FXML
    public void onTableItemUser() {
        try {
            Response<List<UserDTO>> users = Objects.requireNonNull(APIRestConfig.getUsersService().usersGetAll().execute());
            if (users.body() != null) {
                ObservableList<UserDTO> observableListUsers =
                        FXCollections.observableArrayList();
                observableListUsers.addAll(users.body());
                username.setCellValueFactory(new PropertyValueFactory<>("username"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
                telephone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                email.setCellValueFactory(new PropertyValueFactory<>("email"));
                gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
                image.setCellValueFactory(new PropertyValueFactory<>("image"));

                username.setSortType(TableColumn.SortType.DESCENDING);
                name.setSortType(TableColumn.SortType.DESCENDING);
                surname.setSortType(TableColumn.SortType.DESCENDING);
                telephone.setSortType(TableColumn.SortType.DESCENDING);
                email.setSortType(TableColumn.SortType.DESCENDING);
                gender.setSortType(TableColumn.SortType.DESCENDING);
                image.setSortType(TableColumn.SortType.DESCENDING);
                listUsers.setItems(observableListUsers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteUser(ActionEvent event) {
        try {
            UserDTO userDTO = APIRestConfig.getUsersService().deleteUser(listUsers.getSelectionModel().getSelectedItem().getId()).execute().body();
            Response<List<UserDTO>> usersList = Objects.requireNonNull(APIRestConfig.getUsersService().usersGetAll().execute());
            ObservableList<UserDTO> users =
                    FXCollections.observableArrayList();
            if (usersList.body() != null) {
                users.addAll(usersList.body());
            } else {
                users.remove(userDTO);
            }
            listUsers.setItems(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertUser() throws IOException {

        ObservableList<UserDTO> users =
                FXCollections.observableArrayList();
        UserDTO user = new UserDTO();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(usernameTextField.getText());
        user.setName(nameTextField.getText());
        user.setSurname(surnameTextField.getText());
        user.setPassword(passwordTextField.getText());
        user.setPhoneNumber(telephoneTextField.getText());
        user.setEmail(emailTextField.getText());
        user.setGender(UserGender.valueOf(gender_choice_box.getValue()));
        user.setImage(imageTextField.getText());

        APIRestConfig.getUsersService().insertUsers(user).execute();
        Response<List<UserDTO>> usersList = Objects.requireNonNull(APIRestConfig.getUsersService().usersGetAll().execute());
        if (usersList.body() != null) {
            users.addAll(usersList.body());
            listUsers.setItems(users);
        }
    }


}
