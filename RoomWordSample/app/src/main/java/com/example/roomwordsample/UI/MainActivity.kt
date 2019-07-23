package com.example.roomwordsample.UI

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.UserDictionary
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwordsample.Adapters.WordListAdapter
import com.example.roomwordsample.R
import com.example.roomwordsample.Rooms.Word
import com.example.roomwordsample.ViewModel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var modelView: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adt=WordListAdapter(this)
        recyclerView.adapter = adt
        recyclerView.layoutManager = LinearLayoutManager(this)

        modelView=ViewModelProviders.of(this).get(WordViewModel::class.java)

        modelView.allWords.observe(this, Observer { words ->
            words?.let { adt.setWords(it) }
        })

        fab.setOnClickListener{
            val intent=Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

    }

    companion object {
        const val newWordActivityRequestCode = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK){
            data?.let{
                val word= Word(it.getStringExtra(NewWordActivity.EXTRA_REPLY))
                modelView.insert(word)
            }
        }else{
            Toast.makeText(applicationContext,R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

}
