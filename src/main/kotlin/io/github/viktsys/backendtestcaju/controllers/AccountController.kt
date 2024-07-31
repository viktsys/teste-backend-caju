package io.github.viktsys.backendtestcaju.controllers

import io.github.viktsys.backendtestcaju.dtos.AccountDTO
import io.github.viktsys.backendtestcaju.models.Account
import io.github.viktsys.backendtestcaju.services.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account")
class AccountController {

    @Autowired
    lateinit var accountService: AccountService

    @GetMapping("/")
    fun findAll(): List<Account> {
        return accountService.getAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): Account? {
        return accountService.findByID(id)
    }

    @PostMapping
    fun create(@RequestBody account: AccountDTO): Account {
        return accountService.create(account)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        accountService.deleteByID(id)
    }

}