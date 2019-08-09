package com.kode.formscreatorlib.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kode.formscreatorlib.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

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



    public void cacheStringData(final String data, final String FILENAME) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream fos = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(data.getBytes());
                    fos.close();
                    LOG("Writing >>" + data);
                } catch (IOException e) {
                    LOG("Error Caching Data >> " + e.getMessage());
                }
            }
        });

    }


    public String getAnswer(String FILENAME) {
        StringBuffer fileContent = null;
        try {
            FileInputStream fis = mContext.openFileInput(FILENAME);
            if (fis != null) {
                fileContent = new StringBuffer("");

                byte[] buffer = new byte[1024];
                int n;
                while ((n = fis.read(buffer)) != -1) {
                    fileContent.append(new String(buffer, 0, n));
                    LOG("Reading" + fileContent);
                }
            }

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileContent == null)
            return null;

        return String.valueOf(fileContent);
    }



    public static void clearCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }


    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    public static <T> String convertClassToJson(T transaction) {
        try {
            Gson gson = new Gson();
            //  .serializeNulls().create();
            return gson.toJson(transaction);
        } catch (Exception e) {
            return "";
        }
    }


    public static <T> T fromJson(String obj, Class<T> transaction) {
        try {
            Gson gson = new Gson();
            //  .serializeNulls().create();
            return gson.fromJson(obj, (Type) transaction);
        } catch (Exception e) {
            return null;
        }
    }


    public static <T> String convertModel(T object) {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            return gson.toJson(object);
        } catch (Exception e) {
            return "";
        }
    }


    public static <T> String convertArrayObject(ArrayList<T> object) {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            return gson.toJson(object);
        } catch (Exception e) {
            return "";
        }
    }

}
