package com.weavedin.music.app;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SliderIndicatorView extends LinearLayout{


    public SliderIndicatorView(Context context) {
        super(context);
    }

    public SliderIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SliderIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void addIndicator()
    {
        ImageView sliderIndicator = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.slider_indicator, null);
        this.addView(sliderIndicator);
    }

    public void setSelected(int position) {
        int slidesCount = this.getChildCount();
        for (int i=0; i<slidesCount; i++) {
            ImageView sliderIndicator = (ImageView) this.getChildAt(i);
            if (i == position) {
                sliderIndicator.setImageResource(R.mipmap.slider_indicator_selected);
            } else {
                sliderIndicator.setImageResource(R.mipmap.slider_indicator_unselected);
            }
        }
    }
}
