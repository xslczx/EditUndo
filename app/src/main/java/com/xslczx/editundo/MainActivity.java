package com.xslczx.editundo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    protected EditText mEditText;
    private String mOldText;
    private ImageView btnUndo;
    private ImageView btnRedo;
    private UndoRedoList mUndoRedoList;
    private boolean isUndoRedo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edit_text);
        btnUndo = findViewById(R.id.btn_undo);
        btnRedo = findViewById(R.id.btn_redo);
        mUndoRedoList = new UndoRedoList();
        btnRedo.setOnClickListener(view -> {
            if (mUndoRedoList.canRedo()) {
                Action action = mUndoRedoList.redo();
                if (action != null && TextUtils.equals(action.key
                        , String.valueOf(mEditText.getId()))) {
                    isUndoRedo = true;
                    mEditText.setText(String.valueOf(action.value));
                    mEditText.setSelection(String.valueOf(action.value).length());
                }
            }
            mOldText = mEditText.getText().toString();
        });
        btnUndo.setOnClickListener(view -> {
            if (mUndoRedoList.canUndo()) {
                Action action = mUndoRedoList.undo();
                if (action != null && TextUtils.equals(action.key
                        , String.valueOf(mEditText.getId()))) {
                    isUndoRedo = true;
                    mEditText.setText(String.valueOf(action.value));
                    mEditText.setSelection(String.valueOf(action.value).length());
                }
            }
            mOldText = mEditText.getText().toString();
        });
        mEditText.addTextChangedListener(this);
        boolean canRedo = mUndoRedoList.canRedo();
        boolean canUndo = mUndoRedoList.canUndo();
        btnRedo.setImageResource(canRedo ? R.drawable.ic_toolbar_redo_dark : R.drawable.ic_toolbar_redo_light);
        btnUndo.setImageResource(canUndo ? R.drawable.ic_toolbar_undo_dark : R.drawable.ic_toolbar_undo_light);

        String string = "编辑文本，测试撤销（undo）、反撤销（redo）";
        mEditText.setText(string);
        mEditText.setSelection(string.length());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mOldText != null && !isUndoRedo) {
            mUndoRedoList.add(String.valueOf(mEditText.getId()), mOldText, s.toString());
        }
        isUndoRedo = false;
        mOldText = s.toString();
        boolean canRedo = mUndoRedoList.canRedo();
        boolean canUndo = mUndoRedoList.canUndo();
        btnRedo.setImageResource(canRedo ? R.drawable.ic_toolbar_redo_dark : R.drawable.ic_toolbar_redo_light);
        btnUndo.setImageResource(canUndo ? R.drawable.ic_toolbar_undo_dark : R.drawable.ic_toolbar_undo_light);
    }
}