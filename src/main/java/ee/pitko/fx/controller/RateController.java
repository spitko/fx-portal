package ee.pitko.fx.controller;

import ee.pitko.fx.exception.OperationalErrorException;
import ee.pitko.fx.service.RateService;
import ee.pitko.fx.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class RateController {

    @Autowired
    RateService rateService;

    @GetMapping("/current")
    public ResponseEntity<List<ExchangeRate>> getCurrentFxRates() {
        return new ResponseEntity<>(rateService.getCurrentRates(), HttpStatus.OK);
    }

    @ExceptionHandler(OperationalErrorException.class)
    public ResponseEntity<String> handleOperationalErrorException(OperationalErrorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}