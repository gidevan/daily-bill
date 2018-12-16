package org.daily.bill.domain;

import java.util.Date;

/**
 * Created by vano on 20.8.18.
 */
public class Currency implements Identifiable<Long> {

    private Long id;
    private String code;
    private String name;
    private Boolean defaultCurrency;
    private Date created;
    private Date updated;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Boolean defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
