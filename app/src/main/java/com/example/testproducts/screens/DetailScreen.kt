package com.example.testproducts.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.testproducts.presentation.detail.DetailView

const val PRODUCT_ID_ARG = "productIdArg"
const val BASE_ROUTE  ="details"
const val DETAIL_SCREEN_ROUTE = "$BASE_ROUTE/{${PRODUCT_ID_ARG}}"

fun NavGraphBuilder.detailScreen() {
    composable(route = DETAIL_SCREEN_ROUTE, arguments = listOf(
        navArgument(PRODUCT_ID_ARG) { type = NavType.IntType}
    )) {
        DetailView()
    }
}

fun NavHostController.navigateToDetails(id: Int) {
    navigate("$BASE_ROUTE/$id")
}