package com.example.a20220913_oscarhinojosa_nycschools

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.SchoolsRecyclerViewAdapter
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var schoolRecyclerView: RecyclerView
    private lateinit var adapter: SchoolsRecyclerViewAdapter
    private lateinit var selectedSchool: SchoolsItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        schoolRecyclerView = findViewById(R.id.rvSchools)
        val scoreButton = findViewById<Button>(R.id.btnScores)
        //Uses retrofit to gather data
        val retService = RetrofitInstance
            .getRetrofitInstance()
            .create(SchoolService::class.java)
        val responseLiveData: LiveData<Response<Schools>> = liveData {
            val response = retService.getSchools()
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            //iterates then stores in schoolList to then call initRecyclerView
            val schoolsListIt = it.body()?.listIterator()
            val schoolsList = mutableListOf<SchoolsItem>()
            if(schoolsListIt!=null){
                while (schoolsListIt.hasNext()){
                    val schoolsItem = schoolsListIt.next()
                    schoolsList.add(schoolsItem)
                    //Log.i("MYTAG",schoolsItem.school_name)
                }
            }
            initRecyclerView(schoolsList)

            //starts new activity with selected school name in "bundle"
            scoreButton.setOnClickListener {
                //makes button invisible when you return
                scoreButton.isVisible=false

                val intent = Intent(this, ScoreActivity::class.java)
                //Create the bundle
                val bundle = Bundle()
                //Move data to next activity
                bundle.putString("SCHOOL_NAME", selectedSchool.school_name)
                //Add the bundle to the intent
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })

    }

    //Recycler view display and onClick instructions
    private fun initRecyclerView(it:List<SchoolsItem>){
        schoolRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SchoolsRecyclerViewAdapter{
            selectedItem: SchoolsItem -> listItemClicked(selectedItem)
        }
        schoolRecyclerView.adapter = adapter
        displaySchoolList(it)
    }

    private fun displaySchoolList(it:List<SchoolsItem>){
        adapter.setList(it)
    }

    private fun listItemClicked(school: SchoolsItem){
        selectedSchool= school
        val buttonScore = findViewById<Button>(R.id.btnScores)
        if(selectedSchool.school_name!=""){
            //button will be invisible if no school is selected
            buttonScore.isVisible=true
        }
        Toast.makeText(this, selectedSchool.school_name, Toast.LENGTH_SHORT).show()
    }
}