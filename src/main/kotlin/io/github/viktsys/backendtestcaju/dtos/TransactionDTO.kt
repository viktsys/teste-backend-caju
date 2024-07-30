package io.github.viktsys.backendtestcaju.dtos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class TransactionDTO {
    @NotNull(message = "Transaction ID should not be null")
    @Min(value = 0, message = "Account numbers are positive integers")
    var account: Long = 0L;

    @Min(value = 0, message = "Amount should be greater than 0")
    var totalAmount: Float = 0.0f;

    var mcc: String = "";
    var merchant: String = "";
}