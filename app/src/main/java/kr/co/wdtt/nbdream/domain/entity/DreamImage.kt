package kr.co.wdtt.nbdream.domain.entity

import java.io.File

sealed class DreamImage{
    data class RequestImage(val image: File):DreamImage()
    data class ResponseImage(val image:String):DreamImage()
}
