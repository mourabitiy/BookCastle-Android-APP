package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.bookcastle.adapters.OnBoardingAdapter;
import com.android.bookcastle.utils.OnBoardingItems;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class SliderActivity extends AppCompatActivity {
    OnBoardingAdapter onBoardingAdapter;
    LinearLayout layoutOnBoardingIndicators;
    MaterialButton onboarding_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        layoutOnBoardingIndicators = findViewById(R.id.layoutOnboardingIndicator);
        onboarding_btn = findViewById(R.id.onboarding_btn);

        setupOnBoardingAdapter();
        ViewPager2 onBoardingViewPager = findViewById(R.id.onboarding_vp);
        onBoardingViewPager.setAdapter(onBoardingAdapter);

        setupOnBoardingIndicators();
        setCurrentOnBoardingIndicator(0);
        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnBoardingIndicator(position);
            }
        });
        onboarding_btn.setOnClickListener(v ->{
            if(onBoardingViewPager.getCurrentItem() + 1 < onBoardingAdapter.getItemCount()){
                onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() + 1);
            }else{
                startActivity(new Intent(getApplicationContext(), GetstartedActivity.class));
                finish();
            }
        });

    }

    private void setupOnBoardingAdapter() {
        List<OnBoardingItems> onBoardingItems = new ArrayList<>();
        OnBoardingItems item1 = new OnBoardingItems(R.drawable.f1, "Explore, learn and grow",
                "Read across categories and save your favorties to your library");
        OnBoardingItems item2 = new OnBoardingItems(R.drawable.f2, "Stay Updated",
        "get latest updates on books, Authors and more");
        OnBoardingItems item3 = new OnBoardingItems(R.drawable.f3, "Make a good choice",
        "Set a reading goal and track your progress");
        onBoardingItems.add(item1);
        onBoardingItems.add(item2);
        onBoardingItems.add(item3);
        onBoardingAdapter = new OnBoardingAdapter(onBoardingItems);
    }
    private void setupOnBoardingIndicators() {
        ImageView[] indicators = new ImageView[onBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnBoardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnBoardingIndicator(int i){
        int childCount = layoutOnBoardingIndicators.getChildCount();
        for (int j = 0; j < childCount; j++) {
            ImageView imageView = (ImageView) layoutOnBoardingIndicators.getChildAt(j);
            if (j == i) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if(i == onBoardingAdapter.getItemCount() - 1){
            onboarding_btn.setText("Start");
    }else{
            onboarding_btn.setText("Next");
        }
    }
}