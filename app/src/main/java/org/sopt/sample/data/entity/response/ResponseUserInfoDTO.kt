package org.sopt.sample.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserInfoDTO(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<UserInfo>,
    val support: Support
) {
    @Serializable
    data class UserInfo(
        val id: Int,
        val email: String,
        val first_name: String,
        val last_name: String,
        val avatar: String
    )

    @Serializable
    data class Support(
        val url: String,
        val text: String
    )
}
