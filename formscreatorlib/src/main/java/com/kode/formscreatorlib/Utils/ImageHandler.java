/*
package com.kode.formscreatorlib.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.kode.formscreatorlib.Callbacks.OnImageSaved;
import com.kode.formscreatorlib.Callbacks.OnImageSelectedSaved;

import java.io.ByteArrayOutputStream;

*/
/**
 * Created by jamper on 12/18/2017.
 *//*


public class ImageHandler implements OnImageSaved {
    private Activity mContext;
    public int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageView imageView;
    private String imageName;
    private OnImageSelectedSaved onImageSelectedSaved;
    private Bitmap bitmap;

    private ArrayList<Image> images;

    public ImageHandler(Activity context) {
        this.mContext = context;
        this.imageView = null;
        this.imageName = "";
        this.bitmap = null;
    }

    private void openLib(int pos) {
        switch (pos) {
            case 0:
                ImagePicker.create(mContext)
                        .returnAfterFirst(true) // set whether pick or camera action should return immediate result or not. For pick image only work on single mode
                        .single()  // single mode
                        .start(2000);
                break;
            case 1:
                ImagePicker.create(mContext)
                        .returnAfterFirst(true) // set whether pick or camera action should return immediate result or not. For pick image only work on single mode
                        .single()  // single mode
                        .showCamera(false)
                        .start(2000);
                break;
        }

    }

    public ImageHandler setRequest(int requestCode) {
       */
/* switch (requestCode) {
            case 0:
                cameraIntent();
                break;
            case 1:
                galleryIntent();
                break;
            default:
                cameraIntent();
                break;
        }
*//*

        openLib(requestCode);
        return this;


    }

    public ImageHandler setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public ImageHandler setImageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    */
/**
     * Open the gallery to take a picture
     **//*

    private void galleryIntent() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            mContext.startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        } catch (Exception e) {
            Log.d("Error image", "" + e.getMessage());
        }
    }


    */
/**
     * Open the camera to take a picture
     **//*

    private void cameraIntent() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mContext.startActivityForResult(intent, REQUEST_CAMERA);

        } catch (Exception e) {
            Log.d("Error image", "" + e.getMessage());
        }
    }


    public ImageHandler onActivityResults(int requestCode, int resultCode, Intent data) {
        try {
            handleImage(requestCode, data);
        } catch (Exception e) {
            LOG("Error with picture >> " + e.getMessage());
        }
        return this;
    }


    */
/**
     * Register onActivityResults here to capture image
     **//*

    public ImageHandler onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if ((resultCode == Activity.RESULT_OK)) {
                images = new ArrayList<Image>();
                images.clear();
                images = (ArrayList<Image>) ImagePicker.getImages(data);
                printImages(images);
            }
        } catch (Exception e) {
        }
        return this;
    }


    */
/**
     * print images in a gridView for user to select an image
     **//*

    private void printImages(List<Image> images) {
        if (images == null) {
            return;
        }

        try {
            String imagePath = images.get(0).getPath();
            LOG("Path of Image: " + imagePath);
            bitmap = getImageFromPath(imagePath);

        } catch (Exception e) {
            Log.d("Image: ", "Error: " + e.getMessage());
        }

        */
/**Save the selected image and update what is in the database**//*

        //Save image into the database
        Fso.getImageData().put(this.imageName, getBase64Bytes(bitmap));

        if (imageView != null) {
            imageView.setImageBitmap(StringToBitMap(getBase64Bytes(bitmap)));
        } else Toast.makeText(mContext, " View null", Toast.LENGTH_SHORT).show();

        LOG("Image: " + getBase64Bytes(bitmap));

        if (onImageSelectedSaved != null)
            onImageSelectedSaved.getData(getBase64Bytes(bitmap));

    }


    private void handleImage(int requestCode, Intent data) {
        try {
            bitmap = null;
            if (requestCode == REQUEST_CAMERA)
                try {
                    bitmap = (Bitmap) data.getExtras().get("data");
                } catch (Exception e) {
                    LOG("Error with Camera >> " + e.getMessage());
                }

            if (requestCode == SELECT_FILE)
                try {
                    final Uri photoUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), photoUri);
                } catch (Exception e) {
                    LOG("Error with Gallery >> " + e.getMessage());
                }

            if (bitmap == null)
                return;

            //Save image into the database
            Fso.getImageData().put(this.imageName, getBase64Bytes(bitmap));

            if (imageView != null) {
                imageView.setImageBitmap(StringToBitMap(getBase64Bytes(bitmap)));
            } else Toast.makeText(mContext, " View null", Toast.LENGTH_SHORT).show();

            LOG("Image: " + getBase64Bytes(bitmap));

            if (onImageSelectedSaved != null)
                onImageSelectedSaved.getData(getBase64Bytes(bitmap));
        } catch (Exception E) {
            LOG("Error handling Image: >> " + E.getMessage());
        }
    }


    private String getBase64Bytes(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap = Bitmap.createScaledBitmap(bitmap, 350, 350, true);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, bao);
            byte[] ba = bao.toByteArray();
            return Base64.encodeBytes(ba);
        }
        return null;
    }


    @Override
    public void onImageSaved(OnImageSelectedSaved onImageSelectedSaved) {
        this.onImageSelectedSaved = onImageSelectedSaved;
    }
}
*/
