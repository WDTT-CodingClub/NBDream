package kr.co.ui.icon

import androidx.compose.ui.graphics.vector.ImageVector
import kr.co.ui.icon.dreamicon.Home
import kotlin.collections.List as ____KtList

public object DreamIcon

private var __DreamIcon: ____KtList<ImageVector>? = null

public val DreamIcon.DreamIcon: ____KtList<ImageVector>
  get() {
    if (__DreamIcon != null) {
      return __DreamIcon!!
    }
    __DreamIcon= listOf(Home)
    return __DreamIcon!!
  }
