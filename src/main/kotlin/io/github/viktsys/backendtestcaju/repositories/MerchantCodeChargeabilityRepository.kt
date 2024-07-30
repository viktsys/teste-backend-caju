package io.github.viktsys.backendtestcaju.repositories

import io.github.viktsys.backendtestcaju.models.MerchantCodeChargeability
import org.hibernate.annotations.processing.SQL
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantCodeChargeabilityRepository : JpaRepository<MerchantCodeChargeability, Long> {

    @SQL("SELECT * FROM merchant_code_chargeability WHERE code = ?1")
    fun findByCode(code: String): MerchantCodeChargeability?
}