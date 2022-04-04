package com.example.dogapp.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.databinding.ObservableField;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ImageModel {
    // The URL will usually come from a model (i.e Profile)
    static final String IMAGE_URL = "http://cdn.meme.am/instances/60677654.jpg";
    public ObservableField<Drawable> profileImage;
    private BindableFieldTarget bindableFieldTarget;

    public ImageModel(Context context) {
        profileImage = new ObservableField<>();
        // Picasso keeps a weak reference to the target so it needs to be stored in a field
        bindableFieldTarget = new BindableFieldTarget(profileImage, context.getResources());
        Picasso.get()
                .load(IMAGE_URL)

                .into(bindableFieldTarget);
    }

    public class BindableFieldTarget implements Target {

        private ObservableField<Drawable> observableField;
        private Resources resources;

        public BindableFieldTarget(ObservableField<Drawable> observableField, Resources resources) {
            this.observableField = observableField;
            this.resources = resources;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            observableField.set(new BitmapDrawable(resources, bitmap));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            observableField.set(errorDrawable);
        }


        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            observableField.set(placeHolderDrawable);
        }
    }
}