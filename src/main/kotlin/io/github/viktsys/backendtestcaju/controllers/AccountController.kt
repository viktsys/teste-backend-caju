package io.github.viktsys.backendtestcaju.controllers

import io.github.viktsys.backendtestcaju.models.Account
import io.github.viktsys.backendtestcaju.services.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}