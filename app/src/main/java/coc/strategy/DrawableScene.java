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
	Context context;
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
	ArrayList<Element> arrayPointers = new ArrayList<Element>();
	ArrayList<Element> arrayQuantity = new ArrayList<Element>();

	MenuCItem[] menuitems = new MenuCItem[4];

	ArrayList<Rect> arrayTextMenuBounds = new ArrayList<Rect>();
	private int over_engagement = 15; //how much distance to set over item true
	private int click_engagement = 25; //how much distanc to set click item true
	private int troopsIDs[] = {R.drawable.narquera,R.drawable.nbarbaro,R.drawable.ngiga,R.drawable.nmago,R.drawable.nrompemuros,R.drawable.ndragon,R.drawable.npekka,R.drawable.nminidraco};
	private int darkTroopsIDs[] = {R.drawable.obruja,R.drawable.oesbirro,R.drawable.ogolem,R.drawable.omontapuercos,R.drawable.operrolava,R.drawable.ovalquiria};
	private int spellsIDs[] = {R.drawable.obruja,R.drawable.oesbirro,R.drawable.ogolem,R.drawable.omontapuercos,R.drawable.operrolava,R.drawable.ovalquiria};
	private int heroesIDs[] = {R.drawable.hrey,R.drawable.hreina,R.drawable.hcentinela};
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
		arrayPointers.add(menupointer);
		arrayPointers.add(menupointer2);
		menupointer3 = new Element(context, R.drawable.nbarbaro, R.drawable.narquera, 10, "Carta2",false);
		menupointer3.set_homeposition(new Point(
				menuback_X + menuback_width / 2 - menupointer.get_width() / 2,
				menuback_Y + menuback_height / 2 - menupointer.get_height() / 2));
		menupointer3.set_position(
				menupointer.get_homepoint().x+250,
				menupointer.get_homepoint().y+350);
		arrayPointers.add(menupointer3);
		*/
		for (int i=1;i<=4;i++)
		{
			//pointer (center)
			/*
			Element backgroundElement = new Element(context, R.drawable.shield1, R.drawable.shield1, 10, "Escudo"+i,true);
			backgroundElement.set_homeposition(new Point(
					menuback_X + menuback_width / 2 - backgroundElement.get_width() / 2,
					menuback_Y + menuback_height / 2 - backgroundElement.get_height() / 2));
			backgroundElement.set_position(
					0 + arrayPointers.size()* backgroundElement.get_imgradius()+15,
					screenHeight-backgroundElement.get_height());
			*/
			Element pointer = new Element(context, R.drawable.nmago, R.drawable.nmago, 10, "Tropa "+i,false);
			pointer.set_homeposition(new Point(
					menuback_X + menuback_width / 2 - pointer.get_width() / 2,
					menuback_Y + menuback_height / 2 - pointer.get_height() / 2));
			pointer.set_position(
					arrayPointers.size()* pointer.get_imgradius()+ pointer.get_imgradius()*arrayPointers.size(),
					screenHeight-pointer.get_height());
			//arrayPointers.add(backgroundElement);
			arrayPointers.add(pointer);
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
		//Alex. Nada, codigo aislado.
		Paint paint = new Paint();
		paint.setColor(Color.MAGENTA);
		AssetManager am = this.getContext().getAssets();
		Typeface plain = Typeface.createFromAsset(am, "fonts/Supercell.ttf");
		Typeface bold  = Typeface.create(plain, Typeface.BOLD);
		paint.setTypeface(plain);
		paint.setShadowLayer(15, 0, 0, Color.BLACK);
		paint.setTextSize(24);
		//System.out.println("screenWidth  "+screenWidth); //2392
		//System.out.println("screenHeight "+screenHeight);//1440
		/*
		canvas.drawText("SPELLS", 		screenWidth/60, screenHeight/15 * 1, paint);
		canvas.drawText("Troops", 		screenWidth/60, screenHeight/15 * 2, paint);
		canvas.drawText("Dark troops", 	screenWidth/60, screenHeight/15 * 3, paint);
		canvas.drawText("Heroes", 		screenWidth/60, screenHeight/15 * 4, paint);
		*/
		Rect bounds = new Rect();
		paint.getTextBounds("SPELLS",0,"SPELLS".length(),bounds);
		arrayTextMenuBounds.add(bounds);
		paint.getTextBounds("Troops",0,"Troops".length(),bounds);
		arrayTextMenuBounds.add(bounds);
		paint.getTextBounds("Dark troops",0,"Dark troops".length(),bounds);
		arrayTextMenuBounds.add(bounds);
		paint.getTextBounds("Heroes",0,"Heroes".length(),bounds);
		arrayTextMenuBounds.add(bounds);

		//draw thigs on pointer selected
    	//if(menupointer.get_isselected())
    	//{
    		//draw back menu
    		//canvas.drawBitmap(menuback, menuback_X, menuback_Y, null);

    		//draw items

			//Draw items and items are selected
			//alex
			/*
    		for(MenuCItem m : menuitems)
        	{
    			canvas.drawBitmap(m.get_img(), m.get_x(), m.get_y(), null);
        	}
    		//get the item clicked
    		for(MenuCItem m : menuitems)
        	{
    			if(m.get_isclick())
    			{
    	    		Paint paint = new Paint();
    	    		paint.setColor(Color.BLACK); 
    	    		paint.setTextSize(20); 
    				canvas.drawText(new Integer(m.get_id()).toString() + " is clicked", 30, 30, paint);
    			}
        	}
        	*/
    	//}
    	//draw pointer
		int i=0;
		for (Element pointer: arrayPointers)
		{
			canvas.drawBitmap(pointer.get_img(), pointer.get_x(), pointer.get_y(), null);
			canvas.drawBitmap(arrayQuantity.get(i).get_img(), arrayQuantity.get(i).get_x(), arrayQuantity.get(i).get_y(), null);
			canvas.drawText(String.valueOf(arrayQuantity.indexOf(arrayQuantity.get(i))),arrayQuantity.get(i).get_x()+arrayQuantity.get(i).get_imgradius(),arrayQuantity.get(i).get_y()+(arrayQuantity.get(i).get_imgradius()*3/2), paint);
			i++;
		}
		//older draw on canvas
		/*
    	canvas.drawBitmap(menupointer.get_img(), menupointer.get_x(), menupointer.get_y(), null);
		canvas.drawBitmap(menupointer2.get_img(), menupointer2.get_x(), menupointer2.get_y(), null);
		*/
    }

    public boolean onTouchEvent(MotionEvent event)
	{
		eventActionHandler(event);

		// redraw the canvas
		invalidate();
		return true;
    }
	public void eventActionHandler(MotionEvent event)
	{
		//get current touch position
		eventaction = event.getAction();
		current_x = (int)event.getX();
		current_y = (int)event.getY();

		switch (eventaction)
		{
			case MotionEvent.ACTION_DOWN:
				int i = 0;
				for (Element pointer: arrayPointers)
				{
					if(!pointer.isStatic)
					{
						i++;
						// check if the finger is on the pointer
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
							arrayQuantity.get(arrayPointers.indexOf(pointer)).set_isselected(true);
							//System.out.println("Pointer n:" + i);
							break;
						}
					}
				}
				for(Rect rect: arrayTextMenuBounds)
				{
					if(rect.contains(current_x,current_y))
					{
						System.out.println("Was clicked: " + arrayTextMenuBounds.indexOf(rect)+1);
					}
				}
			break;

			case MotionEvent.ACTION_MOVE:
				int x = 0;
				for (Element pointer: arrayPointers)
				{
					x++;
					// move the pointer
					if (pointer.get_isselected())
					{
						//System.out.println("Draggind pointer n"+x);
						//x/y projection to the menu back radius circle
						int circleset_x = (int) (menuback_center_X + (menuback_radius - menuback_border) * (current_x - menuback_center_X) / Math.sqrt(Math.pow(current_x - menuback_center_X, 2) + Math.pow(current_y - menuback_center_Y, 2)));
						int circleset_y = (int) (menuback_center_Y + (menuback_radius - menuback_border) * (current_y - menuback_center_Y) / Math.sqrt(Math.pow(current_x - menuback_center_X, 2) + Math.pow(current_y - menuback_center_Y, 2)));

						//Alex quitamos radio de seguridad:
						/*
						//check max dimensions and project pointer on the circle
						if(	current_x <= circleset_x && current_y <= circleset_y && current_x <= menuback_center_X && current_y <= menuback_center_Y ||
							current_x <= circleset_x && current_y >= circleset_y && current_x <= menuback_center_X && current_y >= menuback_center_Y ||
							current_x >= circleset_x && current_y <= circleset_y && current_x >= menuback_center_X && current_y <= menuback_center_Y ||
							current_x >= circleset_x && current_y >= circleset_y && current_x >= menuback_center_X && current_y >= menuback_center_Y

								)
						{
							menupointer.set_x(circleset_x - menupointer.get_imgradius());
							menupointer.set_y(circleset_y - menupointer.get_imgradius());
						}
						else
						{
							menupointer.set_x(current_x - menupointer.get_imgradius());
							menupointer.set_y(current_y - menupointer.get_imgradius());
						}
						*/
						borderPointerPositionFix(pointer);

						Element pointerQuantity = arrayQuantity.get(arrayPointers.indexOf(pointer));

						borderPointerPositionFix(pointerQuantity);
						//pointerQuantity.set_x(pointerQuantity.get_x()+pointer.get_imgradius());
						//pointerQuantity.set_y(pointerQuantity.get_y()+pointer.get_imgradius());


						//canvas.drawText(String.valueOf(arrayQuantity.indexOf(arrayQuantity.get(i))),arrayQuantity.get(i).get_x()+arrayQuantity.get(i).get_imgradius(),arrayQuantity.get(i).get_y()+(arrayQuantity.get(i).get_imgradius()*3/2), paint);

						//check items over //
						for (MenuCItem m : menuitems)
						{
							if (
									pointer.get_x() + pointer.get_border() + over_engagement < m.get_x() + m.get_width() - m.get_border() &&
											pointer.get_x() - pointer.get_border() + pointer.get_width() - over_engagement > m.get_x() + m.get_border() &&
											pointer.get_y() + pointer.get_border() + over_engagement < m.get_y() + m.get_height() - m.get_border() &&
											pointer.get_y() - pointer.get_border() + pointer.get_height() - over_engagement > m.get_y() + m.get_border())
								m.set_isover(true);
							else
								m.set_isover(false);
						}
						//check items click
						for (MenuCItem m : menuitems)
						{
							if (
									pointer.get_x() + pointer.get_border() + click_engagement < m.get_x() + m.get_width() - m.get_border() &&
											pointer.get_x() - pointer.get_border() + pointer.get_width() - click_engagement > m.get_x() + m.get_border() &&
											pointer.get_y() + pointer.get_border() + click_engagement < m.get_y() + m.get_height() - m.get_border() &&
											pointer.get_y() - pointer.get_border() + pointer.get_height() - click_engagement > m.get_y() + m.get_border())
								m.set_isclick(true);
							else
								m.set_isclick(false);
						}
					}
				}
			break;

			case MotionEvent.ACTION_UP:
				for (Element pointer: arrayPointers)
				{
					// reset the pointer to home
					pointer.set_isselected(false);
					arrayQuantity.get(arrayPointers.indexOf(pointer)).set_isselected(false);
					//Alex. Volver al centro:
					//menupointer.set_position(menupointer.get_homepoint().x, menupointer.get_homepoint().y);
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
			if (current_x < pointer.get_imgradius() / 2 && current_y < pointer.get_imgradius() / 2)
			{
				isCloseToBorder = true;
				////System.out.println("Case1 Touching Top-Left-Landscape" + pointer.getName());
				pointer.set_x(0);
				pointer.set_y(0);
			}
			else if (current_x < pointer.get_imgradius() / 2)
			{
				isCloseToBorder = true;
				//System.out.println("Case2 Touching LEFT - Landscape" + pointer.getName());
				pointer.set_x(0);
				pointer.set_y(current_y - pointer.get_imgradius());
			}
			else if (current_y < pointer.get_imgradius() / 2)
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
			if (current_y > screenHeight - pointer.get_imgradius() && current_x > screenWidth - pointer.get_imgradius())
			{
				////System.out.println("Case4 Touching Bottom-Right-Landscape" + pointer.getName());
				isCloseToBorder = true;
				pointer.set_x(screenWidth - pointer.get_imgradius() * 2);
				pointer.set_y(screenHeight - pointer.get_imgradius() * 2);
			}
			else if (current_y > screenHeight - pointer.get_imgradius())
			{
				//System.out.println("Case5 Touching Bottom - Landscape" + pointer.getName());
				isCloseToBorder = true;
				pointer.set_x(current_x);
				pointer.set_y(screenHeight - pointer.get_imgradius() * 2);
			}
			else if (current_x > screenWidth - pointer.get_imgradius())
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
