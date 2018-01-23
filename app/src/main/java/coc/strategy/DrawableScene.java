package coc.strategy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

//menu class
public class DrawableScene extends View
{
	public boolean lastClickedDrawn = true;

	Context context;
	Canvas canvas;

	private int center_X = 0;
	private int center_Y = 0;
	private int eventaction = 0;

	private int current_x=0;
	private int current_y=0;

	private Bitmap menuback = null;
	private int menuback_center_X = 0;
	private int menuback_center_Y = 0;
	private int menuback_X = 0;
	private int menuback_Y = 0;
	private int menuback_height = 0;
	private int menuback_width = 0;
	private int menuback_radius = 0;
	private int menuback_border = 0;
	private int screenHeight = 0;
	private int screenWidth = 0;
		
	private Element   menupointer  = null;
	private Element   menupointer2 = null;
	private Element   menupointer3 = null;
	private Element[] menuPointers = new Element[40];
	ArrayList<Element> arrayElements = new ArrayList<Element>();
	ArrayList<Element> arrayQuantity = new ArrayList<Element>();

	MenuCItem[] menuitems = new MenuCItem[4];

	ArrayList<Rect> arrayTextMenuBounds = new ArrayList<Rect>();
	private int over_engagement = 15; //how much distance to set over item true
	private int click_engagement = 25; //how much distanc to set click item true

	public void printElementsFromArrayIDs(int[] var)
	{
		for (int i=0;i<var.length;i++)
		{
			Element element = new Element(context, R.drawable.nmago, R.drawable.nmago, 10, "Tropa "+i,false);
			element.set_homeposition(new Point(
					menuback_X + menuback_width / 2 - element.get_width() / 2,
					menuback_Y + menuback_height / 2 - element.get_height() / 2));
		}
	}
	public void addElement(int resImage,int resImageHover)
	{
		Element element = new Element(context,resImage,resImageHover,10,String.valueOf(arrayElements.size()),false);
		element.set_homeposition(new Point(element.get_imgradius()*2, element.get_imgradius()*2));
		element.set_position(element.get_homepoint());
		arrayElements.add(element);
		arrayQuantity.add(element);
	}
	public void drawElementOnCanvas(Element element)
	{
		this.canvas.drawBitmap(element.get_img(), element.get_x(), element.get_y(), null);
	}
    public DrawableScene(Context context)
    {
        super(context);
		this.context = context;
        setFocusable(true); //necessary for getting the touch events
                
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;

		screenHeight = ((MainPage)this.getContext()).get_screenHeight();
		screenWidth = ((MainPage)this.getContext()).get_screenWidth();
        center_X = screenWidth/2;
        center_Y = screenHeight/2;
        
        //menu background
        menuback = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_back);
        menuback_center_X = center_X;
        menuback_center_Y = center_Y;
        menuback_width = menuback.getWidth();
        menuback_height = menuback.getHeight();
        menuback_X = menuback_center_X - menuback_width / 2;
        menuback_Y = menuback_center_Y - menuback_height / 2;
        menuback_radius = menuback_height / 2;
        menuback_border = 35;
        
