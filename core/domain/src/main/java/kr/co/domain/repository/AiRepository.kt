package kr.co.domain.repository

interface AiRepository {
    suspend fun send(m: String): String
}