package io.github.viktsys.backendtestcaju.services

import io.github.viktsys.backendtestcaju.models.MerchantNameChargeability
import io.github.viktsys.backendtestcaju.repositories.MerchantNameChargeabilityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MerchantNameChargeabilityService {

    @Autowired
    lateinit var repository: MerchantNameChargeabilityRepository


    fun getAll(): List<MerchantNameChargeability> {
        return repository.findAll()
    }

    fun getByID(id: Long): Optional<MerchantNameChargeability> {
        return repository.findById(id)
    }

    fun getByCode(code: String): MerchantNameChargeability? {
        return repository.findByName(code)
    }

    fun getByName(name: String): MerchantNameChargeability? {
        return repository.findByName(name)
    }

    fun existsByID(id: Long): Boolean {
        return repository.existsById(id)
    }

    fun existsByName(name: String): Boolean {
        return repository.findByName(name) != null
    }

    fun create(merchantNameChargeability: MerchantNameChargeability): MerchantNameChargeability {
        if(this.existsByName(merchantNameChargeability.name)) {
            throw IllegalArgumentException("Name ${merchantNameChargeability.name} already exists")
        }
        return repository.save(merchantNameChargeability)
    }

    fun update(merchantNameChargeability: MerchantNameChargeability): MerchantNameChargeability {
        if(this.existsByID(merchantNameChargeability.id)) {
            return repository.save(merchantNameChargeability)
        }
        throw IllegalArgumentException("ID ${merchantNameChargeability.id} does not exist")
    }

    fun delete(merchantNameChargeability: MerchantNameChargeability) {
        repository.delete(merchantNameChargeability)
    }
}