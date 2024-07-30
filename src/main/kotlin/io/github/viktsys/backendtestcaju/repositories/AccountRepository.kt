package io.github.viktsys.backendtestcaju.repositories

import io.github.viktsys.backendtestcaju.models.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
}