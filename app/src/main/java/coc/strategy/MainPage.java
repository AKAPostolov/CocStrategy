package coc.strategy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import coc.strategy.R;

public class MainPage extends Activity {
	
	private int screenWidth = 0;
	private int screenHeight = 0; 
	
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
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.LinearLayout_main);
        MenuC myView = new MenuC(this);
        mainLayout.addView(myView);	
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