        //pointer (center)
		/*
        menupointer = new Element(context, R.drawable.nminidraco, R.drawable.ndragon, 10, "Carta1",false);
        menupointer.set_homeposition(new Point(
        		menuback_X + menuback_width / 2 - menupointer.get_width() / 2,
        		menuback_Y + menuback_height / 2 - menupointer.get_height() / 2));
        menupointer.set_position(
        		menupointer.get_homepoint().x,
        		menupointer.get_homepoint().y);

		menupointer2 = new Element(context, R.drawable.nbarbaro, R.drawable.narquera, 10, "Carta2",false);
		menupointer2.set_homeposition(new Point(
				menuback_X + menuback_width / 2 - menupointer.get_width() / 2,
				menuback_Y + menuback_height / 2 - menupointer.get_height() / 2));
		menupointer2.set_position(
				menupointer.get_homepoint().x+250,
				menupointer.get_homepoint().y+250);
		arrayElements.add(menupointer);
		arrayElements.add(menupointer2);
		menupointer3 = new Element(context, R.drawable.nbarbaro, R.drawable.narquera, 10, "Carta2",false);
		menupointer3.set_homeposition(new Point(
				menuback_X + menuback_width / 2 - menupointer.get_width() / 2,
				menuback_Y + menuback_height / 2 - menupointer.get_height() / 2));
		menupointer3.set_position(
				menupointer.get_homepoint().x+250,
				menupointer.get_homepoint().y+350);
		arrayElements.add(menupointer3);
		*/
		//Anulamos rellenado de magos
		int templimit = 0; //poner a 4 para recuperar los magos
		for (int i=1;i<=templimit;i++)
		{
			//pointer (center)
			/*
			Element backgroundElement = new Element(context, R.drawable.shield1, R.drawable.shield1, 10, "Escudo"+i,true);
			backgroundElement.set_homeposition(new Point(
					menuback_X + menuback_width / 2 - backgroundElement.get_width() / 2,
					menuback_Y + menuback_height / 2 - backgroundElement.get_height() / 2));
			backgroundElement.set_position(
					0 + arrayElements.size()* backgroundElement.get_imgradius()+15,
					screenHeight-backgroundElement.get_height());
			*/
			Element pointer = new Element(context, R.drawable.nmago, R.drawable.nmago, 10, "Tropa "+i,false);
			pointer.set_homeposition(new Point(
					menuback_X + menuback_width / 2 - pointer.get_width() / 2,
					menuback_Y + menuback_height / 2 - pointer.get_height() / 2));
			pointer.set_position(
					arrayElements.size()* pointer.get_imgradius()+ pointer.get_imgradius()* arrayElements.size(),
					screenHeight-pointer.get_height());
			//arrayElements.add(backgroundElement);
			arrayElements.add(pointer);
			Element quantity = new Element(context, R.drawable.menu_pointer, R.drawable.menu_pointerover, 10, "Quantity "+i,false);
			int quantityX = arrayQuantity.size()* pointer.get_imgradius()+ pointer.get_imgradius()*arrayQuantity.size()+pointer.get_imgradius();
			int quantityY = screenHeight-pointer.get_height()+ pointer.get_imgradius();
			quantity.set_homeposition(new Point(quantityX,quantityY));
			quantity.set_position(quantityX,quantityY);
			arrayQuantity.add(quantity);
		}



        //a item (0)
        menuitems[0] = new MenuCItem(context, R.drawable.menu_item_a, R.drawable.menu_item_aover, 10, 0);
        menuitems[0].set_position(
        		menuback_X - menuitems[0].get_width() / 2 + menuback_width / 2,
        		menuback_Y - menuitems[0].get_height() / 2 + menuback_border);
        
        //b item (3)
        menuitems[1] = new MenuCItem(context, R.drawable.menu_item_b, R.drawable.menu_item_bover, 10, 1);
        menuitems[1].set_position(
        		menuback_X - menuitems[1].get_width() / 2 + menuback_width - menuback_border,
        		menuback_Y - menuitems[1].get_height() / 2 + menuback_height / 2);
        
        //c item (6)
        menuitems[2] = new MenuCItem(context, R.drawable.menu_item_c, R.drawable.menu_item_cover, 10, 2);
        menuitems[2].set_position(
        		menuback_X - menuitems[2].get_width() / 2 + menuback_width / 2,
        		menuback_Y - menuitems[2].get_height() / 2 + menuback_height - menuback_border);
        
