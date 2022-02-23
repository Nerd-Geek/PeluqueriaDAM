package ies.luisvives.peluqueriadamtpv;

import ies.luisvives.peluqueriadamtpv.model.UserConfiguration;
import ies.luisvives.peluqueriadamtpv.utils.Util;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
	private static Stage stage;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		App.stage = stage;
		startStage();
	}

	public void startStage() throws IOException {
		UserConfiguration.loadData(); //Cargar configuración del administrador
		Parent root = Util.getParentRoot("main_view");
		Scene scene = new Scene(root, 1280, 800);
		stage.setTitle(Util.getString("title.appName"));
		stage.setScene(scene);
		stage.show();
	}

	public void reloadStage() throws IOException {
		stage.close();
		startStage();
	}
}