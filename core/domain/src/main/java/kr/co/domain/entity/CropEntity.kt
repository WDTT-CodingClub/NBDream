package kr.co.domain.entity

data class CropEntity(
    val name: Name,
) {
    enum class Name(
        val koreanName: String,
    ) {
        PEPPER("고추"),
        RICE("벼"),
        POTATO("감자"),
        SWEET_POTATO("고구마"),
        APPLE("사과"),
        STRAWBERRY("딸기"),
        GARLIC("마늘"),
        LETTUCE("상추"),
        NAPPA_CABBAGE("배추"),
        TOMATO("토마토"),
    }

    companion object{
        fun getCropEntity(koreanName: String) = when(koreanName){
            Name.PEPPER.koreanName -> CropEntity(Name.PEPPER)
            Name.RICE.koreanName -> CropEntity(Name.RICE)
            Name.POTATO.koreanName -> CropEntity(Name.POTATO)
            Name.SWEET_POTATO.koreanName -> CropEntity(Name.SWEET_POTATO)
            Name.APPLE.koreanName -> CropEntity(Name.APPLE)
            Name.STRAWBERRY.koreanName -> CropEntity(Name.STRAWBERRY)
            Name.GARLIC.koreanName -> CropEntity(Name.GARLIC)
            Name.LETTUCE.koreanName -> CropEntity(Name.LETTUCE)
            Name.NAPPA_CABBAGE.koreanName -> CropEntity(Name.NAPPA_CABBAGE)
            Name.TOMATO.koreanName -> CropEntity(Name.TOMATO)
            else -> throw IllegalArgumentException("Unknown koreanName")
        }
    }
}
