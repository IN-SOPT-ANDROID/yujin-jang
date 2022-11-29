package org.sopt.sample.data.entity.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginDTO(
    // JSON 객체로 변환될 때 'email': 'nunu.lee@gmail.com' 과 같은 식으로 들어갈 수 있다는 표기
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)
