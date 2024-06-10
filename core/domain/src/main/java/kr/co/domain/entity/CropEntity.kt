package kr.co.domain.entity

data class CropEntity(
    val name: Name
) {
    enum class Name {
        PEPPER,
        RICE,
        POTATO,
        SWEET_POTATO,
        APPLE,
        STRAWBERRY,
        GARLIC,
        LETTUCE,
        NAPPA_CABBAGE,
        TOMATO
    }
}