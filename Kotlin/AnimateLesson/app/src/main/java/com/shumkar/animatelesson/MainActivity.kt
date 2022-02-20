package com.shumkar.animatelesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.shumkar.animatelesson.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.animateAlpha.setOnClickListener {
            animateAlpha()
        }
        binding.animateTranslationXYZ.setOnClickListener {
            animateTranslationXYZ()
        }
        binding.animateRotation.setOnClickListener {
            animateRotation()
        }
        binding.animateRotationX.setOnClickListener {
            animateRotationX()
        }
        binding.animateScale.setOnClickListener {
            animateScale()
        }
        binding.animateDelay.setOnClickListener {
            animateDelay()
        }
        binding.animateAction.setOnClickListener {
            animateAction()
        }

    }

    // Анимация прозрачности
    fun animateAlpha(){
        binding.button.alpha = 0f
        binding.button.animate().alpha(1f).duration = 1500
    }

    // Анимация по  позиции XYZ
    fun animateTranslationXYZ(){
        binding.button.alpha = 0f
        binding.button.translationY = 50f
        binding.button.translationX = 50f
        binding.button.translationZ = 50f
        binding.button
            .animate()
            .alpha(1f)
            .translationY(-50f)
            .translationX(-50f)
            .translationZ(-50f)
            .duration = 1500
    }

    // Анимация Поворота : Rotate
    fun animateRotation(){
        binding.button.animate().rotation(120f).duration = 3000
    }

    // Анимация Поворота - Rotate on Axis
    fun animateRotationX(){
        binding.button.animate().rotationX(360f).duration = 3000
    }

    // Анимация Scale the Button
    fun animateScale(){
        binding.button.animate().scaleYBy(1f).duration = 3000
    }

    // Анимация Подождать : Set Delay
    fun animateDelay(){
        binding.button.alpha = 0f
        binding.button.translationY = 50f
        binding.button.animate().alpha(1f).translationYBy(-50f).setStartDelay(200).duration = 3000
    }

    // Анимация Действия : Set Action
    fun animateAction(){
        binding.button.alpha = 0f
        binding.button.translationY = 50f
        binding.button.animate().alpha(1f).translationYBy(-50f).setStartDelay(200)
            .withStartAction {
                Toast.makeText(this,"Animating Button Start", Toast.LENGTH_SHORT).show()
            }
            .withEndAction {
                Toast.makeText(this,"Animating Button End", Toast.LENGTH_SHORT).show()
            }
            .duration = 1500
    }

}