package com.zxj.article.navigation

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.*
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.zxj.article.*
import com.zxj.article.index.IndexRoute
import com.zxj.article.project.ProjectRoute
import com.zxj.article.square.SquareRoute
import com.zxj.article.system.KnowledgeSystemDetailRoute
import com.zxj.article.system.SystemRoute
import com.zxj.article.wechat.WechatRoute
import com.zxj.model.Knowledge

const val homeRoute = "homeRoute"
const val knowledgeSystemDetailRoute = "knowledgeSystemDetailRoute"
const val knowledgeSystemDetailNameArgs = "name"
const val knowledgeSystemDetailKnowledgesArgs = "knowledges"

// 对外
fun NavController.navigateToHome() {
    navigate(homeRoute)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeScreen(
    navigateToSearch: () -> Unit,
    navigateToCollect: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToScore: () -> Unit,
    navigateToRank: () -> Unit,
    navigateToShare: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToTODO: () -> Unit,
    navigateToKnowledgeSystemDetail: (String, List<Knowledge>) -> Unit,
    navigateToBrowser: (String) -> Unit
) {
    composable(homeRoute) {
        HomeRoute(
            modifier = Modifier.fillMaxSize(),
            navigateToSearch = navigateToSearch,
            navigateToCollect = navigateToCollect,
            navigateToLogin = navigateToLogin,
            navigateToScore = navigateToScore,
            navigateToRank = navigateToRank,
            navigateToShare = navigateToShare,
            navigateToSetting = navigateToSetting,
            navigateToTODO = navigateToTODO,
            navigateToBrowser = navigateToBrowser,
            navigateToKnowledgeSystemDetail = navigateToKnowledgeSystemDetail
        )
    }
}

fun NavController.navigateToKnowledgeSystemDetail(name: String, knowledges: List<Knowledge>) {
    val json = Uri.encode(com.alibaba.fastjson.JSONObject.toJSONString(knowledges))
    navigate("$knowledgeSystemDetailRoute/$name/$json")
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.knowledgeSystemDetailScreen(
    onBack: () -> Unit,
    navigateToBrowser: (String) -> Unit
) {
    composable(
        route = "$knowledgeSystemDetailRoute/{$knowledgeSystemDetailNameArgs}/{$knowledgeSystemDetailKnowledgesArgs}",
        arguments = listOf(
            navArgument(knowledgeSystemDetailNameArgs) {
                this.type = NavType.StringType
            },
            navArgument(knowledgeSystemDetailKnowledgesArgs) {
                this.type = NavType.StringType
            }
        )
    ) {
        KnowledgeSystemDetailRoute(
            modifier = Modifier.fillMaxSize(),
            onBack = onBack,
            navigateToBrowser = navigateToBrowser
        )
    }
}

// 对内
internal const val indexRoute = "indexRoute"
internal const val wechatRoute = "wechatRoute"
internal const val projectRoute = "projectRoute"
internal const val systemRoute = "systemRoute"
internal const val squareRoute = "squareRoute"

internal fun NavController.navigateToIndex() {
    navigate(indexRoute) {
        popUpTo(indexRoute) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

internal fun NavController.navigateToWechat() {
    navigate(wechatRoute) {
        popUpTo(indexRoute) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

internal fun NavController.navigateToProject() {
    navigate(projectRoute) {
        popUpTo(indexRoute) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

internal fun NavController.navigateToSystem() {
    navigate(systemRoute) {
        popUpTo(indexRoute) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

internal fun NavController.navigateToSquare() {
    navigate(squareRoute) {
        popUpTo(indexRoute) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.indexScreen(navigateToBrowser: (String) -> Unit) {
    composable(indexRoute) {
        IndexRoute(
            modifier = Modifier.fillMaxSize(),
            navigateToBrowser = navigateToBrowser,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.wechatScreen(navigateToBrowser: (String) -> Unit) {
    composable(wechatRoute) {
        WechatRoute(
            modifier = Modifier.fillMaxSize(),
            navigateToBrowser = navigateToBrowser,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.projectScreen(navigateToBrowser: (String) -> Unit) {
    composable(projectRoute) {
        ProjectRoute(
            modifier = Modifier.fillMaxSize(),
            navigateToBrowser = navigateToBrowser,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.squareScreen(navigateToBrowser: (String) -> Unit) {
    composable(squareRoute) {
        SquareRoute(
            modifier = Modifier.fillMaxSize(),
            navigateToBrowser = navigateToBrowser,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.systemScreen(
    navigateToBrowser: (String) -> Unit,
    navigateToKnowledgeSystemDetail: (String, List<Knowledge>) -> Unit
) {
    composable(systemRoute) {
        SystemRoute(
            modifier = Modifier.fillMaxSize(),
            navigateToBrowser = navigateToBrowser,
            navigateToKnowledgeSystemDetail = navigateToKnowledgeSystemDetail
        )
    }
}