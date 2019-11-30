package com.example.animationslab;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tvAnimatedValue = findViewById(R.id.tvAnimatedValue);

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 100f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float newValue = (float) animation.getAnimatedValue();

                tvAnimatedValue.setTranslationY(newValue);
//                tvAnimatedValue.setText(String.valueOf(newValue));
//                tvAnimatedValue.setScaleX(newValue);
//                tvAnimatedValue.setScaleY(newValue);
            }
        });

//        valueAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });

//        valueAnimator.setRepeatCount(2);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        valueAnimator.setDuration(2000);

        ValueAnimator colorAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            colorAnimator = ValueAnimator.ofArgb(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
            colorAnimator.setDuration(2000);
            colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    tvAnimatedValue.setTextColor((Integer) animation.getAnimatedValue());
                }
            });
        }

        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tvAnimatedValue, "translationX", -100f);
        objectAnimator.setDuration(2000);


        final ValueAnimator finalColorAnimator = colorAnimator;
        findViewById(R.id.btnAnimation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // animiranje so animate() komandata koja postoi za sekoj View objekt.
                tvAnimatedValue.animate().setDuration(2000).translationY(100f);

                valueAnimator.start();
//                if (finalColorAnimator != null) {
////                    finalColorAnimator.start();
//                    animator set, kombinira dve ili povekje animacii
//                    AnimatorSet set = new AnimatorSet();
//                    set.play(valueAnimator).before(finalColorAnimator);
//                    set.start();
//                }
//                objectAnimator.start();
            }
        });

//        Replacnigo FirstFragment za testiranje na card animacijata.
//        za da raboti, odkomentiraj go FrameLayoutot vo activity_main, i zakomentiraj se ostanato
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frameLayout, new FirstFragment())
//                .commit();
    }
}
