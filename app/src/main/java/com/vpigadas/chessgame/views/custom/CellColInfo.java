package com.vpigadas.chessgame.views.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.vpigadas.chessgame.R;

public class CellColInfo extends LinearLayout {
    public CellColInfo(Context context) {
        super(context);
        init();
    }

    public CellColInfo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CellColInfo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_col_info, this, true);
    }
}
