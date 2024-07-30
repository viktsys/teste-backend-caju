package io.github.viktsys.backendtestcaju.controllers

import io.github.viktsys.backendtestcaju.models.MerchantNameChargeability
import io.github.viktsys.backendtestcaju.repositories.MerchantNameChargeabilityRepository
import io.github.viktsys.backendtestcaju.services.MerchantNameChargeabilityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/merchant-name")
class MerchantNameChargeabilityController {

    @Autowired
    lateinit var merchantNameChargeabilityService: MerchantNameChargeabilityService

    @GetMapping
    fun findAll(): List<MerchantNameChargeability> {
        return merchantNameChargeabilityService.getAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): MerchantNameChargeability? {
        return merchantNameChargeabilityService.getByID(id).orElse(null)
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String): MerchantNameChargeability? {
        return merchantNameChargeabilityService.getByName(name)
    }

    @PostMapping("/")
    fun create(@RequestBody merchantNameChargeability: MerchantNameChargeability): MerchantNameChargeability {
        return merchantNameChargeabilityService.create(merchantNameChargeability)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody merchantNameChargeability: MerchantNameChargeability): MerchantNameChargeability {
        return merchantNameChargeabilityService.update(merchantNameChargeability)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        merchantNameChargeabilityService.delete(merchantNameChargeabilityService.getByID(id).orElse(null)!!)
    }
}