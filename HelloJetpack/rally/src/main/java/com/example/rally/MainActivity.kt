/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.rally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.rally.data.UserData
import com.example.rally.ui.components.RallyTabRow
import com.example.rally.ui.screens.AccountsBody
import com.example.rally.ui.screens.BillsBody
import com.example.rally.ui.screens.OverviewBody
import com.example.rally.ui.screens.RallyScreen
import com.example.rally.ui.screens.RallyScreen.Accounts
import com.example.rally.ui.screens.RallyScreen.Bills
import com.example.rally.ui.screens.RallyScreen.Overview
import com.example.rally.ui.screens.RallyScreen.values
import com.example.rally.ui.screens.SingleAccountBody
import com.example.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
        val allScreens = values().toList()
        val navController = rememberNavController()
        val backstackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = RallyScreen.fromRoute(backstackEntry.value?.destination?.route)
        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = allScreens,
                    currentScreen = currentScreen,
                    onTabSelected = { screen -> navController.navigate(screen.name) },
                )
            }
        ) { innerPadding -> RallyNavHost(navController, Modifier.padding(innerPadding)) }
    }
}

@Composable
fun RallyNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Overview.name,
        modifier = modifier
    ) {
        composable(Overview.name) {
            OverviewBody(
                onClickSeeAllAccounts = { navController.navigate(Accounts.name) },
                onClickSeeAllBills = { navController.navigate(Bills.name) },
                onAccountClick = { name -> navigateToSingleAccount(navController, name) },
            )
        }
        composable(Accounts.name) {
            AccountsBody(UserData.accounts) { name ->
                navigateToSingleAccount(navController, name)
            }
        }
        composable(Bills.name) {
            BillsBody(UserData.bills)
        }
        val accountsName = Accounts.name
        composable(
            route = "$accountsName/{name}",
            arguments = listOf(navArgument("name") {
                type = NavType.StringType
            }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "rally://$accountsName/{name}"
            }),
        ) { entry ->
            val accountName = entry.arguments?.getString("name")
            val account = UserData.getAccount(accountName)
            SingleAccountBody(account)
        }
    }
}

private fun navigateToSingleAccount(navController: NavHostController, accountName: String) {
    navController.navigate("${Accounts.name}/$accountName")
}
