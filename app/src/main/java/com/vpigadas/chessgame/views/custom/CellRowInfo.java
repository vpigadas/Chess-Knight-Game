package com.vpigadas.chessgame.views.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.vpigadas.chessgame.R;

public class CellRowInfo extends LinearLayout {
    public CellRowInfo(Context context) {
        super(context);
        init();
    }

    public CellRowInfo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CellRowInfo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_row_info, this, true);
    }
}
