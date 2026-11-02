package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey
    val id: String,
    val serviceId: String,
    val serviceTitle: String,
    val serviceTitleHindi: String,
    val category: String,
    val customerName: String,
    val customerPhone: String,
    val city: String,
    val locality: String,
    val addressText: String,
    val dateSlot: String,
    val timeSlot: String,
    val isEmergencyExpress: Boolean = false,
    val serviceBasePrice: Double,
    val visitingFee: Double = 49.0,
    val gstRate: Double = 18.0,
    val gstAmount: Double = 0.0,
    val cgstAmount: Double = 0.0,
    val sgstAmount: Double = 0.0,
    val totalAmount: Double,
    val commissionPercent: Double = 18.0,
    val commissionAmount: Double,
    val workerShareAmount: Double,
    val paymentMethod: String, // UPI_PHONEPE, UPI_GPAY, UPI_PAYTM, UPI_QR, CARD_DEBIT, CARD_CREDIT, NET_BANKING, COD
    val paymentStatus: String, // PAID_ONLINE, PENDING_CASH, REFUNDED
    val upiTxnId: String = "",
    val bookingStatus: String = "CONFIRMED", // CONFIRMED, ASSIGNED, EN_ROUTE, IN_PROGRESS, COMPLETED, CANCELLED
    val assignedPartnerName: String = "Rajesh Sharma",
    val assignedPartnerPhone: String = "+91 98261 44520",
    val assignedPartnerRating: Float = 4.9f,
    val securityOtp: String = "4921",
    val ratingStars: Int = 0,
    val reviewText: String = "",
    val invoiceNumber: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "partners")
data class PartnerEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val phone: String,
    val tradeCategory: String,
    val city: String,
    val locality: String,
    val pinCode: String = "492001",
    val district: String = "Raipur",
    val aadhaarNumber: String,
    val experienceYears: Int,
    val registrationFee: Double, // 0.0, 100.0 or 200.0
    val registrationFeeTxnId: String,
    val registrationStatus: String = "APPROVED", // PENDING, APPROVED, REJECTED
    val isOnlineAvailable: Boolean = true,
    val totalJobsCompleted: Int = 0,
    val totalEarnings: Double = 0.0,
    val rating: Float = 5.0f,
    val selfieVerified: Boolean = true,
    val documentType: String = "Aadhaar Card (Offline Masked)",
    val maskedDocumentNumber: String = "XXXX-XXXX-7182",
    val bankAccountOrUpi: String = "9755955659@upi",
    val joinedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "payment_transactions")
data class PaymentTransactionEntity(
    @PrimaryKey
    val id: String, // TXN-..., RZP-...
    val bookingId: String,
    val customerName: String,
    val customerPhone: String,
    val baseAmount: Double,
    val gstRate: Double = 18.0,
    val gstAmount: Double,
    val cgstAmount: Double,
    val sgstAmount: Double,
    val visitingFee: Double,
    val totalAmount: Double,
    val platformCommission: Double,
    val workerPayout: Double,
    val paymentMethod: String, // UPI, DEBIT_CARD, CREDIT_CARD, NET_BANKING, COD
    val gateway: String = "RAZORPAY_TEST",
    val gatewayPaymentId: String,
    val status: String, // SUCCESS, PENDING, FAILED
    val receiptNumber: String,
    val refundStatus: String = "NONE", // NONE, REQUESTED, REFUNDED
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "admin_savings_ledger")
data class AdminSavingEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String, // SERVICE_COMMISSION, PARTNER_REGISTRATION_FEE, EXPRESS_FEE, GST_TAX_COLLECTED
    val title: String,
    val referenceId: String,
    val amount: Double,
    val commissionPercent: Double = 0.0,
    val payerInfo: String,
    val recipientUpiId: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "admin_config")
data class AdminConfigEntity(
    @PrimaryKey
    val id: Int = 1,
    val adminUpiId: String = "narendkurre123@ybl",
    val adminName: String = "Narendra Kumar Kurre (Founder & Owner)",
    val founderName: String = "Narendra Kumar Kurre",
    val founderPhone: String = "+91 9755955659",
    val commissionRatePercent: Double = 18.0,
    val defaultRegistrationFee: Double = 100.0,
    val totalCommissionEarned: Double = 0.0,
    val totalRegistrationFeesEarned: Double = 0.0,
    val walletBalance: Double = 0.0
)
