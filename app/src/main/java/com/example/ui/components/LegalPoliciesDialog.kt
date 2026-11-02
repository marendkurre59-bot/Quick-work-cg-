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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavySurface
import com.example.ui.theme.SaffronPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegalPoliciesDialog(
    initialTab: Int = 0,
    isHindi: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedTab by remember { mutableIntStateOf(initialTab) }

    val tabTitles = listOf(
        if (isHindi) "गोपनीयता नीति" else "Privacy Policy",
        if (isHindi) "नियम व शर्तें" else "Terms of Service",
        if (isHindi) "रिफंड नीति" else "Refund Policy"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
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
                        Icon(Icons.Default.Policy, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = if (isHindi) "कानूनी व सुरक्षा नीतियां" else "Legal & Security Policies",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_legal_policies")) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(Modifier.height(8.dp))

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = NavySurface,
                contentColor = SaffronPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = SaffronPrimary
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTab == index) SaffronPrimary else Color.Gray
                            )
                        }
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
            ) {
                when (selectedTab) {
                    0 -> PrivacyPolicyContent(isHindi)
                    1 -> TermsAndConditionsContent(isHindi)
                    2 -> RefundPolicyContent(isHindi)
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(if (isHindi) "समझ गया (I Understand & Accept)" else "I Understand & Accept", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun PrivacyPolicyContent(isHindi: Boolean) {
    Card(
        colors = CardDefaults.cardColors(containerColor = NavyCard),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text("1. Worker & Customer Consent (सहमति आधारित डेटा)", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 13.sp)
            Text(
                "QuickWork CG collects customer and worker information (Name, Phone number, City, Locality, Work Trade) solely for facilitating on-demand door-to-door services across Chhattisgarh.",
                fontSize = 11.sp, color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(vertical = 4.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text("2. Masked Identity Documents (आधार व पहचान सुरक्षा)", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 13.sp)
            Text(
                "We strictly enforce offline masked document verification. We do NOT store biometric fingerprints, iris scans, or unmasked 12-digit Aadhaar numbers. All stored references are masked (e.g., XXXX-XXXX-7182).",
                fontSize = 11.sp, color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(vertical = 4.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text("3. Live Selfie & Admin Review", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 13.sp)
            Text(
                "Technicians submit a live photo solely to prevent fraud and impersonation. Registration is subject to verification by Admin Narendra Kumar Kurre before going live.",
                fontSize = 11.sp, color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun TermsAndConditionsContent(isHindi: Boolean) {
    Card(
        colors = CardDefaults.cardColors(containerColor = NavyCard),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text("1. Transparent Pricing & 18% GST (टैक्स मानक)", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 13.sp)
            Text(
                "All digital transactions itemize 18% GST (Central GST 9% + Chhattisgarh State SGST 9%) as per government norms for online aggregators. Transparent billing guarantees zero hidden charges.",
                fontSize = 11.sp, color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(vertical = 4.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text("2. Doorstep Safety & OTP Verification", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 13.sp)
            Text(
                "Work begins only when the customer provides the 4-digit security OTP to the assigned verified technician. This safeguards both homeowner and artisan.",
                fontSize = 11.sp, color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(vertical = 4.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text("3. Fair Wage & Instant Payouts", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 13.sp)
            Text(
                "Workers receive 80-85% of job earnings directly to their bank account or UPI ID. Platform commission (15-20%) covers insurance, dispute resolution, and cloud servers.",
                fontSize = 11.sp, color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun RefundPolicyContent(isHindi: Boolean) {
    Card(
        colors = CardDefaults.cardColors(containerColor = NavyCard),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text("1. 100% Cancellation Refund", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 13.sp)
            Text(
                "If a customer cancels the booking before the technician departs, 100% of the online payment is refunded to the original payment source within 24 hours.",
                fontSize = 11.sp, color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(vertical = 4.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text("2. 30-Day Work Warranty Guarantee", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 13.sp)
            Text(
                "Every completed job includes a 30-day Quality Guarantee. If any issue re-occurs within 30 days, QuickWork CG dispatches a certified technician for free re-inspection and repair.",
                fontSize = 11.sp, color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(vertical = 4.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text("3. Founder Escalation Helpdesk", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 13.sp)
            Text(
                "Direct contact: Narendra Kumar Kurre (Founder & Owner) at +91 9755955659 for payment reversals or complaints.",
                fontSize = 11.sp, color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}
