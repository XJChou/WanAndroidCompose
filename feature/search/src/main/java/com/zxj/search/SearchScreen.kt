package com.zxj.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.HistorySearchBean
import com.zxj.model.HotSearchBean
import com.zxj.search.components.HotSearchList
import com.zxj.ui.SearchToolBar
import com.zxj.ui.ToolBarIcon
import com.zxj.ui.rememberSearchState
import kotlinx.coroutines.launch
import com.zxj.ui.R.drawable as UIDrawable


/**
 * 屏蔽viewModel的存在
 */
@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onSearch: (search: String) -> Unit,
) {
    val searchViewState by viewModel.uiState.collectAsState()
    SearchScreen(
        modifier = modifier,
        searchHistoryList = searchViewState.searchHistoryList,
        hotSearchList = searchViewState.hotSearchList,
        hotSearchColorList = searchViewState.hotSearchColorList,
        onBack = onBack,
        onSearch = viewModel::search,
        onClearHistory = viewModel::clearHistorySearch,
        onHistoryDelete = viewModel::deleteHistorySearch,
        onHistoryClick = { viewModel.search(it.search) }
    )

    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect {
            when (it) {
                is SearchViewEvent.ShowToast -> {
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                }
                is SearchViewEvent.SearchSuccess -> {
                    onSearch(it.search)
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchHistoryList: List<HistorySearchBean> = emptyList(),
    hotSearchList: List<HotSearchBean> = emptyList(),
    hotSearchColorList: List<Color> = emptyList(),
    onBack: () -> Unit,
    onSearch: (search: String) -> Unit,
    onClearHistory: () -> Unit,
    onHistoryClick: (HistorySearchBean) -> Unit,
    onHistoryDelete: (HistorySearchBean) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            val searchState = rememberSearchState(value = "")
            SearchToolBar(
                searchState,
                stringResource(id = R.string.search_tint),
                navigationIcon = {
                    ToolBarIcon(drawableRes = UIDrawable.ic_back, onBack)
                },
                actions = {
                    ToolBarIcon(drawableRes = UIDrawable.ic_search_white_24dp) {
                        onSearch(searchState.value)
                    }
                },
                onSearch = onSearch
            )
        }
    ) {
        Column(
            modifier = Modifier
                .background(WanAndroidTheme.colors.viewBackground)
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            // 热门搜索
            Text(
                text = stringResource(id = R.string.hot_search),
                color = WanAndroidTheme.colors.colorAccent,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            HotSearchList(
                hotSearchList = hotSearchList,
                hotSearchColorList = hotSearchColorList,
                modifier = Modifier.fillMaxWidth(),
                onSearch = onSearch
            )

            // 历史搜索
            SearchPageHead(stringResource(id = R.string.history_search), onClearHistory)
            HistorySearchList(
                searchHistoryList = searchHistoryList,
                onHistoryItemClick = onHistoryClick,
                onHistoryItemDelete = onHistoryDelete
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearch() {
    SearchRoute(onBack = {}, onSearch = {})
}