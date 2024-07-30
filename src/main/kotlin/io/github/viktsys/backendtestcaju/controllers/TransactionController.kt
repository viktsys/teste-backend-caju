package io.github.viktsys.backendtestcaju.controllers

import io.github.viktsys.backendtestcaju.dtos.TransactionDTO
import io.github.viktsys.backendtestcaju.models.Transaction
import io.github.viktsys.backendtestcaju.models.enums.TransactionStatus
import io.github.viktsys.backendtestcaju.services.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transaction")
class TransactionController {

    @Autowired
    lateinit var transactionService: TransactionService

    @GetMapping
    fun findAll(): List<Transaction> {
        return transactionService.getAll()
    }

    @PostMapping("/authorize")
    fun authorize(@Validated @RequestBody transaction: TransactionDTO): HashMap<String, Any> {
        val response = HashMap<String, Any>()
        val status = transactionService.handleTransaction(transaction)
        response["code"] = status.code
        return response
    }
}