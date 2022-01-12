package com.nine.childcare.views.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.Observer;

import com.nine.childcare.R;
import com.nine.childcare.databinding.EnglishLearningFragmentBinding;
import com.nine.childcare.model.Word;
import com.nine.childcare.viewmodel.EnglishLearningViewModel;

import java.util.ArrayList;
import java.util.List;

public class EnglishLearningFragment extends BaseFragment<EnglishLearningFragmentBinding, EnglishLearningViewModel> {
    private ArrayAdapter<String> arrayAdapter;
    private List<String> e = new ArrayList<>();

    @Override
    protected Class<EnglishLearningViewModel> getViewModelClass() {
        return EnglishLearningViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.english_learning_fragment;
    }

    @Override
    protected void initViews() {
        arrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1,
                e);
        binding.wordList.setAdapter(arrayAdapter);
        mViewModel.getSearchWord().observe(getViewLifecycleOwner(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                e.clear();
                for (Word w : words) {
                    e.add(w.getWord());
                }

            }
        });
        mViewModel.getWordFromDataBase("hello");

//        binding.englishWordSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() >= 3) {
//                    e.clear();
//                    mViewModel.getWordFromDataBase(s.toString().trim());
//                    arrayAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }
}
