package io.github.viktsys.backendtestcaju.dtos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

class AccountDTO {

    @Min(value = 0, message = "Account numbers are positive integers")
    var id: Long? = null

    @NotNull(message = "Food balance should not be null")
    @Min(value = 0, message = "Amount should be greater than 0")
    var food_balance: Float = 0.0f

    @NotNull(message = "Meal balance should not be null")
    @Min(value = 0, message = "Amount should be greater than 0")
    var meal_balance: Float = 0.0f

    @NotNull(message = "Cash balance should not be null")
    @Min(value = 0, message = "Amount should be greater than 0")
    var cash_balance: Float = 0.0f
}