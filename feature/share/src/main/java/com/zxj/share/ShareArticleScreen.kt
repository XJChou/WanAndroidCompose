package com.zxj.share

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.ui.TextToolBar
import com.zxj.ui.ToolBarIcon
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun ShareArticleRoute(
    modifier: Modifier = Modifier,
    viewModel: ShareArticleViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    ShareArticleScreen(
        modifier = modifier,
        onBack = onBack,
        commitShare = viewModel::commitShare
    )
    val context = LocalContext.current
    LaunchedEffect(viewModel.event) {
        viewModel.event.collect {
            Toast.makeText(context, it.second, Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShareArticleScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    commitShare: (String, String) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    Scaffold(
        modifier = modifier,
        topBar = {
            TextToolBar(
                title = stringResource(id = R.string.share_article),
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    ToolBarIcon(drawableRes = com.zxj.ui.R.drawable.ic_back, onBack)
                },
                actions = {
                    Text(
                        text = stringResource(id = R.string.action_share),
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable(onClick = { commitShare(title, link) })
                            .padding(horizontal = 10.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(vertical = 10.dp, horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.share_article_title),
                    color = WanAndroidTheme.colors.commonColor,
                    fontSize = 14.sp
                )
                TextField(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .defaultMinSize(minHeight = 60.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(
                        color = WanAndroidTheme.colors.commonColor,
                        fontSize = 16.sp
                    ),
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text(text = stringResource(id = R.string.share_article_title_tip))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )

                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    color = WanAndroidTheme.colors.commonColor,
                    text = stringResource(id = R.string.share_article_link),
                    fontSize = 14.sp
                )
                TextField(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .defaultMinSize(minHeight = 60.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(
                        color = WanAndroidTheme.colors.commonColor,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    ),
                    value = link,
                    onValueChange = { link = it },
                    placeholder = {
                        Text(text = stringResource(id = R.string.share_article_link_tip))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                )
            }

            // tip
            Text(
                text = stringResource(id = R.string.share_article_tip),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp, start = 16.dp, end = 20.dp),
                color = WanAndroidTheme.colors.itemDesc,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }

    }
}

@Preview
@Composable
private fun PreviewShareArticleScreen() {
    WanAndroidTheme {
        ShareArticleScreen(
            modifier = Modifier.fillMaxSize(),
            onBack = {},
            commitShare = { _, _ ->

            }
        )
    }
}