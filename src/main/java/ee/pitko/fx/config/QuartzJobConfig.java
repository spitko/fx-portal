package ee.pitko.fx.config;

import ee.pitko.fx.config.job.FetchDailyExchangeRatesJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzJobConfig {

    @Bean
    public JobDetail fetchCurrentRatesJobDetail() {
        return JobBuilder.newJob(FetchDailyExchangeRatesJob.class)
                .withIdentity("fetchCurrentRatesJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger fetchCurrentRatesTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(fetchCurrentRatesJobDetail())
                .withIdentity("fetchCurrentRatesTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(12, 0))
                .build();
    }

}
