package kr.co.domain.entity


data class FarmWorkEntity(
    val id:String,
    val crop:CropEntity,
    val startEra: Era,
    val endEra: Era,
    val category: Category, //농작업 타입 (ex. 생육과정(주요농작업))
    val farmWork:String, //농작업 (ex. 객토, 퇴비 주기)
    val videoUrl: String? = null
){
    enum class Era(val value:Int){
        EARLY(1),
        MID(2),
        LATE(3)
    }
    enum class Category{
        GROWTH,
        PEST,
        CLIMATE
    }
}

