package com.kode.formscreatorlib.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.widget.ImageView;

import com.kode.formscreatorlib.BuildConfig;

public class FormsUtils {
    private Activity mContext;


    public FormsUtils(Activity mContext) {
        this.mContext = mContext;
    }


    public static void LOG(String mess){
        if (BuildConfig.DEBUG){
            Log.d("HTTP" , mess);
        }
    }


    /**
     * Function to set the image drawable from Resource to an imageView
     *
     * @param resourceId the id of the resource from drawables or mipMap
     * @param imageView  the ImageView or CircleImageView to which the drawable will be set to
     **/
    public void setImageResource(int resourceId, ImageView imageView) {
        try {
            imageView.setImageDrawable(mContext.getResources().getDrawable(resourceId));
        } catch (OutOfMemoryError er) {
            LOG("Error Parsing image >> " + er.getMessage());
            try {
                imageView.setImageBitmap(CompressImage.decodeSampledBitmapFromResource(
                        mContext.getResources(), resourceId, 350, 380));
            } catch (OutOfMemoryError eb) {
                try {
                    imageView.setImageResource(resourceId);
                } catch (OutOfMemoryError ef) {
                    LOG("Error Parsing image Alternative One >> " + ef.getMessage());
                }

            }
        } catch (Exception e) {
            LOG("Error Parsing image Alternative 2 >> " + e.getMessage());
            try {
                imageView.setImageBitmap(getBitmapFromVectorDrawable(resourceId));
            } catch (Exception r) {
                LOG("Error Parsing image Alternative 3 >> " + r.getMessage());
                try {
                    imageView.setImageResource(resourceId);
                } catch (Exception b) {
                    LOG("Error Parsing image Alternative 4 >> " + b.getMessage());
                }
            }
        }

    }




    public Bitmap getBitmapFromVectorDrawable(int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }




}
