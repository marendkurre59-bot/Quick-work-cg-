package com.example.data.repository

import com.example.data.local.AdminConfigEntity
import com.example.data.local.AdminDao
import com.example.data.local.AdminSavingEntryEntity
import com.example.data.local.AppDatabase
import com.example.data.local.BookingDao
import com.example.data.local.BookingEntity
import com.example.data.local.PartnerDao
import com.example.data.local.PartnerEntity
import com.example.data.local.PaymentDao
import com.example.data.local.PaymentTransactionEntity
import com.example.data.model.CoverageCity
import com.example.data.model.CoverageData
import com.example.data.model.ServiceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlin.random.Random

class QuickWorkRepository(
    private val database: AppDatabase
) {
    private val bookingDao: BookingDao = database.bookingDao()
    private val partnerDao: PartnerDao = database.partnerDao()
    private val paymentDao: PaymentDao = database.paymentDao()
    private val adminDao: AdminDao = database.adminDao()

    val allBookings: Flow<List<BookingEntity>> = bookingDao.getAllBookings()
    val allPartners: Flow<List<PartnerEntity>> = partnerDao.getAllPartners()
    val allTransactions: Flow<List<PaymentTransactionEntity>> = paymentDao.getAllTransactions()
    val adminLedger: Flow<List<AdminSavingEntryEntity>> = adminDao.getLedgerEntries()
    val adminConfig: Flow<AdminConfigEntity?> = adminDao.getAdminConfig()

    fun getAllServices(): List<ServiceItem> = ServicesData.ALL_50_SERVICES

    fun getServiceById(id: String): ServiceItem? {
        return ServicesData.ALL_50_SERVICES.find { it.id == id }
    }

    fun getAllCities(): List<CoverageCity> = CoverageData.CITIES

    suspend fun initializeDefaultDataIfNeeded() {
        val config = adminDao.getAdminConfig().firstOrNull()
        if (config == null) {
            val initialConfig = AdminConfigEntity(
                id = 1,
                adminUpiId = "narendkurre123@ybl",
                adminName = "Narendra Kumar Kurre (Founder & Owner)",
                founderName = "Narendra Kumar Kurre",
                founderPhone = "+91 9755955659",
                commissionRatePercent = 18.0,
                defaultRegistrationFee = 100.0,
                totalCommissionEarned = 153.0,
                totalRegistrationFeesEarned = 300.0,
                walletBalance = 453.0
            )
            adminDao.setAdminConfig(initialConfig)
        } else if (config.adminUpiId != "narendkurre123@ybl") {
            adminDao.updateAdminSettings("narendkurre123@ybl", config.commissionRatePercent, config.defaultRegistrationFee)
        }

        // Seed initial verified partners from Chhattisgarh
        val p1 = PartnerEntity(
            id = "PT-CG-101",
            name = "Rameshwar Sahu",
            phone = "+91 98271 55601",
            tradeCategory = "Electrical & Power",
            city = "Raipur",
            locality = "Shankar Nagar",
            pinCode = "492007",
            district = "Raipur",
            aadhaarNumber = "XXXX-XXXX-7182",
            experienceYears = 7,
            registrationFee = 200.0,
            registrationFeeTxnId = "UPI-REG-84920",
            registrationStatus = "APPROVED",
            isOnlineAvailable = true,
            totalJobsCompleted = 142,
            totalEarnings = 38450.0,
            rating = 4.9f,
            selfieVerified = true,
            documentType = "Aadhaar Card (Offline Masked)",
            maskedDocumentNumber = "XXXX-XXXX-7182",
            bankAccountOrUpi = "9827155601@upi"
        )
        val p2 = PartnerEntity(
            id = "PT-CG-102",
            name = "Dinesh Dewangan",
            phone = "+91 94252 66314",
            tradeCategory = "Plumbing & Water",
            city = "Bhilai - Durg",
            locality = "Supela",
            pinCode = "490023",
            district = "Durg",
            aadhaarNumber = "XXXX-XXXX-9941",
            experienceYears = 5,
            registrationFee = 100.0,
            registrationFeeTxnId = "UPI-REG-84921",
            registrationStatus = "APPROVED",
            isOnlineAvailable = true,
            totalJobsCompleted = 98,
            totalEarnings = 26500.0,
            rating = 4.8f,
            selfieVerified = true,
            documentType = "Aadhaar Card (Offline Masked)",
            maskedDocumentNumber = "XXXX-XXXX-9941",
            bankAccountOrUpi = "9425266314@paytm"
        )
        val p3 = PartnerEntity(
            id = "PT-CG-103",
            name = "Bhupendra Baghel",
            phone = "+91 91312 44781",
            tradeCategory = "Painting",
            city = "Bilaspur",
            locality = "Vyapar Vihar",
            pinCode = "495001",
            district = "Bilaspur",
            aadhaarNumber = "XXXX-XXXX-3329",
            experienceYears = 6,
            registrationFee = 0.0,
            registrationFeeTxnId = "FREE-REG-332910",
            registrationStatus = "PENDING",
            isOnlineAvailable = false,
            totalJobsCompleted = 0,
            totalEarnings = 0.0,
            rating = 5.0f,
            selfieVerified = true,
            documentType = "Driving Licence (Masked)",
            maskedDocumentNumber = "CG-10-XXXX-3329",
            bankAccountOrUpi = "bhupen9131@axl"
        )
        partnerDao.insertPartner(p1)
        partnerDao.insertPartner(p2)
        partnerDao.insertPartner(p3)

        // Initial seed sample booking with 18% GST itemized
        val booking1 = BookingEntity(
            id = "QW-CG-501",
            serviceId = "ac_jet",
            serviceTitle = "Split AC Foam Jet Deep Service",
            serviceTitleHindi = "स्प्लिट एसी फोम जेट डीप सर्विस",
            category = "AC & Refrigeration",
            customerName = "Aakash Verma",
            customerPhone = "+91 91114 88320",
            city = "Raipur",
            locality = "Telibandha",
            addressText = "Flat 402, Green Valley Enclave, Marine Drive Road",
            dateSlot = "Today",
            timeSlot = "11:00 AM - 1:00 PM",
            isEmergencyExpress = false,
            serviceBasePrice = 499.0,
            visitingFee = 49.0,
            gstRate = 18.0,
            gstAmount = 89.82,
            cgstAmount = 44.91,
            sgstAmount = 44.91,
            totalAmount = 637.82,
            commissionPercent = 18.0,
            commissionAmount = 89.82,
            workerShareAmount = 458.18,
            paymentMethod = "UPI_PHONEPE",
            paymentStatus = "PAID_ONLINE",
            upiTxnId = "UPI9283716491",
            bookingStatus = "EN_ROUTE",
            assignedPartnerName = "Rameshwar Sahu",
            assignedPartnerPhone = "+91 98271 55601",
            assignedPartnerRating = 4.9f,
            securityOtp = "5821",
            invoiceNumber = "QW-INV-2026-0501"
        )
        bookingDao.insertBooking(booking1)

        // Seed initial payment transaction
        val tx1 = PaymentTransactionEntity(
            id = "RZP_TXN_9283716491",
            bookingId = "QW-CG-501",
            customerName = "Aakash Verma",
            customerPhone = "+91 91114 88320",
            baseAmount = 499.0,
            gstRate = 18.0,
            gstAmount = 89.82,
            cgstAmount = 44.91,
            sgstAmount = 44.91,
            visitingFee = 49.0,
            totalAmount = 637.82,
            platformCommission = 89.82,
            workerPayout = 458.18,
            paymentMethod = "UPI",
            gateway = "RAZORPAY_TEST",
            gatewayPaymentId = "pay_N4z1a8pQ92837",
            status = "SUCCESS",
            receiptNumber = "QW-INV-2026-0501",
            refundStatus = "NONE",
            timestamp = System.currentTimeMillis() - 3600000L
        )
        paymentDao.insertTransaction(tx1)

        adminDao.insertLedgerEntry(
            AdminSavingEntryEntity(
                type = "SERVICE_COMMISSION",
                title = "18% Platform Commission on Booking #QW-CG-501",
                referenceId = "QW-CG-501",
                amount = 89.82,
                commissionPercent = 18.0,
                payerInfo = "Aakash Verma (Split AC Service)",
                recipientUpiId = "narendkurre123@ybl"
            )
        )
        adminDao.insertLedgerEntry(
            AdminSavingEntryEntity(
                type = "GST_TAX_COLLECTED",
                title = "GST 18% (CGST 9% + SGST 9%) - Booking #QW-CG-501",
                referenceId = "QW-CG-501",
                amount = 89.82,
                commissionPercent = 18.0,
                payerInfo = "Aakash Verma (Tax Ledger)",
                recipientUpiId = "narendkurre123@ybl"
            )
        )
    }

    /**
     * Creates a customer booking with full 18% GST Tax calculation,
     * platform commission, and records the transaction in the database.
     */
    suspend fun createBooking(
        service: ServiceItem,
        customerName: String,
        customerPhone: String,
        city: String,
        locality: String,
        addressText: String,
        dateSlot: String,
        timeSlot: String,
        isEmergencyExpress: Boolean,
        paymentMethod: String,
        gateway: String = "RAZORPAY_TEST",
        gatewayPaymentId: String = "",
        customCommissionPercent: Double? = null
    ): BookingEntity {
        val config = adminDao.getAdminConfig().firstOrNull()
        val commissionRate = customCommissionPercent 
            ?: if (isEmergencyExpress) 20.0 else (config?.commissionRatePercent ?: 18.0)

        val basePrice = service.basePrice.toDouble()
        val visitingFee = if (isEmergencyExpress) 99.0 else 49.0
        
        // 18% GST (CGST 9% + SGST 9%) on base service fee
        val gstRate = 18.0
        val gstAmount = Math.round((basePrice * 0.18) * 100.0) / 100.0
        val cgstAmount = Math.round((gstAmount / 2.0) * 100.0) / 100.0
        val sgstAmount = gstAmount - cgstAmount

        val totalAmount = Math.round((basePrice + gstAmount + visitingFee) * 100.0) / 100.0
        val commissionAmount = Math.round((basePrice * (commissionRate / 100.0)) * 100.0) / 100.0
        val workerShare = Math.round((basePrice - commissionAmount + visitingFee) * 100.0) / 100.0

        val randomId = "QW-" + Random.nextInt(10000, 99999)
        val otp = Random.nextInt(1000, 9999).toString()
        val invoiceNumber = "QW-INV-2026-" + randomId.removePrefix("QW-")

        val isOnlinePayment = paymentMethod != "COD"
        val txnId = if (gatewayPaymentId.isNotBlank()) gatewayPaymentId 
                    else "TXN_" + System.currentTimeMillis().toString().takeLast(9)

        val newBooking = BookingEntity(
            id = randomId,
            serviceId = service.id,
            serviceTitle = service.title,
            serviceTitleHindi = service.titleHindi,
            category = service.category,
            customerName = customerName,
            customerPhone = customerPhone,
            city = city,
            locality = locality,
            addressText = addressText,
            dateSlot = dateSlot,
            timeSlot = timeSlot,
            isEmergencyExpress = isEmergencyExpress,
            serviceBasePrice = basePrice,
            visitingFee = visitingFee,
            gstRate = gstRate,
            gstAmount = gstAmount,
            cgstAmount = cgstAmount,
            sgstAmount = sgstAmount,
            totalAmount = totalAmount,
            commissionPercent = commissionRate,
            commissionAmount = commissionAmount,
            workerShareAmount = workerShare,
            paymentMethod = paymentMethod,
            paymentStatus = if (isOnlinePayment) "PAID_ONLINE" else "PENDING_CASH",
            upiTxnId = txnId,
            bookingStatus = "CONFIRMED",
            assignedPartnerName = if (city.contains("Raipur", ignoreCase = true)) "Rameshwar Sahu" else "Dinesh Dewangan",
            assignedPartnerPhone = "+91 98271 55601",
            assignedPartnerRating = 4.9f,
            securityOtp = otp,
            invoiceNumber = invoiceNumber,
            createdAt = System.currentTimeMillis()
        )

        bookingDao.insertBooking(newBooking)

        // Record Payment Transaction Entity
        val transaction = PaymentTransactionEntity(
            id = txnId,
            bookingId = randomId,
            customerName = customerName,
            customerPhone = customerPhone,
            baseAmount = basePrice,
            gstRate = gstRate,
            gstAmount = gstAmount,
            cgstAmount = cgstAmount,
            sgstAmount = sgstAmount,
            visitingFee = visitingFee,
            totalAmount = totalAmount,
            platformCommission = commissionAmount,
            workerPayout = workerShare,
            paymentMethod = paymentMethod,
            gateway = gateway,
            gatewayPaymentId = txnId,
            status = if (isOnlinePayment) "SUCCESS" else "PENDING",
            receiptNumber = invoiceNumber,
            refundStatus = "NONE",
            timestamp = System.currentTimeMillis()
        )
        paymentDao.insertTransaction(transaction)

        // Automatically save commission earnings into Admin Savings Ledger
        val adminUpi = config?.adminUpiId ?: "narendkurre123@ybl"
        val ledgerEntry = AdminSavingEntryEntity(
            type = "SERVICE_COMMISSION",
            title = "Platform Commission (${String.format("%.0f", commissionRate)}%) - ${service.title}",
            referenceId = randomId,
            amount = commissionAmount,
            commissionPercent = commissionRate,
            payerInfo = "$customerName ($city)",
            recipientUpiId = adminUpi,
            timestamp = System.currentTimeMillis()
        )
        adminDao.insertLedgerEntry(ledgerEntry)

        // Record GST collected in tax tracking
        val gstLedgerEntry = AdminSavingEntryEntity(
            type = "GST_TAX_COLLECTED",
            title = "GST 18% Collected (CGST ₹${cgstAmount} + SGST ₹${sgstAmount}) - ${service.title}",
            referenceId = randomId,
            amount = gstAmount,
            commissionPercent = 18.0,
            payerInfo = "$customerName (GST Ledger)",
            recipientUpiId = adminUpi,
            timestamp = System.currentTimeMillis()
        )
        adminDao.insertLedgerEntry(gstLedgerEntry)

        return newBooking
    }

    /**
     * Registers a worker/partner with selfie verification, masked identity doc,
     * and bank/UPI payout details.
     */
    suspend fun registerPartner(
        name: String,
        phone: String,
        tradeCategory: String,
        city: String,
        locality: String,
        pinCode: String = "492001",
        district: String = "Raipur",
        aadhaarNumber: String,
        experienceYears: Int,
        feeAmount: Double,
        payoutUpiOrBank: String = "",
        selfieCaptured: Boolean = true
    ): PartnerEntity {
        val config = adminDao.getAdminConfig().firstOrNull()
        val adminUpi = config?.adminUpiId ?: "narendkurre123@ybl"
        val isFree = feeAmount <= 0.0
        val partnerId = "PT-" + Random.nextInt(1000, 9999)
        val txnId = if (isFree) "FREE-REG-${System.currentTimeMillis().toString().takeLast(6)}"
                    else "UPI-REG-${System.currentTimeMillis().toString().takeLast(6)}"

        // Mask document number securely
        val cleanAadhaar = aadhaarNumber.replace(Regex("[^0-9]"), "")
        val masked = if (cleanAadhaar.length >= 4) {
            "XXXX-XXXX-" + cleanAadhaar.takeLast(4)
        } else {
            "XXXX-XXXX-8921"
        }

        val partner = PartnerEntity(
            id = partnerId,
            name = name,
            phone = phone,
            tradeCategory = tradeCategory,
            city = city,
            locality = locality,
            pinCode = pinCode.ifBlank { "492001" },
            district = district.ifBlank { city },
            aadhaarNumber = masked,
            experienceYears = experienceYears,
            registrationFee = feeAmount,
            registrationFeeTxnId = txnId,
            registrationStatus = if (isFree) "PENDING" else "APPROVED",
            isOnlineAvailable = true,
            totalJobsCompleted = 0,
            totalEarnings = 0.0,
            rating = 5.0f,
            selfieVerified = selfieCaptured,
            documentType = "Aadhaar Card (Offline Masked)",
            maskedDocumentNumber = masked,
            bankAccountOrUpi = payoutUpiOrBank.ifBlank { "$phone@upi" },
            joinedAt = System.currentTimeMillis()
        )

        partnerDao.insertPartner(partner)

        // Save registration status into Admin Ledger
        val ledgerEntry = AdminSavingEntryEntity(
            type = if (isFree) "FREE_WORKER_REGISTRATION" else "PARTNER_REGISTRATION_FEE",
            title = if (isFree) "मुफ्त लेबर रजिस्ट्रेशन (Free Labour Registration) - $name" 
                    else "₹${feeAmount.toInt()} Partner Registration Fee - $name",
            referenceId = txnId,
            amount = feeAmount,
            commissionPercent = if (isFree) 0.0 else 100.0,
            payerInfo = "$name ($tradeCategory - $city)",
            recipientUpiId = adminUpi,
            timestamp = System.currentTimeMillis()
        )
        adminDao.insertLedgerEntry(ledgerEntry)

        return partner
    }

    suspend fun updatePartnerApprovalStatus(partnerId: String, status: String) {
        partnerDao.updatePartnerStatus(partnerId, status)
    }

    suspend fun processRefund(transactionId: String) {
        paymentDao.updateTransactionStatus(transactionId, "REFUNDED", "REFUNDED")
    }

    suspend fun updateBookingStatus(bookingId: String, newStatus: String) {
        bookingDao.updateBookingStatus(bookingId, newStatus)
    }

    suspend fun submitRating(bookingId: String, stars: Int, review: String) {
        bookingDao.submitRating(bookingId, stars, review)
    }

    suspend fun updateAdminSettings(upiId: String, commissionRate: Double, regFee: Double) {
        adminDao.updateAdminSettings(upiId, commissionRate, regFee)
    }
}
