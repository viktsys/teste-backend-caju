package io.github.viktsys.backendtestcaju.controllers

import io.github.viktsys.backendtestcaju.models.MerchantCodeChargeability
import io.github.viktsys.backendtestcaju.services.MerchantCodeChargeabilityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/merchant-code")
class MerchantCodeChargeabilityController {
    @Autowired
    lateinit var merchantCodeService: MerchantCodeChargeabilityService


    @GetMapping
    fun findAll(): List<MerchantCodeChargeability> {
        return merchantCodeService.getAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): MerchantCodeChargeability? {
        return merchantCodeService.getByID(id).orElse(null)
    }

    @GetMapping("/code/{code}")
    fun findByCode(@PathVariable code: String): MerchantCodeChargeability? {
        return merchantCodeService.getByCode(code)
    }

    @PostMapping("/")
    fun create(@RequestBody merchantCodeChargeability: MerchantCodeChargeability): MerchantCodeChargeability {
        return merchantCodeService.create(merchantCodeChargeability)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody merchantCodeChargeability: MerchantCodeChargeability): MerchantCodeChargeability {
        return merchantCodeService.update(merchantCodeChargeability)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        merchantCodeService.delete(merchantCodeService.getByID(id).orElse(null)!!)
    }
}
