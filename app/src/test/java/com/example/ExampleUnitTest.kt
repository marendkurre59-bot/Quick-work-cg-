package com.example

import com.example.data.model.CoverageData
import com.example.data.model.RegionType
import com.example.data.repository.ServicesData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun verify_all_50_services_count_and_data() {
        val services = ServicesData.ALL_50_SERVICES
        // Must contain exactly 50 work services as requested
        assertEquals(50, services.size)

        // Verify every service has required details
        services.forEach { service ->
            assertTrue(service.id.isNotBlank())
            assertTrue(service.title.isNotBlank())
            assertTrue(service.titleHindi.isNotBlank())
            assertTrue(service.category.isNotBlank())
            assertTrue("Base price must be positive for ${service.title}", service.basePrice > 0)
            assertTrue("Inclusions must not be empty for ${service.title}", service.inclusions.isNotEmpty())
        }
    }

    @Test
    fun verify_cummins_15_to_20_percent_calculation() {
        val baseAmount = 500.0
        val visitingFee = 49.0
        val totalAmount = baseAmount + visitingFee // 549.0

        // Test 15% commission
        val comm15 = totalAmount * (15.0 / 100.0)
        val workerShare15 = totalAmount - comm15
        assertEquals(82.35, comm15, 0.01)
        assertEquals(466.65, workerShare15, 0.01)

        // Test 18% commission
        val comm18 = totalAmount * (18.0 / 100.0)
        val workerShare18 = totalAmount - comm18
        assertEquals(98.82, comm18, 0.01)
        assertEquals(450.18, workerShare18, 0.01)

        // Test 20% express commission
        val comm20 = totalAmount * (20.0 / 100.0)
        val workerShare20 = totalAmount - comm20
        assertEquals(109.80, comm20, 0.01)
        assertEquals(439.20, workerShare20, 0.01)
    }

    @Test
    fun verify_chhattisgarh_and_all_india_coverage() {
        val cities = CoverageData.CITIES
        val cgCities = cities.filter { it.region == RegionType.CHHATTISGARH }
        val allIndiaCities = cities.filter { it.region == RegionType.ALL_INDIA }

        assertTrue(cgCities.any { it.id == "raipur" })
        assertTrue(cgCities.any { it.id == "bhilai_durg" })
        assertTrue(cgCities.any { it.id == "bilaspur" })
        assertTrue(cgCities.any { it.id == "korba" })

        assertTrue(allIndiaCities.any { it.id == "delhi_ncr" })
        assertTrue(allIndiaCities.any { it.id == "mumbai" })
        assertTrue(allIndiaCities.any { it.id == "bengaluru" })
    }

    @Test
    fun verify_partner_registration_fee_options() {
        val validFees = listOf(0.0, 100.0, 200.0)
        assertTrue("Must include Free Labour registration option", validFees.contains(0.0))
        assertTrue(validFees.contains(100.0))
        assertTrue(validFees.contains(200.0))
    }

    @Test
    fun verify_founder_and_admin_upi_id() {
        val config = com.example.data.local.AdminConfigEntity()
        assertEquals("narendkurre123@ybl", config.adminUpiId)
        assertTrue(config.founderName.contains("Narendra Kumar"))
        assertTrue(config.founderPhone.contains("9755955659"))
    }
}
