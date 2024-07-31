package io.github.viktsys.backendtestcaju.services

import io.github.viktsys.backendtestcaju.dtos.AccountDTO
import io.github.viktsys.backendtestcaju.models.Account
import io.github.viktsys.backendtestcaju.models.enums.ChargeType
import io.github.viktsys.backendtestcaju.repositories.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountService {

    @Autowired
    lateinit var repository: AccountRepository

    fun getAll(): List<Account> {
        return repository.findAll()
    }

    fun findByID(id: Long): Account? {
        return repository.findById(id).orElse(null)
    }

    fun create(account: Account): Account {
        return repository.save(account)
    }

    fun create(account: AccountDTO): Account {
        return this.create(
            Account(
                account.id,
                account.food_balance,
                account.meal_balance,
                account.cash_balance
            )
        )
    }

    fun update(account: Account): Account {
        return repository.save(account)
    }

    fun deleteByID(id: Long) {
        repository.deleteById(id)
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
                return repository.save(account)
            }
            ChargeType.MEAL -> {
                account.meal_balance -= amount
                return repository.save(account)
            }
            ChargeType.CASH -> {
                account.cash_balance -= amount
                return repository.save(account)
            }
        }
    }
}
