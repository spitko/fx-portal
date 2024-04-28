package ee.pitko.fx.config.job;


import ee.pitko.fx.service.RateService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FetchDailyExchangeRatesJob implements Job {

    @Autowired
    private RateService exchangeRateService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        exchangeRateService.fetchCurrentExchangeRatesFromLB();
    }
}
