package io.github.viktsys.backendtestcaju.models

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class AccountTest {

    @Test
    fun showCreateNewAccount() {
        val account = Account(
            id = 111,
            food_balance = 10.0f,
            cash_balance = 10.0f,
            meal_balance = 10.0f
        )
        assertEquals(111, account.id)
        assertEquals(10.0f, account.food_balance)
        assertEquals(10.0f, account.meal_balance)
        assertEquals(10.0f, account.cash_balance)
    }

    @Test
    fun showUpdateAccount() {
        val account = Account(
            id = 111,
            food_balance = 10.0f,
            cash_balance = 10.0f,
            meal_balance = 10.0f
        )
        account.food_balance = 20.0f
        account.meal_balance = 20.0f
        account.cash_balance = 20.0f
        assertEquals(111, account.id)
        assertEquals(20.0f, account.food_balance)
        assertEquals(20.0f, account.meal_balance)
        assertEquals(20.0f, account.cash_balance)
    }
}