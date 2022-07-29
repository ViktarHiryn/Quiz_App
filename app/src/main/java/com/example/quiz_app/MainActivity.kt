package com.example.quiz_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.quiz_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val QUIZ_COUNT = 5

    private val quizData = mutableListOf(
        // country, capital, choice1, choice2, choice3
        mutableListOf("China", "Beijing", "Jakarta", "Manila", "Stockholm"),
        mutableListOf("India", "New Delhi", "Beijing", "Bangkok", "Seoul"),
        mutableListOf("Indonesia", "Jakarta", "Manila", "New Delhi", "Kuala Lumpur"),
        mutableListOf("Japan", "Tokyo", "Bangkok", "Taipei", "Jakarta"),
        mutableListOf("Thailand", "Bangkok", "Berlin", "Havana", "Kingston"),
        mutableListOf("Brazil", "Brasilia", "Havana", "Bangkok", "Copenhagen"),
        mutableListOf("Canada", "Ottawa", "Bern", "Copenhagen", "Jakarta"),
        mutableListOf("Cuba", "Havana", "Bern", "London", "Mexico City"),
        mutableListOf("Mexico", "Mexico City", "Ottawa", "Berlin", "Santiago"),
        mutableListOf("United States", "Washington D.C.", "San Jose", "Buenos Aires", "Kuala Lumpur"),
        mutableListOf("France", "Paris", "Ottawa", "Copenhagen", "Tokyo"),
        mutableListOf("Germany", "Berlin", "Copenhagen", "Bangkok", "Santiago"),
        mutableListOf("Italy", "Rome", "London", "Paris", "Athens"),
        mutableListOf("Spain", "Madrid", "Mexico City", "Jakarta", "Havana"),
        mutableListOf("United Kingdom", "London", "Rome", "Paris", "Singapore")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Shuffle Quiz
        quizData.shuffle()

        showNextQuiz()
    }

    fun showNextQuiz () {
        //Updating count label.
        binding.countLabel.text = getString(R.string.count_label, quizCount)

        //Picking one quiz set.
        val quiz = quizData[0]

        //Setting a question and a right answer
        binding.questionLabel.text = quiz[0]
        rightAnswer = quiz [1]

        //Removes country from list.
        quiz.removeAt(0)

        //Shuffle answer and choices
        quiz.shuffle()

        //Setting Choices
        binding.btnAnswer1.text = quiz [0]
        binding.btnAnswer2.text = quiz [1]
        binding.btnAnswer3.text = quiz [2]
        binding.btnAnswer4.text = quiz [3]

        //Removes this quiz from quizData.
        quizData.removeAt(0)

    }

    fun checkAnswer(view: View) {
        //Getting a pushed button.
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        val alertTitle: String
        if (btnText == rightAnswer) {
            //Correct
            alertTitle = "Correct!"
            rightAnswerCount++
        }
        else {
            //Wrong
            alertTitle = "Wrong..."
        }

        //Creating Dialog
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("Answer: $rightAnswer")
            .setPositiveButton("OK") {dialogInterface, i ->
                checkQuizCount()
            }
            .setCancelable(false)
            .show()
    }

    fun checkQuizCount() {
        if (quizCount == QUIZ_COUNT) {
            //Show results
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)
        }
        else {
            quizCount++
            showNextQuiz()
        }
    }
}