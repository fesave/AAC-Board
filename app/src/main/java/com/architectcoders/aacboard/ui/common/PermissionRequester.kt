package com.architectcoders.aacboard.ui.common

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener

class PermissionRequester(private val activity: Activity, private val permission: String) {

    fun request(continuation: () -> Unit) {
        Dexter
            .withContext(activity)
            .withPermission(permission)
            .withListener(
                object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        continuation()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        continuation()
                    }
                },
            ).check()
    }
}