package oshop.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EntityUtils {

    public static final int DEFAULT_SCALE = 2;

    public static BigDecimal round(BigDecimal bigDecimal, int scale) {
        return bigDecimal != null ? bigDecimal.setScale(scale, RoundingMode.HALF_UP) : null;
    }

    public static BigDecimal round(BigDecimal bigDecimal) {
        return EntityUtils.round(bigDecimal, DEFAULT_SCALE);
    }
}
