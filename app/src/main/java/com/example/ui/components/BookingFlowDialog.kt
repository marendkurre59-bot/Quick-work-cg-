package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ServiceItem
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavySurface
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TealLight
import com.example.ui.theme.TealSuccess
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingFlowDialog(
    service: ServiceItem,
    cityName: String,
    localityName: String,
    isHindi: Boolean,
    adminUpiId: String,
    defaultCommissionRate: Double,
    onConfirmBooking: (
        name: String,
        phone: String,
        address: String,
        dateSlot: String,
        timeSlot: String,
        isExpress: Boolean,
        paymentMethod: String,
        gatewayPaymentId: String,
        gateway: String,
        commissionPercent: Double
    ) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedDateSlot by remember { mutableStateOf("Today") }
    var selectedTimeSlot by remember { mutableStateOf("Morning 10:00 AM - 12:00 PM") }
    var isExpressEmergency by remember { mutableStateOf(false) }
    var showCheckoutGateway by remember { mutableStateOf(false) }
    var showLegalPolicies by remember { mutableStateOf(false) }

    // Commission %
    var commissionPercent by remember {
        mutableDoubleStateOf(if (isExpressEmergency) 20.0 else defaultCommissionRate.coerceIn(15.0, 20.0))
    }

    val basePrice = service.basePrice.toDouble()
    val visitingCharge = if (isExpressEmergency) 99.0 else 49.0
    
    // 18% GST (CGST 9% + SGST 9%)
    val gstRate = 18.0
    val gstAmount = Math.round((basePrice * 0.18) * 100.0) / 100.0
    val cgstAmount = Math.round((gstAmount / 2.0) * 100.0) / 100.0
    val sgstAmount = gstAmount - cgstAmount
    val totalAmount = Math.round((basePrice + gstAmount + visitingCharge) * 100.0) / 100.0
    val platformCommissionAmount = Math.round((basePrice * (commissionPercent / 100.0)) * 100.0) / 100.0
    val workerPayoutAmount = Math.round((basePrice - platformCommissionAmount + visitingCharge) * 100.0) / 100.0

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isHindi) "सेवा बुक करें (Book Service)" else "Book Service",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (isHindi) service.titleHindi else service.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = SaffronPrimary
                    )
                }
                IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_booking_flow")) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Express 30-min toggle
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isExpressEmergency) SaffronPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isExpressEmergency = !isExpressEmergency
                        commissionPercent = if (isExpressEmergency) 20.0 else defaultCommissionRate.coerceIn(15.0, 20.0)
                    }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isExpressEmergency) SaffronPrimary else Color(0xFF64748B)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.FlashOn, contentDescription = "Express", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isHindi) "30 मिनट इमरजेंसी एक्सप्रेस सर्विस" else "30-Min Emergency Express Arrival",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isHindi) "तत्काल 30 मिनट में तकनीशियन पहुंचेगा (+₹99 विजिट)" else "Fast technician dispatch to $localityName (+₹99 visiting)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Date selection
            Text(
                text = if (isHindi) "दिनांक चुनें (Date Slot)" else "Select Date Slot",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Today", "Tomorrow", "Pick Day").forEach { slot ->
                    FilterChip(
                        selected = selectedDateSlot == slot,
                        onClick = { selectedDateSlot = slot },
                        label = { Text(slot, fontSize = 12.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Time Slot selection
            Text(
                text = if (isHindi) "समय स्लॉट चुनें (Time Slot)" else "Select Time Slot",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf(
                    "Morning 10:00 AM - 12:00 PM",
                    "Afternoon 01:00 PM - 03:00 PM",
                    "Evening 04:00 PM - 06:00 PM"
                ).forEach { slot ->
                    Surface(
                        color = if (selectedTimeSlot == slot) SaffronPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        border = if (selectedTimeSlot == slot) androidx.compose.foundation.BorderStroke(1.5.dp, SaffronPrimary) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedTimeSlot = slot }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = if (selectedTimeSlot == slot) SaffronPrimary else Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = slot,
                                fontSize = 12.sp,
                                fontWeight = if (selectedTimeSlot == slot) FontWeight.Bold else FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Contact and Address Inputs
            Text(
                text = if (isHindi) "ग्राहक विवरण व पता" else "Customer Details & Address",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(if (isHindi) "आपका पूरा नाम" else "Your Full Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = SaffronPrimary) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_name_input")
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { if (it.length <= 10) phone = it },
                label = { Text(if (isHindi) "मोबाइल नंबर (10 अंक)" else "Mobile Number (10 digits)") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = SaffronPrimary) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                supportingText = { Text("${phone.length}/10 digits") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_phone_input")
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text(if (isHindi) "मकान नंबर, गली, लैंडमार्क ($localityName, $cityName)" else "House / Flat No., Landmark ($localityName, $cityName)") },
                leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = SaffronPrimary) },
                maxLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_address_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Comprehensive GST Tax & Price Summary Card
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isHindi) "टैक्स व मूल्य ब्रेकडाउन (18% GST)" else "Tax & Price Itemization (18% GST)",
                            color = GoldAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "GSTIN: CG-TAX-2026",
                            fontSize = 9.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Base Service Fee (मूल कार्य):", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        Text(text = "₹${basePrice.toInt()}", color = Color.White, fontSize = 11.sp)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "CGST (Central Tax @ 9%):", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        Text(text = "₹${String.format(Locale.US, "%.2f", cgstAmount)}", color = Color.White, fontSize = 11.sp)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "SGST (Chhattisgarh State Tax @ 9%):", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        Text(text = "₹${String.format(Locale.US, "%.2f", sgstAmount)}", color = Color.White, fontSize = 11.sp)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Total 18% GST (कुल टैक्स):", color = GoldAccent, fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                        Text(text = "₹${String.format(Locale.US, "%.2f", gstAmount)}", color = GoldAccent, fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Visiting / Travel Charge:", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        Text(text = "₹${visitingCharge.toInt()}", color = Color.White, fontSize = 11.sp)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFF334155))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Total Payable (कुल देय राशि):", color = SaffronPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "₹${String.format(Locale.US, "%.2f", totalAmount)}", color = TealSuccess, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        color = NavyCard,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = TealSuccess, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Platform Commission ₹${String.format(Locale.US, "%.2f", platformCommissionAmount)} (${commissionPercent.toInt()}%) automatically tracked for Admin. Artisan receives ₹${String.format(Locale.US, "%.2f", workerPayoutAmount)}.",
                                fontSize = 10.sp,
                                color = Color(0xFFE2E8F0)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Trust & Policy link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Shield, contentDescription = null, tint = TealSuccess, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("30-Day Work Warranty Included", fontSize = 10.sp, color = TealSuccess)
                }
                TextButton(
                    onClick = { showLegalPolicies = true },
                    modifier = Modifier.testTag("view_policies_btn")
                ) {
                    Text("Privacy & Terms", fontSize = 10.sp, color = GoldAccent)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Button -> Opens All-in-One Payment Gateway Checkout
            Button(
                onClick = {
                    if (name.isBlank()) {
                        Toast.makeText(context, if (isHindi) "कृपया अपना पूरा नाम दर्ज करें" else "Please enter your full name", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (phone.length < 10) {
                        Toast.makeText(context, if (isHindi) "कृपया 10 अंकों का मान्य मोबाइल नंबर दर्ज करें" else "Please enter valid 10-digit mobile number", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (address.isBlank()) {
                        Toast.makeText(context, if (isHindi) "कृपया सेवा का पता व मकान नंबर दर्ज करें" else "Please enter service address", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    showCheckoutGateway = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("submit_booking_button")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Payment, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Proceed to Payment (₹${String.format(Locale.US, "%.2f", totalAmount)})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Unified Payment Gateway Checkout Sheet (UPI, Cards, Net Banking, COD + 18% GST)
    if (showCheckoutGateway) {
        PaymentGatewayCheckoutSheet(
            serviceTitle = service.title,
            serviceTitleHindi = service.titleHindi,
            basePrice = basePrice,
            visitingFee = visitingCharge,
            adminUpiId = adminUpiId,
            isHindi = isHindi,
            onPaymentSuccess = { payMethod, gatewayTxnId, gateway, totalPaid, gst ->
                showCheckoutGateway = false
                onConfirmBooking(
                    name, phone, address, selectedDateSlot, selectedTimeSlot,
                    isExpressEmergency, payMethod, gatewayTxnId, gateway, commissionPercent
                )
            },
            onDismiss = { showCheckoutGateway = false }
        )
    }

    // Legal Policies Dialog
    if (showLegalPolicies) {
        LegalPoliciesDialog(
            initialTab = 0,
            isHindi = isHindi,
            onDismiss = { showLegalPolicies = false }
        )
    }
}
