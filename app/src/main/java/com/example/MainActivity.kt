package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppScreen
import com.example.ui.QuickWorkViewModel
import com.example.ui.components.AdminPinDialog
import com.example.ui.components.BottomNavBar
import com.example.ui.components.BookingFlowDialog
import com.example.ui.components.CustomerProfileDialog
import com.example.ui.components.LegalPoliciesDialog
import com.example.ui.components.ServiceDetailDialog
import com.example.ui.components.TopHeaderBar
import com.example.ui.screens.AdminSavingsScreen
import com.example.ui.screens.BookingsListScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PartnerPortalScreen
import com.example.ui.screens.PartnerRegistrationModal
import com.example.ui.screens.ServicesCatalogScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SaffronPrimary
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                QuickWorkApp()
            }
        }
    }
}

@Composable
fun QuickWorkApp(viewModel: QuickWorkViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val isHindi by viewModel.isHindi.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()
    val selectedLocality by viewModel.selectedLocality.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategoryFilter by viewModel.selectedCategoryFilter.collectAsState()

    val allBookings by viewModel.allBookings.collectAsState()
    val allPartners by viewModel.allPartners.collectAsState()
    val allTransactions by viewModel.allTransactions.collectAsState()
    val adminLedger by viewModel.adminLedger.collectAsState()
    val adminConfig by viewModel.adminConfig.collectAsState()

    val activeServiceForBooking by viewModel.activeServiceForBooking.collectAsState()
    val serviceDetailItem by viewModel.serviceDetailItem.collectAsState()
    val showPartnerRegistration by viewModel.showPartnerRegistration.collectAsState()
    val toastBanner by viewModel.toastBanner.collectAsState()

    var isAdminUnlocked by remember { mutableStateOf(false) }
    var showAdminPinDialog by remember { mutableStateOf(false) }
    var showCustomerProfile by remember { mutableStateOf(false) }
    var showLegalPoliciesModal by remember { mutableStateOf(false) }

    // Handle back button to return to home if on secondary tab
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        viewModel.setScreen(AppScreen.HOME)
    }

    // Auto-dismiss toast banner after 3.5 seconds
    LaunchedEffect(toastBanner) {
        if (toastBanner != null) {
            delay(3500)
            viewModel.clearBanner()
        }
    }

    val adminUpiId = adminConfig?.adminUpiId ?: "narendkurre123@ybl"
    val defaultCommissionRate = adminConfig?.commissionRatePercent ?: 18.0

    Scaffold(
        topBar = {
            TopHeaderBar(
                selectedCity = selectedCity,
                selectedLocality = selectedLocality,
                isHindi = isHindi,
                onCitySelected = { viewModel.selectCity(it) },
                onLocalitySelected = { viewModel.selectLocality(it) },
                onToggleLanguage = { viewModel.toggleLanguage() },
                onOpenProfile = { showCustomerProfile = true }
            )
        },
        bottomBar = {
            BottomNavBar(
                currentScreen = currentScreen,
                isHindi = isHindi,
                bookingsCount = allBookings.size,
                onTabSelected = { tab ->
                    if (tab == AppScreen.ADMIN_SAVINGS && !isAdminUnlocked) {
                        showAdminPinDialog = true
                    } else {
                        viewModel.setScreen(tab)
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main Content Screens
            when (currentScreen) {
                AppScreen.HOME -> {
                    HomeScreen(
                        selectedCity = selectedCity,
                        selectedLocality = selectedLocality,
                        isHindi = isHindi,
                        popularServices = viewModel.getFilteredServices(),
                        onServiceSelected = { viewModel.openServiceDetail(it) },
                        onBookServiceClicked = { viewModel.initiateBooking(it) },
                        onViewAllServicesClicked = { viewModel.setScreen(AppScreen.SERVICES_50) },
                        onOpenPartnerRegistration = { viewModel.openPartnerRegistration() },
                        onSearchChanged = { viewModel.setSearchQuery(it) },
                        searchQuery = searchQuery
                    )
                }
                AppScreen.SERVICES_50 -> {
                    ServicesCatalogScreen(
                        services = viewModel.getFilteredServices(),
                        selectedCategory = selectedCategoryFilter,
                        searchQuery = searchQuery,
                        selectedCity = selectedCity,
                        isHindi = isHindi,
                        onCategorySelected = { viewModel.setCategoryFilter(it) },
                        onSearchChanged = { viewModel.setSearchQuery(it) },
                        onServiceClicked = { viewModel.openServiceDetail(it) },
                        onBookClicked = { viewModel.initiateBooking(it) }
                    )
                }
                AppScreen.BOOKINGS -> {
                    BookingsListScreen(
                        bookings = allBookings,
                        isHindi = isHindi,
                        onCallTechnician = { phone -> viewModel.openDialer(phone) },
                        onWhatsAppTechnician = { phone, msg -> viewModel.openWhatsApp(phone, msg) },
                        onAdvanceStatus = { id, nextStatus -> viewModel.updateBookingStatus(id, nextStatus) },
                        onRateBooking = { id, stars, review -> viewModel.submitRating(id, stars, review) }
                    )
                }
                AppScreen.PARTNER_PORTAL -> {
                    PartnerPortalScreen(
                        partners = allPartners,
                        isHindi = isHindi,
                        adminUpiId = adminUpiId,
                        onRegisterPartner = { name, phone, trade, city, locality, pinCode, district, aadhaar, exp, fee, upi, selfie ->
                            viewModel.submitPartnerRegistration(
                                name, phone, trade, city, locality, pinCode, district,
                                aadhaar, exp, fee, upi, selfie
                            )
                        }
                    )
                }
                AppScreen.ADMIN_SAVINGS -> {
                    AdminSavingsScreen(
                        adminConfig = adminConfig,
                        ledgerEntries = adminLedger,
                        allServices = viewModel.getAllServicesList(),
                        allTransactions = allTransactions,
                        allPartners = allPartners,
                        isHindi = isHindi,
                        onUpdateAdminConfig = { upi, comm, regFee ->
                            viewModel.updateAdminConfig(upi, comm, regFee)
                        },
                        onAddNewService = { title, titleHindi, category, price ->
                            viewModel.addNewService(title, titleHindi, category, price)
                        },
                        onLaunchUpiPayment = { amount, note, upiId ->
                            viewModel.launchUpiPaymentIntent(amount, note, upiId)
                        },
                        onUpdatePartnerStatus = { partnerId, status ->
                            viewModel.updatePartnerApprovalStatus(partnerId, status)
                        },
                        onProcessRefund = { txId ->
                            viewModel.processRefund(txId)
                        }
                    )
                }
            }

            // Notification message banner
            AnimatedVisibility(
                visible = toastBanner != null,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            ) {
                toastBanner?.let { message ->
                    Surface(
                        color = Color(0xFF0F172A),
                        shape = MaterialTheme.shapes.medium,
                        shadowElevation = 6.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.clearBanner() }
                    ) {
                        Text(
                            text = message,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                        )
                    }
                }
            }
        }
    }

    // Booking Flow Dialog
    activeServiceForBooking?.let { service ->
        BookingFlowDialog(
            service = service,
            cityName = selectedCity.name,
            localityName = selectedLocality,
            isHindi = isHindi,
            adminUpiId = adminUpiId,
            defaultCommissionRate = defaultCommissionRate,
            onConfirmBooking = { name, phone, address, date, time, isExpress, payMethod, gatewayTxnId, gateway, commissionPercent ->
                viewModel.confirmBooking(
                    service = service,
                    customerName = name,
                    customerPhone = phone,
                    addressText = address,
                    dateSlot = date,
                    timeSlot = time,
                    isExpressEmergency = isExpress,
                    paymentMethod = payMethod,
                    gatewayPaymentId = gatewayTxnId,
                    gateway = gateway,
                    commissionPercent = commissionPercent
                )
            },
            onDismiss = { viewModel.dismissBooking() }
        )
    }

    // Service Detail Dialog
    serviceDetailItem?.let { service ->
        ServiceDetailDialog(
            service = service,
            isHindi = isHindi,
            onBookClicked = {
                viewModel.closeServiceDetail()
                viewModel.initiateBooking(service)
            },
            onDismiss = { viewModel.closeServiceDetail() }
        )
    }

    // Partner Registration Modal
    if (showPartnerRegistration) {
        PartnerRegistrationModal(
            adminUpiId = adminUpiId,
            isHindi = isHindi,
            onDismiss = { viewModel.dismissPartnerRegistration() },
            onSubmit = { name, phone, trade, city, locality, pinCode, district, aadhaar, exp, fee, upi, selfie ->
                viewModel.submitPartnerRegistration(
                    name, phone, trade, city, locality, pinCode, district,
                    aadhaar, exp, fee, upi, selfie
                )
            }
        )
    }

    // Admin Security PIN Dialog
    if (showAdminPinDialog) {
        AdminPinDialog(
            isHindi = isHindi,
            onPinSuccess = {
                isAdminUnlocked = true
                showAdminPinDialog = false
                viewModel.setScreen(AppScreen.ADMIN_SAVINGS)
            },
            onDismiss = { showAdminPinDialog = false }
        )
    }

    // Customer Profile & Settings Dialog
    if (showCustomerProfile) {
        CustomerProfileDialog(
            initialName = "Narendra Kumar Kurre",
            initialPhone = "9755955659",
            initialEmail = "Marendkurre59@gmail.com",
            city = selectedCity.name,
            locality = selectedLocality,
            isHindi = isHindi,
            onDismiss = { showCustomerProfile = false },
            onOpenPolicies = {
                showCustomerProfile = false
                showLegalPoliciesModal = true
            }
        )
    }

    // Legal Policies Dialog
    if (showLegalPoliciesModal) {
        LegalPoliciesDialog(
            initialTab = 0,
            isHindi = isHindi,
            onDismiss = { showLegalPoliciesModal = false }
        )
    }
}
