package coc.strategy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
//import android.support.v4.graphics.ColorUtils;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import coc.strategy.FloatingMenu.CocElementDM;
import coc.strategy.FloatingMenu.CocElementsRow;
import coc.strategy.FloatingMenu.CustomAdapter;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainPage extends Activity implements View.OnTouchListener
{
    LinearLayout child;
    private int clickedViewDrawableID = 0;
    private int currentShadowPaintColor = Color.WHITE;
    private int currentWaveNum          = 1;
    private int screenWidth             = 0;
    private int screenHeight            = 0;

    private int[] smallTroops = new int[24];
    private String smallTroopsNames[];

    private int normalTroopsIDs[] = {R.drawable.narquera, R.drawable.nbarbaro, R.drawable.ngiga, R.drawable.nmago, R.drawable.nrompemuros, R.drawable.ndragon, R.drawable.npekka, R.drawable.nminidraco};
    private int darkTroopsIDs[]   = {R.drawable.obruja,R.drawable.oesbirro,R.drawable.ogolem,R.drawable.omontapuercos,R.drawable.operrolava,R.drawable.ovalquiria,R.drawable.ovalquiria};
    private int spellsIDs[]       = {R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell};
    private int heroesIDs[]       = {R.drawable.hrey,R.drawable.hreina,R.drawable.hcentinela};

    private ArrayList<CocElementDM> elements;

    LinearLayout mainLayout;
    LinearLayout layoutButtons;
    ImageView imageSwitcher = null;

    //mainButtons
    TextView tvWaveNum;
    TextView tvUndoLabel;
    TextView tvClearLabel;
    TextView tvSaveLabel;
    Button   btnAddWave;
    Button   boton1;
    Button   boton2;
    Button   boton3;
    Button   boton4;
    Button   botonAdd;

    //NormalTroops
    ImageButton boton1nt;
    ImageButton boton2nt;
    ImageButton boton3nt;
    ImageButton boton4nt;
    ImageButton boton5nt;
    ImageButton boton6nt;
    ImageButton boton7nt;
    ImageButton boton8nt;
    Button botonOKnt;
    //heroes
    ImageButton boton1he;
    ImageButton boton2he;
    ImageButton boton3he;
    Button   btnHeroesOK;
    //spells
    ImageButton boton1sp;
    ImageButton boton2sp;
    ImageButton boton3sp;
    ImageButton boton4sp;
    ImageButton boton5sp;
    ImageButton boton6sp;
    ImageButton boton7sp;
    ImageButton boton8sp;
    Button botonOKsp;
    //DarkTroops
    ImageButton boton1dt;
    ImageButton boton2dt;
    ImageButton boton3dt;
    ImageButton boton4dt;
    ImageButton boton5dt;
    ImageButton boton6dt;
    ImageButton boton7dt;
    Button botondtOK;
    ListView listView;

    DrawableScene drawableScene;
    //TODO
    //Pillar imagen de la galeria
    // takePictureFromGalleryOrAnyOtherFolder();

    //TODO
    //Compartir pantalla
                /*
                try
                {
                    Bitmap screenshotBitmap = Tools.takeScreenShot(this);
                    String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), screenshotBitmap,"title", null);
                    Uri bitmapUri = Uri.parse(bitmapPath);
                    inflateShareIntent(bitmapUri);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;
                */
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("Activity state: onCreate");
        //array get from XML and set
        String strDrawableValues[] = getResources().getStringArray(R.array.stringDrawableSmallTroops);
        for (int i=0; i<strDrawableValues.length;i++)
        {
            smallTroops[i] = this.getResources().getIdentifier(strDrawableValues[i], "drawable", this.getPackageName());
        }
        elements = new ArrayList<>();

        smallTroopsNames = getResources().getStringArray(R.array.array_troops_names);
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

        obtainViewButtons();
        setUpPaletteArrayAdapter();

        child = (LinearLayout) mainLayout.getChildAt(0);
        //((RelativeLayout)mainLayout.getParent()).setOnTouchListener(null);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        System.out.println("Activity state: onResume");
        //drawableScene.invalidate();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        System.out.println("Activity state: onRestart");
    }


    public void obtainViewButtons()
    {
        tvWaveNum   = (TextView) findViewById(R.id.tvWaveNum);
        tvUndoLabel   = (TextView) findViewById(R.id.tvUndoLabel);
        tvClearLabel   = (TextView) findViewById(R.id.tvClearLabel);
        tvSaveLabel   = (TextView) findViewById(R.id.tvSaveLabel);

        btnAddWave   = (Button) findViewById(R.id.btnAddWave);
        boton1   = (Button) findViewById(R.id.button1);
        boton2   = (Button) findViewById(R.id.button2);
        boton3   = (Button) findViewById(R.id.button3);
        boton4   = (Button) findViewById(R.id.button4);
        botonAdd = (Button) findViewById(R.id.button5);

        boton1dt  = (ImageButton) findViewById(R.id.btnDarkTroops1);
        boton2dt  = (ImageButton) findViewById(R.id.btnDarkTroops2);
        boton3dt  = (ImageButton) findViewById(R.id.btnDarkTroops3);
        boton4dt  = (ImageButton) findViewById(R.id.btnDarkTroops4);
        boton5dt  = (ImageButton) findViewById(R.id.btnDarkTroops5);
        boton6dt  = (ImageButton) findViewById(R.id.btnDarkTroops6);
        boton7dt  = (ImageButton) findViewById(R.id.btnDarkTroops7);
        botondtOK = (Button) findViewById(R.id.btnDarkTroopsOK);

        boton1nt  = (ImageButton) findViewById(R.id.btnNormalTroops1);
        boton2nt  = (ImageButton) findViewById(R.id.btnNormalTroops2);
        boton3nt  = (ImageButton) findViewById(R.id.btnNormalTroops3);
        boton4nt  = (ImageButton) findViewById(R.id.btnNormalTroops4);
        boton5nt  = (ImageButton) findViewById(R.id.btnNormalTroops5);
        boton6nt  = (ImageButton) findViewById(R.id.btnNormalTroops6);
        boton7nt  = (ImageButton) findViewById(R.id.btnNormalTroops7);
        boton8nt  = (ImageButton) findViewById(R.id.btnNormalTroops8);
        botonOKnt = (Button) findViewById(R.id.btnOK);

        boton1he     = (ImageButton) findViewById(R.id.btnHeroes1);
        boton2he     = (ImageButton) findViewById(R.id.btnHeroes2);
        boton3he     = (ImageButton) findViewById(R.id.btnHeroes3);
        btnHeroesOK  = (Button) findViewById(R.id.btnHeroesOK);

        boton1sp  = (ImageButton) findViewById(R.id.btnSpells1);
        boton2sp  = (ImageButton) findViewById(R.id.btnSpells2);
        boton3sp  = (ImageButton) findViewById(R.id.btnSpells3);
        boton4sp  = (ImageButton) findViewById(R.id.btnSpells4);
        boton5sp  = (ImageButton) findViewById(R.id.btnSpells5);
        boton6sp  = (ImageButton) findViewById(R.id.btnSpells6);
        boton7sp  = (ImageButton) findViewById(R.id.btnSpells7);
        boton8sp  = (ImageButton) findViewById(R.id.btnSpells8);
        botonOKsp = (Button) findViewById(R.id.btnOKSpells);

        AssetManager am = this.getAssets();
        Typeface plain  = Typeface.createFromAsset(am, "fonts/Supercell.ttf");
        Typeface bold   = Typeface.create(plain, Typeface.BOLD);

        btnAddWave.setTypeface(plain);
        boton1.setTypeface(plain);
        boton2.setTypeface(plain);
        boton3.setTypeface(plain);
        boton4.setTypeface(plain);
    }
    public void setUpPaletteArrayAdapter()
    {
        listView = (ListView) findViewById(R.id.listViewFloating);


        loadItemsInArrayList(elements);
        final ArrayList<CocElementsRow> rows = getRowsFromElements(elements);
        CustomAdapter                 adapter  = new CustomAdapter(this,rows,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {


            }
        });
    }
    public void drawElementByDrawableResourceID()
    {
        drawElementByDrawableResourceID(clickedViewDrawableID);
        /*
        DrawableElement drawableElement = drawableScene.arrayDrawableElements.get(
                drawableScene.arrayDrawableElements.size() - 1);
        drawableScene.arrayDrawableElements.add(drawableElement);
        drawableScene.invalidate();
        */
    }
    public void drawElementByDrawableResourceID(int drawableResourceID)
    {
        clickedViewDrawableID = drawableResourceID;
        //We void multiClick multiDraw with lastClickedDrawn. MultiClick musn't draw many images.
        //Allowing switch selected element to draw the last clicked:
        System.out.println("drawElementByDrawableResourceID  ");

        ///TODO permito multiple dibujo de las tropas:
        drawableScene.addElement(drawableResourceID,drawableResourceID, currentShadowPaintColor);
        drawableScene.lastClickedDrawn = false;
        //Para revertir Descomentar:
        /*
        if(drawableScene.lastClickedDrawn)
        {
            drawableScene.addElement(drawableResourceID,drawableResourceID, currentShadowPaintColor);
            drawableScene.lastClickedDrawn = false;
        }
        else
        {
            System.out.println("Switching last clicked element");
            drawableScene.removeLastElement();
            drawableScene.addElement(drawableResourceID,drawableResourceID, currentShadowPaintColor);
            drawableScene.lastClickedDrawn = false;
        }
        */
    }
    public ArrayList<CocElementsRow> getRowsFromElements(ArrayList<CocElementDM> elements)
    {
        ArrayList<CocElementsRow> arrayCocElementsRows = new ArrayList<>();
        int i = 0;
        for (CocElementDM element: elements)
        {
            i++;
            if(i==1)
            {
                arrayCocElementsRows.add(new CocElementsRow());
                arrayCocElementsRows.get(arrayCocElementsRows.size()-1).element1 = element;
            }
            if(i==2)
            {
                arrayCocElementsRows.get(arrayCocElementsRows.size()-1).element2 = element;
            }
            if(i==3)
            {
                arrayCocElementsRows.get(arrayCocElementsRows.size()-1).element3 = element;
                i=0;
            }
        }
        return arrayCocElementsRows;
    }
    public void loadItemsInArrayList(ArrayList<CocElementDM> elements)
    {
        /*
        //My drawables
        loadNormal(elements);
        loadDark(elements);
        loadHeroes(elements);
        loadSpells(elements);
        */
        //The other drawables:
        loadOtherDrawables(elements);
    }
    public void loadOtherDrawables(ArrayList<CocElementDM> elements)
    {
        addToCocElementsArray(smallTroops,elements);
    }
    public void loadHeroes(ArrayList<CocElementDM> elements)
    {
        addToCocElementsArray(heroesIDs, elements);
    }
    public void loadNormal(ArrayList<CocElementDM> elements)
    {
        addToCocElementsArray(normalTroopsIDs, elements);
    }
    public void loadDark(ArrayList<CocElementDM> elements)
    {
        addToCocElementsArray(darkTroopsIDs, elements);
    }
    public void loadSpells(ArrayList<CocElementDM> elements)
    {
        addToCocElementsArray(spellsIDs, elements);
    }
    public void addToCocElementsArray(int[] input,ArrayList<CocElementDM> elements)
    {
        for (int i=0;i<input.length;i++)
        {
            elements.add(new CocElementDM(input[i]));
        }
    }
    public void manageMainButtons(View v)
    {
        //System.out.println("View clicked " + v.getTag().toString());
        switch (Integer.parseInt(v.getTag().toString()))
        {
            case 1:
                hideMainButtons();
                showNormalTroopsButtons();
                //switchAddButtonVisible();
            break;
            case 2:
                hideMainButtons();
                showDarkTroopsButtons();
                //switchAddButtonVisible();
            break;
            case 3:
                hideMainButtons();
                showHeroesButtons();
                //switchAddButtonVisible();
            break;
            case 4:
                bringDrawingToFront();
                hideMainButtons();
                showSpellsButtons();
                //switchAddButtonVisible();
            break;
            case 5:
                showMainButtons();
                switchAddButtonVisible();
            break;
            case 6: //nuevo método para añadir Oleadas addWaves method
                if(currentWaveNum<=8)
                {
                    currentShadowPaintColor = getRandomizedColor();
                    //if(!drawableScene.waveColorUsed)
                    //{
                    //drawableScene.addDrawableWaveElements(currentWaveNum);
                    currentWaveNum += 1;
                    tvWaveNum.setText(String.valueOf(currentWaveNum));
                    drawableScene.waveColorUsed = true;
                    //}
                }
                else
                {
                    Toast.makeText(this,this.getResources().getString(R.string.str_toast_max_waves_reached),Toast.LENGTH_SHORT).show();
                }
            break;
            case 7:
                drawableScene.removeLastElement();
            break;
            case 8:
                drawableScene.removeAllElements();
            break;
            case 9:
                System.out.println("Saving screenshot !!!");
                String fileName = "tester_1.png";
                Tools.takeScreenShot(this);

                File directory = this.getExternalCacheDir();
                File file = new File( directory, fileName);

                try
                {
                    file.getAbsoluteFile().mkdirs();
                    Log.d("Create File", "File exists? " + file.exists());  // false

                    File         f          = new File(directory.getAbsolutePath() + "/-bw.jpg");
                    OutputStream fileStream = new BufferedOutputStream(new FileOutputStream(directory.getAbsolutePath() + "/-bw.jpg"));
                    Tools.temporaryBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileStream);
                    fileStream.close();

                    //Tools.savePic(Tools.temporaryBitmap, file.getAbsoluteFile().toString());


                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/jpeg");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    Tools.temporaryBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    File file1 = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
                    try {
                        f.createNewFile();
                        FileOutputStream fo = new FileOutputStream(f);
                        fo.write(bytes.toByteArray());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse(f.toURI().toString()));
                    startActivity(Intent.createChooser(share, "Share Image"));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            break;
        }
    }

    public int getRandomizedColor()
    {
        int BLACK       = 0xFF000000;
        int DKGRAY      = 0xFF444444;
        int GRAY        = 0xFF888888;
        int LTGRAY      = 0xFFCCCCCC;
        int WHITE       = 0xFFFFFFFF;
        int RED         = 0xFFFF0000;
        int GREEN       = 0xFF00FF00;
        int BLUE        = 0xFF0000FF;
        int YELLOW      = 0xFFFFFF00;
        int CYAN        = 0xFF00FFFF;
        int MAGENTA     = 0xFFFF00FF;
        Random rand = new Random();
        int  n = rand.nextInt(9) + 0;

        //TODO ñapa devolver solo negro color negro
        //return BLACK;
        //TODO UNDO THIS COMMENT para fix ñapa color negro
        switch (n)
        {
            case 1:
                return MAGENTA;
            case 2:
                return CYAN;
            case 3:
                return BLUE;
            case 4:
                return GRAY;
            case 5:
                return YELLOW;
            case 6:
                return GREEN;
            case 7:
                return WHITE;
            default:
                return BLACK;
        }
    }
    public int getRandomColor()
    {
        Random rand = new Random();
        int  n = rand.nextInt(9) + 0;
        switch (n)
        {
            case 0:
                return R.color.Yellow2;
            case 1:
                return R.color.Green;
            case 2:
                return android.R.color.holo_blue_bright;
            case 3:
                return android.R.color.holo_blue_dark;
            case 4:
                return R.color.Orange1;
            case 5:
                return R.color.White;
            case 6:
                return R.color.Lime;
            case 7:
                return android.R.color.black;
            case 8:
                return R.color.Pink;
            default:
                return android.R.color.holo_red_dark;
        }
    }
    /*
    public static int randomColor(){
        float[] TEMP_HSL = new float[]{0, 0, 0};
        float[] hsl = TEMP_HSL;
        hsl[0] = (float) (Math.random() * 360);
        hsl[1] = (float) (40 + (Math.random() * 60));
        hsl[2] = (float) (40 + (Math.random() * 60));
        return ColorUtils.HSLToColor(hsl);
    }*/
    public void dragMenu(View v)
    {
        child.setOnTouchListener(this);
    }
    public void heroesTroopsClick(View v)
    {
        int tagToInt = Integer.valueOf(v.getTag().toString());
        System.out.println("Touched heroes: " + tagToInt);
        switch (tagToInt)
        {
            case 1:
            case 2:
            case 3:
                drawHeroesTroopClicked(tagToInt-1);
                break;
            default:
                hideHeroesButtons();
                switchAddButtonVisible();
                break;
        }
    }
    public void spellsClick(View v)
    {
        int tagToInt = Integer.valueOf(v.getTag().toString());
        System.out.println("Touched spells: " + tagToInt);
        switch (tagToInt)
        {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                drawSpellsClicked(tagToInt-1);
            default:
                hideSpellsButtons();
                switchAddButtonVisible();
                break;
        }
    }
    public void normalTroopsClick(View v)
    {
        int tagToInt = Integer.valueOf(v.getTag().toString());
        System.out.println("Touched normal: " + tagToInt);
        switch (tagToInt)
        {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                drawNormalTroopClicked(tagToInt-1);
                break;
            default:
                hideNormalTroopButtons();
                break;
        }
    }
    public void darkTroopsClick(View v)
    {
        int tagToInt = Integer.valueOf(v.getTag().toString());
        System.out.println("Touched dark: " + tagToInt);
        switch (tagToInt)
        {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                drawDarkTroopClicked(tagToInt-1);
            default:
                boton1dt.setVisibility(View.GONE);
                boton2dt.setVisibility(View.GONE);
                boton3dt.setVisibility(View.GONE);
                boton4dt.setVisibility(View.GONE);
                boton5dt.setVisibility(View.GONE);
                boton6dt.setVisibility(View.GONE);
                boton7dt.setVisibility(View.GONE);
                botondtOK.setVisibility(View.GONE);
                botonAdd.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void bringDrawingToFront()
    {
        drawableScene.bringToFront();
        //drawableScene.invalidate();
    }
    public void invalidateDrawableScene()
    {
        drawableScene.invalidate();
    }
    public void drawDarkTroopClicked(int clicked)
    {
        addDrawableSceneElement(darkTroopsIDs,clicked);
        invalidateDrawableScene();
    }
    public void drawSpellsClicked(int clicked)
    {
        addDrawableSceneElement(spellsIDs,clicked);
        invalidateDrawableScene();
    }
    public void drawHeroesTroopClicked(int clicked)
    {
        addDrawableSceneElement(heroesIDs,clicked);
        invalidateDrawableScene();
    }
    public void drawNormalTroopClicked(int clicked)
    {
        System.out.println("drawNormalTroopClicked: " + clicked);
        addDrawableSceneElement(normalTroopsIDs,clicked);
        invalidateDrawableScene();
    }
    public void addDrawableSceneElement(int[] drawableResourceID, int clicked)
    {
        System.out.println("addDrawableSceneElement: " + clicked);
        drawableScene.addElement(drawableResourceID[clicked],drawableResourceID[clicked], currentShadowPaintColor);
    }
    public void hideNormalTroopButtons()
    {
        boton1nt.setVisibility(View.GONE);
        boton2nt.setVisibility(View.GONE);
        boton3nt.setVisibility(View.GONE);
        boton4nt.setVisibility(View.GONE);
        boton5nt.setVisibility(View.GONE);
        boton6nt.setVisibility(View.GONE);
        boton7nt.setVisibility(View.GONE);
        boton8nt.setVisibility(View.GONE);
        botonOKnt.setVisibility(View.GONE);
        botonAdd.setVisibility(View.VISIBLE);
    }
    public void showMainButtons()
    {
        boton1.setVisibility(View.VISIBLE);
        boton2.setVisibility(View.VISIBLE);
        boton3.setVisibility(View.VISIBLE);
        boton4.setVisibility(View.VISIBLE);
    }
    public void hideMainButtons()
    {
        boton1.setVisibility(View.GONE);
        boton2.setVisibility(View.GONE);
        boton3.setVisibility(View.GONE);
        boton4.setVisibility(View.GONE);
    }
    public void showDarkTroopsButtons()
    {
        boton1dt.setVisibility(View.VISIBLE);
        boton2dt.setVisibility(View.VISIBLE);
        boton3dt.setVisibility(View.VISIBLE);
        boton4dt.setVisibility(View.VISIBLE);
        boton5dt.setVisibility(View.VISIBLE);
        boton6dt.setVisibility(View.VISIBLE);
        boton7dt.setVisibility(View.VISIBLE);
        botondtOK.setVisibility(View.VISIBLE);
    }
    public void showNormalTroopsButtons()
    {
        boton1nt.setVisibility(View.VISIBLE);
        boton2nt.setVisibility(View.VISIBLE);
        boton3nt.setVisibility(View.VISIBLE);
        boton4nt.setVisibility(View.VISIBLE);
        boton5nt.setVisibility(View.VISIBLE);
        boton6nt.setVisibility(View.VISIBLE);
        boton7nt.setVisibility(View.VISIBLE);
        boton8nt.setVisibility(View.VISIBLE);
        botonOKnt.setVisibility(View.VISIBLE);
    }
    public void showSpellsButtons()
    {
        boton1sp .setVisibility(View.VISIBLE);
        boton2sp .setVisibility(View.VISIBLE);
        boton3sp .setVisibility(View.VISIBLE);
        boton4sp .setVisibility(View.VISIBLE);
        boton5sp .setVisibility(View.VISIBLE);
        boton6sp .setVisibility(View.VISIBLE);
        boton7sp .setVisibility(View.VISIBLE);
        boton8sp .setVisibility(View.VISIBLE);
        botonOKsp.setVisibility(View.VISIBLE);
    }
    public void hideSpellsButtons()
    {
        boton1sp .setVisibility(View.GONE);
        boton2sp .setVisibility(View.GONE);
        boton3sp .setVisibility(View.GONE);
        boton4sp .setVisibility(View.GONE);
        boton5sp .setVisibility(View.GONE);
        boton6sp .setVisibility(View.GONE);
        boton7sp .setVisibility(View.GONE);
        boton8sp .setVisibility(View.GONE);
        botonOKsp.setVisibility(View.GONE);
    }
    public void showHeroesButtons()
    {
        boton1he    .setVisibility(View.VISIBLE);
        boton2he    .setVisibility(View.VISIBLE);
        boton3he    .setVisibility(View.VISIBLE);
        btnHeroesOK .setVisibility(View.VISIBLE);
    }
    public void hideHeroesButtons()
    {
        boton1he    .setVisibility(View.GONE);
        boton2he    .setVisibility(View.GONE);
        boton3he    .setVisibility(View.GONE);
        btnHeroesOK .setVisibility(View.GONE);
    }
    public void switchAddButtonVisible()
    {
        if(botonAdd.getVisibility()==View.GONE)
        {botonAdd.setVisibility(View.VISIBLE);}
        else
        {botonAdd.setVisibility(View.GONE);}
    }

    public void inflateShareIntent(Uri bitmapUri)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        //intent.putExtra(Intent.EXTRA_STREAM, bitmap);
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri );
        startActivity(Intent.createChooser(intent , "Share"));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
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
                                realuri = Tools.getPathFromUri(this, selectedImageUri);
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
                final Drawable bg = Drawable.createFromPath(realuri);
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

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int _xDelta =0;
        int _yDelta =0;
        String tag = "";
        //v = (View)v.getParent();
        if(v.getTag()!=null)
        {
            tag = v.getTag().toString();
            System.out.println("tag: " + tag);
        }
        if(true)//(tag.contains("LinearLayout_main"))
        {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) v.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    child.setOnTouchListener(null);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    int ancho = v.getWidth();
                    int alto  = v.getHeight();
                    System.out.println("X: " + X + " Y: " + Y);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
                    /*
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    */

                    int limiteEjeX = ancho + (int)getResources().getDimension(R.dimen.rightMargin);
                    if(X>=screenWidth-limiteEjeX)
                    {
                        layoutParams.setMargins(screenWidth-limiteEjeX, 0, (int)getResources().getDimension(R.dimen.rightMargin),(int)getResources().getDimension(R.dimen.bottomMargin));
                    }
                    else
                    {
                        layoutParams.setMargins(X, 0, (int)getResources().getDimension(R.dimen.rightMargin),(int)getResources().getDimension(R.dimen.bottomMargin));
                    }

                    v.setLayoutParams(layoutParams);
                    break;
            }
            ((RelativeLayout)((LinearLayout)v.getParent()).getParent()).invalidate();
            ((RelativeLayout)((LinearLayout)v.getParent()).getParent()).requestLayout();
            ((RelativeLayout)((LinearLayout)v.getParent()).getParent()).refreshDrawableState();
            //ViewGroup vg = findViewById(R.id.mainPaletteLayout);
            //getWindow().getDecorView().findViewById(android.R.id.content).invalidate();

            return true;
        }
        return false;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  //For older versions than ECLAIR
    {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0)
        {
            // Take care of calling this method on earlier versions of
            // the platform where it doesn't exist.
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() //For modern versions
    {
        // This will be called either automatically for you on 2.0
        // or later, or by the code above on earlier versions of the
        // platform.
        buildExitDialog();
        return;
    }

    //MANEJAMOS ESTE EVENTO POR SI ALGUIEN PULSA LA TECLA DE MENU.
    // No quiero que haga nada en realidad, no hay un menu preparado o pensado para esta app
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                //me corrijo: QUITO ESTO DE LLAMAR AL MÉTODO PARA QUE NO HAGA NADA.
                //onBackPressed();
                break;
        }
        return true;
    }

    public void buildExitDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title

        alertDialogBuilder.setTitle(R.string.alert_dialog_title);
        alertDialogBuilder.setIcon(R.drawable.bar);

        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.alert_dialog_question)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_dialog_yes,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        MainPage.this.finish();
                    }
                })
                .setNegativeButton(R.string.alert_dialog_no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();



        // show it
        alertDialog.show();


    }
    /**
     * Codigo RESIDUAL *******************************************************************
     *
     *
     *
     * Buscar Layout children by tag
     * */
        /*for (int i=0;i<mainLayout.getChildCount();i++)
        {
            boolean LinearLayout = false;
            try
            {
                child = (LinearLayout) mainLayout.getChildAt(i);
                if(child.getTag()!=null)
                {
                    System.out.println("Child tag: " + child.getTag().toString() + " i:" + i);
                }
            }
            catch(Exception e)
            {
                System.out.println("Exception e: " + e.getMessage());
            }
            finally
            {

            }
        }
        */
}