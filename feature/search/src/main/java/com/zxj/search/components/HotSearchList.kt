package com.zxj.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.zxj.model.HotSearchBean
import com.zxj.designsystem.theme.Shapes

@Composable
fun HotSearchList(
    hotSearchList: List<HotSearchBean>,
    hotSearchColorList: List<Color>,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    FlowRow(modifier = modifier) {
        hotSearchList.forEachIndexed { index, item ->
            Text(
                text = item.name,
                modifier = Modifier
                    .padding(5.dp)
                    .background(Color(0xFFEEEEEE), Shapes.medium)
                    .clickable { onSearch(item.name) }
                    .padding(vertical = 6.dp, horizontal = 10.dp),
                color = hotSearchColorList[index]
            )
        }
    }

}