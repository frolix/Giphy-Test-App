package com.example.giphytestapp.presintation.fragment.giphyFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.GiphyItem
import com.example.giphytestapp.R
import com.example.giphytestapp.databinding.FragmentGiphyBinding
import com.example.giphytestapp.presintation.viewmodel.GetGiphyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GiphyFragment : Fragment(), GiphyViewHolder.OnImageViewClickListener,
    GiphyViewHolder.OnClickFavoriteClickListener {

    private lateinit var binding: FragmentGiphyBinding
    private val getGiphyListViewModel: GetGiphyListViewModel by activityViewModels()
    private lateinit var adapter: GiphyListAdapter
    private var layoutManagerGrid: GridLayoutManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGiphyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Viewmodel", "viewModel: " + getGiphyListViewModel.toString())


        initAdapter()
        initView()



        startSearch("")
    }

    private fun initView() {

        binding.searchGiphyEdit.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    startSearch(v.text.toString())
                    true
                }
                else -> false
            }
        }
        val listOfSpinnerItem = arrayOf("List", "Table")
        val spinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.spinner_item, listOfSpinnerItem)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)

        binding.spinnerLayout.adapter = spinnerAdapter
        binding.spinnerLayout.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (position) {
                    0 -> layoutManagerGrid?.spanCount = 1
                    1 -> layoutManagerGrid?.spanCount = 2
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }


    private fun startSearch(product: String) {
        Log.d("startSearch", "startSearch: ")
        lifecycleScope.launch {
            getGiphyListViewModel.searchGiphy(product).collectLatest {
                adapter.submitData(this@GiphyFragment.lifecycle, it)
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onClickFavoriteViewListener(item: GiphyItem) {
        super.onClickFavoriteViewListener(item)


    }

    override fun onClickImageViewListener(item: GiphyItem,position:Int) {
        super.onClickImageViewListener(item, position )

        findNavController().navigate(
            R.id.action_giphyFragment_to_viewGiphyFragment,
            bundleOf(
                "loadStatePosition" to (position),

                )
        )
//        Log.d("onClickImageViewListener", "onClickImageViewListener: ${adapter.snapshot()}")
    }


    private fun initAdapter() {
        adapter = GiphyListAdapter(this, this)
        layoutManagerGrid = GridLayoutManager(requireContext(), 2)


        binding.recyclerViewGiphy.apply {
            layoutManager = layoutManagerGrid
            setHasFixedSize(true)
        }

        binding.recyclerViewGiphy.adapter = adapter

        adapter.addLoadStateListener { loadState ->
            Log.d("addLoadStateListener", "loadState" + loadState)
//            Log.d("addLoadStateListener","loadState.mediator?.prepend" + (loadState.mediator?.prepend ?: "null"))
//            Log.d("addLoadStateListener","loadState.mediator?.append" + (loadState.mediator?.append ?: "null"))
//            Log.d("addLoadStateListener","loadState.mediator?.refresh" + (loadState.mediator?.refresh ?: "null"))
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
        }
    }

}