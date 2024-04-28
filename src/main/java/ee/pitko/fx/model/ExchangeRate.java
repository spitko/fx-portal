package ee.pitko.fx.model;

import ee.pitko.fx.client.FxRateHandling;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table
@IdClass(ExchangeRateId.class)
public class ExchangeRate implements Serializable {

    @Id
    private LocalDate date;

    @Id
    @Column(length = 3)
    private String currency;

    @Column(nullable = false)
    private BigDecimal rate;

    public ExchangeRate() {
    }


    public ExchangeRate(LocalDate date, String currency, BigDecimal rate) {
        this.date = date;
        this.currency = currency;
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(date, that.date) && Objects.equals(currency, that.currency) && Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, currency, rate);
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "date=" + date +
                ", currency='" + currency + '\'' +
                ", rate=" + rate +
                '}';
    }

    public static ExchangeRate fromXML(FxRateHandling fxRate) {
        return new ExchangeRate(
                fxRate.getDt().toGregorianCalendar().toZonedDateTime().toLocalDate(),
                fxRate.getCcyAmt().get(1).getCcy().value(),
                fxRate.getCcyAmt().get(1).getAmt()
        );
    }
}
