package com.livejournal.karino2.amzaffilink

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText

class SettingActivity : AppCompatActivity() {

    companion object {
        fun getAppPreferences(ctx : Context) = ctx.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        fun getTrackingIdFromPreferences(prefs: SharedPreferences): String {
            return prefs.getString("tracking_id", "")
        }

    }

    val prefs : SharedPreferences by lazy { getAppPreferences(this) }

    var trackingId : String
        get() = getTrackingIdFromPreferences(prefs)
        set(value) {
            prefs.edit()
                    .putString("tracking_id", value)
                    .commit()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        (findViewById(R.id.editText) as EditText).setText(trackingId)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        /*
        intent?.let {
            if(intent.action == Intent.ACTION_SEND) {
                if(trackingId != "") {
                    val url = intent.getStringExtra("url")
                    val newIntent = Intent(this, LinkGenActivity::class.java)
                    newIntent.action = Intent.ACTION_SEND

                    newIntent.putExtra("url", url)
                    startActivity(newIntent)
                    finish()
                }
            }
        }
        */


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                trackingId = (findViewById(R.id.editText) as EditText).text.toString()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
