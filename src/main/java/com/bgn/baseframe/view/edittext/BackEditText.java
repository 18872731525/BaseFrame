package com.bgn.baseframe.view.edittext;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.bgn.baseframe.utils.ToastUtil;

/**
 * Created by Administrator on 2018/3/31.
 */

public class BackEditText extends AppCompatEditText {
    public BackEditText(Context context) {
        super(context);
    }

    public BackEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface BackListener {
        void back(TextView textView);
    }


    private BackListener listener;

    public void setBackListener(BackListener listener) {
        this.listener = listener;
    }

//    @Override
//
//    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (listener != null) {
//                listener.back(this);
//            }
//        }
//        return false;
//    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ENTER) {
            this.clearFocus();
            if (listener != null) {
                listener.back(this);
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
