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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import coc.strategy.FloatingMenu.CocElementDM;
import coc.strategy.FloatingMenu.CocElementsRow;
import coc.strategy.FloatingMenu.CustomAdapter;

public class MainPage extends Activity {

    private int screenWidth = 0;
    private int screenHeight = 0;

    private int smallTroops[] = {R.drawable.arc,R.drawable.arq,R.drawable.bab,R.drawable.bak,R.drawable.bal,R.drawable.bar,R.drawable.bow,R.drawable.dra,R.drawable.gia,R.drawable.gob,R.drawable.gol,R.drawable.gra,R.drawable.hea,R.drawable.hog,R.drawable.lav,R.drawable.min,R.drawable.mnr,R.drawable.pek,R.drawable.val,R.drawable.wal,R.drawable.wit,R.drawable.wiz};
    private int normalTroopsIDs[] = {R.drawable.narquera, R.drawable.nbarbaro, R.drawable.ngiga, R.drawable.nmago, R.drawable.nrompemuros, R.drawable.ndragon, R.drawable.npekka, R.drawable.nminidraco};
    private int darkTroopsIDs[]   = {R.drawable.obruja,R.drawable.oesbirro,R.drawable.ogolem,R.drawable.omontapuercos,R.drawable.operrolava,R.drawable.ovalquiria,R.drawable.ovalquiria};
    private int spellsIDs[]       = {R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell,R.drawable.red_spell};
    private int heroesIDs[]       = {R.drawable.hrey,R.drawable.hreina,R.drawable.hcentinela};

    LinearLayout mainLayout;
    LinearLayout layoutButtons;
    ImageView imageSwitcher = null;

    //mainButtons
    Button boton1;
    Button boton2;
    Button boton3;
    Button boton4;
    Button botonAdd;

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

        obtainViewButtons();
        setUpPaletteArrayAdapter();
    }
    public void obtainViewButtons()
    {
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

        boton1.setTypeface(plain);
        boton2.setTypeface(plain);
        boton3.setTypeface(plain);
        boton4.setTypeface(plain);
    }
    public void setUpPaletteArrayAdapter()
    {
        listView = (ListView) findViewById(R.id.listViewFloating);

        final ArrayList<CocElementDM> elements = new ArrayList<>();
        loadItemsInArrayList(elements);
        final ArrayList<CocElementsRow> rows = getRowsFromElements(elements);
        CustomAdapter                 adapter  = new CustomAdapter(rows,getApplicationContext());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CocElementDM element = elements.get(position);

                /*Snackbar.make(view, elements.getName()+"\n"+elements.getType()+" API: "+elements.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                        */
            }
        });
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
        }
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
        addDrawableSceneElement(normalTroopsIDs,clicked);
        invalidateDrawableScene();
    }
    public void addDrawableSceneElement(int[] drawableResourceID, int clicked)
    {
        drawableScene.addElement(drawableResourceID[clicked],drawableResourceID[clicked]);
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
}