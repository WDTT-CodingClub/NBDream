package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.ServerImageResult
import kr.co.remote.model.response.PostServerImageResponse

internal object ServerImageRemoteMapper :
    Mapper<PostServerImageResponse, ServerImageResult> {
    override fun convert(param: PostServerImageResponse): ServerImageResult =
        ServerImageResult(url = param.data)
}
