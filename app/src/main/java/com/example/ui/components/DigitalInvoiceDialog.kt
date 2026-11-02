package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BookingEntity
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavySurface
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TealSuccess
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalInvoiceDialog(
    booking: BookingEntity,
    isHindi: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val invoiceNo = if (booking.invoiceNumber.isNotBlank()) booking.invoiceNumber else "QW-INV-2026-${booking.id}"
    val formattedDate = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(booking.createdAt))

    val shareText = buildString {
        appendLine("🧾 *QUICKWORK CG – DIGITAL WORK PLATFORM*")
        appendLine("छत्तीसगढ़ कारीगर एवं मजदूर सेवा | Tax Invoice & Warranty")
        appendLine("----------------------------------------")
        appendLine("📄 *Invoice No:* $invoiceNo")
        appendLine("📅 *Date:* $formattedDate")
        appendLine("👤 *Customer:* ${booking.customerName} (${booking.customerPhone})")
        appendLine("📍 *Address:* ${booking.locality}, ${booking.city}")
        appendLine("🛠️ *Service:* ${if (isHindi) booking.serviceTitleHindi else booking.serviceTitle}")
        appendLine("----------------------------------------")
        appendLine("💵 *Base Service Fee:* ₹${booking.serviceBasePrice.toInt()}")
        appendLine("🏛️ *CGST (9%):* ₹${String.format(Locale.US, "%.2f", booking.cgstAmount)}")
        appendLine("🏛️ *SGST (9%):* ₹${String.format(Locale.US, "%.2f", booking.sgstAmount)}")
        appendLine("🧾 *Total 18% GST:* ₹${String.format(Locale.US, "%.2f", booking.gstAmount)}")
        appendLine("🛵 *Visiting / Inspection:* ₹${booking.visitingFee.toInt()}")
        appendLine("💰 *TOTAL PAID:* ₹${String.format(Locale.US, "%.2f", booking.totalAmount)}")
        appendLine("💳 *Payment Mode:* ${booking.paymentMethod}")
        appendLine("----------------------------------------")
        appendLine("👷 *Assigned Expert:* ${booking.assignedPartnerName} (${booking.assignedPartnerPhone})")
        appendLine("🔐 *Doorstep Security OTP:* ${booking.securityOtp}")
        appendLine("🛡️ *30-DAY WORK WARRANTY ASSURED*")
        appendLine("Narendra Kumar Kurre (Founder & Owner)")
        appendLine("📞 Support: +91 9755955659 | UPI: narendkurre123@ybl")
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
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
            // Header with Close
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(NavySurface, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.ReceiptLong,
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isHindi) "डिजिटल टैक्स इनवॉइस व गारंटी" else "Digital Tax Invoice & Warranty",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = invoiceNo,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
                IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_invoice_dialog")) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(Modifier.height(14.dp))

            // Main Invoice Card
            Card(
                colors = CardDefaults.cardColors(containerColor = NavyCard),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Organization Banner
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "QUICKWORK CG",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = GoldAccent
                            )
                            Text(
                                text = "Digital Work Platform (छत्तीसगढ़)",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                        Surface(
                            color = TealSuccess.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "PAID & VERIFIED",
                                color = TealSuccess,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    Spacer(Modifier.height(12.dp))

                    // Booking & Customer details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("ग्राहक (Customer):", fontSize = 10.sp, color = Color.Gray)
                            Text(booking.customerName, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                            Text(booking.customerPhone, fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                        }
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                            Text("स्थान (City):", fontSize = 10.sp, color = Color.Gray)
                            Text("${booking.locality}, ${booking.city}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                            Text(formattedDate, fontSize = 9.sp, color = Color.White.copy(alpha = 0.7f))
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // Service Title
                    Surface(
                        color = NavySurface,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("सेवा का नाम (Service):", fontSize = 10.sp, color = Color.Gray)
                            Text(
                                text = if (isHindi) booking.serviceTitleHindi else booking.serviceTitle,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(Modifier.height(14.dp))
                    Text("टैक्स व भुगतान विवरण (Tax & Bill Itemization):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                    Spacer(Modifier.height(8.dp))

                    // Price Breakdown Table
                    BillRow(label = "Base Service Fee (मूल कार्य शुल्क):", value = "₹${booking.serviceBasePrice.toInt()}")
                    BillRow(label = "CGST (Central GST @ 9%):", value = "₹${String.format(Locale.US, "%.2f", booking.cgstAmount)}")
                    BillRow(label = "SGST (Chhattisgarh GST @ 9%):", value = "₹${String.format(Locale.US, "%.2f", booking.sgstAmount)}")
                    BillRow(
                        label = "Total 18% GST (कुल जीएसटी):", 
                        value = "₹${String.format(Locale.US, "%.2f", booking.gstAmount)}",
                        valueColor = GoldAccent
                    )
                    BillRow(label = "Visiting & Inspection Fee:", value = "₹${booking.visitingFee.toInt()}")

                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider(color = Color.White.copy(alpha = 0.15f))
                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "कुल देय राशि (TOTAL PAID):",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "₹${String.format(Locale.US, "%.2f", booking.totalAmount)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TealSuccess
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    // Assigned Partner & OTP
                    Surface(
                        color = NavySurface,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("सत्यापित कारीगर (Assigned Technician):", fontSize = 9.sp, color = Color.Gray)
                                Text(booking.assignedPartnerName, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(booking.assignedPartnerPhone, fontSize = 11.sp, color = SaffronPrimary)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("डोरस्टेप OTP", fontSize = 9.sp, color = Color.Gray)
                                Surface(
                                    color = SaffronPrimary,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = booking.securityOtp,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // 30-Day Warranty Badge
                    Surface(
                        color = GoldAccent.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Verified,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "30-DAY QUICKWORK CG WARRANTY",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldAccent
                                )
                                Text(
                                    text = "कार्योपरांत 30 दिनों तक निःशुल्क री-विजिट और संतुष्टि गारंटी।",
                                    fontSize = 10.sp,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Authorized by: Narendra Kumar Kurre (Founder & Owner)\nQuickWork CG Digital Work Platform • Contact: 9755955659",
                        fontSize = 9.sp,
                        color = Color.Gray,
                        lineHeight = 13.sp
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Action Buttons: WhatsApp Share & Copy Text
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("QuickWork Invoice", shareText)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "इनवॉइस विवरण कॉपी हो गया (Copied)", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("copy_invoice_btn")
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Copy Bill", fontSize = 13.sp)
                }

                Button(
                    onClick = {
                        try {
                            val url = "https://api.whatsapp.com/send?text=${Uri.encode(shareText)}"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Share QuickWork Invoice"))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                    modifier = Modifier
                        .weight(1.3f)
                        .height(48.dp)
                        .testTag("share_whatsapp_invoice_btn")
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("WhatsApp Share", fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun BillRow(
    label: String,
    value: String,
    valueColor: Color = Color.White
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
        Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = valueColor)
    }
}
