package com.nine.childcare.viewmodel;



import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nine.childcare.model.Question;


public class QuizViewModel extends BaseViewModel {
    private MutableLiveData<Question> questionMutableLiveData = new MutableLiveData<>();
    private int mId;
    private Question currentQues;
    private DatabaseReference quesIdRef;

    public QuizViewModel() {
        quesIdRef = firebaseDatabase.getReference("users").child(firebaseAuth.getCurrentUser().getUid()).child("quesId");
        quesIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    mId = ((Long) snapshot.getValue()).intValue();
                    setQues(mId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void nextQuestion() {
        mId++;
        currentQues = databaseManager.getQuestion(mId);
        questionMutableLiveData.setValue(currentQues);
        quesIdRef.setValue(mId);
    }

    public boolean checkAns(int ans) {
        return ans == currentQues.getTrueAns();
    }

    public int getTrueCase() {
        return currentQues.getTrueAns();
    }

    public void setQues(int id) {
        currentQues = databaseManager.getQuestion(mId);
        questionMutableLiveData.setValue(databaseManager.getQuestion(id));
    }

    public MutableLiveData<Question> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }
}
