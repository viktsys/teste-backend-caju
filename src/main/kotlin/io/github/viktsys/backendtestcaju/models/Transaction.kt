package io.github.viktsys.backendtestcaju.models

import com.fasterxml.jackson.annotation.JsonView
import io.github.viktsys.backendtestcaju.models.enums.ChargeType
import io.github.viktsys.backendtestcaju.models.enums.TransactionStatus
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "transaction")
class Transaction () {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "account_id")
    var account: Account? = null;

    @Column(nullable = false)
    var amount: Float = 0.0f;

    @Column
    var mcc: String = "";

    @Column
    var merchant: String = "";

    @Column
    @Enumerated(EnumType.STRING)
    var chargeType: ChargeType? = null;

    @Column
    @Enumerated(EnumType.STRING)
    var status: TransactionStatus? = null;
}