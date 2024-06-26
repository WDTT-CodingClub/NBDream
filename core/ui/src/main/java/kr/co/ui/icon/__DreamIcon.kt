package kr.co.ui.icon

import androidx.compose.ui.graphics.vector.ImageVector
import kr.co.ui.icon.dreamicon.Spinner
import kotlin.collections.List as ____KtList

public object DreamIcon

private var __AllIcons: ____KtList<ImageVector>? = null

public val DreamIcon.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Spinner)
    return __AllIcons!!
  }
