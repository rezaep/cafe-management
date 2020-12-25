package com.sflpro.cafemanager.table.controller.model.request;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class CreatTableRequest {
    @Positive
    private int number;
}
