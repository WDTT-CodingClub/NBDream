package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.ServerImageResult
import kr.co.domain.entity.ServerImageEntity

internal object ServerImageMapper : Mapper<ServerImageResult, ServerImageEntity?> {
    /** Result에 url 없으면 Entity 말고 null 반환 */
    override fun convert(param: ServerImageResult): ServerImageEntity? {
        return if (param.url.isNullOrBlank()) null
        else ServerImageEntity(url = param.url)
    }
}
