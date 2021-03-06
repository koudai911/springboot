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
public class TargetBean {
    private Integer intProperty;
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
}
