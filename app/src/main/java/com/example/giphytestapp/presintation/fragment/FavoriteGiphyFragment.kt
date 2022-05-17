package com.example.giphytestapp.presintation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.giphytestapp.R
import com.example.giphytestapp.databinding.FragmentFavoriteGiphyBinding


class FavoriteGiphyFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteGiphyBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteGiphyBinding.inflate(inflater, container, false)
        return binding.root
    }

}