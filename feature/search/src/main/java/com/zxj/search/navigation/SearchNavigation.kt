package com.zxj.search.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.zxj.search.SearchResultRoute
import com.zxj.search.SearchRoute


const val searchGraphRoutePattern = "/searchGraph"
const val searchRoute = "/searchGraph/search"

const val searchDetailRoute = "/searchGraph/searchDetail"
internal const val searchDetailRouteArg = "searchContent"

internal class SearchDetailArgs private constructor(val searchContent: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(Uri.decode(savedStateHandle[searchDetailRouteArg]))
}

// 搜索图
fun NavGraphBuilder.searchGraph(
    onBack: () -> Unit,
    onBrowser: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    navigation(startDestination = searchRoute, route = searchGraphRoutePattern) {
        searchScreen(onBack = onBack, onSearch = onSearch)
        searchDetailScreen(onBack = onBack, onBrowser = onBrowser)
    }
}

// 搜索内容
fun NavController.navigateToSearch() {
    navigate(searchRoute)
}

fun NavGraphBuilder.searchScreen(
    onBack: () -> Unit,
    onSearch: (String) -> Unit
) {
    composable(searchRoute) {
        SearchRoute(
            modifier = Modifier.fillMaxSize(),
            onBack = onBack,
            onSearch = onSearch
        )
    }
}

// 搜索详情
fun NavController.navigationToSearchDetail(searchContent: String) {
    val encodeId = Uri.encode(searchContent)
    navigate("${searchDetailRoute}/$encodeId")
}

fun NavGraphBuilder.searchDetailScreen(
    onBack: () -> Unit,
    onBrowser: (String) -> Unit
) {
    composable(
        route = "$searchDetailRoute/$searchDetailRouteArg",
        arguments = listOf(
            navArgument(searchDetailRouteArg) { type = NavType.StringType }
        )
    ) {
        SearchResultRoute(
            onBack = onBack,
            onItemClick = { onBrowser(it.link) },
            modifier = Modifier.fillMaxSize()
        )
    }
}