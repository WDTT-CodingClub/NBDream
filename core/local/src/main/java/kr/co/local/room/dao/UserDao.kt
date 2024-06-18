package kr.co.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.co.local.model.UserEntity

@Dao
internal interface UserDao {
    @Query("SELECT * FROM user WHERE name = :name")
    fun fetchUser(name: String): Flow<UserEntity?>

    @Insert(onConflict = REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}