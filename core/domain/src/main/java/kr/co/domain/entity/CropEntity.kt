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
}
