package ee.pitko.fx.repository;

import ee.pitko.fx.model.ExchangeRate;
import ee.pitko.fx.model.ExchangeRateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RateRepository extends JpaRepository<ExchangeRate, ExchangeRateId> {


    @Query("""
            SELECT er
            FROM ExchangeRate er
            WHERE er.date = (SELECT MAX(innerER.date)
                             FROM ExchangeRate innerER
                             WHERE innerER.currency = er.currency)
            ORDER BY er.currency ASC
            """)
    List<ExchangeRate> getCurrentRates();

    
    @Query("""
            SELECT er
            FROM ExchangeRate er
            WHERE er.currency = :currency
            AND er.date >= DATEADD(Year, -1, current_date)
            ORDER BY er.date ASC
            """)
    List<ExchangeRate> getHistoricalRates(String currency);
}
