package com.zxj.user.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zxj.user.LoginRoute
import com.zxj.user.RegisterRoute

const val accountGraph = "/accountGraph"
const val loginRoute = "${accountGraph}/loginRoute"
const val registerRoute = "${accountGraph}/registerRoute"


fun NavGraphBuilder.accountGraph(
    onBack: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToLogin: () -> Unit
) {
    navigation(startDestination = loginRoute, route = accountGraph) {
        loginScreen(
            onBack = onBack,
            navigateToRegister = navigateToRegister
        )
        registerScreen(
            onBack = onBack,
            navigateToLogin = navigateToLogin
        )
    }
}

fun NavGraphBuilder.loginScreen(
    onBack: () -> Unit,
    navigateToRegister: () -> Unit
) {
    composable(loginRoute) {
        LoginRoute(
            modifier = Modifier.fillMaxSize(),
            onBack = onBack,
            navigateToRegister = navigateToRegister
        )
    }
}

fun NavGraphBuilder.registerScreen(
    onBack: () -> Unit,
    navigateToLogin: () -> Unit
) {
    composable(registerRoute) {
        RegisterRoute(
            modifier = Modifier.fillMaxSize(),
            onBack = onBack,
            navigateToLogin = navigateToLogin
        )
    }
}

fun NavController.navigateToLogin() {
    navigate(loginRoute) {
        popUpTo(registerRoute) {
            inclusive = true
            saveState = true
        }
        restoreState = true
    }
}

fun NavController.navigateToRegister() {
    navigate(registerRoute) {
        popUpTo(loginRoute) {
            inclusive = true
            saveState = true
        }
        restoreState = true
    }
}