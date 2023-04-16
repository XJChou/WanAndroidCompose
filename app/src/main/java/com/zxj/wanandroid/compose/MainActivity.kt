package com.zxj.wanandroid.compose

import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.values
import androidx.core.graphics.withMatrix
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.navigation.WanAndroidNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 声明自身管理WindowInsets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // 当前的主题
            WanAndroidTheme(WanAndroidTheme.theme) {

                // 窗体设置
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(Color.Transparent) // 状态栏设置为透明
                systemUiController.setNavigationBarColor(Color.Black)

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    color = WanAndroidTheme.colors.windowBackground
                ) {
                    WanAndroidNavHost(
                        navController = rememberAnimatedNavController(),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewImageDoubleTap() {
    val imageBitmap = ImageBitmap.imageResource(id = R.drawable.image)
    var outerMatrix by remember { mutableStateOf(Matrix()) }
    var width by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }
    Canvas(modifier = Modifier
        .fillMaxSize()
        .onPlaced {
            width = it.size.width
            height = it.size.height
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = {
                    val matrix = Matrix()
                    if (outerMatrix.values()[0] == 2f) {
                        outerMatrix = matrix
                    } else {
                        // 指定位置放大
                        matrix.postScale(2f, 2f, it.x, it.y)
                        // 先偏移指定位置
                        matrix.postTranslate(width / 2f - it.x, height / 2f - it.y)
                        outerMatrix = matrix
                    }
                }
            )
        }
        .pointerInput(Unit) {
            val currentContext = currentCoroutineContext()
            awaitEachGesture {
                awaitFirstDown()
                while (currentContext.isActive) {
                    val event = awaitPointerEvent()
                    if (event.type == PointerEventType.Move) {
                        val matrix = Matrix(outerMatrix)
                        val offset = event.changes[0].let { it.position - it.previousPosition }
                        matrix.postTranslate(offset.x, offset.y)    // post：位移都是原尺寸
                        outerMatrix = matrix    // 更新
                    }
                }
            }
        }
    ) {
        // 全屏占满
        val sourceRect =
            RectF(0f, 0f, imageBitmap.width.toFloat(), imageBitmap.height.toFloat())
        val viewRect = RectF(0f, 0f, size.width, size.height)

        val innerMatrix = Matrix()
        // 图片本身变化的差值
        innerMatrix.setRectToRect(sourceRect, viewRect, Matrix.ScaleToFit.CENTER)
        // 外部操作变化差值
        innerMatrix.postConcat(outerMatrix)
        drawContext.canvas.nativeCanvas.withMatrix(innerMatrix) {
            drawImage(imageBitmap)
        }
    }
}
