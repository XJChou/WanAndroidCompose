package com.zxj.share

import android.content.Context
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.data.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareArticleViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val articleRepository: ArticleRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val _event = MutableSharedFlow<Pair<Boolean, String>>()
    val event = _event.asSharedFlow()

    fun commitShare(title: String, link: String) {
        stateHandle.setSavedStateProvider("12") {
            bundleOf()
        }

        viewModelScope.launch {
            val response = articleRepository.shareArticle(title, link)
            if (response.isSuccess) {
                _event.emit(true to context.getString(R.string.share_success))
            } else {
                _event.emit(false to response.errorMsgOrDefault)
            }
        }
    }
}