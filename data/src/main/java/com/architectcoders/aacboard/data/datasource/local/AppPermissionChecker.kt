package com.architectcoders.aacboard.data.datasource.local

interface AppPermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}