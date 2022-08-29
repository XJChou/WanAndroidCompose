package com.zxj.wanandroid.compose.utils

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role

fun Modifier.clickableNotEffect(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = composed {
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
}