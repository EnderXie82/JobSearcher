package com.enderx.jobsearcher

import android.os.Bundle
import com.enderx.jobsearcher.core.BaseActivity
import com.enderx.jobsearcher.databinding.ActivityMainBinding
import com.enderx.jobsearcher.databinding.LayoutLoadingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var dataBinding: ActivityMainBinding

    override fun getLoadingBinding(): LayoutLoadingBinding = dataBinding.loadingLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
    }
}