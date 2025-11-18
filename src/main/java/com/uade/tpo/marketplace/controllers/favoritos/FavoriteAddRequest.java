package com.uade.tpo.marketplace.controllers.favoritos;

import jakarta.validation.constraints.NotBlank;

public record FavoriteAddRequest(
        @NotBlank String userId,
        @NotBlank String productId
) {}

//usreID=0 productId=0