package coc.strategy;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.transitionseverywhere.TransitionManager;

import coc.strategy.R;

public class MainPage extends Activity {
	
	private int screenWidth = 0;
	private int screenHeight = 0;

    LinearLayout mainLayout;
    LinearLayout layoutButtons;

    Button boton1;
    Button boton2;
    Button boton3;
    Button boton4;
    Button boton5;

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
        //layoutButtons = (LinearLayout) findViewById(R.id.LinearLayout_buttons);
        MenuC myView = new MenuC(this);
        mainLayout.addView(myView);


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



    }

    public void manageButtons(View v)
    {
        //System.out.println("View clicked " + v.getTag().toString());
        switch (Integer.parseInt(v.getTag().toString()))
        {
            case 1:
                boton1.setVisibility(View.GONE);

                TransitionManager.beginDelayedTransition(mainLayout);
                int visible = boton1.getVisibility();

                boton1.setVisibility(false ? View.VISIBLE : View.GONE);

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
    
}