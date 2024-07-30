package io.github.viktsys.backendtestcaju.services

import io.github.viktsys.backendtestcaju.dtos.TransactionDTO
import io.github.viktsys.backendtestcaju.models.Account
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    lateinit var transactionService: TransactionService

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var merchantCodeChargeabilityService: MerchantCodeChargeabilityService

    @Autowired
    lateinit var merchantNameChargeabilityService: MerchantNameChargeabilityService

    @Test
    fun contextLoads() {
        assertNotNull(transactionService)
        assertNotNull(accountService)
        assertNotNull(merchantCodeChargeabilityService)
        assertNotNull(merchantNameChargeabilityService)
    }

    @BeforeEach
    fun setupTest() {
        val account123 = accountService.create(
            Account(
                123,
                100.0f,
                100.0f,
                100.0f
            )
        )

        accountService.create(account123)
    }

    @Test
    fun authorizedTransactionTest() {
        val transactionChargeToCashBalance = TransactionDTO()
        transactionChargeToCashBalance.account = 123
        transactionChargeToCashBalance.mcc = "9810"
        transactionChargeToCashBalance.totalAmount = 10.0f
        transactionChargeToCashBalance.merchant = "Test Merchant"

        val transactionChargeToFoodBalance = TransactionDTO()
        transactionChargeToFoodBalance.account = 123
        transactionChargeToFoodBalance.mcc = "5411"
        transactionChargeToFoodBalance.totalAmount = 10.0f
        transactionChargeToFoodBalance.merchant = "Test Merchant"

        val transactionChargeToMealBalance = TransactionDTO()
        transactionChargeToMealBalance.account = 123
        transactionChargeToMealBalance.mcc = "5811"
        transactionChargeToMealBalance.totalAmount = 10.0f
        transactionChargeToMealBalance.merchant = "Test Merchant"

        val chargeStatusCashBalance = transactionService.handleTransaction(transactionChargeToCashBalance)
        val chargeStatusFoodBalance = transactionService.handleTransaction(transactionChargeToFoodBalance)
        val chargeStatusMealBalance = transactionService.handleTransaction(transactionChargeToMealBalance)

        assertEquals(chargeStatusCashBalance.code, "00")
        assertEquals(chargeStatusFoodBalance.code, "00")
        assertEquals(chargeStatusMealBalance.code, "00")
    }

}