package kr.co.wdtt.nbdream.domain.entity

data class CalendarDiaryEntity(
    val userId: String? = null,
    val crops: String,
    val registerDate: String,
    val dateName: String,// 5/2일 목요일
    val amTemper: Int,
    val pmTemper: Int,
    val precipitation: Int,
    val weather: String,
    val workers: Int,
    val hour: Int,
    val area: Int,
    val workDescription: List<WorkDescription>,
    val memo: String,
)

data class WorkDescription(
    val type: String,
    val description: String,
)
