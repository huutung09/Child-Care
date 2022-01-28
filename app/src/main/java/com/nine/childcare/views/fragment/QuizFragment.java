package com.nine.childcare.views.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.nine.childcare.R;
import com.nine.childcare.databinding.QuizFragmentBinding;
import com.nine.childcare.model.Question;
import com.nine.childcare.viewmodel.QuizViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuizFragment extends BaseFragment<QuizFragmentBinding, QuizViewModel> {
    private List<TextView> answerButtons;
    private List<ImageView> soundButtons;
    private TextToSpeech textToSpeech;

    @Override
    protected Class<QuizViewModel> getViewModelClass() {
        return QuizViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.quiz_fragment;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        textToSpeech = new TextToSpeech(requireActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
        answerButtons = new ArrayList<>();
        soundButtons = new ArrayList<>();
        answerButtons.add(binding.ansA);
        answerButtons.add(binding.ansB);
        answerButtons.add(binding.ansC);
        answerButtons.add(binding.ansD);
        soundButtons.add(binding.btnAnsASound);
        soundButtons.add(binding.btnAnsBSound);
        soundButtons.add(binding.btnAnsCSound);
        soundButtons.add(binding.btnAnsDSound);
        setButtonListener();
        setAnswerClickable(false);
        binding.btnQuesSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String speakText = binding.questionContent.getText().toString().trim().replaceAll("[.]{2,}", " dot dot dot ");
                textToSpeech.speak(speakText, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
        binding.btnNextQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                mViewModel.nextQuestion();
            }
        });

        mViewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                setQuestion(question);
                setAnswerClickable(true);
                for (TextView a : answerButtons) {
                    a.setBackgroundResource(R.drawable.background_ans);
                }
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), this::makeToast);
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
        for (int i = 0; i < answerButtons.size(); i++) {
            TextView a = answerButtons.get(i);
            ImageView img = soundButtons.get(i);
            a.setOnClickListener(v -> handleChooseAnswer(a, answerButtons.indexOf(a)));
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!a.getText().toString().trim().equals("")) {
                        textToSpeech.speak(a.getText().toString().substring(3).trim(), TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                }
            });

        }
    }

    private void setAnswerClickable(Boolean b) {
        for (TextView a : answerButtons) {
            a.setClickable(b);
        }
    }

    private void handleChooseAnswer(TextView answerBtn, int pos) {
        setAnswerClickable(false);
        if (mViewModel.checkAns(pos)) {
            answerBtn.setBackgroundResource(R.drawable.background_ans_true);
        } else {
            answerButtons.get(mViewModel.getTrueCase()).setBackgroundResource(R.drawable.background_ans_true);
            answerBtn.setBackgroundResource(R.drawable.background_ans_false);
        }
        binding.btnNextQues.setVisibility(View.VISIBLE);
        String[] quesText = binding.questionContent.getText().toString().trim().split("[.]{2,}");
        Spannable ansWord = new SpannableString(answerButtons.get(mViewModel.getTrueCase()).getText().toString().substring(2).trim());
        ansWord.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, ansWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.questionContent.setText("");
        binding.questionContent.append(quesText[0]);
        binding.questionContent.append(ansWord);
        binding.questionContent.append(quesText[1]);
    }

}
