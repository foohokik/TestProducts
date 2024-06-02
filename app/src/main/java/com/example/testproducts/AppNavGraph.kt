package com.example.testproducts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.testproducts.screens.PRODUCT_SCREEN_ROUTE
import com.example.testproducts.screens.detailScreen
import com.example.testproducts.screens.navigateToDetails
import com.example.testproducts.screens.productScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = PRODUCT_SCREEN_ROUTE
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        productScreen(onProductClick = { navController.navigateToDetails(it) })
        detailScreen()
    }
}