        //d item (9)
        menuitems[3] = new MenuCItem(context, R.drawable.menu_item_d, R.drawable.menu_item_dover, 10, 3);
        menuitems[3].set_position(
        		menuback_X - menuitems[3].get_width() / 2 + menuback_border,
        		menuback_Y - menuitems[3].get_height() / 2 + menuback_height / 2);

    }
    
    @Override
    protected void onDraw(Canvas canvas) 
    {
		this.canvas = canvas;

		Paint paint = new Paint();
		paint.setColor(Color.MAGENTA);
		AssetManager am = this.getContext().getAssets();
		Typeface plain = Typeface.createFromAsset(am, "fonts/Supercell.ttf");
		Typeface bold  = Typeface.create(plain, Typeface.BOLD);
		paint.setTypeface(plain);
		paint.setShadowLayer(5, 5, 5, Color.RED);
		paint.setTextSize(30);

		//Draw quantity
		for (Element element: arrayElements)
		{
			drawElementOnCanvas(element);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			paint.setStrokeWidth(1);
			paint.setColor(Color.WHITE);
			canvas.drawText(String.valueOf(arrayElements.indexOf(element)),element.get_x()+element.get_imgradius()*2,element.get_y()+(element.get_imgradius()*5/2), paint);
		}
    }
    public boolean onTouchEvent(MotionEvent event)
	{
		eventActionHandler(event);// our custom handler
		invalidate();// this redraw the canvas
		return true;
    }
	public void eventActionHandler(MotionEvent event)
	{
		eventaction = event.getAction(); //get current touch position
		current_x = (int)event.getX();
		current_y = (int)event.getY();
		System.out.println("Drawable scene evenActionHandler");
		switch (eventaction)
		{
			case MotionEvent.ACTION_DOWN:
				lastClickedDrawn = true;
				System.out.println("Elements_:" + arrayElements.size());
				for (Element element: arrayElements)
				{
					if(!element.isDrawn())
					{
						element.set_homeposition(new Point(center_X,current_y));
						element.set_x(current_x);
						element.set_y(current_y);
						element.isDrawn(true);
					}
				}
				int i=0;
				for (Element pointer: arrayElements) //Item is selected (Check and mark)
				{
					if(!pointer.isStatic)
					{
						i++; // check if the finger is on the pointer
						int menupointer_x = pointer.get_x() + pointer.get_imgradius();
						int menupointer_y = pointer.get_y() + pointer.get_imgradius();
						//new Alex:

						// distance from the touch pointe to the center of the pointer
						double pointer_radius = Math.sqrt((double) (Math.pow(menupointer_x - current_x, 2) + Math.pow(menupointer_y - current_y, 2)));
						//check if the pointer is selected (add some distance)
						if (pointer_radius < pointer.get_imgradius() - 3)
						{
							pointer.set_isselected(true);
							//Per each selected Item we carry the quantity as selected.
							arrayQuantity.get(arrayElements.indexOf(pointer)).set_isselected(true);
							break;
						}
					}
				}
			break;

			case MotionEvent.ACTION_MOVE:
				int x = 0;
				for (Element pointer: arrayElements)
				{
					x++;// move the pointer
					if (pointer.get_isselected())
					{
						borderPointerPositionFix(pointer);

						Element pointerQuantity = arrayQuantity.get(arrayElements.indexOf(pointer));

						borderPointerPositionFix(pointerQuantity);
					}
				}
			break;

			case MotionEvent.ACTION_UP:
				for (Element pointer: arrayElements)
				{// reset the pointer to home if needed
					pointer.set_isselected(false);
					arrayQuantity.get(arrayElements.indexOf(pointer)).set_isselected(false);
				}
			break;
		}
	}
	private void borderPointerPositionFix(Element pointer)
	{
		if(!pointer.isStatic)
		{
			//Alex fix border touch
			boolean isCloseToBorder = false;
			if (current_x <= pointer.get_imgradius() / 2 && current_y <= pointer.get_imgradius() / 2)
			{
				////System.out.println("Case1 Touching Top-Left-Landscape" + pointer.getName());
				isCloseToBorder = true;
				pointer.set_x(0);
				pointer.set_y(0);
			}
			else if (current_x <= pointer.get_imgradius() / 2)
			{
				//System.out.println("Case2 Touching LEFT - Landscape" + pointer.getName());
				isCloseToBorder = true;
				pointer.set_x(0);
				pointer.set_y(current_y - pointer.get_imgradius());
			}
			else if (current_y <= pointer.get_imgradius() / 2)
			{
				////System.out.println("Case3 Touching TOP - Landscape" + pointer.getName());
				isCloseToBorder = true;
				pointer.set_x(current_x - pointer.get_imgradius());
				pointer.set_y(0);
			}
			else
			{
				isCloseToBorder = false;
			}
			if (current_y >= screenHeight - pointer.get_imgradius() && current_x >=screenWidth - pointer.get_imgradius())
			{
				////System.out.println("Case4 Touching Bottom-Right-Landscape" + pointer.getName());
				isCloseToBorder = true;
				pointer.set_x(screenWidth - pointer.get_imgradius() * 2);
				pointer.set_y(screenHeight - pointer.get_imgradius() * 2);
			}
			else if (current_y >=screenHeight - pointer.get_imgradius())
			{
				//System.out.println("Case5 Touching Bottom - Landscape" + pointer.getName());
				isCloseToBorder = true;
				pointer.set_x(current_x);
				pointer.set_y(screenHeight - pointer.get_imgradius() * 2);
			}
			else if (current_x >=screenWidth - pointer.get_imgradius())
			{
				//System.out.println("Case6 Touching Right - Landscape" + pointer.getName());
				isCloseToBorder = true;
				pointer.set_x(screenWidth - pointer.get_imgradius() * 2);
				pointer.set_y(current_y);
			}
			if (!isCloseToBorder) // Mayor parte del tiempo:
			{
				//System.out.println("Case7  (NORMAL move)" + pointer.getName());
				pointer.set_x(current_x - pointer.get_imgradius());
				pointer.set_y(current_y - pointer.get_imgradius());
			}
			isCloseToBorder = false;
		}
	}
}
