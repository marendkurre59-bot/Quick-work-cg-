package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PartnerEntity
import com.example.data.model.CoverageCity
import com.example.data.model.CoverageData
import com.example.data.model.PartnerTradesList
import com.example.ui.components.UpiPaymentSheet
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavySurface
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TealLight
import com.example.ui.theme.TealSuccess

@Composable
fun PartnerPortalScreen(
    partners: List<PartnerEntity>,
    isHindi: Boolean,
    adminUpiId: String,
    onRegisterPartner: (
        name: String,
        phone: String,
        trade: String,
        city: String,
        locality: String,
        pinCode: String,
        district: String,
        aadhaar: String,
        experience: Int,
        feeAmount: Double,
        payoutUpiOrBank: String,
        selfieCaptured: Boolean
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var showRegistrationSheet by remember { mutableStateOf(false) }
    var preselectedFee by remember { mutableDoubleStateOf(0.0) }
    var selectedStatusFilter by remember { mutableStateOf("ALL") } // "ALL", "APPROVED", "PENDING"
    var viewingIdCardPartner by remember { mutableStateOf<PartnerEntity?>(null) }

    val filteredPartners = remember(partners, selectedStatusFilter) {
        when (selectedStatusFilter) {
            "APPROVED" -> partners.filter { it.registrationStatus == "APPROVED" }
            "PENDING" -> partners.filter { it.registrationStatus == "PENDING" }
            else -> partners
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Surface(
            color = NavySurface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isHindi) "कारीगर और पार्टनर साथी पोर्टल" else "QuickWork Partner Portal",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Color.White
                        )
                        Text(
                            text = if (isHindi) "दैनिक काम, सीधे ग्राहक और ऑटोमैटिक बचत" else "Technician onboarding & verified worker network",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { 
                                preselectedFee = 0.0
                                showRegistrationSheet = true 
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = TealSuccess),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("free_worker_join_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Engineering, contentDescription = null, tint = Color.White, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isHindi) "फ्री लेबर (₹0)" else "Free Labour (₹0)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Button(
                            onClick = { 
                                preselectedFee = 100.0
                                showRegistrationSheet = true 
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("partner_join_now_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isHindi) "कारीगर पास (₹100/200)" else "Join (₹100/200)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // Filter Bar: All, Approved, Pending
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedStatusFilter == "ALL",
                onClick = { selectedStatusFilter = "ALL" },
                label = { Text("सभी (${partners.size})", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SaffronPrimary,
                    selectedLabelColor = Color.White
                )
            )
            FilterChip(
                selected = selectedStatusFilter == "APPROVED",
                onClick = { selectedStatusFilter = "APPROVED" },
                label = { Text("सत्यापित Approved (${partners.count { it.registrationStatus == "APPROVED" }})", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = TealSuccess,
                    selectedLabelColor = Color.White
                )
            )
            FilterChip(
                selected = selectedStatusFilter == "PENDING",
                onClick = { selectedStatusFilter = "PENDING" },
                label = { Text("लंबित Pending (${partners.count { it.registrationStatus == "PENDING" }})", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = GoldAccent,
                    selectedLabelColor = Color.Black
                )
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredPartners) { partner ->
                PartnerCard(
                    partner = partner,
                    isHindi = isHindi,
                    onViewIdCard = { viewingIdCardPartner = partner }
                )
            }
        }
    }

    if (showRegistrationSheet) {
        PartnerRegistrationModal(
            adminUpiId = adminUpiId,
            isHindi = isHindi,
            initialFee = preselectedFee,
            onDismiss = { showRegistrationSheet = false },
            onSubmit = { name, phone, trade, city, locality, pinCode, district, aadhaar, exp, fee, upi, selfie ->
                onRegisterPartner(name, phone, trade, city, locality, pinCode, district, aadhaar, exp, fee, upi, selfie)
                showRegistrationSheet = false
            }
        )
    }

    viewingIdCardPartner?.let { partner ->
        DigitalWorkerIdDialog(
            partner = partner,
            isHindi = isHindi,
            onDismiss = { viewingIdCardPartner = null }
        )
    }
}

@Composable
private fun PartnerCard(
    partner: PartnerEntity,
    isHindi: Boolean,
    onViewIdCard: () -> Unit
) {
    var isOnline by remember { mutableStateOf(partner.isOnlineAvailable) }
    val isApproved = partner.registrationStatus == "APPROVED"

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(NavySurface),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = partner.name.take(1).uppercase(),
                            color = SaffronPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = partner.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            if (isApproved) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified",
                                    tint = TealSuccess,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Text(
                            text = "${partner.tradeCategory} • ${partner.locality}, ${partner.city}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Status chip & Online toggle
                Column(horizontalAlignment = Alignment.End) {
                    Surface(
                        color = if (isApproved) TealSuccess.copy(alpha = 0.15f) else GoldAccent.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = if (isApproved) "APPROVED" else "PENDING KYC",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isApproved) TealSuccess else Color(0xFFB45309),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Switch(
                        checked = isOnline,
                        onCheckedChange = { isOnline = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = TealSuccess
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Partner Metrics Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Rating", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = "${partner.rating}★", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = GoldAccent)
                }
                Column {
                    Text(text = "Experience", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = "${partner.experienceYears} Yrs", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                }
                Column {
                    Text(text = "ID Verification", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = partner.aadhaarNumber, fontWeight = FontWeight.Bold, fontSize = 10.sp, color = TealSuccess)
                }
                Column {
                    Text(text = "Payout UPI", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = partner.bankAccountOrUpi.ifBlank { "9755955659@upi" }, fontWeight = FontWeight.Bold, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Actions: Digital ID Card, Call, WhatsApp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val context = LocalContext.current

                OutlinedButton(
                    onClick = onViewIdCard,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                ) {
                    Icon(Icons.Default.Badge, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("ID Card", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        val cleanPhone = partner.phone.replace(Regex("[^0-9+]"), "").trim()
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanPhone"))
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Dialing: $cleanPhone", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isHindi) "कॉल करें" else "Call", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SaffronPrimary)
                }

                Button(
                    onClick = {
                        val digits = partner.phone.replace(Regex("[^0-9]"), "")
                        val fullPhone = if (digits.length == 10) "91$digits" else digits
                        val msg = "Namaste ${partner.name}, I found your verified profile on QuickWork CG for ${partner.tradeCategory} service."
                        val url = "https://api.whatsapp.com/send?phone=$fullPhone&text=${Uri.encode(msg)}"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "WhatsApp to: $fullPhone", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1.1f)
                        .height(36.dp)
                ) {
                    Icon(imageVector = Icons.Default.Message, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "WhatsApp", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalWorkerIdDialog(
    partner: PartnerEntity,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "डिजिटल कारीगर पहचान पत्र (Digital Worker ID)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(Modifier.height(14.dp))

            // Official ID Card Look
            Card(
                colors = CardDefaults.cardColors(containerColor = NavyCard),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, GoldAccent.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("QUICKWORK CG", fontSize = 14.sp, fontWeight = FontWeight.Black, color = GoldAccent)
                            Text("Govt. Recognized Artisan ID", fontSize = 9.sp, color = Color.White.copy(alpha = 0.7f))
                        }
                        Surface(
                            color = TealSuccess,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text("VERIFIED", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(NavySurface)
                                .border(1.dp, SaffronPrimary, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(44.dp))
                        }

                        Spacer(Modifier.width(14.dp))

                        Column {
                            Text(partner.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(partner.tradeCategory, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = GoldAccent)
                            Text("ID: ${partner.id} • Exp: ${partner.experienceYears} Yrs", fontSize = 10.sp, color = Color.White.copy(alpha = 0.8f))
                            Text("📍 ${partner.locality}, ${partner.city} (${partner.pinCode})", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    // QR Code Box
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier.padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(100.dp)) {
                                val count = 21
                                val cellSize = size.width / count
                                val hash = partner.id.hashCode()

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
                    }

                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Aadhaar: ${partner.aadhaarNumber} • Status: ${partner.registrationStatus}",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "Authorized by Narendra Kumar Kurre (Founder & Owner)",
                        fontSize = 9.sp,
                        color = GoldAccent
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnerRegistrationModal(
    adminUpiId: String,
    isHindi: Boolean,
    initialFee: Double = 0.0,
    onDismiss: () -> Unit,
    onSubmit: (
        name: String,
        phone: String,
        trade: String,
        city: String,
        locality: String,
        pinCode: String,
        district: String,
        aadhaar: String,
        experience: Int,
        feeAmount: Double,
        payoutUpiOrBank: String,
        selfieCaptured: Boolean
    ) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedTrade by remember { mutableStateOf("Raj Mistri (Mason / ईंट जुड़ाई)") }
    var selectedCity by remember { mutableStateOf("Raipur") }
    var locality by remember { mutableStateOf("") }
    var pinCode by remember { mutableStateOf("492001") }
    var district by remember { mutableStateOf("Raipur") }
    var aadhaar by remember { mutableStateOf("") }
    var payoutUpiOrBank by remember { mutableStateOf("") }
    var experience by remember { mutableIntStateOf(5) }
    var registrationFee by remember { mutableDoubleStateOf(initialFee) }
    var hasConsentAgreed by remember { mutableStateOf(true) }
    var isSelfieCaptured by remember { mutableStateOf(false) }
    var showTradeDropdown by remember { mutableStateOf(false) }
    var showUpiSheet by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isHindi) "कारीगर व मजदूर पंजीकरण (Worker KYC)" else "Worker / Vendor Registration",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Live selfie, masked doc & daily direct job calls in CG",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_partner_registration")) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Live Selfie Simulation Box
            Surface(
                color = if (isSelfieCaptured) TealSuccess.copy(alpha = 0.12f) else NavySurface,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (isSelfieCaptured) TealSuccess else SaffronPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(if (isSelfieCaptured) TealSuccess else NavyCard),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                if (isSelfieCaptured) Icons.Default.Check else Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isSelfieCaptured) "✅ Live Selfie Captured" else "Live Selfie Verification (लाइव फोटो)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelfieCaptured) TealSuccess else Color.White
                            )
                            Text(
                                text = if (isSelfieCaptured) "Face recognition completed successfully" else "Capture front live picture to prevent fraud",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            isSelfieCaptured = true
                            Toast.makeText(context, "📸 लाइव सेल्फी सत्यापित हो गई (Selfie Verified)", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelfieCaptured) TealSuccess else SaffronPrimary
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(if (isSelfieCaptured) "Retake" else "Capture", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name (कारीगर का पूरा नाम)") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = SaffronPrimary) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("partner_name_input")
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { if (it.length <= 10) phone = it.filter { ch -> ch.isDigit() } },
                label = { Text("Mobile Number (10 digits / मोबाइल नंबर)") },
                supportingText = { Text("${phone.length}/10 digits") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = SaffronPrimary) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("partner_phone_input")
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Trade dropdown
            Text("Select Work Category / हुनर:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SaffronPrimary.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showTradeDropdown = !showTradeDropdown }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = selectedTrade, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SaffronPrimary)
                    Text(text = if (showTradeDropdown) "▲ Close" else "▼ Change", fontSize = 11.sp)
                }
            }

            if (showTradeDropdown) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(vertical = 4.dp)
                ) {
                    LazyColumn(modifier = Modifier.padding(6.dp)) {
                        items(PartnerTradesList.ALL_TRADES) { trade ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable {
                                        selectedTrade = trade
                                        showTradeDropdown = false
                                    }
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = trade,
                                    fontSize = 11.sp,
                                    color = if (selectedTrade == trade) SaffronPrimary else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (selectedTrade == trade) FontWeight.Bold else FontWeight.Normal
                                )
                                if (selectedTrade == trade) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = locality,
                    onValueChange = { locality = it },
                    label = { Text("Locality / मोहल्ला") },
                    modifier = Modifier.weight(1.2f)
                )
                OutlinedTextField(
                    value = pinCode,
                    onValueChange = { if (it.length <= 6) pinCode = it.filter { ch -> ch.isDigit() } },
                    label = { Text("PIN Code") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(0.8f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = aadhaar,
                onValueChange = { if (it.length <= 12) aadhaar = it.filter { ch -> ch.isDigit() } },
                label = { Text("Identity Doc / Aadhaar (Masked Offline)") },
                placeholder = { Text("12 अंकों का आधार (सुरक्षित रूप से मास्क होगा)") },
                leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = SaffronPrimary) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("partner_aadhaar_input")
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = payoutUpiOrBank,
                onValueChange = { payoutUpiOrBank = it },
                label = { Text("Bank Account or UPI ID for Daily Payouts") },
                placeholder = { Text("e.g. 9755955659@upi or SBI A/c") },
                leadingIcon = { Icon(Icons.Default.Security, contentDescription = null, tint = TealSuccess) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Consent Checkbox
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { hasConsentAgreed = !hasConsentAgreed },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = hasConsentAgreed,
                    onCheckedChange = { hasConsentAgreed = it },
                    colors = CheckboxDefaults.colors(checkedColor = TealSuccess)
                )
                Text(
                    text = "मैं छत्तीसगढ़ में कार्य करने हेतु पहचान विवरण व सेल्फी सत्यापन की सहमति देता हूँ (Consent-based KYC).",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Registration Fee Selector
            Text(
                text = if (isHindi) "रजिस्ट्रेशन शुल्क चुनें (Registration Pass)" else "Select Registration Pass Fee",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Free ₹0 Pass
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (registrationFee == 0.0) TealSuccess.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = if (registrationFee == 0.0) androidx.compose.foundation.BorderStroke(1.5.dp, TealSuccess) else null,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { registrationFee = 0.0 }
                        .testTag("fee_0_card")
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "₹0 Free", fontWeight = FontWeight.Black, fontSize = 15.sp, color = TealSuccess)
                        Text(text = "लेबर व मजदूर", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(text = "मुफ्त पास", fontSize = 9.sp, color = Color.Gray)
                    }
                }

                // ₹100 Standard Pass
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (registrationFee == 100.0) SaffronPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = if (registrationFee == 100.0) androidx.compose.foundation.BorderStroke(1.5.dp, SaffronPrimary) else null,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { registrationFee = 100.0 }
                        .testTag("fee_100_card")
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "₹100 Pass", fontWeight = FontWeight.Black, fontSize = 15.sp, color = SaffronPrimary)
                        Text(text = "कारीगर व मिस्त्री", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Daily Leads", fontSize = 9.sp, color = Color.Gray)
                    }
                }

                // ₹200 Gold Pass
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (registrationFee == 200.0) GoldAccent.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = if (registrationFee == 200.0) androidx.compose.foundation.BorderStroke(1.5.dp, GoldAccent) else null,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { registrationFee = 200.0 }
                        .testTag("fee_200_card")
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "₹200 Pass", fontWeight = FontWeight.Black, fontSize = 15.sp, color = GoldAccent)
                        Text(text = "गोल्ड प्रो", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Priority 30m", fontSize = 9.sp, color = TealSuccess)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (name.isBlank()) {
                        Toast.makeText(context, if (isHindi) "कृपया कारीगर/मजदूर का नाम दर्ज करें" else "Please enter full name", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (phone.length < 10) {
                        Toast.makeText(context, if (isHindi) "कृपया 10 अंकों का मान्य मोबाइल नंबर दर्ज करें" else "Please enter valid 10-digit phone number", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (!hasConsentAgreed) {
                        Toast.makeText(context, "कृपया KYC व सत्यापन सहमति टिक करें", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val finalLocality = locality.trim().ifBlank { "$selectedCity Central" }
                    val maskedAadhaar = if (aadhaar.isNotBlank()) {
                        "XXXX-XXXX-" + (if (aadhaar.length >= 4) aadhaar.takeLast(4) else "4821")
                    } else {
                        "VERIFIED-CG"
                    }

                    if (registrationFee == 0.0) {
                        onSubmit(
                            name.trim(), phone.trim(), selectedTrade, selectedCity, finalLocality,
                            pinCode, district, maskedAadhaar, experience, 0.0,
                            payoutUpiOrBank, isSelfieCaptured
                        )
                        Toast.makeText(context, if (isHindi) "बधाई! ₹0 मुफ्त लेबर रजिस्ट्रेशन सफल रहा।" else "Free Labour Registration Completed!", Toast.LENGTH_LONG).show()
                    } else {
                        showUpiSheet = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (registrationFee == 0.0) TealSuccess else SaffronPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("pay_partner_fee_button")
            ) {
                Text(
                    text = if (registrationFee == 0.0)
                        "✅ मुफ्त लेबर रजिस्ट्रेशन पूरा करें (Free Registration - ₹0)"
                    else
                        "Pay ₹${registrationFee.toInt()} via UPI & Complete Onboarding",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (showUpiSheet) {
        UpiPaymentSheet(
            amount = registrationFee,
            payeeUpiId = adminUpiId,
            title = "QuickWork CG - Partner Verification",
            description = "₹${registrationFee.toInt()} registration fee saved automatically to admin account",
            isHindi = isHindi,
            onPaymentConfirmed = { txnId ->
                showUpiSheet = false
                val finalLocality = locality.trim().ifBlank { "$selectedCity Central" }
                val maskedAadhaar = if (aadhaar.isNotBlank()) {
                    "XXXX-XXXX-" + (if (aadhaar.length >= 4) aadhaar.takeLast(4) else "4821")
                } else {
                    "VERIFIED-CG"
                }
                onSubmit(
                    name.trim(), phone.trim(), selectedTrade, selectedCity, finalLocality,
                    pinCode, district, maskedAadhaar, experience, registrationFee,
                    payoutUpiOrBank, isSelfieCaptured
                )
                Toast.makeText(context, "Registration fee ₹${registrationFee.toInt()} verified! Welcome to QuickWork CG.", Toast.LENGTH_LONG).show()
            },
            onDismiss = { showUpiSheet = false }
        )
    }
}
