package com.example.ui

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AdminConfigEntity
import com.example.data.local.AdminSavingEntryEntity
import com.example.data.local.AppDatabase
import com.example.data.local.BookingEntity
import com.example.data.local.PartnerEntity
import com.example.data.local.PaymentTransactionEntity
import com.example.data.model.CoverageCity
import com.example.data.model.CoverageData
import com.example.data.model.RegionType
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceItem
import com.example.data.repository.QuickWorkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

enum class AppScreen {
    HOME,
    SERVICES_50,
    BOOKINGS,
    PARTNER_PORTAL,
    ADMIN_SAVINGS
}

class QuickWorkViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val repository = QuickWorkRepository(database)

    val allBookings: StateFlow<List<BookingEntity>> = repository.allBookings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allPartners: StateFlow<List<PartnerEntity>> = repository.allPartners
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTransactions: StateFlow<List<PaymentTransactionEntity>> = repository.allTransactions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val adminLedger: StateFlow<List<AdminSavingEntryEntity>> = repository.adminLedger
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val adminConfig: StateFlow<AdminConfigEntity?> = repository.adminConfig
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // UI state
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _isHindi = MutableStateFlow(false)
    val isHindi: StateFlow<Boolean> = _isHindi.asStateFlow()

    private val _selectedCity = MutableStateFlow<CoverageCity>(CoverageData.CITIES.first())
    val selectedCity: StateFlow<CoverageCity> = _selectedCity.asStateFlow()

    private val _selectedLocality = MutableStateFlow<String>(CoverageData.CITIES.first().localities.first())
    val selectedLocality: StateFlow<String> = _selectedLocality.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryFilter = MutableStateFlow<String?>(null)
    val selectedCategoryFilter: StateFlow<String?> = _selectedCategoryFilter.asStateFlow()

    private val _selectedRegionFilter = MutableStateFlow<RegionType?>(null)
    val selectedRegionFilter: StateFlow<RegionType?> = _selectedRegionFilter.asStateFlow()

    // Active Service Detail / Booking
    private val _activeServiceForBooking = MutableStateFlow<ServiceItem?>(null)
    val activeServiceForBooking: StateFlow<ServiceItem?> = _activeServiceForBooking.asStateFlow()

    private val _serviceDetailItem = MutableStateFlow<ServiceItem?>(null)
    val serviceDetailItem: StateFlow<ServiceItem?> = _serviceDetailItem.asStateFlow()

    // UPI Payment Sheet state
    private val _showUpiPaymentSheet = MutableStateFlow(false)
    val showUpiPaymentSheet: StateFlow<Boolean> = _showUpiPaymentSheet.asStateFlow()

    private val _pendingUpiPaymentData = MutableStateFlow<UpiPaymentIntentData?>(null)
    val pendingUpiPaymentData: StateFlow<UpiPaymentIntentData?> = _pendingUpiPaymentData.asStateFlow()

    // Partner Registration Sheet
    private val _showPartnerRegistration = MutableStateFlow(false)
    val showPartnerRegistration: StateFlow<Boolean> = _showPartnerRegistration.asStateFlow()

    // Notification message banner
    private val _toastBanner = MutableStateFlow<String?>(null)
    val toastBanner: StateFlow<String?> = _toastBanner.asStateFlow()

    // Saved / Bookmarked Favorites
    private val _savedServiceIds = MutableStateFlow<Set<String>>(setOf("1", "2", "3"))
    val savedServiceIds: StateFlow<Set<String>> = _savedServiceIds.asStateFlow()

    private val _savedWorkerNames = MutableStateFlow<Set<String>>(setOf("संतोष यादव (Santosh Yadav)", "महेश कुमार देवांगन (Mahesh Dewangan)"))
    val savedWorkerNames: StateFlow<Set<String>> = _savedWorkerNames.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeDefaultDataIfNeeded()
        }
    }

    fun setScreen(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun toggleLanguage() {
        _isHindi.value = !_isHindi.value
    }

    fun selectCity(city: CoverageCity) {
        _selectedCity.value = city
        _selectedLocality.value = city.localities.firstOrNull() ?: "Main City"
    }

    fun selectLocality(locality: String) {
        _selectedLocality.value = locality
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(category: String?) {
        _selectedCategoryFilter.value = category
    }

    fun setRegionFilter(region: RegionType?) {
        _selectedRegionFilter.value = region
    }

    fun toggleSaveService(serviceId: String) {
        val current = _savedServiceIds.value.toMutableSet()
        if (current.contains(serviceId)) {
            current.remove(serviceId)
            _toastBanner.value = if (_isHindi.value) "सेवा पसंदीदा से हटाई गई" else "Service removed from favorites"
        } else {
            current.add(serviceId)
            _toastBanner.value = if (_isHindi.value) "सेवा पसंदीदा में सेव हो गई" else "Service added to favorites"
        }
        _savedServiceIds.value = current
    }

    fun toggleSaveWorker(workerName: String) {
        val current = _savedWorkerNames.value.toMutableSet()
        if (current.contains(workerName)) {
            current.remove(workerName)
            _toastBanner.value = if (_isHindi.value) "कारीगर पसंदीदा से हटाया गया" else "Worker removed from favorites"
        } else {
            current.add(workerName)
            _toastBanner.value = if (_isHindi.value) "कारीगर पसंदीदा में सेव हो गया" else "Worker saved to favorites"
        }
        _savedWorkerNames.value = current
    }

    fun openServiceDetail(service: ServiceItem) {
        _serviceDetailItem.value = service
    }

    fun closeServiceDetail() {
        _serviceDetailItem.value = null
    }

    fun initiateBooking(service: ServiceItem) {
        _activeServiceForBooking.value = service
    }

    fun dismissBooking() {
        _activeServiceForBooking.value = null
    }

    fun openPartnerRegistration() {
        _showPartnerRegistration.value = true
    }

    fun dismissPartnerRegistration() {
        _showPartnerRegistration.value = false
    }

    // Dynamic Services added by Admin
    private val _customServices = MutableStateFlow<List<ServiceItem>>(emptyList())
    val customServices: StateFlow<List<ServiceItem>> = _customServices.asStateFlow()

    fun getAllServicesList(): List<ServiceItem> {
        return repository.getAllServices() + _customServices.value
    }

    fun addNewService(title: String, titleHindi: String, category: String, price: Int) {
        val newId = "srv_custom_" + System.currentTimeMillis().toString().takeLast(6)
        val newItem = ServiceItem(
            id = newId,
            title = title,
            titleHindi = titleHindi.ifBlank { title },
            category = category,
            categoryHindi = category,
            iconKey = "handyman",
            basePrice = price,
            timeEstimate = "Standard 45-60 mins",
            description = "Custom registered department service verified by QuickWork CG Admin Narendra Kumar Kurre.",
            descriptionHindi = "त्वरित और प्रमाणित सेवा, क्विकवर्क सीजी एडमिन द्वारा सत्यापित।",
            isPopular = true,
            isEmergencyEligible = true,
            inclusions = listOf("Expert technician dispatch", "Safety guaranteed", "Original bill & warranty"),
            serialNumber = (repository.getAllServices().size + _customServices.value.size + 1)
        )
        _customServices.value = _customServices.value + newItem
        _toastBanner.value = "New service '$title' added to $category department successfully!"
    }

    fun clearBanner() {
        _toastBanner.value = null
    }

    fun getFilteredServices(): List<ServiceItem> {
        val query = _searchQuery.value.trim().lowercase()
        val cat = _selectedCategoryFilter.value
        val all = getAllServicesList()

        return all.filter { item ->
            val matchesQuery = query.isEmpty() ||
                item.title.lowercase().contains(query) ||
                item.titleHindi.lowercase().contains(query) ||
                item.description.lowercase().contains(query) ||
                item.category.lowercase().contains(query) ||
                item.inclusions.any { it.lowercase().contains(query) }

            val matchesCat = cat == null || item.category == cat

            matchesQuery && matchesCat
        }
    }

    /**
     * Submit Booking with 18% GST Tax, Gateway details, and itemization
     */
    fun confirmBooking(
        service: ServiceItem,
        customerName: String,
        customerPhone: String,
        addressText: String,
        dateSlot: String,
        timeSlot: String,
        isExpressEmergency: Boolean,
        paymentMethod: String,
        gatewayPaymentId: String = "",
        gateway: String = "RAZORPAY_TEST",
        commissionPercent: Double = 18.0
    ) {
        viewModelScope.launch {
            val booking = repository.createBooking(
                service = service,
                customerName = customerName,
                customerPhone = customerPhone,
                city = _selectedCity.value.name,
                locality = _selectedLocality.value,
                addressText = addressText,
                dateSlot = dateSlot,
                timeSlot = timeSlot,
                isEmergencyExpress = isExpressEmergency,
                paymentMethod = paymentMethod,
                gateway = gateway,
                gatewayPaymentId = gatewayPaymentId,
                customCommissionPercent = commissionPercent
            )

            _activeServiceForBooking.value = null
            _toastBanner.value = "Booking #${booking.id} confirmed! Total ₹${String.format(Locale.US, "%.2f", booking.totalAmount)} (includes 18% GST ₹${String.format(Locale.US, "%.2f", booking.gstAmount)})."
            _currentScreen.value = AppScreen.BOOKINGS
        }
    }

    /**
     * Complete Partner registration with selfie verification, masked doc, and bank/UPI payout
     */
    fun submitPartnerRegistration(
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
    ) {
        viewModelScope.launch {
            val partner = repository.registerPartner(
                name = name,
                phone = phone,
                tradeCategory = tradeCategory,
                city = city,
                locality = locality,
                pinCode = pinCode,
                district = district,
                aadhaarNumber = aadhaarNumber,
                experienceYears = experienceYears,
                feeAmount = feeAmount,
                payoutUpiOrBank = payoutUpiOrBank,
                selfieCaptured = selfieCaptured
            )
            _showPartnerRegistration.value = false
            _toastBanner.value = "Welcome ${partner.name}! Registration recorded with status ${partner.registrationStatus}."
            _currentScreen.value = AppScreen.PARTNER_PORTAL
        }
    }

    fun updatePartnerApprovalStatus(partnerId: String, status: String) {
        viewModelScope.launch {
            repository.updatePartnerApprovalStatus(partnerId, status)
            _toastBanner.value = "Worker $partnerId status updated to $status"
        }
    }

    fun processRefund(transactionId: String) {
        viewModelScope.launch {
            repository.processRefund(transactionId)
            _toastBanner.value = "Transaction $transactionId refunded successfully."
        }
    }

    fun updateBookingStatus(bookingId: String, nextStatus: String) {
        viewModelScope.launch {
            repository.updateBookingStatus(bookingId, nextStatus)
            _toastBanner.value = "Booking #$bookingId updated to $nextStatus"
        }
    }

    fun submitRating(bookingId: String, stars: Int, review: String) {
        viewModelScope.launch {
            repository.submitRating(bookingId, stars, review)
            _toastBanner.value = "Thank you! Rating saved successfully."
        }
    }

    fun updateAdminConfig(upiId: String, commissionRate: Double, regFee: Double) {
        viewModelScope.launch {
            repository.updateAdminSettings(upiId, commissionRate, regFee)
            _toastBanner.value = "Admin account settings updated! Default commission set to ${commissionRate.toInt()}%."
        }
    }

    fun launchUpiPaymentIntent(amount: Double, note: String, upiId: String) {
        val cleanNote = note.replace(Regex("[^a-zA-Z0-9 ]"), "").trim().ifBlank { "QuickWork CG" }
        val uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", "Narendra Kumar Kurre")
            .appendQueryParameter("mc", "")
            .appendQueryParameter("tr", "TXN" + System.currentTimeMillis().toString().takeLast(9))
            .appendQueryParameter("tn", cleanNote)
            .appendQueryParameter("am", String.format(Locale.US, "%.2f", amount))
            .appendQueryParameter("cu", "INR")
            .build()

        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val chooser = Intent.createChooser(intent, "Pay ₹${amount.toInt()} with UPI").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            getApplication<Application>().startActivity(chooser)
        } catch (e: Exception) {
            try {
                getApplication<Application>().startActivity(intent)
            } catch (e2: Exception) {
                _toastBanner.value = "UPI: ₹${amount.toInt()} to $upiId (ID copied to clipboard)"
            }
        }
    }

    fun openDialer(phoneNumber: String) {
        try {
            val cleanNumber = phoneNumber.replace(Regex("[^0-9+]"), "").trim()
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanNumber")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            getApplication<Application>().startActivity(dialIntent)
        } catch (e: Exception) {
            _toastBanner.value = "Calling: $phoneNumber"
        }
    }

    fun openWhatsApp(phoneNumber: String, textMessage: String) {
        try {
            val digits = phoneNumber.replace(Regex("[^0-9]"), "")
            val fullPhone = if (digits.length == 10) "91$digits" else digits
            val url = "https://api.whatsapp.com/send?phone=$fullPhone&text=${Uri.encode(textMessage)}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            getApplication<Application>().startActivity(intent)
        } catch (e: Exception) {
            _toastBanner.value = "WhatsApp: $textMessage"
        }
    }
}

data class UpiPaymentIntentData(
    val amount: Double,
    val payeeUpiId: String,
    val note: String,
    val referenceId: String
)
