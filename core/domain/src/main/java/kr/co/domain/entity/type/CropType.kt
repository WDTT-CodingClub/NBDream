package kr.co.domain.entity.type

enum class CropType(
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
    TOMATO("토마토");

    companion object{
        fun ofValue(koreanName: String) = when(koreanName){
            PEPPER.koreanName -> PEPPER
            RICE.koreanName -> RICE
            POTATO.koreanName -> POTATO
            SWEET_POTATO.koreanName -> SWEET_POTATO
            APPLE.koreanName -> APPLE
            STRAWBERRY.koreanName -> STRAWBERRY
            GARLIC.koreanName -> GARLIC
            LETTUCE.koreanName -> LETTUCE
            NAPPA_CABBAGE.koreanName -> NAPPA_CABBAGE
            TOMATO.koreanName -> TOMATO
            else -> throw IllegalArgumentException("CropType) Unknown koreanName")
        }
    }
}