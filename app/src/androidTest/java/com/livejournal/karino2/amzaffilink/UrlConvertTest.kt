package com.livejournal.karino2.amzaffilink

import android.support.test.InstrumentationRegistry
import org.junit.Test
import org.junit.Assert.*
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith

/**
 * Created by _ on 2017/12/03.
 */
@RunWith(AndroidJUnit4::class)
class UrlConvertTest {
    @Test
    fun testUrlConvert() {
        val trackingId = "hogeika203-22"
        val target = "https://www.amazon.co.jp/dp/4774187593/ref=cm_sw_r_apa_f&6iAb8XH6XEB"

        val expect = """<iframe style="width:120px;height:240px;" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" src="https://rcm-fe.amazon-adsystem.com/e/cm?ref=qf_sp_asin_til&t=hogeika203-22&m=amazon&o=9&p=8&l=as1&IS1=1&detail=1&asins=4774187593&bc1=ffffff<1=_top&fc1=333333&lc1=0066c0&bg1=ffffff&f=ifr"> </iframe>"""

        val actual = LinkGenActivity.urlConvert(trackingId, target)

        assertEquals(expect, actual)
    }
}