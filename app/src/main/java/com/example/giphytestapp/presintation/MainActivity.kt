package com.example.giphytestapp.presintation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.giphytestapp.R
import com.example.giphytestapp.presintation.fragment.giphyFragment.GiphyListAdapter
import com.example.giphytestapp.databinding.ActivityMainBinding
import com.example.giphytestapp.presintation.fragment.giphyFragment.GiphyFragment
import com.example.giphytestapp.presintation.fragment.viewGiphy.ViewGiphyFragment
import com.example.giphytestapp.presintation.viewmodel.GetGiphyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, ViewGiphyFragment())

//        lifecycleScope.launch {
//            delay(3000)
//            initView()
//        }
    }

    private fun initView() {
        navController.navigate(
            R.id.viewGiphyFragment
        )
    }

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

}