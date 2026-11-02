package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Engineering
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.theme.NavySurface
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary

@Composable
fun BottomNavBar(
    currentScreen: AppScreen,
    isHindi: Boolean,
    bookingsCount: Int,
    onTabSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = NavySurface,
        contentColor = Color.White,
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("main_bottom_nav_bar")
    ) {
        // Tab 1: Home
        NavigationBarItem(
            selected = currentScreen == AppScreen.HOME,
            onClick = { onTabSelected(AppScreen.HOME) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = if (isHindi) "होम" else "Home",
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == AppScreen.HOME) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SaffronPrimary,
                selectedTextColor = SaffronPrimary,
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8),
                indicatorColor = SaffronLight.copy(alpha = 0.2f)
            ),
            modifier = Modifier.testTag("nav_tab_home")
        )

        // Tab 2: 50 Services Catalog
        NavigationBarItem(
            selected = currentScreen == AppScreen.SERVICES_50,
            onClick = { onTabSelected(AppScreen.SERVICES_50) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.SERVICES_50) Icons.Filled.Build else Icons.Outlined.Build,
                    contentDescription = "50 Services"
                )
            },
            label = {
                Text(
                    text = if (isHindi) "50 सेवाएं" else "50 Services",
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == AppScreen.SERVICES_50) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SaffronPrimary,
                selectedTextColor = SaffronPrimary,
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8),
                indicatorColor = SaffronLight.copy(alpha = 0.2f)
            ),
            modifier = Modifier.testTag("nav_tab_50_services")
        )

        // Tab 3: Bookings
        NavigationBarItem(
            selected = currentScreen == AppScreen.BOOKINGS,
            onClick = { onTabSelected(AppScreen.BOOKINGS) },
            icon = {
                if (bookingsCount > 0) {
                    BadgedBox(
                        badge = {
                            Badge(containerColor = SaffronPrimary) {
                                Text("$bookingsCount", color = Color.White)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (currentScreen == AppScreen.BOOKINGS) Icons.AutoMirrored.Filled.ReceiptLong else Icons.AutoMirrored.Outlined.ReceiptLong,
                            contentDescription = "Bookings"
                        )
                    }
                } else {
                    Icon(
                        imageVector = if (currentScreen == AppScreen.BOOKINGS) Icons.AutoMirrored.Filled.ReceiptLong else Icons.AutoMirrored.Outlined.ReceiptLong,
                        contentDescription = "Bookings"
                    )
                }
            },
            label = {
                Text(
                    text = if (isHindi) "बुकिंग्स" else "Bookings",
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == AppScreen.BOOKINGS) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SaffronPrimary,
                selectedTextColor = SaffronPrimary,
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8),
                indicatorColor = SaffronLight.copy(alpha = 0.2f)
            ),
            modifier = Modifier.testTag("nav_tab_bookings")
        )

        // Tab 4: Partner / Worker Portal
        NavigationBarItem(
            selected = currentScreen == AppScreen.PARTNER_PORTAL,
            onClick = { onTabSelected(AppScreen.PARTNER_PORTAL) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.PARTNER_PORTAL) Icons.Filled.Engineering else Icons.Outlined.Engineering,
                    contentDescription = "Partner"
                )
            },
            label = {
                Text(
                    text = if (isHindi) "कारीगर साथी" else "Partner",
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == AppScreen.PARTNER_PORTAL) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SaffronPrimary,
                selectedTextColor = SaffronPrimary,
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8),
                indicatorColor = SaffronLight.copy(alpha = 0.2f)
            ),
            modifier = Modifier.testTag("nav_tab_partner")
        )

        // Tab 5: Admin Savings ("My Account Saving Features")
        NavigationBarItem(
            selected = currentScreen == AppScreen.ADMIN_SAVINGS,
            onClick = { onTabSelected(AppScreen.ADMIN_SAVINGS) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.ADMIN_SAVINGS) Icons.Filled.AccountBalanceWallet else Icons.Outlined.AccountBalanceWallet,
                    contentDescription = "Savings"
                )
            },
            label = {
                Text(
                    text = if (isHindi) "खाता बचत" else "Savings",
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == AppScreen.ADMIN_SAVINGS) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SaffronPrimary,
                selectedTextColor = SaffronPrimary,
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8),
                indicatorColor = SaffronLight.copy(alpha = 0.2f)
            ),
            modifier = Modifier.testTag("nav_tab_savings")
        )
    }
}
