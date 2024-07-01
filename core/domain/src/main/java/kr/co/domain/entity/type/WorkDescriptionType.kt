package kr.co.domain.entity.type

enum class WorkDescriptionType(val koreanName: String) {
    SEED_PREP("종자 예조 및 소독"),
    SEEDBED_PREP("묘상 준비 및 설치"),
    SOW("파종"),
    GRAFT("접목"),
    BURY_SEEDLING("가식"),
    SEEDBED_MANAGE("묘판관리"),
    STOP_TILLAGE("경운정지"),
    INITIAL_FERTIL("퇴비 및 기비살포"),
    PLANT_SEEDLING("정식"),
    STILTS_NETS("지주, 네트 세우기"),
    ADDITIONAL_FERTIL("추비 살포"),
    PEST_CONTROL("병충해 방제"),
    WEED("제초"),
    SOIL_COVER("비닐 및 흙 덮기"),
    TRIMMING("적심적야"),
    WATER_MANAGE("물 관리"),
    HOUSE_MANAGE("하우스 설치 및 관리"),
    TEMP_MANAGE("온도 관리"),
    HARVEST("수확"),
    PACK("선별 및 포장"),
    SHIP("운반 및 저장"),
    ETC("기타");

    companion object{
        fun ofValue(koreanName: String) = when(koreanName){
            SEED_PREP.koreanName -> SEED_PREP
            SEEDBED_PREP.koreanName -> SEEDBED_PREP
            SOW.koreanName -> SOW
            GRAFT.koreanName -> GRAFT
            BURY_SEEDLING.koreanName -> BURY_SEEDLING
            SEEDBED_MANAGE.koreanName -> SEEDBED_MANAGE
            STOP_TILLAGE.koreanName -> STOP_TILLAGE
            INITIAL_FERTIL.koreanName -> INITIAL_FERTIL
            PLANT_SEEDLING.koreanName -> PLANT_SEEDLING
            STILTS_NETS.koreanName -> STILTS_NETS
            ADDITIONAL_FERTIL.koreanName -> ADDITIONAL_FERTIL
            PEST_CONTROL.koreanName -> PEST_CONTROL
            WEED.koreanName -> WEED
            SOIL_COVER.koreanName -> SOIL_COVER
            TRIMMING.koreanName -> TRIMMING
            WATER_MANAGE.koreanName -> WATER_MANAGE
            HOUSE_MANAGE.koreanName -> HOUSE_MANAGE
            TEMP_MANAGE.koreanName -> TEMP_MANAGE
            HARVEST.koreanName -> HARVEST
            PACK.koreanName -> PACK
            SHIP.koreanName -> SHIP
            ETC.koreanName -> ETC
            else -> throw IllegalArgumentException("WorkDescriptionType) Unknown work type")
        }
    }
}