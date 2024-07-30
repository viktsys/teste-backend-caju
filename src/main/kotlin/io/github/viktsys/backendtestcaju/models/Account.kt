package io.github.viktsys.backendtestcaju.models

import jakarta.persistence.*

@Entity
@Table(name = "account")
class Account (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false)
    var food_balance: Float = 0.0f,

    @Column(nullable = false)
    var meal_balance: Float = 0.0f,

    @Column(nullable = false)
    var cash_balance: Float = 0.0f,
)