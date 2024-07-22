package kr.co.data.model.data

data class AlarmHistoryListData(
    val items: List<Item>
) {
    data class Item(
        val id: Long,
        val alarmType: String,
        val title: String,
        val content: String,
        val checked: Boolean,
        val createdDate: String
    )
}