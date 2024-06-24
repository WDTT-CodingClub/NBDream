package kr.co.common.mapper

abstract class BaseMapper<LEFT, RIGHT> {
    abstract fun toRight(left: LEFT): RIGHT
    abstract fun toLeft(right: RIGHT): LEFT
}