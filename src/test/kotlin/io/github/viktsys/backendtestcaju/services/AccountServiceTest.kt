package io.github.viktsys.backendtestcaju.services

import io.github.viktsys.backendtestcaju.dtos.AccountDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertNotNull

@ExtendWith(SpringExtension::class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AccountServiceTest {

    @Autowired
    lateinit var accountService: AccountService

    @Test
    fun `create account using AccountDTO`() {
        /* Account used for testing */
        val account_dto = AccountDTO()
        account_dto.cash_balance = 100.0f
        account_dto.food_balance = 50.0f
        account_dto.meal_balance = 2.0f

        val account = accountService.create(account_dto)

        assertEquals(100.0f, account.cash_balance)
        assertEquals(50.0f, account.food_balance)
        assertEquals(2.0f, account.meal_balance)
        assertNotNull(account.id)
    }
}