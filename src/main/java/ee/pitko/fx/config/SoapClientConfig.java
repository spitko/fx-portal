package ee.pitko.fx.config;

import ee.pitko.fx.client.FxRates;
import ee.pitko.fx.client.FxRatesSoap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapClientConfig {

    @Bean
    public FxRatesSoap fxRatesSoap() {
        FxRates fxRates = new FxRates();
        return fxRates.getFxRatesSoap();
    }
}
