package kr.co.main.model.my

internal enum class MyPageProfileDialog(val value: String) {
    GALLERY("사진첩"),
    CAMERA("카메라"),
    DEFAULT("기본 프로필로 설정"),
    ;

    companion object {
        fun fromValue(value: String): MyPageProfileDialog = MyPageProfileDialog.entries.find { it.value == value }?: DEFAULT
    }
}