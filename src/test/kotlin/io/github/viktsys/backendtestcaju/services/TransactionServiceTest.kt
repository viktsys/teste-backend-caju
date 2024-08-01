package io.github.viktsys.backendtestcaju.services

import io.github.viktsys.backendtestcaju.dtos.AccountDTO
import io.github.viktsys.backendtestcaju.dtos.TransactionDTO
import io.github.viktsys.backendtestcaju.models.MerchantCodeChargeability
import io.github.viktsys.backendtestcaju.models.MerchantNameChargeability
import io.github.viktsys.backendtestcaju.models.enums.ChargeType
import io.github.viktsys.backendtestcaju.models.enums.TransactionStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertEquals

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class TransactionServiceTest {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var transactionService: TransactionService

    @Autowired
    lateinit var merchantCodeChargeabilityService: MerchantCodeChargeabilityService

    @Autowired
    lateinit var merchantNameChargeabilityService: MerchantNameChargeabilityService

    @Test
    fun `Test transaction based on MCC number`() {
        /* Account used for testing */
        val account_dto = AccountDTO()
        account_dto.cash_balance = 100.0f
        account_dto.food_balance = 50.0f
        account_dto.meal_balance = 20.0f
        val account = accountService.create(account_dto)

        /* MCC used for testing */
        val mcc = merchantCodeChargeabilityService.create(
            MerchantCodeChargeability(
                id = 1L,
                code = "5411",
                chargeType = ChargeType.FOOD
            )
        )

        /* Transaction used for testing */
        val transaction_dto = TransactionDTO()
        transaction_dto.account = account.id!!
        transaction_dto.merchant = "McDonald's"
        transaction_dto.mcc = mcc.code
        transaction_dto.totalAmount = 10.0f

        val result = transactionService.handleTransaction(transaction_dto)

        assertEquals(result, TransactionStatus.ACCEPTED)
        assertEquals(result.code, "00")
    }

    @Test
    fun `Test transaction based on Merchant's Name`() {
        /* Account used for testing */
        val accountDTO = AccountDTO()
        accountDTO.cash_balance = 100.0f
        accountDTO.food_balance = 50.0f
        accountDTO.meal_balance = 20.0f
        val account = accountService.create(accountDTO)

        /* MCC used for testing */
        var mnc = MerchantNameChargeability(
            id = 1,
            name = "McDonald's",
            chargeType = ChargeType.MEAL
        )
        mnc = merchantNameChargeabilityService.create(mnc)

        /* Transaction used for testing */
        val transactionDTO = TransactionDTO()
        transactionDTO.account = account.id!!
        transactionDTO.merchant = "McDonald's"
        transactionDTO.mcc = "6666"
        transactionDTO.totalAmount = 10.0f

        val result = transactionService.handleTransaction(transactionDTO)

        assertEquals(result, TransactionStatus.ACCEPTED)
        assertEquals(result.code, "00")
    }

    @Test
    fun `Test transaction fallback`() {
        /* Account used for testing */
        val accountDTO = AccountDTO()
        accountDTO.cash_balance = 100.0f
        accountDTO.food_balance = 50.0f
        accountDTO.meal_balance = 20.0f
        val account = accountService.create(accountDTO)

        /* MCC used for testing */
        val mcc = merchantCodeChargeabilityService.create(
            MerchantCodeChargeability(
                id = 1L,
                code = "1234",
                chargeType = ChargeType.FOOD
            )
        )

        /* MCC used for testing */
        var mnc = MerchantNameChargeability(
            id = 1,
            name = "Burger King    SAO PAULO",
            chargeType = ChargeType.MEAL
        )
        mnc = merchantNameChargeabilityService.create(mnc)

        /* Transaction used for testing */
        val transactionDTO = TransactionDTO()
        transactionDTO.account = account.id!!
        transactionDTO.merchant = "Burger King    SAO PAULO"
        transactionDTO.mcc = "1234"
        transactionDTO.totalAmount = 60.0f

        val result = transactionService.handleTransaction(transactionDTO)

        assertEquals(result, TransactionStatus.ACCEPTED)
        assertEquals(result.code, "00")
    }

}