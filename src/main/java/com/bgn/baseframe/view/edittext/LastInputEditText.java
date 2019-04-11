package com.bgn.baseframe.view.edittext;

import android.content.Context;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

public class LastInputEditText extends EditText {

    public LastInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        blockContextMenu();
    }

    public LastInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        blockContextMenu();
    }

    public LastInputEditText(Context context) {
        super(context);
        blockContextMenu();
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //保证光标始终在最后面
        if (selStart == selEnd) {//防止不能多选
            setSelection(getText().length());
        }
    }


    private void blockContextMenu() {
        this.setCustomSelectionActionModeCallback(new android.view.ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {

            }
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LastInputEditText.this.clearFocus();
                return false;
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // setInsertionDisabled when user touches the view
            try {
                Field editorField = TextView.class.getDeclaredField("mEditor");
                editorField.setAccessible(true);
                Object editorObject = editorField.get(this);

                Class editorClass = Class.forName("android.widget.Editor");
                Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
                mInsertionControllerEnabledField.setAccessible(true);
                mInsertionControllerEnabledField.set(editorObject, false);
            } catch (Exception ignored) {
                // ignore exception here
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }


}