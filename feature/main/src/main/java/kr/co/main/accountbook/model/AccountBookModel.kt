package kr.co.main.accountbook.model

import kr.co.domain.entity.AccountBookEntity

internal fun AccountBookEntity.Category?.getDisplay(): String {
    return when (this) {
        AccountBookEntity.Category.FARM_PRODUCT_SALES -> "농산물 판매"
        AccountBookEntity.Category.FERTILIZER -> "비료"
        AccountBookEntity.Category.SEEDS_AND_SEEDLINGS -> "농자/종묘"
        AccountBookEntity.Category.PESTICIDES -> "농약"
        AccountBookEntity.Category.FARM_MACHINERY -> "농기계"
        AccountBookEntity.Category.DISTRIBUTION_EXPENSE -> "유통비"
        AccountBookEntity.Category.RENT -> "임차료"
        AccountBookEntity.Category.SEED_SALES -> "종자 판매"
        AccountBookEntity.Category.FARM_EQUIPMENT_USAGE_FEE -> "농기구 사용료"
        AccountBookEntity.Category.FARM_EQUIPMENT_RENTAL -> "농기구 대여"
        AccountBookEntity.Category.OTHER -> "기타"
        else -> ""
    }
}

internal fun AccountBookEntity.TransactionType?.getTransactionType(): String {
    return when (this) {
        AccountBookEntity.TransactionType.EXPENSE -> "수입"
        AccountBookEntity.TransactionType.REVENUE -> "지출"
        else -> ""
    }
}
