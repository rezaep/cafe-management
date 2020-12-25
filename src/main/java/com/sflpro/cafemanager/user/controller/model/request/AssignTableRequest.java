package com.sflpro.cafemanager.user.controller.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AssignTableRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long tableId;
}
