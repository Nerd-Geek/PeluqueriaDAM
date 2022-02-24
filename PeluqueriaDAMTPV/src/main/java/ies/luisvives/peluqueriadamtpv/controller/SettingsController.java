package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.App;
import ies.luisvives.peluqueriadamtpv.model.UserConfiguration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsController {
    @FXML
    private VBox content;
    @FXML
    private ChoiceBox cbLanguage;
    @FXML
    private ChoiceBox cbTheme;

    @FXML
    public void initialize() {
        UserConfiguration config = UserConfiguration.getInstance();

        //Agregar idiomas disponibles
        List<String> langs = new ArrayList<>(config.getAvailableLanguages());

        //Asignar nombres
        String[] languageNames = new String[langs.size()];
        for (int n = 0; n < langs.size(); n++) {
            languageNames[n] = getLanguageName(langs.get(n).split("_")[0]);
        }
        //Asignar al ChoiceBox
        ObservableList obListLanguage = FXCollections.observableArrayList(languageNames);
        cbLanguage.setItems(obListLanguage);
        cbLanguage.setValue(getLanguageName(config.getActualLanguage().split("_")[0]));

        cbLanguage.setOnAction(event -> {
            for (int n = 0; n < languageNames.length; n++) {
                if (languageNames[n].equalsIgnoreCase(cbLanguage.getValue().toString())) {
                    config.setActualLanguage(langs.get(n));
                }
            }
        });

        //Agregar selección de tema
        List<String> themes = new ArrayList<>(config.getAvailableThemes());
        ObservableList obListTheme = FXCollections.observableArrayList(themes);
        cbTheme.setItems(obListTheme);
        cbTheme.setValue(config.getActualTheme());
        cbTheme.setOnAction(event -> {
            for (String theme : themes) {
                if (theme.equalsIgnoreCase(cbTheme.getValue().toString())) {
                    config.setActualTheme(theme);
                }
            }
        });
    }

    private String getLanguageName(String lng) {
        Locale loc = new Locale(lng);
        String str = loc.getDisplayLanguage(loc);
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    @FXML
    protected void updateApp() {
        try {
            new App().reloadStage();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
