package com.example.roomwordsample.Repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.roomwordsample.DAO.WordDao
import com.example.roomwordsample.Rooms.Word

class WordRepository(val wordDao: WordDao){
    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word:Word){
        wordDao.insert(word)
    }

}