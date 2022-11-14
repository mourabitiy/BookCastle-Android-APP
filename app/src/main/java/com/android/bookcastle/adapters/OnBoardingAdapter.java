package com.android.bookcastle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bookcastle.R;
import com.android.bookcastle.utils.OnBoardingItems;

import java.util.List;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder> {
    private List<OnBoardingItems> onBoardingItems;

    public OnBoardingAdapter(List<OnBoardingItems> onBoardingItems) {
        this.onBoardingItems = onBoardingItems;
    }

    @NonNull
    @Override
    public OnBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnBoardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.onboarding_items,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoardingViewHolder holder, int position) {
        holder.setOnBoardingData(onBoardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onBoardingItems.size();
    }

    class OnBoardingViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageOnBoarding;

        public OnBoardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.onboarding_title);
            textDescription = itemView.findViewById(R.id.onboarding_desc);
            imageOnBoarding = itemView.findViewById(R.id.onboarding_iv);
        }

        void setOnBoardingData(OnBoardingItems onBoardingItems) {
            textTitle.setText(onBoardingItems.getTitle());
            textDescription.setText(onBoardingItems.getDescription());
            imageOnBoarding.setImageResource(onBoardingItems.getImage());
        }
    }
}
