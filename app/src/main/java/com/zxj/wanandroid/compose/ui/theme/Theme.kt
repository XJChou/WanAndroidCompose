package com.zxj.wanandroid.compose.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*

object WanAndroidTheme {
    /**
     * 提供给别人使用
     */

    var theme by mutableStateOf(Theme.Normal)

    fun changeTheme(theme: Theme?=null){
        this.theme= theme
            ?: if (this.theme == Theme.Night) {
                Theme.Normal
            } else {
                Theme.Night
            }
    }

    val colors: WanAndroidColors
        @Composable
        get() = LocalWanAndroidColors.current

    enum class Theme {
        Normal, Night
    }
}

@Composable
fun WanAndroidTheme(
    theme: WanAndroidTheme.Theme = WanAndroidTheme.Theme.Normal,
    content: @Composable () -> Unit
) {

    val targetColors = when (theme) {
        WanAndroidTheme.Theme.Normal -> {
            NormalColors
        }
        WanAndroidTheme.Theme.Night -> {
            NightColors
        }
    }

    val colorPrimary = animateColorAsState(targetValue = targetColors.colorPrimary, TweenSpec(600))
    val colorPrimaryDark =
        animateColorAsState(targetValue = targetColors.colorPrimaryDark, TweenSpec(600))
    val colorAccent = animateColorAsState(targetValue = targetColors.colorAccent, TweenSpec(600))
    val textColorPrimary =
        animateColorAsState(targetValue = targetColors.textColorPrimary, TweenSpec(600))
    val viewBackground =
        animateColorAsState(targetValue = targetColors.viewBackground, TweenSpec(600))
    val windowBackground =
        animateColorAsState(targetValue = targetColors.windowBackground, TweenSpec(600))
    val lineDivider = animateColorAsState(targetValue = targetColors.lineDivider, TweenSpec(600))
    val listDivider = animateColorAsState(targetValue = targetColors.listDivider, TweenSpec(600))
    val maskColor = animateColorAsState(targetValue = targetColors.maskColor, TweenSpec(600))
    val commonColor = animateColorAsState(targetValue = targetColors.commonColor, TweenSpec(600))
    val itemTitle = animateColorAsState(targetValue = targetColors.itemTitle, TweenSpec(600))
    val itemAuthor = animateColorAsState(targetValue = targetColors.itemAuthor, TweenSpec(600))
    val itemDesc = animateColorAsState(targetValue = targetColors.itemDesc, TweenSpec(600))
    val itemTime = animateColorAsState(targetValue = targetColors.itemTime, TweenSpec(600))
    val itemDate = animateColorAsState(targetValue = targetColors.itemDate, TweenSpec(600))
    val itemChapter = animateColorAsState(targetValue = targetColors.itemChapter, TweenSpec(600))
    val itemTagTv = animateColorAsState(targetValue = targetColors.itemTagTv, TweenSpec(600))
    val itemNavColorTv =
        animateColorAsState(targetValue = targetColors.itemNavColorTv, TweenSpec(600))
    val colorTitleBg = animateColorAsState(targetValue = targetColors.colorTitleBg, TweenSpec(600))
    val colorAboutTv = animateColorAsState(targetValue = targetColors.colorAboutTv, TweenSpec(600))
    val transparent75 =
        animateColorAsState(targetValue = targetColors.transparent75, TweenSpec(600))
    val verticalTabLayoutBg =
        animateColorAsState(targetValue = targetColors.verticalTabLayoutBg, TweenSpec(600))
    val verticalTabLayoutIndicatorColor = animateColorAsState(
        targetValue = targetColors.verticalTabLayoutIndicatorColor,
        TweenSpec(600)
    )
    val itemFlowLayoutBg =
        animateColorAsState(targetValue = targetColors.itemFlowLayoutBg, TweenSpec(600))
    val arrowColor = animateColorAsState(targetValue = targetColors.arrowColor, TweenSpec(600))
    val stickyHeaderBg =
        animateColorAsState(targetValue = targetColors.stickyHeaderBg, TweenSpec(600))
    val blueGrey = animateColorAsState(targetValue = targetColors.blueGrey, TweenSpec(600))

    val colors = WanAndroidColors(
        colorPrimary = colorPrimary.value,
        colorPrimaryDark = colorPrimaryDark.value,
        colorAccent = colorAccent.value,
        textColorPrimary = textColorPrimary.value,
        viewBackground = viewBackground.value,
        windowBackground = windowBackground.value,
        lineDivider = lineDivider.value,
        listDivider = listDivider.value,
        maskColor = maskColor.value,
        commonColor = commonColor.value,
        itemTitle = itemTitle.value,
        itemAuthor = itemAuthor.value,
        itemDesc = itemDesc.value,
        itemTime = itemTime.value,
        itemDate = itemDate.value,
        itemChapter = itemChapter.value,
        itemTagTv = itemTagTv.value,
        itemNavColorTv = itemNavColorTv.value,
        colorTitleBg = colorTitleBg.value,
        colorAboutTv = colorAboutTv.value,
        transparent75 = transparent75.value,
        verticalTabLayoutBg = verticalTabLayoutBg.value,
        verticalTabLayoutIndicatorColor = verticalTabLayoutIndicatorColor.value,
        itemFlowLayoutBg = itemFlowLayoutBg.value,
        arrowColor = arrowColor.value,
        stickyHeaderBg = stickyHeaderBg.value,
        blueGrey = blueGrey.value
    )

    CompositionLocalProvider(LocalWanAndroidColors provides colors) {
        MaterialTheme(
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}