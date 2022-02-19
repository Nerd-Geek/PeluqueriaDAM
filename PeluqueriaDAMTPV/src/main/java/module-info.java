module ies.luisvives.peluqueriadamtpv {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.kordamp.ikonli.core;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.ikonli.fontawesome5;

	opens ies.luisvives.peluqueriadamtpv to javafx.fxml;
	exports ies.luisvives.peluqueriadamtpv;
	exports ies.luisvives.peluqueriadamtpv.controller;
	opens ies.luisvives.peluqueriadamtpv.controller to javafx.fxml;
}