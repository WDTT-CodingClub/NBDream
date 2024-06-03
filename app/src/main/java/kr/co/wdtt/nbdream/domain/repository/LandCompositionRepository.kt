package kr.co.wdtt.nbdream.domain.repository

import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.entity.LandCompositionEntity

interface LandCompositionRepository {
    suspend fun get(pnuCode: String): EntityWrapper<LandCompositionEntity>
}