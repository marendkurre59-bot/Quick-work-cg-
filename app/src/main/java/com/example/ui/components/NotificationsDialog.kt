package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavySurface
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TealSuccess

data class AppNotificationItem(
    val id: String,
    val titleHindi: String,
    val titleEnglish: String,
    val messageHindi: String,
    val messageEnglish: String,
    val time: String,
    val type: String // "booking", "payment", "offer", "worker"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsDialog(
    isHindi: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val notifications = listOf(
        AppNotificationItem(
            id = "notif-1",
            titleHindi = "बुकिंग स्वीकृत (Booking Confirmed)",
            titleEnglish = "Booking Confirmed #BK-7821",
            messageHindi = "आपकी बिजली मिस्त्री सेवा बुकिंग स्वीकृत हो चुकी है। तकनीशियन 30 मिनट में पहुंचेंगे। डोर OTP: 4821",
            messageEnglish = "Your electrician service is confirmed. Technician arriving in 30 mins. Door OTP: 4821",
            time = "10 मिनट पहले",
            type = "booking"
        ),
        AppNotificationItem(
            id = "notif-2",
            titleHindi = "18% GST टैक्स इनवॉइस जारी",
            titleEnglish = "18% GST Tax Invoice Generated",
            messageHindi = "इनवॉइस #INV-2026-7821 (₹235) तैयार है। 30 दिनों की निःशुल्क संतुष्टि कार्य वारंटी सक्रिय है।",
            messageEnglish = "Tax Invoice #INV-2026-7821 (₹235) generated with 30-day work warranty included.",
            time = "25 मिनट पहले",
            type = "invoice"
        ),
        AppNotificationItem(
            id = "notif-3",
            titleHindi = "UPI पेमेंट सफल (Payment Received)",
            titleEnglish = "UPI Payment Successful",
            messageHindi = "₹235 का भुगतान आधिकारिक मर्चेंट VPA narendkurre123@ybl पर प्राप्त हो गया है।",
            messageEnglish = "Payment of ₹235 successfully received via UPI (narendkurre123@ybl).",
            time = "1 घंटा पहले",
            type = "payment"
        ),
        AppNotificationItem(
            id = "notif-4",
            titleHindi = "विशेष ऑफर (15% OFF)",
            titleEnglish = "Special Offer: 15% OFF",
            messageHindi = "अपनी प्रथम सेवा बुकिंग पर 15% की छूट पाने के लिए कूपन कोड QUICK15 का उपयोग करें।",
            messageEnglish = "Use promo code QUICK15 on your first booking to get flat 15% off.",
            time = "आज",
            type = "offer"
        ),
        AppNotificationItem(
            id = "notif-5",
            titleHindi = "कारीगर KYC सत्यापन सफल",
            titleEnglish = "Worker KYC Approved",
            messageHindi = "कारीगर लाइव सेल्फी और मास्कड पहचान पत्र सत्यापन एडमिन द्वारा स्वीकृत कर लिया गया है।",
            messageEnglish = "Worker live selfie and masked KYC documents verified & approved by admin.",
            time = "कल",
            type = "worker"
        )
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = NavySurface
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
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
                            .background(SaffronPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = SaffronPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isHindi) "सूचनाएं एवं अपडेट्स" else "Notifications & Updates",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = if (isHindi) "बुकिंग, पेमेंट एवं कारीगर लाइव अपडेट" else "Booking, payment & worker alerts",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("close_notifications_btn")
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                }
            }

            Spacer(Modifier.height(14.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            ) {
                items(notifications) { item ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = NavyCard),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when (item.type) {
                                            "payment" -> TealSuccess.copy(alpha = 0.2f)
                                            "offer" -> GoldAccent.copy(alpha = 0.2f)
                                            "invoice" -> Color(0xFF3B82F6).copy(alpha = 0.2f)
                                            else -> SaffronPrimary.copy(alpha = 0.2f)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = when (item.type) {
                                        "payment" -> Icons.Default.Payment
                                        "offer" -> Icons.Default.LocalOffer
                                        "invoice" -> Icons.Default.ReceiptLong
                                        "worker" -> Icons.Default.VerifiedUser
                                        else -> Icons.Default.CheckCircle
                                    },
                                    contentDescription = null,
                                    tint = when (item.type) {
                                        "payment" -> TealSuccess
                                        "offer" -> GoldAccent
                                        "invoice" -> Color(0xFF3B82F6)
                                        else -> SaffronPrimary
                                    },
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = if (isHindi) item.titleHindi else item.titleEnglish,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = item.time,
                                        fontSize = 10.sp,
                                        color = Color(0xFF94A3B8)
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = if (isHindi) item.messageHindi else item.messageEnglish,
                                    fontSize = 11.5.sp,
                                    color = Color(0xFFCBD5E1),
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
