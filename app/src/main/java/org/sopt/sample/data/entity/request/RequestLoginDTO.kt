package org.sopt.sample.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginDTO(
    val email: String,
    val password: String
)
