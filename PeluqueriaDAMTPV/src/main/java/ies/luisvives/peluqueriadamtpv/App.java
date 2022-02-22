package ies.luisvives.peluqueriadamtpv;

import ies.luisvives.peluqueriadamtpv.model.UserConfiguration;
import ies.luisvives.peluqueriadamtpv.utils.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		UserConfiguration.loadData(); //Cargar configuración del administrador
		Parent root = Util.setAndGetLanguage("main_view");
		Scene scene = new Scene(root, 1280, 800);
		stage.setTitle("Peluquería DAM TPV");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}