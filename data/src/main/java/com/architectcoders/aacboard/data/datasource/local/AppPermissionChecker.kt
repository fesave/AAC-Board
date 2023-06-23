package com.architectcoders.aacboard.data.datasource.local

interface AppPermissionChecker {

    enum class Permission { COARSE_LOCATION }

    fun check(permission: Permission): Boolean
}