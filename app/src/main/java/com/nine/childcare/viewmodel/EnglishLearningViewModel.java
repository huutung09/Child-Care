package com.nine.childcare.viewmodel;

import androidx.lifecycle.MutableLiveData;


public class EnglishLearningViewModel extends BaseViewModel{
    private MutableLiveData<String> searchWord = new MutableLiveData<>();

    public void getWordFromDataBase(String s) {
        String wordMean = databaseManager.getWordMean(s.toLowerCase());
        if (wordMean != null) {
            searchWord.setValue(wordMean);
        } else {
            searchWord.setValue("KKKhông có từ");
        }
    }

    public MutableLiveData<String> getSearchWord() {
        return searchWord;
    }
}
