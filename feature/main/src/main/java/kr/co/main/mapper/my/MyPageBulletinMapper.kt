package kr.co.main.mapper.my

import kr.co.common.mapper.Mapper
import kr.co.domain.entity.BulletinEntity
import kr.co.main.my.community.written.MyPageWriteViewModel

internal object MyPageBulletinMapper
    :Mapper<BulletinEntity, MyPageWriteViewModel.State.Bulletin>{
    override fun convert(param: BulletinEntity): MyPageWriteViewModel.State.Bulletin {
        return with(param) {
            MyPageWriteViewModel.State.Bulletin(
                id = bulletinId,
                content = content,
                thumbnail = imageUrls.firstOrNull(),
                commentCount = comments.size,
                bookmarkedCount = bookmarkedCount,
                createdAt = createdTime,
            )
        }
    }
}