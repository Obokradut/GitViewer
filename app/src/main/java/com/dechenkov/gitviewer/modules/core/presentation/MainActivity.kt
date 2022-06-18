package com.dechenkov.gitviewer.modules.core.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dechenkov.gitviewer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_authorization)
    }
}