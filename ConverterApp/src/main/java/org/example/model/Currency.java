package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Currency {
    private String CharCode;
    private double Value;
}