package com.example.giphytestapp.presintation.fragment.viewGiphy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.giphytestapp.databinding.FragmentViewGiphyBinding
import com.example.giphytestapp.presintation.viewmodel.GetGiphyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewGiphyFragment : Fragment() {
    private lateinit var binding: FragmentViewGiphyBinding
    private lateinit var adapter: GiphyViewAdapter
    private val getGiphyListViewModel: GetGiphyListViewModel by activityViewModels()
    private var loadStatePosition: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewGiphyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadStatePosition = arguments?.getInt("loadStatePosition")
//        Log.d("loadStatePosition", "loadStatePosition: " + loadStatePosition)


        initRecyclerView()
        startSearch("")
//        lifecycleScope.launch {
////            delay(2000)
//            startSearch("")
////            delay(2000)
////            binding.viewPagerGiphySlider.currentItem = 11
//
//        }


    }

    private fun startSearch(product: String) {
        Log.d("startSearch", "startSearch: ")
        lifecycleScope.launch {
            getGiphyListViewModel.searchGiphy(product).collectLatest {
                Log.d("startSearch", "startSearch: " + it)
                adapter.submitData(this@ViewGiphyFragment.lifecycle, it)
            }

        }
    }

    private fun initRecyclerView() {
        adapter = GiphyViewAdapter()

        adapter.addLoadStateListener { loadState ->


            if (loadState.mediator?.refresh is LoadState.Loading) {
                if (adapter.snapshot().isEmpty()) {
//                    binding.progress.isVisible = true
                }
//                binding.errorTxt.isVisible = false
            } else {
//                binding.progress.isVisible = false
                val error = when {
                    loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                    loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                    loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    if (adapter.snapshot().isEmpty()) {
//                        binding.errorTxt.isVisible = true
//                        binding.errorTxt.text = it.error.localizedMessage
                    }
                }
            }

            Log.d("loadStatePosition", "loadStatePosition: $loadStatePosition")
            if (loadStatePosition != null) {
                binding.viewPagerGiphySlider.currentItem = loadStatePosition as Int
                loadStatePosition = null
            }
        }

        binding.viewPagerGiphySlider.adapter = adapter
//        binding.viewPagerGiphySlider.offscreenPageLimit =   

    }

}