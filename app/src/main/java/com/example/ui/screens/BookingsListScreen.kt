package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BookingEntity
import com.example.ui.components.DigitalInvoiceDialog
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavySurface
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TealSuccess
import java.util.Locale

@Composable
fun BookingsListScreen(
    bookings: List<BookingEntity>,
    isHindi: Boolean,
    onCallTechnician: (String) -> Unit,
    onWhatsAppTechnician: (phone: String, text: String) -> Unit,
    onAdvanceStatus: (bookingId: String, nextStatus: String) -> Unit,
    onRateBooking: (bookingId: String, stars: Int, review: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var ratingBookingId by remember { mutableStateOf<String?>(null) }
    var selectedStars by remember { mutableIntStateOf(5) }
    var reviewText by remember { mutableStateOf("") }
    var viewingInvoiceBooking by remember { mutableStateOf<BookingEntity?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            color = NavySurface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (isHindi) "मेरी बुकिंग्स (Active & Past Orders)" else "My Bookings & Live Tracking",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color.White
                )
                Text(
                    text = if (isHindi) "तकनीशियन की लाइव स्थिति, 18% GST टैक्स इनवॉइस व वारंटी" else "Real-time tracking, 18% GST invoices & 30-day warranty",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
                )
            }
        }

        if (bookings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isHindi) "अभी कोई बुकिंग नहीं है" else "No bookings yet",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isHindi) "होम या 50 सेवाओं से कोई भी सेवा बुक करें" else "Book any service from Home or 50 Services tab",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(bookings) { booking ->
                    BookingItemCard(
                        booking = booking,
                        isHindi = isHindi,
                        onCall = { onCallTechnician(booking.assignedPartnerPhone) },
                        onWhatsApp = {
                            val msg = "Namaste ${booking.assignedPartnerName}, regarding QuickWork CG Booking #${booking.id} for ${booking.serviceTitle} at ${booking.locality}, ${booking.city}."
                            onWhatsAppTechnician(booking.assignedPartnerPhone, msg)
                        },
                        onAdvanceStatus = { next -> onAdvanceStatus(booking.id, next) },
                        onOpenRating = {
                            ratingBookingId = booking.id
                            selectedStars = 5
                            reviewText = ""
                        },
                        onOpenInvoice = { viewingInvoiceBooking = booking }
                    )
                }
            }
        }
    }

    // Digital Tax Invoice & Warranty Card Modal
    viewingInvoiceBooking?.let { booking ->
        DigitalInvoiceDialog(
            booking = booking,
            isHindi = isHindi,
            onDismiss = { viewingInvoiceBooking = null }
        )
    }

    // Rating Dialog
    if (ratingBookingId != null) {
        AlertDialog(
            onDismissRequest = { ratingBookingId = null },
            title = {
                Text(
                    text = if (isHindi) "सेवा का अनुभव रेटिंग करें" else "Rate Technician Service",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "How was the work by technician?",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (i in 1..5) {
                            IconButton(onClick = { selectedStars = i }) {
                                Icon(
                                    imageVector = if (i <= selectedStars) Icons.Default.Star else Icons.Default.StarOutline,
                                    contentDescription = "Star $i",
                                    tint = GoldAccent,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { reviewText = it },
                        placeholder = { Text("Write feedback (e.g. fast, neat work, polite)...", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        ratingBookingId?.let { id ->
                            onRateBooking(id, selectedStars, reviewText)
                        }
                        ratingBookingId = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                ) {
                    Text("Submit Rating", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { ratingBookingId = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun BookingItemCard(
    booking: BookingEntity,
    isHindi: Boolean,
    onCall: () -> Unit,
    onWhatsApp: () -> Unit,
    onAdvanceStatus: (String) -> Unit,
    onOpenRating: () -> Unit,
    onOpenInvoice: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("booking_card_${booking.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Booking ID & Status Chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "#${booking.id}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = SaffronPrimary
                        )
                        if (booking.isEmergencyExpress) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = SaffronPrimary,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "30-MIN EXPRESS",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }
                    Text(
                        text = "${booking.dateSlot} • ${booking.timeSlot}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                StatusBadge(status = booking.bookingStatus)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Service details
            Text(
                text = if (isHindi) booking.serviceTitleHindi else booking.serviceTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "📍 ${booking.addressText}, ${booking.locality}, ${booking.city}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Assigned Technician Strip
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
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
                            Text(
                                text = booking.assignedPartnerName.take(1),
                                color = SaffronPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = booking.assignedPartnerName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verified",
                                    tint = TealSuccess,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                            Text(
                                text = "OTP: ${booking.securityOtp} • ⭐ ${booking.assignedPartnerRating}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(
                            onClick = onCall,
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(SaffronPrimary.copy(alpha = 0.15f))
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = SaffronPrimary, modifier = Modifier.size(16.dp))
                        }

                        IconButton(
                            onClick = onWhatsApp,
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF25D366).copy(alpha = 0.2f))
                        ) {
                            Icon(imageVector = Icons.Default.Message, contentDescription = "WhatsApp", tint = Color(0xFF25D366), modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Pricing & Automatic 18% GST Breakdown
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total Paid: ₹${String.format(Locale.US, "%.2f", booking.totalAmount)} (${booking.paymentMethod})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Includes 18% GST: ₹${String.format(Locale.US, "%.2f", booking.gstAmount)} (CGST + SGST)",
                        fontSize = 10.sp,
                        color = GoldAccent,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Surface(
                    color = if (booking.paymentStatus == "PAID_ONLINE" || booking.paymentStatus == "PAID_UPI") TealSuccess.copy(alpha = 0.15f) else SaffronPrimary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = if (booking.paymentStatus == "PAID_ONLINE" || booking.paymentStatus == "PAID_UPI") "PAID ONLINE" else "PAY ON WORK",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (booking.paymentStatus == "PAID_ONLINE" || booking.paymentStatus == "PAID_UPI") TealSuccess else SaffronPrimary,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Row: View Tax Invoice & Advance Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // View Digital Tax Invoice Button
                OutlinedButton(
                    onClick = onOpenInvoice,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Tax Invoice & Warranty", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                // Next status simulation button
                when (booking.bookingStatus) {
                    "CONFIRMED" -> {
                        Button(
                            onClick = { onAdvanceStatus("EN_ROUTE") },
                            colors = ButtonDefaults.buttonColors(containerColor = NavySurface),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("En Route", fontSize = 11.sp, color = Color.White)
                        }
                    }
                    "EN_ROUTE" -> {
                        Button(
                            onClick = { onAdvanceStatus("IN_PROGRESS") },
                            colors = ButtonDefaults.buttonColors(containerColor = NavySurface),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("Start Work", fontSize = 11.sp, color = Color.White)
                        }
                    }
                    "IN_PROGRESS" -> {
                        Button(
                            onClick = { onAdvanceStatus("COMPLETED") },
                            colors = ButtonDefaults.buttonColors(containerColor = TealSuccess),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("Complete", fontSize = 11.sp, color = Color.White)
                        }
                    }
                    "COMPLETED" -> {
                        if (booking.ratingStars == 0) {
                            OutlinedButton(
                                onClick = onOpenRating,
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Rate", fontSize = 11.sp)
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
                                Text(
                                    text = " ${booking.ratingStars}★",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: String) {
    val (label, color) = when (status) {
        "CONFIRMED" -> "Confirmed" to Color(0xFF2563EB)
        "ASSIGNED" -> "Assigned" to Color(0xFF7C3AED)
        "EN_ROUTE" -> "En Route (ETA 15m)" to Color(0xFFEA580C)
        "IN_PROGRESS" -> "In Progress" to Color(0xFFD97706)
        "COMPLETED" -> "Completed" to Color(0xFF16A34A)
        else -> status to Color(0xFF64748B)
    }

    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}
