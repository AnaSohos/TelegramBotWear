package com.amurtigerstudio.boot;

import lombok.Data;

@Data
public class Model {
    private String name;
    private Double temp;
    private Double pressure;
    private String icon;
    private String main;
}

