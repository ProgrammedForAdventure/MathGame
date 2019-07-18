package com.example.mathgame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var openingLayout : ConstraintLayout
    lateinit var gameplayLayout : ConstraintLayout
    lateinit var button0 : Button
    lateinit var button1 : Button
    lateinit var button2 : Button
    lateinit var button3 : Button
    lateinit var goButton : Button
    lateinit var playAgainButton : Button
    lateinit var timerTextView : TextView
    lateinit var resultTextView : TextView
    lateinit var scoreTextView : TextView
    var answerLocation : Int = 0
    var score : Int = 0
    var totalQuestions : Int = 0

    fun start(view : View){
        goButton.visibility = View.INVISIBLE
        gameplayLayout.visibility = View.VISIBLE

        playAgain(playAgainButton)
    }

    fun chooseAnswer(view : View){
        Log.d("chooseAnswer", "answerLocation: " + answerLocation.toString())
        Log.d("chooseAnswer", "view.tag: " + view.tag.toString())
        if(answerLocation.toString() == (view.tag)) {
            resultTextView.text = "Correct!"
            score++
        }
        else {
            resultTextView.text = "Wrong :("
        }

        // In case the results text is not visible, make it visible
        resultTextView.visibility = View.VISIBLE

        totalQuestions++
        scoreTextView.text = score.toString() + "/" + totalQuestions.toString()
        newQuestion()
    }

    private fun newQuestion(){
        var problemTextView : TextView = findViewById(R.id.problemTextView)
        var answers : ArrayList<Int> = ArrayList()

        // Create the random number generator
        var random : Random = Random()

        // Get a random number for a and b between 0 and 20
        var a : Int = random.nextInt(21)
        var b : Int = random.nextInt(21)

        problemTextView.text = a.toString() + " + " + b.toString()

        answerLocation = random.nextInt(4)

        // Reset the array before adding more to it
        answers.clear()
        for(i in 0..3) {
            if(i == answerLocation) {
                answers.add(a + b)
            }
            else {
                // We want to add a random value, between 0 and the max of the other two
                var wrongAnswer : Int = random.nextInt(41)

                // If the wrong answer is the same as the right answer, get a new wrong answer
                while(wrongAnswer == (a + b)) {
                    wrongAnswer = random.nextInt(41)
                }

                answers.add(wrongAnswer)
            }
        }

        button0.text = answers[0].toString()
        button1.text = answers[1].toString()
        button2.text = answers[2].toString()
        button3.text = answers[3].toString()
    }

    fun playAgain(view : View){
        score = 0
        totalQuestions = 0

        playAgainButton.visibility = View.INVISIBLE
        resultTextView.visibility = View.INVISIBLE
        button0.isClickable = true
        button1.isClickable = true
        button2.isClickable = true
        button3.isClickable = true
        newQuestion()

        scoreTextView.text = score.toString() + "/" + totalQuestions.toString()

        val timer = object: CountDownTimer(30100, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = (millisUntilFinished/1000).toString() + "s"
            }

            override fun onFinish() {
                // Make the results visible
                resultTextView.text = "Done!"
                resultTextView.visibility = View.VISIBLE
                playAgainButton.visibility = View.VISIBLE

                // Deactivate the gameplay buttons
                button0.isClickable = false
                button1.isClickable = false
                button2.isClickable = false
                button3.isClickable = false
            }
        }
        timer.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        goButton = findViewById(R.id.goButton)
        playAgainButton = findViewById(R.id.playAgainButton)
        timerTextView = findViewById(R.id.timeLeftTextView)
        resultTextView = findViewById(R.id.resultTextView)
        scoreTextView = findViewById(R.id.scoreTextView)

        openingLayout = findViewById(R.id.openingLayout)
        gameplayLayout = findViewById(R.id.gameplayLayout)

        goButton.visibility = View.VISIBLE
        gameplayLayout.visibility = View.INVISIBLE
    }
}
