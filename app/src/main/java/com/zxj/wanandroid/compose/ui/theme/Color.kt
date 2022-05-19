package com.zxj.wanandroid.compose.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces

val Grey50 = Color(0xFFFAFAFA).convert(ColorSpaces.CieXyz)
val Grey100 = Color(0xFFF5F5F5).convert(ColorSpaces.CieXyz)
val Grey200 = Color(0xFFEEEEEE).convert(ColorSpaces.CieXyz)
val Grey300 = Color(0xFFE0E0E0).convert(ColorSpaces.CieXyz)
val Grey400 = Color(0xFFBDBDBD).convert(ColorSpaces.CieXyz)
val Grey500 = Color(0xFF9E9E9E).convert(ColorSpaces.CieXyz)
val Grey600 = Color(0xFF757575).convert(ColorSpaces.CieXyz)
val Grey700 = Color(0xFF616161).convert(ColorSpaces.CieXyz)
val Grey800 = Color(0xFF424242).convert(ColorSpaces.CieXyz)
val Grey900 = Color(0xFF212121).convert(ColorSpaces.CieXyz)


/**
 * 主题色
 */
@Stable
class WanAndroidColors(
    colorPrimary: Color,
    colorPrimaryDark: Color,
    colorAccent: Color,
    textColorPrimary: Color,
    viewBackground: Color,
    windowBackground: Color,
    lineDivider: Color,
    listDivider: Color,
    maskColor: Color,

    /* item */
    commonColor: Color,
    itemTitle: Color,
    itemAuthor: Color,
    itemDesc: Color,
    itemTime: Color,
    itemDate: Color,
    itemChapter: Color,
    itemTagTv: Color,
    itemNavColorTv: Color,
    colorTitleBg: Color,
    colorAboutTv: Color,
    transparent75: Color,
    verticalTabLayoutBg: Color,
    verticalTabLayoutIndicatorColor: Color,
    itemFlowLayoutBg: Color,
    arrowColor: Color,
    stickyHeaderBg: Color,

    blueGrey: Color
) {
    var colorPrimary: Color by mutableStateOf(colorPrimary)
        private set
    var colorPrimaryDark: Color by mutableStateOf(colorPrimaryDark)
        private set
    var colorAccent: Color by mutableStateOf(colorAccent)
        private set
    var textColorPrimary: Color by mutableStateOf(textColorPrimary)
        private set
    var viewBackground: Color by mutableStateOf(viewBackground)
        private set
    var windowBackground: Color by mutableStateOf(windowBackground)
        private set
    var lineDivider: Color by mutableStateOf(lineDivider)
        private set
    var listDivider: Color by mutableStateOf(listDivider)
        private set
    var maskColor: Color by mutableStateOf(maskColor)
        private set

    // item
    var commonColor: Color by mutableStateOf(commonColor)
        private set
    var itemTitle: Color by mutableStateOf(itemTitle)
        private set
    var itemAuthor: Color by mutableStateOf(itemAuthor)
        private set
    var itemDesc: Color by mutableStateOf(itemDesc)
        private set
    var itemTime: Color by mutableStateOf(itemTime)
        private set
    var itemDate: Color by mutableStateOf(itemDate)
        private set
    var itemChapter: Color by mutableStateOf(itemChapter)
        private set
    var itemTagTv: Color by mutableStateOf(itemTagTv)
        private set
    var itemNavColorTv: Color by mutableStateOf(itemNavColorTv)
        private set
    var colorTitleBg: Color by mutableStateOf(colorTitleBg)
        private set
    var colorAboutTv: Color by mutableStateOf(colorAboutTv)
        private set
    var transparent75: Color by mutableStateOf(transparent75)
        private set
    var verticalTabLayoutBg: Color by mutableStateOf(verticalTabLayoutBg)
        private set
    var verticalTabLayoutIndicatorColor: Color by mutableStateOf(verticalTabLayoutIndicatorColor)
        private set
    var itemFlowLayoutBg: Color by mutableStateOf(itemFlowLayoutBg)
        private set
    var arrowColor: Color by mutableStateOf(arrowColor)
        private set
    var stickyHeaderBg: Color by mutableStateOf(stickyHeaderBg)
        private set

    var blueGrey: Color by mutableStateOf(blueGrey)
        private set
}

