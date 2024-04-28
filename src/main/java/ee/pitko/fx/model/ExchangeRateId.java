package ee.pitko.fx.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ExchangeRateId implements Serializable {
    private LocalDate date;

    private String currency;

    public ExchangeRateId() {
    }

    public ExchangeRateId(LocalDate date, String currency) {
        this.date = date;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateId that = (ExchangeRateId) o;
        return Objects.equals(date, that.date) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, currency);
    }
}
