package org.sopt.sample.util

import android.app.Application
import com.facebook.flipper.BuildConfig
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initFlipper()
    }

    private fun initFlipper() {
        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this).apply {
                addPlugin(InspectorFlipperPlugin(this@App, DescriptorMapping.withDefaults()))
                addPlugin(networkFlipperPlugin)
                // addPlugin(LeakCanary2FlipperPlugin()) -> 디버그에서 메모리 릭 잡아주는 친구
                // addPlugin(SharedPreferencesFlipperPlugin(app, "SOPT_DATA")) -> 해당 키를 가진 SharedPreference 내부 데이터를 볼 수 있는 친구
            }.start()
        }
    }

    companion object {
        val networkFlipperPlugin = NetworkFlipperPlugin()
    }
}
