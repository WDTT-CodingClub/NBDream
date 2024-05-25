package kr.co.wdtt.nbdream.domain.entity

import kr.co.wdtt.nbdream.ui.theme.ColorSet
import java.io.File

sealed class DreamImage private constructor(){
    companion object{
        fun <T> create(image:T): DreamImage = when(image){
            is File -> RequestImage(image)
            is String -> ResponseImage(image)
            else -> throw IllegalArgumentException("Unknown type")
        }
    }
    abstract fun getImage(): Any

    private data class RequestImage(private val image: File):DreamImage(){
        override fun getImage(): File= image
    }
    private data class ResponseImage(private val image:String):DreamImage(){
        override fun getImage(): String = image
    }
}

