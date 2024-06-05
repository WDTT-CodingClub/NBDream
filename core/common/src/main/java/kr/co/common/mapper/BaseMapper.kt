package kr.co.common.mapper

abstract class BaseMapper<LEFT, RIGHT> {
    abstract fun toRight(model: LEFT): RIGHT
    abstract fun toLeft(entity: RIGHT): LEFT
}