val NormalColors = WanAndroidColors(
    colorPrimary = Color(0xFFE91E63).convert(ColorSpaces.CieXyz),
    colorPrimaryDark = Color(0xFFC2185B).convert(ColorSpaces.CieXyz),
    colorAccent = Color.Cyan.convert(ColorSpaces.CieXyz),
    textColorPrimary = Color(0xFF616161).convert(ColorSpaces.CieXyz),
    viewBackground = Color.White.convert(ColorSpaces.CieXyz),
    windowBackground = Grey100,
    lineDivider = Grey400,
    listDivider = Grey400,
    maskColor = Color(0xFFF5F5F5).convert(ColorSpaces.CieXyz),
    /* item */
    commonColor = Color(0xFF19191B).convert(ColorSpaces.CieXyz),
    itemTitle = Color(0xFF19191B).convert(ColorSpaces.CieXyz),
    itemAuthor = Grey700,
    itemDesc = Grey600,
    itemTime = Grey600,
    itemDate = Grey600,
    itemChapter = Grey600,
    itemTagTv = Color.White.convert(ColorSpaces.CieXyz),
    itemNavColorTv = Color(0xFF19191B).convert(ColorSpaces.CieXyz),
    colorTitleBg = Color(0xCCFFFFFF).convert(ColorSpaces.CieXyz),
    colorAboutTv = Grey600,
    transparent75 = Color(0xBA000000).convert(ColorSpaces.CieXyz),
    verticalTabLayoutBg = Grey100,
    verticalTabLayoutIndicatorColor = Color.White.convert(ColorSpaces.CieXyz),
    itemFlowLayoutBg = Grey200,
    arrowColor = Grey700,
    stickyHeaderBg = Grey100,

    blueGrey = Color(0xFF607D8B).convert(ColorSpaces.CieXyz)
)

val NightColors = WanAndroidColors(
    colorPrimary = Color(0xFF35464E).convert(ColorSpaces.CieXyz),
    colorPrimaryDark = Color(0xFF212A2F).convert(ColorSpaces.CieXyz),
    colorAccent = Color.Cyan.convert(ColorSpaces.CieXyz),
    textColorPrimary = Color(0xFF616161).convert(ColorSpaces.CieXyz),
    viewBackground = Grey800,
    windowBackground = Grey900,
    lineDivider = Grey700,
    listDivider = Grey600,
    maskColor = Color(0xFF111111).convert(ColorSpaces.CieXyz),
    /* item */
    commonColor = Grey300,
    itemTitle = Grey300,
    itemAuthor = Grey500,
    itemDesc = Grey500,
    itemTime = Grey500,
    itemDate = Grey500,
    itemChapter = Grey500,
    itemTagTv = Color.White.convert(ColorSpaces.CieXyz),
    itemNavColorTv = Grey500,
    colorTitleBg = Color(0xCCFFFFFF).convert(ColorSpaces.CieXyz),
    colorAboutTv = Grey600,
    transparent75 = Color(0xBA000000).convert(ColorSpaces.CieXyz),
    verticalTabLayoutBg = Grey800,
    verticalTabLayoutIndicatorColor = Grey600,
    itemFlowLayoutBg = Grey700,
    arrowColor = Grey400,
    stickyHeaderBg = Color(0xFF515151).convert(ColorSpaces.CieXyz),

    blueGrey = Color(0xFF607D8B).convert(ColorSpaces.CieXyz)
)

val LocalWanAndroidColors = compositionLocalOf { NormalColors }