package io.github.viktsys.backendtestcaju.models.enums

enum class TransactionStatus(val code: String) {
    ACCEPTED("00"),
    REJECTED_BY_INSUFFICIENT_BALANCE("51"),
    REJECTED_BY_UNKNOWN_ERROR("07")
}