package com.example.podcaster.fragments

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.podcaster.R
import com.example.podcaster.adapters.PodcastsAdapter
import com.example.podcaster.databinding.FragmentDiscoverBinding
import java.lang.Math.abs


class DiscoverFragment : Fragment() {

    lateinit var binding: FragmentDiscoverBinding
    lateinit var tabs: MutableList<TextView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabs()
        initPodcasts()
        initLists()
    }

    fun initLists() {
        val adapter = PodcastsAdapter()
        val manager = LinearLayoutManager(context, HORIZONTAL, false)
        val manager2 = LinearLayoutManager(context, HORIZONTAL, false)

        binding.rvList1.adapter = adapter
        binding.rvList1.layoutManager = manager

        binding.rvList2.adapter = adapter
        binding.rvList2.layoutManager = manager2

        binding.rvList2.addItemDecoration(FirstItemMarginleftDecoration())
        binding.rvList1.addItemDecoration(FirstItemMarginleftDecoration())

    }

    fun initTabs() {
        tabs = mutableListOf(binding.tvTab1, binding.tvTab2, binding.tvTab3, binding.tvTab4)

        for(i in tabs) {
            i.setOnClickListener {
                for(u in tabs) {
                    u.background = null
                }
                i.background = context?.getDrawable(R.drawable.tab_selected)
            }
        }
    }

    fun initPodcasts() {
        binding.viewPager.adapter = activity?.let { PagerAdapter(fragmentActivity = it) }

        binding.viewPager.offscreenPageLimit = 1

        val nextItemVisiblePx = 100
        val currentItemHorizontalMarginPx = 100
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.2f * abs(position))
            page.scaleX = 1 - (0.2f * abs(position))
        }

        binding.viewPager.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            170
        )
        binding.viewPager.addItemDecoration(itemDecoration)

    }

    class HorizontalMarginItemDecoration(context: Context, horizontalMarginInPx: Int) :
        RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int =
            horizontalMarginInPx

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
        }

    }

    class FirstItemMarginleftDecoration: RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
//            parent.getChildAdapterPosition(view)
            if(parent.getChildAdapterPosition(view) == 0) {
                outRect.left = 64
            }
        }
    }


    class PagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = 6

        override fun createFragment(position: Int): Fragment {
            return PodcastCarouselItemFragment()
        }

    }



}