package io.github.viktsys.backendtestcaju.repositories

import io.github.viktsys.backendtestcaju.models.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TransactionRepository : JpaRepository<Transaction, UUID> {
}