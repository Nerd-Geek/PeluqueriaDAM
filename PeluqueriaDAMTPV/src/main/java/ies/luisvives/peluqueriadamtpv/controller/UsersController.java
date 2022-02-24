package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.model.User;
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
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;

public class UsersController implements Initializable, Callback {

    @FXML
    TextField usernameTextField, nameTextField, surnameTextField, passwordTextField, telephoneTextField, emailTextField, imageTextField;
    @FXML
    ChoiceBox<String> gender_choice_box;
    @FXML
    TableView<User> listUsers;

    private final TableColumn<User, String> username = new TableColumn<>("username");
    private final TableColumn<User, String> name = new TableColumn<>("name");
    private final TableColumn<User, String> surname = new TableColumn<>("surname");
    private final TableColumn<User, String> telephone = new TableColumn<>("telephone");
    private final TableColumn<User, String> email = new TableColumn<>("email");
    private final TableColumn<User, String> gender = new TableColumn<>("gender");
    private final TableColumn<User, String> image = new TableColumn<>("image");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listUsers.getColumns().addAll(username, name, surname, telephone, email, gender, image);
        ObservableList<UserGender> genders = new SimpleListProperty<>(UserGender.Female.name(), UserGender.Male.name());
        gender_choice_box.getItems().addAll(UserGender.Male.name(), UserGender.Female.name());
        gender_choice_box.setValue(UserGender.Male.name());
        onTableItemUser();
    }

    @FXML
    public void onTableItemUser() {
        try {
            Response<List<User>> users = Objects.requireNonNull(APIRestConfig.getUsersService().usersGetAll().execute());
            if (users.body() != null) {
                ObservableList<User> observableListUsers =
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
            User user = APIRestConfig.getUsersService().deleteUser(listUsers.getSelectionModel().getSelectedItem().getId()).execute().body();
            Response<List<User>> usersList = Objects.requireNonNull(APIRestConfig.getUsersService().usersGetAll().execute());
            ObservableList<User> users =
                    FXCollections.observableArrayList();
            if (usersList.body() != null) {
                users.addAll(usersList.body());
            } else {
                users.remove(user);
            }
            listUsers.setItems(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertUser() throws IOException {

        ObservableList<User> users =
                FXCollections.observableArrayList();
        User user = new User();
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
        Response<List<User>> usersList = Objects.requireNonNull(APIRestConfig.getUsersService().usersGetAll().execute());
        if (usersList.body() != null) {
            users.addAll(usersList.body());
            listUsers.setItems(users);
        }
    }
}