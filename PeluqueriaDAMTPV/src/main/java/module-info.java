module ies.luisvives.peluqueriadamtpv {
	requires javafx.controls;
	requires javafx.fxml;


	opens ies.luisvives.peluqueriadamtpv to javafx.fxml;
	exports ies.luisvives.peluqueriadamtpv;
	exports ies.luisvives.peluqueriadamtpv.controller;
	opens ies.luisvives.peluqueriadamtpv.controller to javafx.fxml;
}