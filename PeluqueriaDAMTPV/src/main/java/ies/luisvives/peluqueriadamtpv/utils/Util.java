package ies.luisvives.peluqueriadamtpv.utils;

import ies.luisvives.peluqueriadamtpv.model.UserConfiguration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Util {
    private static ResourceBundle resourceBundle;
    private static final String PACKAGE_DIR  = "/ies/luisvives/peluqueriadamtpv/";

    private Util(){
    }

    private static void setResourceBundleLanguage(){
        String[] language = UserConfiguration.getInstance().getActualLanguage().split("_");
        Locale locale = new Locale(language[0], language[1]);
        resourceBundle = ResourceBundle.getBundle(PACKAGE_DIR + "i18n/strings", locale);
    }

    public static Parent getParentRoot(String file) throws IOException {
        setResourceBundleLanguage();
        return FXMLLoader.load(Objects.requireNonNull(Util.class.getResource(PACKAGE_DIR + file + ".fxml")),
                resourceBundle);
    }

    public static String getString(String str){
        if (resourceBundle == null){
            setResourceBundleLanguage();
        }
        return resourceBundle.getString(str);
    }
}
