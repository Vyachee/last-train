package com.example.podcaster

import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.podcaster.databinding.ActivityTrackBinding
import android.R.attr.path

import android.media.MediaPlayer
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.podcaster.adapters.TracksAdapter
import com.example.podcaster.common.FileSystemHelper
import kotlinx.coroutines.*
import java.io.File
import java.lang.Exception
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.FileInputStream


class TrackActivity : AppCompatActivity() {

    lateinit var binding: ActivityTrackBinding
    lateinit var mp: MediaPlayer
    var isPlaying = false
    var isFirst = true
    var job: Job? = null

    lateinit var tabs: MutableList<TextView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        initTracksList()
        initTabs()

    }

    fun initBottomSheet() {

        toggleBottomSheetBg(true)

        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from<View>(binding.includeBigPlayer.root)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetBehavior.setBottomSheetCallback(object:
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    toggleBottomSheetBg(false)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })

        binding.includeBigPlayer.ivClose.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            toggleBottomSheetBg(false)
        }
    }

    fun toggleBottomSheetBg(state: Boolean) {
        if(state) {
            // show
            binding.bottomSheetBg.alpha = 0f
            binding.bottomSheetBg.visibility = View.VISIBLE
            binding.bottomSheetBg.animate().alpha(1f).setDuration(300)
        }   else {
            //hide
            binding.bottomSheetBg.animate().alpha(0f).setDuration(300)
            GlobalScope.launch(Dispatchers.Main) {
                delay(300)
                binding.bottomSheetBg.alpha = 0f
                binding.bottomSheetBg.visibility = View.GONE
            }
        }
    }

    fun initTabs() {
        tabs = mutableListOf(binding.tvTab11, binding.tvTab12, binding.tvTab13)

        for(i in tabs) {
            i.setOnClickListener {
                for(u in tabs) {
                    u.background = null
                }
                i.background = baseContext.getDrawable(R.drawable.tabv2_selected)
            }
        }
    }

    fun initTracksList() {

        val adapter = TracksAdapter()
        val manager = LinearLayoutManager(baseContext)

        binding.rvList3.adapter = adapter
        binding.rvList3.layoutManager = manager

    }

    fun stop() {
        Log.e("DEBUG", "pause")
        mp.pause()
    }

    fun unPause() {
        Log.e("DEBUG", "started")
        mp.start()
    }

    fun playFirst() {
        mp = MediaPlayer()

        val list = FileSystemHelper().getRecords(this)
        val last = list.last()


        binding.includeMiniplayer.tvMiniTitle.text = last.title
        binding.includeBigPlayer.tvBigTitle.text = last.title

        val newPath = last.path
        val file = File(newPath)
        val fis = FileInputStream(file)


        try {
            mp.setDataSource(fis.fd)
            mp.prepare()
            mp.setOnPreparedListener {
                mp.start()
                mp.setVolume(100f, 100f)
                job = GlobalScope.launch {

                    while(true) {
                        delay(500)

                        val duration = mp.duration
                        val current = mp.currentPosition

                        val progress = (current.toDouble() / duration.toDouble())
                        initTimeline(progress)
                        initTimeProgress(duration/1000, current/1000)
                    }

                }
            }

            mp.setOnErrorListener(object: MediaPlayer.OnErrorListener {
                override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
                    return false
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(baseContext, "Unexpected error. Shit happens", Toast.LENGTH_SHORT).show()
        }



    }

    fun initTimeProgress(duration: Int, progress: Int) {



        val duration = String.format("%02d:%02d", (duration % 3600) / 60, duration % 60)

        val progress = String.format("%02d:%02d", (progress % 3600) / 60, progress % 60)

        GlobalScope.launch(Dispatchers.Main) {
            binding.includeBigPlayer.tvProgress.text = "$progress / $duration"
        }

    }

    fun initTimeline(progress: Double) {
        val maxPx = binding.includeMiniplayer.ivProgressBg.width
        val needlePx = maxPx * progress
        val params = binding.includeMiniplayer.ivProgress.layoutParams

        params.width = needlePx.toInt()

        GlobalScope.launch(Dispatchers.Main) {
            binding.includeMiniplayer.ivProgress.layoutParams = params
        }

        // for bottom sheet

        val maxPx2 = binding.includeBigPlayer.seekBar.width
        val needlePx2 = maxPx2 * progress

        val params2 = binding.includeBigPlayer.ivProgress.layoutParams

        params2.width = needlePx2.toInt()
        GlobalScope.launch(Dispatchers.Main) {
            binding.includeBigPlayer.ivProgress.layoutParams = params2
        }


    }

    fun initListeners() {
        binding.includeMiniplayer.ivPlayToggle.setOnClickListener {
            if(isFirst) {
                isFirst = false
                playFirst()
            }   else {
                if(isPlaying) {
                    stop()
                }   else {
                    unPause()
                }
            }

            toggleIcon()
        }

        binding.includeBigPlayer.ivPlayToggle2.setOnClickListener {
            binding.includeMiniplayer.ivPlayToggle.callOnClick()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.includeMiniplayer.root.setOnClickListener {
            initBottomSheet()
        }
    }

    fun toggleIcon() {
        if(isPlaying) {
            binding.includeMiniplayer.ivPlayToggle.setImageDrawable(getDrawable(R.drawable.ic_play))
            binding.includeBigPlayer.ivPlayToggle2.setImageDrawable(getDrawable(R.drawable.ic_play))
        }   else {
            binding.includeMiniplayer.ivPlayToggle.setImageDrawable(getDrawable(R.drawable.ic_pause))
            binding.includeBigPlayer.ivPlayToggle2.setImageDrawable(getDrawable(R.drawable.ic_pause))
        }
        isPlaying = !isPlaying
    }
}