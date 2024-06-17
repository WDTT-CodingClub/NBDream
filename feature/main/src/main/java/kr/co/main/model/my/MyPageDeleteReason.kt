package kr.co.main.model.my

internal enum class MyPageDeleteReason(
    val text: String,
) {
    OTHER_APP(
        "다른 어플이 더 좋아서"
    ),
    OTHER_JOB(
        "다른 일을 하게 되어서"
    ),
    INCONVENIENT(
        "농부의꿈 이용에 불편함이 생겨서"
    ),
}