package io.github.viktsys.backendtestcaju.services

import io.github.viktsys.backendtestcaju.models.MerchantCodeChargeability
import io.github.viktsys.backendtestcaju.repositories.MerchantCodeChargeabilityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MerchantCodeChargeabilityService {

    @Autowired
    lateinit var repository: MerchantCodeChargeabilityRepository

    fun getAll(): List<MerchantCodeChargeability> {
        return repository.findAll()
    }

    fun getByID(id: Long): Optional<MerchantCodeChargeability> {
        return repository.findById(id)
    }

    fun getByCode(code: String): MerchantCodeChargeability? {
        return repository.findByCode(code)
    }

    fun existsByID(id: Long): Boolean {
        return repository.existsById(id)
    }

    fun existsByCode(code: String): Boolean {
        return repository.findByCode(code) != null
    }

    fun create(merchantCodeChargeability: MerchantCodeChargeability): MerchantCodeChargeability {
        if (this.existsByCode(merchantCodeChargeability.code)) {
            throw IllegalArgumentException("Code ${merchantCodeChargeability.code} already exists")
        }
        return repository.save(merchantCodeChargeability)
    }

    fun update(merchantCodeChargeability: MerchantCodeChargeability): MerchantCodeChargeability {
        if (this.existsByID(merchantCodeChargeability.id)) {
            return repository.save(merchantCodeChargeability)
        }
        throw IllegalArgumentException("ID ${merchantCodeChargeability.id} does not exist")
    }

    fun delete(merchantCodeChargeability: MerchantCodeChargeability) {
        repository.delete(merchantCodeChargeability)
    }

}