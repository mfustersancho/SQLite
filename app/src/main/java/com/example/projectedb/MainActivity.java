package com.example.projectedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.projectedb.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private DBAssistant mDBAssistant;

    private ArrayList<CommentaryModel> mCommentaryList;

    private Integer mCommentaryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnVeure.setOnClickListener(v -> {
            viewCommentary();
        });
        mBinding.btnCrear.setOnClickListener(v -> {
            addCommentary();
        });

        mBinding.btnBorrar.setOnClickListener(v -> {
            deleteCommentary();
        });

        mCommentaryList = new ArrayList<>();

        mDBAssistant = new DBAssistant(this);

        initializeList();
    }

    private void initializeList() {
        ArrayList<String> titleList = new ArrayList<>();
        mCommentaryList = mDBAssistant.getCommentaries();
        mCommentaryList.forEach(commentary -> {
            titleList.add(commentary.getTitle());
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, titleList);
        mBinding.spinner.setAdapter(adapter);
    }



    private void viewCommentary() {
        CommentaryModel commentary = mCommentaryList.get(mBinding.spinner.getSelectedItemPosition());
        mCommentaryId = commentary.getId();
        mBinding.txtTitolVista.setText(commentary.getTitle());
        mBinding.txtCosVista.setText(commentary.getBody());
    }

    private void addCommentary() {
        CommentaryModel commentary = new CommentaryModel();
        commentary.setTitle(mBinding.txtTitol.getText().toString());
        commentary.setBody(mBinding.txtCos.getText().toString());
        mDBAssistant.addCommentary(commentary);
        mBinding.txtTitol.setText("");
        mBinding.txtCos.setText("");
        initializeList();
    }

    private void deleteCommentary() {
        if(mCommentaryId != null) {
            mDBAssistant.deleteCommentary(mCommentaryId);
            mCommentaryId = null;
            mBinding.txtTitolVista.setText("");
            mBinding.txtCosVista.setText("");
            initializeList();
        }
    }
}