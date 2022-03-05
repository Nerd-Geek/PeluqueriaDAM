package ies.luisvives.serverpeluqueriadam.exceptions.appointment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String id, LocalDate date, LocalTime time) {
        super("There is no stock left for the service " + id + "at datetime" + date + "" + time);
    }
}
