package com.example.podcaster.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.podcaster.R
import com.example.podcaster.TrackActivity

class PodcastCarouselItemFragment : Fragment() {
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_podcast_carousel_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener {
            requireContext().startActivity(Intent(activity, TrackActivity::class.java))
        }
    }

    fun newInstance(): PodcastCarouselItemFragment {
        return PodcastCarouselItemFragment()
    }

}