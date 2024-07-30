package io.github.viktsys.backendtestcaju.models

import io.github.viktsys.backendtestcaju.models.enums.ChargeType
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "merchant_code_chargeability")
class MerchantCodeChargeability(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Merchant code is required")
    var code: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Charge type is required")
    var chargeType: ChargeType,
)