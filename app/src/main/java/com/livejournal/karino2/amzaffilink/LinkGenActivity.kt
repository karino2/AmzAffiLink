package com.livejournal.karino2.amzaffilink

import android.content.*
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast

class LinkGenActivity : AppCompatActivity() {

    companion object {


        fun urlConvert(trackingId: String, targetUrl: String) : String {
            val url = Uri.parse(targetUrl)
            val pathSegs = url.pathSegments
            if(pathSegs.size < 2)
                throw RuntimeException("Wrong url ${url}")

            val asins = pathSegs[1]
            return """<iframe style="width:120px;height:240px;" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" src="https://rcm-fe.amazon-adsystem.com/e/cm?ref=qf_sp_asin_til&t=${trackingId}&m=amazon&o=9&p=8&l=as1&IS1=1&detail=1&asins=${asins}&bc1=ffffff<1=_top&fc1=333333&lc1=0066c0&bg1=ffffff&f=ifr"> </iframe>"""
        }
    }

    val prefs : SharedPreferences by lazy { SettingActivity.getAppPreferences(this) }

    val trackingId : String
        get() = SettingActivity.getTrackingIdFromPreferences(prefs)

    fun showMessage(msg : String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            if (it.action == Intent.ACTION_SEND) {
                val url = urlConvert(trackingId, intent.getStringExtra("url"))
                (findViewById(R.id.editTextUrl) as EditText).setText(url)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link_gen)

        if(trackingId == "") {
            gotoSettingActivity()
            return
        }

        intent?.let {
            if (it.action == Intent.ACTION_SEND) {
                val url = urlConvert(trackingId, intent.getStringExtra("url"))
                (findViewById(R.id.editTextUrl) as EditText).setText(url)
            }
            if(it.action == Intent.ACTION_MAIN) {
                showMessage("Please use this app from SHARE of amazon app.")
            }
        }

        findViewById(R.id.buttonCopy).setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("url", (findViewById(R.id.editTextUrl) as EditText).text.toString())
            clipboard.setPrimaryClip(clip)
            showMessage("Link copied to clipboard")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.link_gen, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_setting -> {
                gotoSettingActivity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun gotoSettingActivity() {
        val newIntent = Intent(this, SettingActivity::class.java)
        startActivity(newIntent)
    }
}
