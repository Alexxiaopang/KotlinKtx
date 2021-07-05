package com.android.ktx.udid

import android.provider.Settings
import com.android.ktx.provider.ContextProvider
import java.net.NetworkInterface
import java.util.*

/**
 * @ProjectName: PufferLive_kt
 * @Package: com.puffer.live.utils
 * @ClassName: UdidUtils
 * @Description: java类作用描述
 * @Author: Alex
 * @CreateDate: 2020/1/14 17:10
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/1/14 17:10
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
object UdidUtils {

    @JvmStatic
    val identity: String
        get() = macAddress

    val androidId: String
        get() = Settings.System.getString(ContextProvider.getContext().contentResolver, Settings.Secure.ANDROID_ID)

    //                LogUtils.e("设备号:" + res1.toString());
    val macAddress: String
        get() {
            try {
                val all: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
                for (nif in all) {
                    if (nif.name != "wlan0") {
                        continue
                    }
                    val macBytes: ByteArray = nif.hardwareAddress ?: return androidId
                    val res1 = StringBuilder()
                    for (b in macBytes) {
                        res1.append(String.format("%02X:", b))
                    }
                    if (res1.isNotEmpty()) {
                        res1.deleteCharAt(res1.length - 1)
                    }
                    //                LogUtils.e("设备号:" + res1.toString());
                    return res1.toString().replace(":", "")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return androidId
        }
}