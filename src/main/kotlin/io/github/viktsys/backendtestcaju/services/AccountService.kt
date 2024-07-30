package io.github.viktsys.backendtestcaju.services

import io.github.viktsys.backendtestcaju.models.Account
import io.github.viktsys.backendtestcaju.models.enums.ChargeType
import io.github.viktsys.backendtestcaju.repositories.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService {

    @Autowired
    lateinit var accountRepository: AccountRepository

    fun getAll(): List<Account> {
        return accountRepository.findAll()
    }

    fun findByID(id: Long): Account? {
        return accountRepository.findById(id).orElse(null)
    }

    fun create(account: Account): Account {
        return accountRepository.save(account)
    }

    fun update(account: Account): Account {
        return accountRepository.save(account)
    }

    fun deleteByID(id: Long) {
        accountRepository.deleteById(id)
    }

    fun isChargeable(account: Account, amount: Float, chargeType: ChargeType): Boolean {
        return when (chargeType) {
            ChargeType.FOOD -> (account.food_balance >= amount)
            ChargeType.MEAL -> (account.meal_balance >= amount)
            ChargeType.CASH -> (account.cash_balance >= amount)
        }
    }

    fun charge(account: Account, amount: Float, chargeType: ChargeType): Account {
        when (chargeType) {
            ChargeType.FOOD -> {
                account.food_balance -= amount
                return accountRepository.save(account)
            }
            ChargeType.MEAL -> {
                account.meal_balance -= amount
                return accountRepository.save(account)
            }
            ChargeType.CASH -> {
                account.cash_balance -= amount
                return accountRepository.save(account)
            }
        }
    }
}
