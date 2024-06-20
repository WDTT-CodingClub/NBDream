package kr.co.data.model.data.calendar

data class FarmWorkListResDTO (
    val resultCode: String,
    val resultMessage: String,
    val resultData: ResultData
){
    data class ResultData(
        val farmWorkList: List<FarmWorkResDTO>
    )
}