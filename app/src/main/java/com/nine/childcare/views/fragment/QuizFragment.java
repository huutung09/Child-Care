package com.nine.childcare.views.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.nine.childcare.R;
import com.nine.childcare.databinding.QuizFragmentBinding;
import com.nine.childcare.model.Question;
import com.nine.childcare.viewmodel.QuizViewModel;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends BaseFragment<QuizFragmentBinding, QuizViewModel> {
    private List<TextView> answerButtons;

    @Override
    protected Class<QuizViewModel> getViewModelClass() {
        return QuizViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.quiz_fragment;
    }

    @Override
    protected void initViews() {
        answerButtons = new ArrayList<>();
        answerButtons.add(binding.ansA);
        answerButtons.add(binding.ansB);
        answerButtons.add(binding.ansC);
        answerButtons.add(binding.ansD);
        setButtonListener();
        setAnswerClickable(false);
        mViewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                setQuestion(question);
                setAnswerClickable(true);
            }
        });
    }

    private void setQuestion(Question question) {
        binding.questionContent.setText(question.getQues());
        String answerAText = question.getAnsA();
        String answerBText = question.getAnsB();
        String answerCText = question.getAnsC();
        String answerDText = question.getAnsD();
        binding.ansA.setText(answerAText);
        binding.ansB.setText(answerBText);
        binding.ansC.setText(answerCText);
        binding.ansD.setText(answerDText);
    }

    private void setButtonListener() {
        for (TextView a : answerButtons) {
            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleChooseAnswer(a, answerButtons.indexOf(a));
                }
            });
        }
    }

    private void setAnswerClickable(Boolean b) {
        for (TextView a : answerButtons) {
           a.setClickable(b);
        }
    }

    private void playGame() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewModel.nextQuestion();
                setAnswerClickable(true);
                for (TextView a : answerButtons) {
                    a.setBackgroundResource(R.drawable.background_ans);
                }
            }
        }, 2000);
    }

    private void handleChooseAnswer(TextView answerBtn, int pos) {
        setAnswerClickable(false);
        if (mViewModel.checkAns(pos)) {
            answerBtn.setBackgroundResource(R.drawable.background_ans_true);
            playGame();
        } else {
            answerButtons.get(mViewModel.getTrueCase()).setBackgroundResource(R.drawable.background_ans_true);
            answerBtn.setBackgroundResource(R.drawable.background_ans_false);
            playGame();
        }
    }

}
