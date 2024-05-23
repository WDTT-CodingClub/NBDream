package kr.co.wdtt.nbdream.domain.entity

//API 형식을 잘 몰라서,,, 반환 형식 확인 해봐야 할듯?
data class CalendarTopicEntity(
    val userid: String? = null,
    val crops: String,
    val month: Int,
    val early: String,
    val mid: String,
    val late: String,
    val notices: List<String> = emptyList(),
    val pests: List<String> = emptyList()
)
