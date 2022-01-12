package com.nine.childcare.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nine.childcare.App;
import com.nine.childcare.manager.DatabaseManager;
import com.nine.childcare.model.Word;

import java.util.List;

public class EnglishLearningViewModel extends BaseViewModel{
    private final DatabaseManager databaseManager;
    private MutableLiveData<List<Word>> searchWord;

    public EnglishLearningViewModel() {
        databaseManager = App.getInstance().getDatabaseManager();
        searchWord = new MutableLiveData<>();
    }

    public void getWordFromDataBase(String s) {
        List<Word> wordList = databaseManager.getWordMean(s);
        if (wordList != null) {
            searchWord.postValue(wordList);
            for (Word w : wordList) {
                Log.e("out", "getWordFromDataBase: " + w.getWord());
            }
        }

    }

    public MutableLiveData<List<Word>> getSearchWord() {
        return searchWord;
    }
}
