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
class WanAndroidColors(
    val colorPrimary: Color,
    val colorPrimaryDark: Color,
    val colorAccent: Color,
    val textColorPrimary: Color,
    val viewBackground: Color,
    val windowBackground: Color,
    val lineDivider: Color,
    val listDivider: Color,
    val maskColor: Color,

    /* item */
    val commonColor: Color,
    val itemTitle: Color,
    val itemAuthor: Color,
    val itemDesc: Color,
    val itemTime: Color,
    val itemDate: Color,
    val itemChapter: Color,
    val itemTagTv: Color,
    val itemNavColorTv: Color,
    val colorTitleBg: Color,
    val colorAboutTv: Color,
    val transparent75: Color,
    val verticalTabLayoutBg: Color,
    val verticalTabLayoutIndicatorColor: Color,
    val itemFlowLayoutBg: Color,
    val arrowColor: Color,
    val stickyHeaderBg: Color,

    val blueGrey: Color
)

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