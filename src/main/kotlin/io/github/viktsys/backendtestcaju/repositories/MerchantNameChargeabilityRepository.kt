package io.github.viktsys.backendtestcaju.repositories

import io.github.viktsys.backendtestcaju.models.MerchantNameChargeability
import org.hibernate.annotations.processing.SQL
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantNameChargeabilityRepository : JpaRepository<MerchantNameChargeability, Long> {

    @SQL("SELECT * FROM merchant_name_chargeability WHERE name = '%?1%'")
    fun findByName(name: String): MerchantNameChargeability?
}