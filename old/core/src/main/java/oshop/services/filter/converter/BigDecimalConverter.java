package oshop.services.filter.converter;

import oshop.model.EntityUtils;

import java.math.BigDecimal;

public class BigDecimalConverter extends BaseConverter<BigDecimal> {

    @Override
    public BigDecimal convert(String value) {
        return EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value)));
    }
}
