package com.example.redfruit.gamersgrub;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PhotoHolder> {

    private ArrayList<Photo> mPhotos;


    public static class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mItemImage;
        private TextView mItemTitle;
        private TextView mItemDescription;
        private Photo mPhoto;


        private static final String PHOTO_KEY = "PHOTO";


        public PhotoHolder(View v) {
            super(v);

            mItemImage = (ImageView) v.findViewById(R.id.item_image);
            mItemTitle = (TextView) v.findViewById(R.id.item_date);
            mItemDescription = (TextView) v.findViewById(R.id.item_description);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent showPhotoIntent = new Intent(context, Event.class);
            showPhotoIntent.putExtra("PHOTO", mPhoto);
            context.startActivity(showPhotoIntent);
        }

        public void bindPhoto(Photo photo) {
            mPhoto = photo;
            Picasso.with(mItemImage.getContext()).load(photo.getImage()).into(mItemImage);
            mItemTitle.setText(photo.getTitle());
            mItemDescription.setText(photo.getDesc());
        }
    }



    public RecyclerAdapter(ArrayList<Photo> photos) {
        mPhotos = photos;
    }

    @Override
    public RecyclerAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row, parent, false);
        return new PhotoHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.PhotoHolder holder, int position) {

        Photo itemPhoto = mPhotos.get(position);
        holder.bindPhoto(itemPhoto);

    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }
}
