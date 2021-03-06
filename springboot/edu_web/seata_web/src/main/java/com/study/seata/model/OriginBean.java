package com.study.seata.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2020-08-18 17:53
 **/
@Data
public class OriginBean {
    private int intProperty;
    private boolean booleanProperty;
    private float floatProperty;
    private double doubleProperty;
    private long longProperty;
    private char charProperty;
    private byte byteProperty;
    private short shortProperty;
    private Integer integerProperty;
    private Boolean booleanObjProperty;
    private Float floatObjProperty;
    private Double doubleObjProperty;
    private Long longObjProperty;
    private Short shortObjProperty;
    private Byte byteObjProperty;
    private BigInteger bigIntegerProperty;
    private BigDecimal bigDecimalProperty;
    private String stringProperty;

    public OriginBean() {
        this.intProperty = 10000;
        this.booleanProperty = true;
        this.floatProperty = 0.556F;
        this.doubleProperty = 10000.221D;
        this.longProperty = 99999999999L;
        this.charProperty = 'a';
        this.byteProperty = 123;
        this.shortProperty = 12222;
        this.integerProperty = 11111111;
        this.booleanObjProperty = Boolean.TRUE;
        this.floatObjProperty = 99.32322F;
        this.doubleObjProperty = 222121.3232D;
        this.longObjProperty = 333333L;
        this.shortObjProperty = 12121;
        this.byteObjProperty = 121;
        this.bigIntegerProperty = new BigInteger("12121212121");
        this.bigDecimalProperty = new BigDecimal(212123323323232L);
        this.stringProperty = "功盖三分国，名成八阵图。江流石不转，遗恨失吞吴。";
    }
}
