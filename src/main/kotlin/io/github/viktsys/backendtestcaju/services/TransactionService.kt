package io.github.viktsys.backendtestcaju.services

import io.github.viktsys.backendtestcaju.dtos.TransactionDTO
import io.github.viktsys.backendtestcaju.models.Transaction
import io.github.viktsys.backendtestcaju.models.enums.ChargeType
import io.github.viktsys.backendtestcaju.models.enums.TransactionStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import io.github.viktsys.backendtestcaju.repositories.TransactionRepository
import jakarta.transaction.Transactional
import java.util.Optional
import java.util.UUID


@Service
class TransactionService {

    /* Repository for Transactions */

    @Autowired
    lateinit var repository: TransactionRepository

    /* Service for Merchant Codes and Merchant Names */

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var merchantCodeService: MerchantCodeChargeabilityService

    @Autowired
    lateinit var merchantNameService: MerchantNameChargeabilityService

    fun getAll(): List<Transaction> {
        return repository.findAll()
    }

    fun findByID(id: UUID): Optional<Transaction> {
        return repository.findById(id)
    }

    fun create(transaction: Transaction): Transaction {
        return repository.save(transaction)
    }

    fun update(transaction: Transaction): Transaction {
        return repository.save(transaction)
    }

    fun deleteByID(id: UUID) {
        repository.deleteById(id)
    }


    @Transactional
    fun handleTransaction(data: TransactionDTO): TransactionStatus {
        val account = accountService.findByID(data.account)
        val merchantNameChargeability = merchantNameService.getByName(data.merchant)
        val merchantCodeChargeability = merchantCodeService.getByCode(data.mcc)

        val transaction = Transaction()
        transaction.account = account
        transaction.merchant = data.merchant
        transaction.mcc = data.mcc
        transaction.amount = data.totalAmount

        // If the account is not found, reject the transaction
        if(account == null) {
            transaction.status = TransactionStatus.REJECTED_BY_UNKNOWN_ERROR
            transaction.chargeType = null
            transaction.account = null
            repository.save(transaction)
            return TransactionStatus.REJECTED_BY_UNKNOWN_ERROR
        }

        // Define the ChargeType based on the Merchant Name or Merchant Code
        // Check if the account is chargeable based on type of balance to be used
        // By business logic, always fallback on CASH balance if the other balances are insufficient
        var chargeType: ChargeType?
        var isChargeable: Boolean?

        if(merchantNameChargeability != null) {
            chargeType = merchantNameChargeability.chargeType
            isChargeable = accountService.isChargeable(account, data.totalAmount, chargeType)
        }
        else if(merchantCodeChargeability != null) {
            chargeType = merchantCodeChargeability.chargeType
            isChargeable = accountService.isChargeable(account, data.totalAmount, chargeType)
        }
        else {
            chargeType = ChargeType.CASH
            isChargeable = accountService.isChargeable(account, data.totalAmount, chargeType)
        }

        if(isChargeable) {
            accountService.charge(account, data.totalAmount, chargeType)
            transaction.status = TransactionStatus.ACCEPTED
            transaction.chargeType = chargeType
            repository.save(transaction)
            return TransactionStatus.ACCEPTED
        }
        else {
            val isChargeableCash = accountService.isChargeable(account, data.totalAmount, ChargeType.CASH)
            if (isChargeableCash) {
                accountService.charge(account, data.totalAmount, ChargeType.CASH)
                transaction.status = TransactionStatus.ACCEPTED
                transaction.chargeType = ChargeType.CASH
                repository.save(transaction)
                return TransactionStatus.ACCEPTED
            } else {
                transaction.status = TransactionStatus.REJECTED_BY_INSUFFICIENT_BALANCE
                transaction.chargeType = chargeType
                repository.save(transaction)
                return TransactionStatus.REJECTED_BY_INSUFFICIENT_BALANCE
            }
        }
    }
}