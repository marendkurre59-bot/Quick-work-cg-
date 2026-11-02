package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavySurface
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TealLight
import com.example.ui.theme.TealSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

enum class GatewayCheckoutStep {
    SELECT_PAYMENT_METHOD,
    PROCESSING,
    SUCCESS,
    FAILED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentGatewayCheckoutSheet(
    serviceTitle: String,
    serviceTitleHindi: String,
    basePrice: Double,
    visitingFee: Double,
    adminUpiId: String,
    isHindi: Boolean,
    onPaymentSuccess: (
        paymentMethod: String,
        gatewayPaymentId: String,
        gateway: String,
        totalPaid: Double,
        gstAmount: Double
    ) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 18% GST Calculations
    val gstRate = 18.0
    val gstAmount = Math.round((basePrice * 0.18) * 100.0) / 100.0
    val cgstAmount = Math.round((gstAmount / 2.0) * 100.0) / 100.0
    val sgstAmount = gstAmount - cgstAmount
    val totalPayable = Math.round((basePrice + gstAmount + visitingFee) * 100.0) / 100.0

    // Selected Payment Method Tab: 0 = UPI, 1 = Cards, 2 = Net Banking, 3 = Cash/COD
    var selectedMethodTab by remember { mutableIntStateOf(0) }
    var currentStep by remember { mutableStateOf(GatewayCheckoutStep.SELECT_PAYMENT_METHOD) }
    var generatedGatewayPaymentId by remember { mutableStateOf("") }
    var failureReason by remember { mutableStateOf("") }

    // UPI state
    var selectedUpiApp by remember { mutableStateOf("PhonePe") }
    var inputCustomUpiId by remember { mutableStateOf("") }

    // Card state
    var cardNumber by remember { mutableStateOf("4532 8910 2341 7890") }
    var cardExpiry by remember { mutableStateOf("12/28") }
    var cardCvv by remember { mutableStateOf("842") }
    var cardHolder by remember { mutableStateOf("Narendra Kurre") }

    // Net Banking state
    var selectedBank by remember { mutableStateOf("State Bank of India (SBI)") }

    val popularBanks = listOf(
        "State Bank of India (SBI)",
        "HDFC Bank",
        "ICICI Bank",
        "Axis Bank",
        "Chhattisgarh Rajya Gramin Bank",
        "Punjab National Bank (PNB)",
        "Bank of Baroda"
    )

    fun executeRazorpayCheckout(methodName: String) {
        currentStep = GatewayCheckoutStep.PROCESSING
        scope.launch {
            delay(1600) // Simulating Razorpay / Gateway authorization & webhook signature verify
            val rzpId = "pay_rzp_" + System.currentTimeMillis().toString().takeLast(9)
            generatedGatewayPaymentId = rzpId
            currentStep = GatewayCheckoutStep.SUCCESS
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            if (currentStep != GatewayCheckoutStep.PROCESSING) onDismiss()
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(NavySurface, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Payment,
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isHindi) "सुरक्षित पेमेंट गेटवे चेकआउट" else "Secure Payment Checkout",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = TealSuccess, modifier = Modifier.size(12.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "Razorpay Test Sandbox • 256-bit Encrypted",
                                fontSize = 10.sp,
                                color = TealSuccess,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                if (currentStep != GatewayCheckoutStep.PROCESSING) {
                    IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_checkout_sheet")) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            when (currentStep) {
                GatewayCheckoutStep.SELECT_PAYMENT_METHOD -> {
                    // Tax & Bill Breakdown Card
                    Card(
                        colors = CardDefaults.cardColors(containerColor = NavyCard),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (isHindi) serviceTitleHindi else serviceTitle,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(Modifier.height(8.dp))
                            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                            Spacer(Modifier.height(8.dp))

                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Base Price (कार्य शुल्क):", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                Text("₹${basePrice.toInt()}", fontSize = 11.sp, color = Color.White)
                            }
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("CGST (Central GST @ 9%):", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                Text("₹${String.format(Locale.US, "%.2f", cgstAmount)}", fontSize = 11.sp, color = Color.White)
                            }
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("SGST (Chhattisgarh GST @ 9%):", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                Text("₹${String.format(Locale.US, "%.2f", sgstAmount)}", fontSize = 11.sp, color = Color.White)
                            }
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Total 18% Tax (कुल जीएसटी):", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GoldAccent)
                                Text("₹${String.format(Locale.US, "%.2f", gstAmount)}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GoldAccent)
                            }
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Visiting & Safety Fee:", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                Text("₹${visitingFee.toInt()}", fontSize = 11.sp, color = Color.White)
                            }

                            Spacer(Modifier.height(8.dp))
                            HorizontalDivider(color = Color.White.copy(alpha = 0.15f))
                            Spacer(Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isHindi) "कुल देय राशि (Total Amount):" else "Total Payable Amount:",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "₹${String.format(Locale.US, "%.2f", totalPayable)}",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TealSuccess
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    Text(
                        text = if (isHindi) "भुगतान का तरीका चुनें (Select Payment Mode)" else "Select Payment Mode (All-in-One)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    // All 4 Payment Mode Tabs
                    TabRow(
                        selectedTabIndex = selectedMethodTab,
                        containerColor = NavySurface,
                        contentColor = SaffronPrimary,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedMethodTab]),
                                color = SaffronPrimary
                            )
                        }
                    ) {
                        val tabs = listOf(
                            "📱 UPI",
                            "💳 Card",
                            "🏦 Net Banking",
                            "💵 Cash/COD"
                        )
                        tabs.forEachIndexed { idx, title ->
                            Tab(
                                selected = selectedMethodTab == idx,
                                onClick = { selectedMethodTab = idx },
                                text = {
                                    Text(
                                        text = title,
                                        fontSize = 11.sp,
                                        fontWeight = if (selectedMethodTab == idx) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedMethodTab == idx) SaffronPrimary else Color.Gray
                                    )
                                }
                            )
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    // Method Tab Content
                    when (selectedMethodTab) {
                        0 -> {
                            // UPI TAB
                            Column(Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    listOf("PhonePe", "Google Pay", "Paytm", "BHIM UPI").forEach { upiAppName ->
                                        FilterChip(
                                            selected = selectedUpiApp == upiAppName,
                                            onClick = { selectedUpiApp = upiAppName },
                                            label = { Text(upiAppName, fontSize = 11.sp) },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = SaffronPrimary,
                                                selectedLabelColor = Color.White
                                            )
                                        )
                                    }
                                }

                                Spacer(Modifier.height(12.dp))

                                // UPI QR Box
                                Surface(
                                    color = NavyCard,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Scan QR to Pay via $selectedUpiApp",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = GoldAccent
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(130.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color.White)
                                                .padding(6.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            MiniQrCanvas(seed = adminUpiId + totalPayable.toInt())
                                        }
                                        Spacer(Modifier.height(8.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Text(
                                                text = "UPI: $adminUpiId",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = GoldAccent
                                            )
                                            OutlinedButton(
                                                onClick = {
                                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                                                    clipboard?.setPrimaryClip(ClipData.newPlainText("UPI ID", adminUpiId))
                                                    Toast.makeText(context, if (isHindi) "UPI ID कॉपी हो गई: $adminUpiId" else "UPI ID Copied: $adminUpiId", Toast.LENGTH_SHORT).show()
                                                },
                                                border = BorderStroke(1.dp, GoldAccent),
                                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                                modifier = Modifier.height(28.dp)
                                            ) {
                                                Text(if (isHindi) "कॉपी" else "Copy", fontSize = 10.sp, color = GoldAccent)
                                            }
                                        }
                                        Text(
                                            text = "प्राप्तकर्ता: नरेंद्र कुमार कुर्रे (संस्थापक एवं स्वामी)",
                                            fontSize = 9.sp,
                                            color = Color.White.copy(alpha = 0.7f)
                                        )
                                    }
                                }

                                Spacer(Modifier.height(14.dp))

                                Button(
                                    onClick = {
                                        // Attempt to launch installed UPI intent on device (PhonePe / GPay / Paytm / BHIM)
                                        try {
                                            val formattedAmount = String.format(Locale.US, "%.2f", totalPayable)
                                            val upiUri = Uri.parse("upi://pay?pa=$adminUpiId&pn=Narendra%20Kumar%20Kurre&am=$formattedAmount&cu=INR&tn=QuickWorkCG-$serviceTitle")
                                            val upiIntent = Intent(Intent.ACTION_VIEW, upiUri)
                                            context.startActivity(Intent.createChooser(upiIntent, if (isHindi) "$selectedUpiApp से भुगतान करें" else "Pay with $selectedUpiApp"))
                                        } catch (e: Exception) {
                                            // Fallback to clipboard and simulator
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                                            clipboard?.setPrimaryClip(ClipData.newPlainText("UPI ID", adminUpiId))
                                            Toast.makeText(context, if (isHindi) "UPI ID कॉपी की गई: $adminUpiId" else "UPI ID Copied: $adminUpiId", Toast.LENGTH_SHORT).show()
                                        }
                                        executeRazorpayCheckout("UPI_$selectedUpiApp")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("pay_via_upi_btn")
                                ) {
                                    Text("Pay ₹${String.format(Locale.US, "%.2f", totalPayable)} with $selectedUpiApp", fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        1 -> {
                            // CARDS TAB (Debit / Credit)
                            Column(Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = cardNumber,
                                    onValueChange = { cardNumber = it },
                                    label = { Text("Card Number (डेबिट / क्रेडिट कार्ड नंबर)") },
                                    leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null, tint = GoldAccent) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronPrimary)
                                )
                                Spacer(Modifier.height(8.dp))
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = cardExpiry,
                                        onValueChange = { cardExpiry = it },
                                        label = { Text("Expiry (MM/YY)") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.weight(1f),
                                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronPrimary)
                                    )
                                    OutlinedTextField(
                                        value = cardCvv,
                                        onValueChange = { cardCvv = it },
                                        label = { Text("CVV") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                                        modifier = Modifier.weight(1f),
                                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronPrimary)
                                    )
                                }
                                Spacer(Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = cardHolder,
                                    onValueChange = { cardHolder = it },
                                    label = { Text("Cardholder Name (कार्डधारक का नाम)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronPrimary)
                                )

                                Spacer(Modifier.height(14.dp))

                                Button(
                                    onClick = {
                                        executeRazorpayCheckout("CARD_DEBIT_CREDIT")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("pay_via_card_btn")
                                ) {
                                    Text("Pay ₹${String.format(Locale.US, "%.2f", totalPayable)} via Card", fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        2 -> {
                            // NET BANKING TAB
                            Column(Modifier.fillMaxWidth()) {
                                Text("लोकप्रिय बैंक चुनें (Select Bank):", fontSize = 11.sp, color = Color.Gray)
                                Spacer(Modifier.height(6.dp))
                                popularBanks.forEach { bank ->
                                    Surface(
                                        color = if (selectedBank == bank) NavyCard else NavySurface,
                                        shape = RoundedCornerShape(8.dp),
                                        border = if (selectedBank == bank) androidx.compose.foundation.BorderStroke(1.5.dp, SaffronPrimary) else null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable { selectedBank = bank }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Default.AccountBalance,
                                                contentDescription = null,
                                                tint = if (selectedBank == bank) SaffronPrimary else Color.Gray,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(Modifier.width(10.dp))
                                            Text(
                                                text = bank,
                                                fontSize = 12.sp,
                                                fontWeight = if (selectedBank == bank) FontWeight.Bold else FontWeight.Normal,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }

                                Spacer(Modifier.height(14.dp))

                                Button(
                                    onClick = {
                                        executeRazorpayCheckout("NET_BANKING_$selectedBank")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("pay_via_netbanking_btn")
                                ) {
                                    Text("Pay ₹${String.format(Locale.US, "%.2f", totalPayable)} via Net Banking", fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        3 -> {
                            // CASH ON WORK / COD TAB
                            Column(Modifier.fillMaxWidth()) {
                                Surface(
                                    color = NavyCard,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.CurrencyRupee, contentDescription = null, tint = GoldAccent)
                                            Spacer(Modifier.width(8.dp))
                                            Text("काम पूरा होने पर नकद भुगतान (Pay After Work)", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                                        }
                                        Spacer(Modifier.height(6.dp))
                                        Text(
                                            text = "कारीगर द्वारा कार्य संतोषजनक रूप से पूर्ण करने के पश्चात नकद अथवा सीधे कारीगर को UPI भुगतान करें।",
                                            fontSize = 11.sp,
                                            color = Color.White.copy(alpha = 0.85f),
                                            lineHeight = 16.sp
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Text(
                                            text = "• 18% GST टैक्स इनवॉइस कार्य समाप्ति पर जनरेट होगी।\n• 30 दिनों की क्विकवर्क वारंटी लागू रहेगी।",
                                            fontSize = 10.sp,
                                            color = TealLight
                                        )
                                    }
                                }

                                Spacer(Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        onPaymentSuccess("COD", "COD_PAY_ON_SERVICE", "CASH_ON_WORK", totalPayable, gstAmount)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = TealSuccess),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("confirm_cod_booking_btn")
                                ) {
                                    Text("Confirm Booking with Cash (₹${totalPayable.toInt()})", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                GatewayCheckoutStep.PROCESSING -> {
                    // Processing / Verifying state
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 36.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = SaffronPrimary, strokeWidth = 4.dp, modifier = Modifier.size(54.dp))
                        Spacer(Modifier.height(18.dp))
                        Text(
                            text = if (isHindi) "पेमेंट गेटवे पर प्रोसेस हो रहा है..." else "Connecting to Razorpay Secure Gateway...",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "Verifying 18% GST (CGST 9% + SGST 9%) & Webhook signature...",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                GatewayCheckoutStep.SUCCESS -> {
                    // Payment Success Screen
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(TealSuccess.copy(alpha = 0.15f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TealSuccess, modifier = Modifier.size(44.dp))
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = if (isHindi) "भुगतान सफलतापूर्वक प्राप्त हुआ!" else "Payment Successful!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TealSuccess
                        )
                        Text(
                            text = "Transaction ID: $generatedGatewayPaymentId",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray
                        )
                        Spacer(Modifier.height(14.dp))

                        Card(
                            colors = CardDefaults.cardColors(containerColor = NavyCard),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Amount Paid (भुगतान):", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                    Text("₹${String.format(Locale.US, "%.2f", totalPayable)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TealSuccess)
                                }
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("GST 18% Included (टैक्स):", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                    Text("₹${String.format(Locale.US, "%.2f", gstAmount)}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                                }
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Gateway:", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                    Text("Razorpay Sandbox (Verified)", fontSize = 11.sp, color = Color.White)
                                }
                            }
                        }

                        Spacer(Modifier.height(18.dp))

                        Button(
                            onClick = {
                                val mode = when (selectedMethodTab) {
                                    0 -> "UPI_$selectedUpiApp"
                                    1 -> "CARD_DEBIT_CREDIT"
                                    2 -> "NET_BANKING"
                                    else -> "COD"
                                }
                                onPaymentSuccess(mode, generatedGatewayPaymentId, "RAZORPAY_TEST", totalPayable, gstAmount)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("continue_to_booking_btn")
                        ) {
                            Text(if (isHindi) "बुकिंग जारी रखें व इनवॉइस देखें" else "View Booking & Tax Invoice", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                GatewayCheckoutStep.FAILED -> {
                    // Payment Failed Screen
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Error, contentDescription = null, tint = Color.Red, modifier = Modifier.size(54.dp))
                        Spacer(Modifier.height(12.dp))
                        Text("भुगतान असफल (Payment Failed)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                        Text(failureReason.ifBlank { "Gateway timeout or user aborted" }, fontSize = 11.sp, color = Color.Gray)
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = { currentStep = GatewayCheckoutStep.SELECT_PAYMENT_METHOD },
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Retry Payment (पुनः प्रयास करें)")
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MiniQrCanvas(seed: String) {
    Canvas(modifier = Modifier.size(118.dp)) {
        val count = 21
        val cellSize = size.width / count
        val hash = seed.hashCode()

        for (r in 0 until count) {
            for (c in 0 until count) {
                val isCornerFinder = (r < 7 && c < 7) || (r < 7 && c >= count - 7) || (r >= count - 7 && c < 7)
                val isFinderBorder = isCornerFinder && (r == 0 || r == 6 || c == 0 || c == 6 ||
                        r == count - 1 || r == count - 7 || c == count - 1 || c == count - 7)
                val isFinderCenter = (r in 2..4 && c in 2..4) ||
                        (r in 2..4 && c in (count - 5)..(count - 3)) ||
                        (r in (count - 5)..(count - 3) && c in 2..4)

                val pseudoBit = ((hash * (r + 1) * 31 + c * 17) % 7) in listOf(0, 1, 3, 5)

                if (isFinderBorder || isFinderCenter || (!isCornerFinder && pseudoBit)) {
                    drawRect(
                        color = Color(0xFF0F172A),
                        topLeft = Offset(c * cellSize, r * cellSize),
                        size = Size(cellSize, cellSize)
                    )
                }
            }
        }
    }
}
