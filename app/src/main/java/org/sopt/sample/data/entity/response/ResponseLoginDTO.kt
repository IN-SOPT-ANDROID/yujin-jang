package org.sopt.sample.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseLoginDTO(
    val status: Int,
    val message: String,
    val result: User
) {
    @Serializable
    data class User(
        val id: Int,
        val name: String,
        val profileImage: String?,
        val bio: String?,
        val email: String,
        val password: String
    )
}
