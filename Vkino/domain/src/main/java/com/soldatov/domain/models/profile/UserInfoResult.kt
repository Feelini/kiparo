package com.soldatov.domain.models.profile

sealed class UserInfoResult{
    class Success(val data: UserInfo): UserInfoResult()
    class Error(val message: String): UserInfoResult()
}
