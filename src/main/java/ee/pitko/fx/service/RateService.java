package ee.pitko.fx.service;


import ee.pitko.fx.exception.OperationalErrorException;
import ee.pitko.fx.client.FxRatesHandling;
import ee.pitko.fx.client.FxRatesSoap;
import ee.pitko.fx.model.ExchangeRate;
import ee.pitko.fx.repository.RateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class RateService {

    private static final String EXCHANGE_RATE_TYPE = "EU";
    private static final int CURRENCY_COUNT = 86;
    private static final Logger logger = LoggerFactory.getLogger(RateService.class);


    FxRatesSoap fxRatesSoap;
    RateRepository rateRepository;

    @Autowired
    public RateService(FxRatesSoap fxRatesSoap, RateRepository rateRepository) {
        this.fxRatesSoap = fxRatesSoap;
        this.rateRepository = rateRepository;
    }

    public List<ExchangeRate> getCurrentRates() {
        logger.info("Fetching current exchange rates from db");
        List<ExchangeRate> cachedRates = rateRepository.getCurrentRates();
        if (cachedRates.size() == CURRENCY_COUNT) {
            return cachedRates;
        }
        logger.info("Fetching current exchange rates from LB service");
        List<ExchangeRate> newRates = fetchCurrentExchangeRatesFromLB();
        rateRepository.saveAll(newRates);
        return newRates;
    }

    public List<ExchangeRate> getHistoricalRates(String currency) {
        logger.info("Fetching historical exchange rates for {} from db", currency);
        List<ExchangeRate> cachedRates = rateRepository.getHistoricalRates(currency);
        if (isHistoricalRateCacheValid(cachedRates)) {
            return cachedRates;
        }
        logger.info("Fetching historical exchange rates for {} from LB service", currency);
        List<ExchangeRate> newRates = fetchHistoricalExchangeRatesFromLB(currency);
        rateRepository.saveAll(newRates);
        return newRates;
    }

    private List<ExchangeRate> fetchCurrentExchangeRatesFromLB() {
        FxRatesHandling soapResponse = fxRatesSoap.getCurrentFxRates(EXCHANGE_RATE_TYPE).getFxRates();
        if (soapResponse.getOprlErr() != null) {
            throw OperationalErrorException.fromXML(soapResponse.getOprlErr());
        }
        return soapResponse.getFxRate().stream().map(ExchangeRate::fromXML).sorted(Comparator.comparing(ExchangeRate::getCurrency)).toList();
    }

    private List<ExchangeRate> fetchHistoricalExchangeRatesFromLB(String currency) {
        String startDate = LocalDate.now().minusYears(1).toString();
        String endDate = LocalDate.now().toString();
        FxRatesHandling soapResponse = fxRatesSoap.getFxRatesForCurrency(EXCHANGE_RATE_TYPE, currency, startDate, endDate).getFxRates();
        if (soapResponse.getOprlErr() != null) {
            throw OperationalErrorException.fromXML(soapResponse.getOprlErr());
        }
        return soapResponse.getFxRate().stream().map(ExchangeRate::fromXML).sorted(Comparator.comparing(ExchangeRate::getDate)).toList();
    }

    private boolean isHistoricalRateCacheValid(List<ExchangeRate> cachedRates) {
        return !cachedRates.isEmpty() && cachedRates.get(0).getDate().isBefore(LocalDate.now().minusYears(1).plusWeeks(1));
    }
}
