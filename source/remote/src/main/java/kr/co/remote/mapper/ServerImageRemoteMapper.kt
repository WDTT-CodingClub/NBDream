package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.ServerImageResult
import kr.co.remote.model.response.GetServerImageResponse

internal object ServerImageRemoteMapper :
    Mapper<GetServerImageResponse, ServerImageResult> {
    override fun convert(param: GetServerImageResponse): ServerImageResult =
        ServerImageResult(url = param.data)
}
