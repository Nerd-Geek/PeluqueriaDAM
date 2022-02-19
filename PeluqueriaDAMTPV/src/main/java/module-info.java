module ies.luisvives.peluqueriadamtpv {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.kordamp.ikonli.core;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.ikonli.fontawesome5;
	requires retrofit2;
	requires retrofit2.converter.jackson;

	opens ies.luisvives.peluqueriadamtpv to javafx.fxml;
	exports ies.luisvives.peluqueriadamtpv;
	exports ies.luisvives.peluqueriadamtpv.controller;
	opens ies.luisvives.peluqueriadamtpv.controller to javafx.fxml;
}