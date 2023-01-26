package com.example.quizit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quizit.QuizViewModel
import com.example.quizit.databinding.FragmentQuizBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding

    private val viewModel : QuizViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        updateUi()

        binding.musicianButton.setOnClickListener {
            viewModel.checkAnswer(true)
            if (!viewModel.currentQuestion.isMusician){
                binding.wrongText!!.text = "Falsche Antwort"
            } else{
                binding.wrongText!!.text = ""
            }
            updateUi()
        }

        binding.footballButton.setOnClickListener {
            viewModel.checkAnswer(false)
            if (viewModel.currentQuestion.isMusician){
                binding.wrongText!!.text = "Falsche Antwort"
            } else{
                binding.wrongText!!.text = ""
            }
            updateUi()
        }
    }

    private fun updateUi() {
        binding.scoreText.text = viewModel.score.toString()
        binding.questionText.text = viewModel.currentQuestion.name

        if (viewModel.score == 5){
            showEndDialog()
        }
    }

    private fun showEndDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Gut gemacht!")
            .setMessage("Du hast viel Wissen!")
            .setCancelable(false)
            .setNegativeButton("verlassen") { _,_ ->
                activity?.finish()
            }
            .setPositiveButton("wiederholen") { _,_ ->
                viewModel.restartGame()
                updateUi()
            }
            .show()
    }
}
