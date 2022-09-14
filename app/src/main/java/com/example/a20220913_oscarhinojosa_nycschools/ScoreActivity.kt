package com.example.a20220913_oscarhinojosa_nycschools

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        //data from previous activity "Selected School Name
        val bundle = intent.extras
        var sName = bundle!!.getString("SCHOOL_NAME")

        val selectedSchoolTextView = findViewById<TextView>(R.id.tvNameOfSchool)
        val mathTextView = findViewById<TextView>(R.id.tvMath)
        val readingTextView = findViewById<TextView>(R.id.tvReading)
        val writingTextView = findViewById<TextView>(R.id.tvWriting)
        selectedSchoolTextView.text = sName
        sName = sName?.uppercase()

        //retrofit instructions to get scores of selected school
        val retService2 = RetrofitInstance
            .getRetrofitInstance()
            .create(SchoolService::class.java)
        val responseLiveData: LiveData<Response<SchoolScores>> = liveData {
            val response = retService2.getScores(sName)
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val scoreListIt = it.body()?.listIterator()
            val scoreList = mutableListOf<SchoolScoresItem>()
            if(scoreListIt!=null){
                while (scoreListIt.hasNext()){
                    val scoreItem = scoreListIt.next()
                    scoreList.add(scoreItem)
                }
            }

            //If the school was not found it will display ???
            if(scoreList.size>0){
                val mathScore = scoreList[0].sat_math_avg_score
                val readingScore = scoreList[0].sat_critical_reading_avg_score
                val writingScore = scoreList[0].sat_writing_avg_score
                mathTextView.text = "Math: $mathScore"
                readingTextView.text = "Reading: $readingScore"
                writingTextView.text = "Math: $writingScore"
            }
            else {
                mathTextView.text = "Math: ???"
                readingTextView.text = "Reading: ???"
                writingTextView.text = "Math: ???"

            }

            })

    }
}