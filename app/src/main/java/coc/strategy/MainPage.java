package coc.strategy;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;

public class MainPage extends Activity {

    private int screenWidth = 0;
    private int screenHeight = 0;

    LinearLayout mainLayout;
    LinearLayout layoutButtons;
    ImageView imageSwitcher = null;

    Button boton1;
    Button boton2;
    Button boton3;
    Button boton4;
    Button boton5;

    DrawableScene drawableScene;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //get screen dimension
        Display display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();

        //display layout
        setContentView(R.layout.mainpage);

        //add the menu layout
        mainLayout = (LinearLayout) findViewById(R.id.LinearLayout_main);

        drawableScene = new DrawableScene(this);

        mainLayout.addView(drawableScene);
        imageSwitcher = (ImageView) findViewById(R.id.ImageSwitcherBackground);
        /*FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        if(frameLayout!=null)
            mainLayout.addView(frameLayout);
        */

        boton1 = (Button) findViewById(R.id.button1);
        boton2 = (Button) findViewById(R.id.button2);
        boton3 = (Button) findViewById(R.id.button3);
        boton4 = (Button) findViewById(R.id.button4);
        boton5 = (Button) findViewById(R.id.button5);

        AssetManager am = this.getAssets();
        Typeface plain  = Typeface.createFromAsset(am, "fonts/Supercell.ttf");
        Typeface bold   = Typeface.create(plain, Typeface.BOLD);

        boton1.setTypeface(plain);
        boton2.setTypeface(plain);
        boton3.setTypeface(plain);
        boton4.setTypeface(plain);
        /*boton1.setOnClickListener(new VisibleToggleClickListener() {
            @Override
            protected void changeVisibility(boolean visible) {
                TransitionManager.beginDelayedTransition(mainLayout, new Slide(Gravity.RIGHT));
                button1.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }); );*/


    }
    public void bringDrawingToFront()
    {
        drawableScene.bringToFront();
        //drawableScene.invalidate();
    }
    public void manageButtons(View v)
    {
        //System.out.println("View clicked " + v.getTag().toString());
        switch (Integer.parseInt(v.getTag().toString()))
        {
            case 1:
                TransitionManager.beginDelayedTransition(mainLayout,new Slide(Gravity.RIGHT));

                boton1.setVisibility(View.GONE);

                boton2.setVisibility(View.GONE);
                boton3.setVisibility(View.GONE);
                boton4.setVisibility(View.GONE);
                boton5.setVisibility(View.VISIBLE);
                break;

            case 2:
                boton1.setVisibility(View.GONE);
                boton2.setVisibility(View.GONE);
                boton3.setVisibility(View.GONE);
                boton4.setVisibility(View.GONE);
                boton5.setVisibility(View.VISIBLE);
                break;
            case 3:
                boton1.setVisibility(View.GONE);
                boton2.setVisibility(View.GONE);
                boton3.setVisibility(View.GONE);
                boton4.setVisibility(View.GONE);
                boton5.setVisibility(View.VISIBLE);
                break;
            case 4:
                takePictureFromGalleryOrAnyOtherFolder();
                bringDrawingToFront();
                boton1.setVisibility(View.GONE);
                boton2.setVisibility(View.GONE);
                boton3.setVisibility(View.GONE);
                boton4.setVisibility(View.GONE);
                boton5.setVisibility(View.VISIBLE);
                break;
            case 5:
                boton1.setVisibility(View.VISIBLE);
                boton2.setVisibility(View.VISIBLE);
                boton3.setVisibility(View.VISIBLE);
                boton4.setVisibility(View.VISIBLE);
                boton5.setVisibility(View.GONE);
                break;
        }
    }

    public int get_screenWidth()
    {
        return screenWidth;
    }

    public int get_screenHeight()
    {
        return screenHeight;
    }
    public static final int PICK_IMAGE = 1;
    private void takePictureFromGalleryOrAnyOtherFolder()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String realuri = null;
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PICK_IMAGE )
            {
                Uri selectedImageUri = data.getData();

                System.out.println("SDK_int : " + Build.VERSION.SDK_INT);
                System.out.println("selectedImageUri: " + selectedImageUri);
                System.out.println("realuri: " + realuri);

                //selectedImageUri = Uri.parse(realuri.toString());
                if(realuri==null)
                {
                    try {
                        Uri originalUri = data.getData();
                        String pathsegment[] = originalUri.getLastPathSegment().split(":");
                        String id = pathsegment[0];
                        final String[] imageColumns = { MediaStore.Images.Media.DATA };
                        final String imageOrderBy = null;

                        Uri uri = selectedImageUri;
                        Cursor imageCursor = getContentResolver().query(originalUri, imageColumns,
                                MediaStore.Images.Media._ID + "=" + id, null, null);

                        if (imageCursor.moveToFirst()) {
                            realuri = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        }

                        try
                        {
                            if(realuri==null)
                            {
                                realuri = RealPathUtil.getRealPathFromURI_API19(this, selectedImageUri);
                            }
                            //final InputStream imageStream   = getContentResolver().openInputStream(Uri.parse(realuri));
                            //final Bitmap      selectedImage = BitmapFactory.decodeStream(imageStream);
                            //bg = Drawable.createFromStream(imageStream, realuri.toString());
                            //bg = Drawable.createFromStream(imageStream, selectedImageUri.toString());
                            //bg = Drawable.createFromPath(selectedImageUri.toString());
                        }
                        catch (Exception e)
                        {
                            //bg = ContextCompat.getDrawable(this, R.drawable.bg);
                            System.out.println("FileNotFoundexception: " + e.getMessage());
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(this, "Failed to get image", Toast.LENGTH_LONG).show();
                    }
                }
                final Drawable bg = Drawable.createFromPath(realuri);;
                if (bg != null)
                {
                    Toast.makeText(this, "Found img uri: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println("img uri: " + selectedImageUri.toString());

                    if(imageSwitcher!=null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageSwitcher.setImageDrawable(bg);
                            }
                        });

                    }
                    else
                    {
                        System.out.println("imageSwitcher R.id.ImageSwitcherBackground is null");
                    }
                }
                else
                {
                    System.out.println("NOT Found img uri: " + selectedImageUri.toString());
                }
                //linearLayout.refreshDrawableState();
                //linearLayout.invalidateDrawable(d);
            }
        }
    }
}