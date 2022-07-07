package com.payconiq.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Serialized class to format the currency field
 *
 */
public class CurrencySerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeString(value.setScale(2, RoundingMode.HALF_UP).toString());
    }
}