package kr.co.wdtt.nbdream.ui.icon.dreamicon

import androidx.compose.ui.graphics.vector.ImageVector
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Add
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Alarm
import kr.co.wdtt.nbdream.ui.icon.dreamicon.ArrowLeft
import kr.co.wdtt.nbdream.ui.icon.dreamicon.ArrowRight
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Delete
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Dropdown
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Edit
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Search
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Spinner
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Sprout
import kotlin.collections.List as ____KtList

public object DreamIcon

private var __AllIcons: ____KtList<ImageVector>? = null

public val DreamIcon.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Add, Alarm, ArrowLeft, ArrowRight, Delete, Dropdown, Edit, Search, Spinner,
        Sprout)
    return __AllIcons!!
  }
