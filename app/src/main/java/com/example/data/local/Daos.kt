package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY createdAt DESC")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Query("SELECT * FROM bookings WHERE id = :id LIMIT 1")
    suspend fun getBookingById(id: String): BookingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity)

    @Update
    suspend fun updateBooking(booking: BookingEntity)

    @Query("UPDATE bookings SET bookingStatus = :status WHERE id = :id")
    suspend fun updateBookingStatus(id: String, status: String)

    @Query("UPDATE bookings SET ratingStars = :rating, reviewText = :review WHERE id = :id")
    suspend fun submitRating(id: String, rating: Int, review: String)
}

@Dao
interface PartnerDao {
    @Query("SELECT * FROM partners ORDER BY joinedAt DESC")
    fun getAllPartners(): Flow<List<PartnerEntity>>

    @Query("SELECT * FROM partners WHERE registrationStatus = 'APPROVED' ORDER BY joinedAt DESC")
    fun getApprovedPartners(): Flow<List<PartnerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPartner(partner: PartnerEntity)

    @Query("SELECT COUNT(*) FROM partners")
    suspend fun getPartnerCount(): Int

    @Query("UPDATE partners SET isOnlineAvailable = :isOnline WHERE id = :id")
    suspend fun updateOnlineStatus(id: String, isOnline: Boolean)

    @Query("UPDATE partners SET registrationStatus = :status WHERE id = :id")
    suspend fun updatePartnerStatus(id: String, status: String)
}

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payment_transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<PaymentTransactionEntity>>

    @Query("SELECT * FROM payment_transactions WHERE id = :id LIMIT 1")
    suspend fun getTransactionById(id: String): PaymentTransactionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: PaymentTransactionEntity)

    @Query("UPDATE payment_transactions SET status = :status, refundStatus = :refundStatus WHERE id = :id")
    suspend fun updateTransactionStatus(id: String, status: String, refundStatus: String)
}

@Dao
interface AdminDao {
    @Query("SELECT * FROM admin_savings_ledger ORDER BY timestamp DESC")
    fun getLedgerEntries(): Flow<List<AdminSavingEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLedgerEntry(entry: AdminSavingEntryEntity)

    @Query("SELECT * FROM admin_config WHERE id = 1 LIMIT 1")
    fun getAdminConfig(): Flow<AdminConfigEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAdminConfig(config: AdminConfigEntity)

    @Query("UPDATE admin_config SET adminUpiId = :upiId, commissionRatePercent = :commission, defaultRegistrationFee = :fee WHERE id = 1")
    suspend fun updateAdminSettings(upiId: String, commission: Double, fee: Double)
}
