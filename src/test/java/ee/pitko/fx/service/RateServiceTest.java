package ee.pitko.fx.service;

import ee.pitko.fx.client.ErrorCode;
import ee.pitko.fx.client.FxRatesHandling;
import ee.pitko.fx.client.FxRatesSoap;
import ee.pitko.fx.client.GetCurrentFxRatesResponse.GetCurrentFxRatesResult;
import ee.pitko.fx.client.OprlErrHandling;
import ee.pitko.fx.exception.OperationalErrorException;
import ee.pitko.fx.model.ExchangeRate;
import ee.pitko.fx.repository.RateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RateServiceTest {

    @Mock
    private FxRatesSoap fxRatesSoap;

    @Mock
    private RateRepository rateRepository;

    @InjectMocks
    private RateService rateService;

    @Test
    public void testGetCurrentRates_CacheFull_FetchesFromDB() {
        List<ExchangeRate> cachedRates = createSampleExchangeRates();
        when(rateRepository.getCurrentRates()).thenReturn(cachedRates);
        List<ExchangeRate> result = rateService.getCurrentRates();
        assertEquals(cachedRates, result);
        verify(fxRatesSoap, times(0)).getCurrentFxRates("EU");
    }

    @Test
    public void testGetCurrentRates_CacheEmpty_FetchesFromLB() {
        List<ExchangeRate> cachedRates = List.of();
        when(rateRepository.getCurrentRates()).thenReturn(cachedRates);
        when(fxRatesSoap.getCurrentFxRates(anyString())).thenReturn(createSampleFxRatesResult());
        try {
            rateService.getCurrentRates();
            fail();
        } catch (OperationalErrorException expected) {
            verify(fxRatesSoap, times(1)).getCurrentFxRates("EU");
        }
    }

    private List<ExchangeRate> createSampleExchangeRates() {
        return IntStream.range(0, 86).mapToObj((n) -> new ExchangeRate()).toList();
    }

    private GetCurrentFxRatesResult createSampleFxRatesResult() {
        FxRatesHandling fxRatesHandling = new FxRatesHandling();
        OprlErrHandling errHandling = new OprlErrHandling();
        errHandling.setErr(new ErrorCode());
        errHandling.setDesc("desc");
        fxRatesHandling.setOprlErr(errHandling);
        GetCurrentFxRatesResult result = new GetCurrentFxRatesResult();
        result.setFxRates(fxRatesHandling);
        return result;
    }

}

