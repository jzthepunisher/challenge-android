package com.bcp.androidchallenge

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bcp.androidchallenge.data.remote.api.Api
import com.bcp.androidchallenge.integration.ChallengeActivityListener
import com.bcp.androidchallenge.integration.ChallengeBCP
import com.bcp.androidchallenge.presentation.ui.widget.progress.ProgressDialog

class MainActivity : AppCompatActivity(), ChallengeActivityListener {
    private lateinit var progress : ProgressDialog

    override fun hideProgress() {
        progress.hide()
    }

    override fun showProgress() {
        progress.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = ProgressDialog(this)
        initialize("http://www.bcp.com:9081/currency/api/", this)

        if(savedInstanceState == null){
            ChallengeBCP.loadMainFragment(this, R.id.content, null)
        }
    }

    override fun onResume() {
        super.onResume()
        hideToolbar()
    }

    fun hideToolbar(){
        this.supportActionBar?.hide()
    }


    fun initialize(baseUrl: String, context : Context) {
        Api.buildRetrofit(baseUrl, context)
    }
}