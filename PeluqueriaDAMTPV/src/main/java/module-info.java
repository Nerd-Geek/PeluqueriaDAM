module ies.luisvives.peluqueriadamtpv {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.kordamp.ikonli.core;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.ikonli.fontawesome5;
	requires retrofit2;
	requires retrofit2.converter.jackson;
	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.core;
	requires lombok;

	opens ies.luisvives.peluqueriadamtpv to javafx.fxml, com.fasterxml.jackson.databind;
	opens ies.luisvives.peluqueriadamtpv.model to com.fasterxml.jackson.databind;
	opens ies.luisvives.peluqueriadamtpv.controller to javafx.fxml, com.fasterxml.jackson.databind;
	opens ies.luisvives.peluqueriadamtpv.restcontroller to com.fasterxml.jackson.core, com.fasterxml.jackson.annotation, com.fasterxml.jackson.databind;
	exports ies.luisvives.peluqueriadamtpv;
	exports ies.luisvives.peluqueriadamtpv.controller;
	exports ies.luisvives.peluqueriadamtpv.model;
	exports ies.luisvives.peluqueriadamtpv.restcontroller;
}