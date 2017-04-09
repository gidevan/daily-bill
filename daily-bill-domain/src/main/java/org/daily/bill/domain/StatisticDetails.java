package org.daily.bill.domain;

import java.math.BigDecimal;

/**
 * Created by vano on 10.4.17.
 */
public class StatisticDetails implements Identifiable<Long>{

    private Long id;
    private String name;
    private BigDecimal price;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
