package igor.second.riddle.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAds(id: Int){
    AndroidView(factory = {context ->
        AdView(context).apply {
            setAdSize(AdSize.FULL_BANNER)
            adUnitId = context.getString(id)
            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)
        }
    })
}
