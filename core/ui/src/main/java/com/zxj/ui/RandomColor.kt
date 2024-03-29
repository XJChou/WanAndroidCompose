package com.zxj.ui

import com.zxj.designsystem.theme.WanAndroidTheme
import java.util.*

fun randomColor(): Int {
    val random = Random()
    //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
    var red = random.nextInt(190)
    var green = random.nextInt(190)
    var blue = random.nextInt(190)
    if (WanAndroidTheme.theme == WanAndroidTheme.Theme.Night) {
        //150-255
        red = random.nextInt(105) + 150
        green = random.nextInt(105) + 150
        blue = random.nextInt(105) + 150
    }
    //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
    return android.graphics.Color.rgb(red, green, blue)
}
