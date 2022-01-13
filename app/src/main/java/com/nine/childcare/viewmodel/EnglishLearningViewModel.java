package com.nine.childcare.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.nine.childcare.App;
import com.nine.childcare.manager.DatabaseManager;

public class EnglishLearningViewModel extends BaseViewModel{
    private MutableLiveData<String> searchWord;

    public EnglishLearningViewModel() {
        searchWord = new MutableLiveData<>();
    }

    public void getWordFromDataBase(String s) {
        String wordMean = databaseManager.getWordMean(s.toLowerCase());
        if (wordMean != null) {
            searchWord.setValue(wordMean);
        } else {
            searchWord.setValue("KKhông có từ");
        }

    }

    public MutableLiveData<String> getSearchWord() {
        return searchWord;
    }
}
