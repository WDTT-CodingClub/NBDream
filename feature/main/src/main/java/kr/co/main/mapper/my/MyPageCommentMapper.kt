package kr.co.main.mapper.my

import kr.co.common.mapper.Mapper
import kr.co.domain.entity.CommentEntity
import kr.co.main.my.community.MyPageWriteViewModel

internal object MyPageCommentMapper
    :Mapper<CommentEntity, MyPageWriteViewModel.State.Comment>{
    override fun convert(param: CommentEntity): MyPageWriteViewModel.State.Comment {
        return with(param) {
            MyPageWriteViewModel.State.Comment(
                id = commentId,
                bulletinId = bulletinId,
                authorName = bulletinAuthorName,
                content = content,
                createAt = createdTime
            )
        }
    }
}
