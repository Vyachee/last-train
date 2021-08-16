package com.example.podcaster.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.podcaster.R
import com.example.podcaster.common.DateFormatter
import com.example.podcaster.databinding.FragmentRecordBinding
import com.example.podcaster.databinding.InputFileQuestionBinding
import kotlinx.coroutines.*
import java.io.File
import java.util.jar.Manifest

class RecordFragment : Fragment() {

    lateinit var binding: FragmentRecordBinding
    var filename = ""

    var seconds = 0

    var isRecording = false
    lateinit var recorder: MediaRecorder

    var timer: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecordBinding.inflate(inflater)
        return binding.root
    }

    fun startRecording() {
        recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)

        var newPath = context?.externalCacheDir?.absolutePath + "/" +
                DateFormatter().getCurrentDateFormatted()

        val dir = File(newPath)
        if(!dir.exists()) {
            dir.mkdir()
        }

        newPath += "/" + filename + ".3gp"


        recorder.setOutputFile(newPath)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        recorder.prepare()
        recorder.start()

        isRecording = true
        setStatus("Recording")

        timer = GlobalScope.launch(Dispatchers.Main) {
            while(true) {
                delay(1000)
                seconds++
                setTime(String.format("%02d:%02d", (seconds % 3600) / 60, seconds % 60))

            }
        }

    }
    private fun stopRecording() {
        recorder.release()
        Toast.makeText(context, "Recording stop", Toast.LENGTH_SHORT).show()
        isRecording = false
        timer?.cancel()
        setTime("00:00")
        setStatus("Press record button to start record!")

        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(100)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPerms()

        binding.tvStatus.setOnClickListener {
            checkPerms()
        }

        binding.ivBtnRecord.setOnClickListener {
            if(!isRecording) {
                showAlert()
            }   else {
                stopRecording()
            }
        }
    }


    fun showAlert() {

        val alertBinding = InputFileQuestionBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(context)
            .setTitle("Enter file name you want")
            .setView(alertBinding.root)
            .setPositiveButton("OK", {_, it ->
                filename = alertBinding.etFilename.text.toString()
                if(filename.isEmpty()) {
                    filename = DateFormatter().getCurrentTimeFormatted()
                }
                startRecording()
            })
            .setNegativeButton("Cancel", {_, it ->

            })

        val alert = builder.create()
        alert.show()
    }

    fun checkPerms() {
        GlobalScope.launch(Dispatchers.Main) {
            if(
                requireContext().checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                &&
                requireContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                binding.ivBtnRecord.visibility = View.VISIBLE
                setStatus("Press record button to start record!")
            }   else {

                setStatus("Waiting for permission")
                binding.ivBtnRecord.visibility = View.GONE
                requestPermissions(arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            binding.ivBtnRecord.visibility = View.VISIBLE
            setStatus("Press record button to start record!")
        }   else {
            setStatus("I can't record voice without permission. Click me!")
        }
    }



    fun setStatus(text: String) {
        binding.tvStatus.text = text
    }

    fun setTime(text: String) {
        binding.tvTimer.text = text
    }
}