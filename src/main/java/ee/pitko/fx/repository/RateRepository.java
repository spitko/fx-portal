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
            """)
    List<ExchangeRate> getCurrentRates();
}
