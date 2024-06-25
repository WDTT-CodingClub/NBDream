package kr.co.main.model.calendar.type

internal enum class ScreenModeType(val id:Int) {
    POST_MODE(0),
    EDIT_MODE(1);

    companion object{
        fun ofValue(id:Int):ScreenModeType = when(id){
            POST_MODE.id -> POST_MODE
            EDIT_MODE.id -> EDIT_MODE
            else -> throw IllegalArgumentException("Invalid screen mode id")
        }
    }
}