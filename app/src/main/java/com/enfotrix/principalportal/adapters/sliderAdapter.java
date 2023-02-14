package com.enfotrix.principalportal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.models.SliderItem;

import java.util.List;

public class sliderAdapter extends RecyclerView.Adapter<sliderAdapter.SliderViewHolder> {

    private List<SliderItem> sliderItemList;
    private ViewPager2 viewPager2;

    public sliderAdapter(List<SliderItem> sliderItemList, ViewPager2 viewPager2) {
        this.sliderItemList = sliderItemList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.slide_image_container, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItemList.get(position));

        if (position == sliderItemList.size() - 2 ){
            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return sliderItemList.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;


        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_slide);

        }

        void setImage(SliderItem sliderItem) {
            imageView.setImageResource(sliderItem.getImage());
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItemList.addAll(sliderItemList);
            notifyDataSetChanged();
        }
    };
}
