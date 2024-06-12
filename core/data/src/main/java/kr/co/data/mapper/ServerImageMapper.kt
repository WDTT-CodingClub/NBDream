package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.ServerImageResult
import kr.co.domain.entity.ServerImageEntity

internal object ServerImageMapper
    : Mapper<ServerImageResult, ServerImageEntity> {
    override fun convert(param: ServerImageResult): ServerImageEntity =
        ServerImageEntity(url = param.url)
}
