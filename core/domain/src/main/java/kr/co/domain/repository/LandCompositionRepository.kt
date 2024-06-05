package kr.co.domain.repository

import kr.co.domain.entity.LandCompositionEntity

interface LandCompositionRepository {
    suspend fun get(pnuCode: String): LandCompositionEntity
}