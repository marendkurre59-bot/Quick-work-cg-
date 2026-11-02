package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.AdminConfigEntity
import com.example.data.local.AdminSavingEntryEntity
import com.example.data.local.PartnerEntity
import com.example.data.local.PaymentTransactionEntity
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceItem
import com.example.ui.components.UpiQrCodeCanvas
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavySurface
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TealSuccess
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminSavingsScreen(
    adminConfig: AdminConfigEntity?,
    ledgerEntries: List<AdminSavingEntryEntity>,
    allServices: List<ServiceItem>,
    allTransactions: List<PaymentTransactionEntity> = emptyList(),
    allPartners: List<PartnerEntity> = emptyList(),
    isHindi: Boolean,
    onUpdateAdminConfig: (upiId: String, commissionRate: Double, regFee: Double) -> Unit,
    onAddNewService: (title: String, titleHindi: String, category: String, price: Int) -> Unit,
    onLaunchUpiPayment: (amount: Double, note: String, upiId: String) -> Unit,
    onUpdatePartnerStatus: (partnerId: String, status: String) -> Unit = { _, _ -> },
    onProcessRefund: (transactionId: String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var selectedAdminTab by remember { mutableIntStateOf(0) } // 0 = Savings & Ledger, 1 = Services & Departments, 2 = Payments & GST, 3 = Worker KYC
    var isEditingSettings by remember { mutableStateOf(false) }
    var inputUpiId by remember(adminConfig) {
        mutableStateOf(adminConfig?.adminUpiId ?: "narendkurre123@ybl")
    }
    var commissionRate by remember(adminConfig) {
        mutableDoubleStateOf(adminConfig?.commissionRatePercent ?: 18.0)
    }
    var regFeeOption by remember(adminConfig) {
        mutableDoubleStateOf(adminConfig?.defaultRegistrationFee ?: 100.0)
    }

    // Services Tab Filters & State
    var serviceSearchQuery by remember { mutableStateOf("") }
    var selectedDepartmentFilter by remember { mutableStateOf<String?>(null) }
    var showAddServiceDialog by remember { mutableStateOf(false) }

    // Dynamic calculations from all ledger entries
    val totalSavings by remember(ledgerEntries) {
        derivedStateOf { ledgerEntries.sumOf { it.amount } }
    }
    val totalCommissions by remember(ledgerEntries) {
        derivedStateOf { ledgerEntries.filter { it.type == "SERVICE_COMMISSION" }.sumOf { it.amount } }
    }
    val totalRegFees by remember(ledgerEntries) {
        derivedStateOf { ledgerEntries.filter { it.type == "PARTNER_REGISTRATION_FEE" }.sumOf { it.amount } }
    }
    val totalGstTaxCollected by remember(allTransactions) {
        derivedStateOf { allTransactions.filter { it.status == "SUCCESS" }.sumOf { it.gstAmount } }
    }
    val totalTransactionVolume by remember(allTransactions) {
        derivedStateOf { allTransactions.filter { it.status == "SUCCESS" }.sumOf { it.totalAmount } }
    }

    // Filtered services for admin panel
    val filteredAdminServices by remember(allServices, serviceSearchQuery, selectedDepartmentFilter) {
        derivedStateOf {
            val q = serviceSearchQuery.trim().lowercase()
            allServices.filter { srv ->
                val matchesQ = q.isEmpty() ||
                    srv.title.lowercase().contains(q) ||
                    srv.titleHindi.lowercase().contains(q) ||
                    srv.category.lowercase().contains(q) ||
                    srv.categoryHindi.lowercase().contains(q)
                val matchesDept = selectedDepartmentFilter == null || srv.category == selectedDepartmentFilter
                matchesQ && matchesDept
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
        // 1. Header Banner
        item {
            Surface(
                color = NavySurface,
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Savings,
                                    contentDescription = null,
                                    tint = SaffronPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isHindi) "एडमिन पैनल और खाता बचत फीचर्स" else "Admin Panel & Account Savings",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                            }
                            Text(
                                text = "18% GST Tax Ledger • Razorpay Sandbox • 48+ Services Manager",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        IconButton(
                            onClick = { isEditingSettings = !isEditingSettings },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(NavyCard)
                        ) {
                            Icon(
                                imageVector = if (isEditingSettings) Icons.Default.Check else Icons.Default.Edit,
                                contentDescription = "Edit Settings",
                                tint = SaffronPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. Founder & Owner Profile Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavyCard),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(NavySurface)
                                .border(1.5.dp, SaffronPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_founder_narendra),
                                contentDescription = "Narendra Kumar Kurre",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Narendra Kumar Kurre",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = "Verified Owner",
                                    tint = GoldAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "Founder & Owner • QuickWork CG",
                                fontSize = 11.sp,
                                color = SaffronPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "UPI: $inputUpiId • Mob: 9755955659",
                                fontSize = 11.sp,
                                color = Color(0xFFCBD5E1)
                            )
                        }
                    }
                }
            }
        }

        // 3. Admin 4-Tab Selector
        item {
            TabRow(
                selectedTabIndex = selectedAdminTab,
                containerColor = NavySurface,
                contentColor = SaffronPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedAdminTab]),
                        color = SaffronPrimary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                val tabs = listOf(
                    "📊 बचत (${ledgerEntries.size})",
                    "🛠️ सेवाएं (${allServices.size})",
                    "💳 GST व पेमेंट (${allTransactions.size})",
                    "👷 KYC (${allPartners.size})"
                )
                tabs.forEachIndexed { idx, label ->
                    Tab(
                        selected = selectedAdminTab == idx,
                        onClick = { selectedAdminTab = idx },
                        text = {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (selectedAdminTab == idx) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedAdminTab == idx) SaffronPrimary else Color(0xFF94A3B8)
                            )
                        }
                    )
                }
            }
        }

        // TAB 0: Automatic Ledger History
        if (selectedAdminTab == 0) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isHindi) "स्वतः बचत खाता बही विवरण" else "Automatic Platform Savings Entries",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Total: ₹${String.format(Locale.US, "%.1f", totalSavings)}",
                        fontSize = 11.sp,
                        color = TealSuccess,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (ledgerEntries.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                        Text("No ledger transactions recorded yet.", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    }
                }
            } else {
                items(ledgerEntries) { entry ->
                    LedgerItemCard(entry = entry, isHindi = isHindi)
                }
            }
        }

        // TAB 1: All Departments & 50+ Services Admin Manager
        if (selectedAdminTab == 1) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "कुल सेवाएं: ${filteredAdminServices.size} (11 विभाग)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Button(
                        onClick = { showAddServiceDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "➕ नई सेवा जोड़ें", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = serviceSearchQuery,
                    onValueChange = { serviceSearchQuery = it },
                    placeholder = { Text("सेवा या विभाग खोजें...", fontSize = 12.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SaffronPrimary) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronPrimary)
                )
            }

            items(filteredAdminServices) { service ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isHindi) service.titleHindi else service.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${service.category} • Base ₹${service.basePrice}",
                                fontSize = 11.sp,
                                color = SaffronPrimary
                            )
                        }
                        Surface(
                            color = TealSuccess.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Active",
                                color = TealSuccess,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // TAB 2: Payments Gateway & 18% GST Ledger
        if (selectedAdminTab == 2) {
            // Metrics Strip
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = NavyCard),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Total Volume", fontSize = 10.sp, color = Color.Gray)
                            Text("₹${String.format(Locale.US, "%.1f", totalTransactionVolume)}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = NavyCard),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("18% GST Collected", fontSize = 10.sp, color = GoldAccent)
                            Text("₹${String.format(Locale.US, "%.2f", totalGstTaxCollected)}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = NavyCard),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Success Count", fontSize = 10.sp, color = TealSuccess)
                            Text("${allTransactions.count { it.status == "SUCCESS" }} Txns", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TealSuccess)
                        }
                    }
                }
            }

            if (allTransactions.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                        Text("No payment transactions recorded yet.", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    }
                }
            } else {
                items(allTransactions) { tx ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = tx.id, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SaffronPrimary)
                                    Text(text = "Booking #${tx.bookingId} • ${tx.customerName}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                                }
                                Surface(
                                    color = if (tx.status == "SUCCESS") TealSuccess.copy(alpha = 0.15f) else Color(0xFF3B82F6).copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = tx.status,
                                        color = if (tx.status == "SUCCESS") TealSuccess else Color(0xFF3B82F6),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Base: ₹${tx.baseAmount.toInt()} | CGST 9%: ₹${String.format(Locale.US, "%.1f", tx.cgstAmount)} | SGST 9%: ₹${String.format(Locale.US, "%.1f", tx.sgstAmount)}", fontSize = 10.sp, color = Color.Gray)
                                Text("Total: ₹${String.format(Locale.US, "%.2f", tx.totalAmount)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TealSuccess)
                            }

                            Spacer(Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Gateway: ${tx.gateway} (${tx.paymentMethod})", fontSize = 10.sp, color = Color.Gray)

                                if (tx.status == "SUCCESS" && tx.refundStatus == "NONE") {
                                    OutlinedButton(
                                        onClick = {
                                            onProcessRefund(tx.id)
                                            Toast.makeText(context, "रिफंड प्रोसेस हो गया: ${tx.id}", Toast.LENGTH_SHORT).show()
                                        },
                                        shape = RoundedCornerShape(6.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        modifier = Modifier.height(28.dp)
                                    ) {
                                        Text("Refund", fontSize = 10.sp, color = Color.Red)
                                    }
                                } else if (tx.refundStatus == "REFUNDED") {
                                    Text("REFUNDED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3B82F6))
                                }
                            }
                        }
                    }
                }
            }
        }

        // TAB 3: Worker KYC & Approvals
        if (selectedAdminTab == 3) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "कारीगर व लेबर केवाईसी सत्यापन (${allPartners.size})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Approved: ${allPartners.count { it.registrationStatus == "APPROVED" }} | Pending: ${allPartners.count { it.registrationStatus == "PENDING" }}",
                        fontSize = 11.sp,
                        color = TealSuccess,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (allPartners.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                        Text("No partners registered yet.", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    }
                }
            } else {
                items(allPartners) { partner ->
                    val isApproved = partner.registrationStatus == "APPROVED"
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(NavySurface),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(partner.name.take(1), fontWeight = FontWeight.Bold, color = SaffronPrimary)
                                    }
                                    Spacer(Modifier.width(10.dp))
                                    Column {
                                        Text(partner.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("${partner.tradeCategory} • ${partner.locality}, ${partner.city}", fontSize = 11.sp, color = Color.Gray)
                                    }
                                }

                                Surface(
                                    color = if (isApproved) TealSuccess.copy(alpha = 0.15f) else GoldAccent.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = partner.registrationStatus,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isApproved) TealSuccess else Color(0xFFB45309),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(Modifier.height(8.dp))

                            Text("Doc: ${partner.documentType} (${partner.aadhaarNumber}) • Payout: ${partner.bankAccountOrUpi}", fontSize = 10.sp, color = Color.Gray)

                            Spacer(Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (!isApproved) {
                                    Button(
                                        onClick = {
                                            onUpdatePartnerStatus(partner.id, "APPROVED")
                                            Toast.makeText(context, "${partner.name} approved!", Toast.LENGTH_SHORT).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = TealSuccess),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                        Spacer(Modifier.width(4.dp))
                                        Text("Approve Worker", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    OutlinedButton(
                                        onClick = {
                                            onUpdatePartnerStatus(partner.id, "PENDING")
                                            Toast.makeText(context, "${partner.name} set to pending", Toast.LENGTH_SHORT).show()
                                        },
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Revoke", fontSize = 11.sp, color = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Service Dialog
    if (showAddServiceDialog) {
        var newTitle by remember { mutableStateOf("") }
        var newTitleHindi by remember { mutableStateOf("") }
        var newDepartment by remember { mutableStateOf(ServiceCategory.MASONRY_CONSTRUCTION.englishName) }
        var newPriceText by remember { mutableStateOf("499") }
        var showDeptPicker by remember { mutableStateOf(false) }

        val calculatedComm = remember(newPriceText, commissionRate) {
            val p = newPriceText.toDoubleOrNull() ?: 0.0
            p * (commissionRate / 100.0)
        }

        AlertDialog(
            onDismissRequest = { showAddServiceDialog = false },
            title = {
                Text(text = "नई सेवा व विभाग जोड़ें (Add Service)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("सेवा का नाम (English Title)") },
                        placeholder = { Text("e.g. Solar Panel Cleaning & Maintenance") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newTitleHindi,
                        onValueChange = { newTitleHindi = it },
                        label = { Text("सेवा का हिंदी नाम (Hindi Title)") },
                        placeholder = { Text("जैसे: सोलर पैनल सफाई एवं रिपेयर") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "विभाग चुनें (Department):", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDeptPicker = !showDeptPicker }
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = newDepartment, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SaffronPrimary)
                            Text(text = if (showDeptPicker) "▲" else "▼", fontSize = 11.sp)
                        }
                    }

                    if (showDeptPicker) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .padding(vertical = 4.dp)
                        ) {
                            LazyColumn(modifier = Modifier.padding(4.dp)) {
                                items(ServiceCategory.entries) { cat ->
                                    Text(
                                        text = "${cat.hindiName} (${cat.englishName})",
                                        fontSize = 11.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                newDepartment = cat.englishName
                                                showDeptPicker = false
                                            }
                                            .padding(vertical = 6.dp, horizontal = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newPriceText,
                        onValueChange = { if (it.length <= 6) newPriceText = it.filter { ch -> ch.isDigit() } },
                        label = { Text("मूल सेवा शुल्क (Base Price in ₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = NavySurface,
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "💡 ${commissionRate.toInt()}% एडमिन बचत: ₹${String.format(Locale.getDefault(), "%.1f", calculatedComm)} स्वतः खाते में सेव होगी।",
                            fontSize = 10.sp,
                            color = TealSuccess,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTitle.isBlank()) {
                            Toast.makeText(context, "कृपया सेवा का नाम लिखें", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val price = newPriceText.toIntOrNull() ?: 399
                        onAddNewService(newTitle.trim(), newTitleHindi.trim(), newDepartment, price)
                        showAddServiceDialog = false
                        Toast.makeText(context, "सेवा सफलतापूर्वक जोड़ी गई!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                ) {
                    Text("सेवा जोड़ें (Add)", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddServiceDialog = false }) {
                    Text("रद्द करें")
                }
            }
        )
    }
}

@Composable
private fun LedgerItemCard(
    entry: AdminSavingEntryEntity,
    isHindi: Boolean
) {
    val isCommission = entry.type == "SERVICE_COMMISSION"
    val dateFormat = remember { SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()) }
    val formattedDate = remember(entry.timestamp) { dateFormat.format(Date(entry.timestamp)) }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(if (isCommission) TealSuccess.copy(alpha = 0.15f) else GoldAccent.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isCommission) Icons.AutoMirrored.Filled.TrendingUp else Icons.Default.Paid,
                    contentDescription = null,
                    tint = if (isCommission) TealSuccess else GoldAccent,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entry.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${entry.payerInfo} • $formattedDate",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "UPI: ${entry.recipientUpiId}",
                    fontSize = 10.sp,
                    color = Color(0xFF64748B)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "+₹${String.format(Locale.getDefault(), "%.1f", entry.amount)}",
                    fontWeight = FontWeight.Black,
                    fontSize = 15.sp,
                    color = TealSuccess
                )
                Surface(
                    color = if (isCommission) TealSuccess.copy(alpha = 0.12f) else GoldAccent.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = if (isCommission) "${entry.commissionPercent.toInt()}% Cummins" else "Reg Pass",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCommission) TealSuccess else GoldAccent,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                    )
                }
            }
        }
    }
}
