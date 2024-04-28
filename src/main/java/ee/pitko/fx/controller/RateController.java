package ee.pitko.fx.controller;

import ee.pitko.fx.exception.OperationalErrorException;
import ee.pitko.fx.service.RateService;
import ee.pitko.fx.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/rates")
public class RateController {

    @Autowired
    RateService rateService;

    @GetMapping("/current")
    public ResponseEntity<List<ExchangeRate>> getCurrentFxRates() {
        return new ResponseEntity<>(rateService.getCurrentRates(), HttpStatus.OK);
    }

    @GetMapping("/historical/{currency}")
    public ResponseEntity<List<ExchangeRate>> getHistoricalFxRates(@PathVariable String currency) {
        return new ResponseEntity<>(rateService.getHistoricalRates(currency), HttpStatus.OK);
    }


    @ExceptionHandler(OperationalErrorException.class)
    public ResponseEntity<String> handleOperationalErrorException(OperationalErrorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
