package com.zxj.ui.component

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoPlayer(
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier,
    onSwitchFullScreen: (Boolean) -> Unit,
) {
    AndroidView(
        factory = {
            StyledPlayerView(it).apply {
                player = exoPlayer
                setFullscreenButtonClickListener {
                    onSwitchFullScreen(it)
                }
            }
        },
        modifier = modifier
    )

    val owner = LocalLifecycleOwner.current
    DisposableEffect(owner) {
        val observer = LifecycleEventObserver { source, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                exoPlayer.pause()
            } else if (event == Lifecycle.Event.ON_START) {
                exoPlayer.play()
            }
        }
        owner.lifecycle.addObserver(observer)
        onDispose {
            owner.lifecycle.removeObserver(observer)
        }
    }
}

//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun LookaheadLayoutScope.movableVideoPlayer(): @Composable LookaheadLayoutScope.() -> Unit {
//    return remember {
//        movableContentWithReceiverOf<LookaheadLayoutScope> {
//
//        }
//    }
//}


@Composable
fun SwitchVideoPlayer(
    exoPlayer: ExoPlayer
) {
    val context = LocalContext.current
    context as Activity
    if (context.isNotFullScreen()) {
        VideoPlayer(
            exoPlayer = exoPlayer,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
    } else {
        VideoPlayer(exoPlayer = exoPlayer, modifier = Modifier.fillMaxSize()) {
            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    BackHandler {
        if(!context.isNotFullScreen()) {
            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}

private fun Activity.isNotFullScreen(): Boolean {
    println("requestedOrientation = ${requestedOrientation}")
    return when (requestedOrientation) {
        ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED,

        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,
        ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT,

        ActivityInfo.SCREEN_ORIENTATION_SENSOR,
        ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT,

        ActivityInfo.SCREEN_ORIENTATION_USER,
        ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT -> true
        else -> false
    }
}