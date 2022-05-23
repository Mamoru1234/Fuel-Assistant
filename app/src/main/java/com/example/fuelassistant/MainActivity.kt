package com.example.fuelassistant

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fuelassistant.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculate.setOnClickListener { calculate() }
    }

    private fun calculate() {
        val distanceText = binding.distance.text
        val aSpeedText = binding.aSpeed.text
        val bSpeedText = binding.bSpeed.text
        if (distanceText.isEmpty() || aSpeedText.isEmpty() || bSpeedText.isEmpty()) {
            binding.result.text = resources.getString(R.string.invalid_input)
            return
        }
        try {
            val aSpeed = aSpeedText.toString().toInt()
            val bSpeed = bSpeedText.toString().toInt()
            val distance = distanceText.toString().toInt()
            val aScale: Double = (60).toDouble() / aSpeed
            val bScale: Double = (60).toDouble() / bSpeed
            val aTime = (distance * aScale).roundToInt()
            val bTime = (distance * bScale).roundToInt()
            Log.i(TAG, "aTime $aTime")
            Log.i(TAG, "bTime $bTime")
            val timeDiff = abs(aTime - bTime)
            binding.result.text = resources.getString(R.string.result, formatMinutes(timeDiff))
        } catch (e: NumberFormatException) {
            Log.e(TAG, "Number format exception", e)
            binding.result.text = resources.getString(R.string.invalid_input)
        }
    }

    private fun formatMinutes(value: Int): String {
        val hours = floor(value.toDouble() / 60).toInt()
        return if (hours > 0) {
            val minutes = value % 60
            "$hours:$minutes"
        } else {
            value.toString()
        }
    }
}