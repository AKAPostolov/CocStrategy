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
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

//menu class
public class DrawableScene extends View
{
	DrawableElement drawableElement = null;
	public ArrayList<DrawableWave> arrayDrawableWaves;
	public boolean lastClickedDrawn = true;
	public boolean waveColorUsed = false;
	Context context;
	Canvas canvas;

	private int center_X = 0;
	private int center_Y = 0;
	private int eventaction = 0;

	private int current_x=0;
	private int current_y=0;
	Paint paint = new Paint();
	Paint paintForNumbers = new Paint();
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
		
	private DrawableElement   menupointer  = null;
	private DrawableElement   menupointer2 = null;
	private DrawableElement   menupointer3 = null;
	private DrawableElement[] menuPointers = new DrawableElement[40];
	ArrayList<DrawableElement> arrayDrawableElements = new ArrayList<DrawableElement>();
	ArrayList<DrawableElement> arrayQuantity         = new ArrayList<DrawableElement>();

	MenuCItem[] menuitems = new MenuCItem[4];

	ArrayList<Rect> arrayTextMenuBounds = new ArrayList<Rect>();
	private int over_engagement = 15; //how much distance to set over item true
	private int click_engagement = 25; //how much distanc to set click item true

	public void printElementsFromArrayIDs(int[] var)
	{
		for (int i=0;i<var.length;i++)
		{
			DrawableElement drawableElement = new DrawableElement(context, R.drawable.nmago, R.drawable.nmago, 10, "Tropa "+i,false);
			drawableElement.set_homeposition(new Point(
					menuback_X + menuback_width / 2 - drawableElement.get_width() / 2,
					menuback_Y + menuback_height / 2 - drawableElement.get_height() / 2));
		}
	}
	public void removeAllElements()
	{
		arrayDrawableElements.clear();
		arrayQuantity.clear();
		invalidate();
		System.out.println("Deleted & ALL CLEAR ?");
	}
	public void removeLastElement()
	{
		if(arrayDrawableElements.size()>0)
		{
			arrayDrawableElements.remove(arrayDrawableElements.size() - 1);
			arrayQuantity.remove(arrayQuantity.size() - 1);
			invalidate();
			if(arrayDrawableElements.size()==1)
			{
				arrayDrawableElements.clear();
				arrayQuantity.clear();
			}
			System.out.println("Deleted element ? YES");
		}
		else
		{
			System.out.println("Deleted element ? No because they are 0");
		}
	}
	public ArrayList<DrawableElement> getDrawableElements(int currentWave)
	{
		ArrayList<DrawableElement> arrayListResult = new ArrayList<>();
		for (DrawableElement element: arrayDrawableElements)
		{
			if(element.wave==0)
			{
				element.wave = currentWave;
				arrayListResult.add(element);
			}
		}
		return arrayListResult;
	}
	public void addDrawableWaveElements(int currentWave)
	{
		arrayDrawableWaves.add(new DrawableWave(getDrawableElements(currentWave)));
	}
	public void addElement(Bitmap resImage,Bitmap resImageHover)
	{
		DrawableElement drawableElement = new DrawableElement(context,resImage,resImageHover,10,String.valueOf(
				arrayDrawableElements.size()),false);
		drawableElement.set_homeposition(new Point(drawableElement.get_imgradius()*2, drawableElement.get_imgradius()*2));
		drawableElement.set_position(drawableElement.get_homepoint());
		arrayDrawableElements.add(drawableElement);
		arrayQuantity.add(drawableElement);
	}
	public void addPreviousElement(DrawableElement drawableElement)
	{
		if(drawableElement!=null) ((MainPage)context).drawElementByDrawableResourceID();
	}
	public void addElement(int resImage,int resImageHover,int currentPaintColor)
	{
		System.out.println("\n Ini: addElement" + arrayDrawableElements.size());
		drawableElement = new DrawableElement(context,resImage,resImageHover,10,String.valueOf(arrayDrawableElements.size()),false);
		drawableElement.set_homeposition(new Point(drawableElement.get_imgradius()*2, drawableElement.get_imgradius()*2));
		Point point = drawableElement.get_homepoint();
		System.out.println("\n element point: " + point);
		drawableElement.set_position(point);
		drawableElement.currentPaintColor = currentPaintColor;
		//if(point.x!=52 && point.y!=52)
		//{
			arrayDrawableElements.add(drawableElement);
			arrayQuantity.add(drawableElement);
			System.out.println(" Fini: addElement" + arrayDrawableElements.size());
		//}
        /*for (DrawableElement drawableElement : arrayDrawableElements){
			System.out.println(" element pos_X: " + drawableElement.get_x() );
			System.out.println(" element pos_Y: " + drawableElement.get_y() );
		}*/
	}
	public void drawElementOnCanvas(DrawableElement drawableElement)
	{
		this.canvas.drawBitmap(drawableElement.get_img(), drawableElement.get_x(), drawableElement.get_y(), null);
	}
    public DrawableScene(Context context)
    {
        super(context);

		this.context = context;
        //setFocusable(true); //necessary for getting the touch events
		arrayDrawableWaves = new ArrayList<>();
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

		//Anulamos rellenado de magos
		int templimit = 0; //poner a 4 para recuperar los magos
		for (int i=1;i<=templimit;i++)
		{
			DrawableElement pointer = new DrawableElement(context, R.drawable.nmago, R.drawable.nmago, 10, "Tropa "+i,false);
			pointer.set_homeposition(new Point(
					menuback_X + menuback_width / 2 - pointer.get_width() / 2,
					menuback_Y + menuback_height / 2 - pointer.get_height() / 2));
			pointer.set_position(
					arrayDrawableElements.size()* pointer.get_imgradius()+ pointer.get_imgradius()* arrayDrawableElements.size(),
					screenHeight-pointer.get_height());
			//arrayDrawableElements.add(backgroundElement);
			arrayDrawableElements.add(pointer);
			DrawableElement quantity  = new DrawableElement(context, R.drawable.menu_pointer, R.drawable.menu_pointerover, 10, "Quantity "+i,false);
			int             quantityX = arrayQuantity.size()* pointer.get_imgradius()+ pointer.get_imgradius()*arrayQuantity.size()+pointer.get_imgradius();
			int             quantityY = screenHeight-pointer.get_height()+ pointer.get_imgradius();
			quantity.set_homeposition(new Point(quantityX,quantityY));
			quantity.set_position(quantityX,quantityY);
			arrayQuantity.add(quantity);
		}
    }
    
    @Override
    protected void onDraw(Canvas canvas) 
    {
		System.out.println("onDraw DrawableScene event size: " + arrayDrawableElements.size());
		paint.setColor(Color.WHITE); //default
		this.canvas = canvas;
		AssetManager am = this.getContext().getAssets();

		Typeface plainNumbers = Typeface.createFromAsset(am, "fonts/Quango.otf");
		paintForNumbers.setTypeface(plainNumbers);
		paintForNumbers.setTextSize(50);
		paintForNumbers.setColor(Color.WHITE);

		Typeface plain = Typeface.createFromAsset(am, "fonts/Supercell.ttf");
		Typeface bold  = Typeface.create(plain, Typeface.BOLD);
		paint.setTypeface(plain);
		paint.setTextSize(30);

		//Draw quantity text
		for (DrawableElement drawableElement : arrayDrawableElements)
		{
			paintForNumbers.setStyle(Paint.Style.FILL_AND_STROKE);
			paintForNumbers.setStrokeWidth(5);
			paintForNumbers.setShadowLayer(7, 7, 7, drawableElement.currentPaintColor);
			//paint.setShadowLayer(5, 5, 5,Color.RED);
			//canvas.drawText("☐",drawableElement.get_x()+drawableElement.get_imgradius()-2,drawableElement.get_y()+drawableElement.get_imgradius()+2, paintForNumbers);

			//arrayDrawableElements.remove(arrayDrawableElements.get(arrayDrawableElements.size()-1));

			//Alex diciembre2018
			if(arrayDrawableElements.size()>1 && arrayDrawableElements.indexOf(drawableElement)<arrayDrawableElements.size()-1)
			{
				drawElementOnCanvas(drawableElement);
			}

			//Aqui va el numero de la tropa:
			///TODO: Continuar con la numeración de tropas o oleadas
			///canvas.drawText("1",//String.valueOf(drawableElement.getName()),drawableElement.get_x()+ drawableElement.get_imgradius()*5/2,drawableElement.get_y()+(drawableElement.get_imgradius()*5/2), paintForNumbers);
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

		switch (eventaction)
		{
			case MotionEvent.ACTION_DOWN:
				System.out.println("Drawable scene ACTION_DOWN");
				boolean someItemIsSelected = false;
				int i=0;
				for (DrawableElement drawableElement : arrayDrawableElements) //Item is selected (Check and mark)
				{
					if(!drawableElement.isStatic)
					{
						i++; // check if the finger is on the drawableElement
						int menupointer_x = drawableElement.get_x() + drawableElement.get_imgradius();
						int menupointer_y = drawableElement.get_y() + drawableElement.get_imgradius();

						// distance from the touch point to the center of the drawnElement
						double pointer_radius = Math.sqrt((double) (Math.pow(menupointer_x - current_x, 2) + Math.pow(menupointer_y - current_y, 2)));
						//check if the drawableElement is selected (add some distance)
						if (pointer_radius < drawableElement.get_imgradius() - 1)
						{
							drawableElement.set_isselected(true);
							//Per each selected Item we carry the quantity as selected.
							arrayQuantity.get(arrayDrawableElements.indexOf(drawableElement)).set_isselected(true);
							System.out.println("\nItem selected");
							someItemIsSelected = true;
							break;
						}
					}
				}
				///TODO permito multiples dibujos de una tropa:
				//antes:
				//lastClickedDrawn = true;
				//despues:
				//if(lastClickedDrawn)
				//{
				//lastClickedDrawn = false;
				//addPreviousElement();
				//invalidate();
				//}
				System.out.println("\nEvent DOWN Elements_:" + arrayDrawableElements.size());
				if(!someItemIsSelected)
				{
					for (DrawableElement drawableElement : arrayDrawableElements)
					{
						///TODO permito multiples dibujos de una tropa:
						if (!drawableElement.isDrawn())
						{
							drawableElement.set_homeposition(new Point(center_X, current_y));
							drawableElement.set_x(current_x);
							drawableElement.set_y(current_y);
							drawableElement.isDrawn(true);
							//despues:
							lastClickedDrawn = true;
						}
					}
				}
			break;

			case MotionEvent.ACTION_MOVE:
				int x = 0;
				for (DrawableElement drawableElement : arrayDrawableElements)
				{
					//if (arrayDrawableElements.size() > 1 && drawableElement.get_x() == 52 && drawableElement.get_x() == 52)
					if (true)
					{
						x++;// move the drawableElement
						if (drawableElement.get_isselected())
						{
							System.out.println("\nDrawableSelected is: " + drawableElement.getName() );
							borderElementPositionFix(drawableElement);

							DrawableElement pointerQuantity = arrayQuantity.get(
									arrayDrawableElements.indexOf(drawableElement));

							borderElementPositionFix(pointerQuantity);
						}
					}
				}
			break;

			case MotionEvent.ACTION_UP:
				boolean thereIsOneSelected = false;
				for (DrawableElement drawableElement : arrayDrawableElements)
				{// reset the drawableElement to home if needed
					if (drawableElement.get_isselected()==true)
					{
						thereIsOneSelected=true;
						drawableElement.set_isselected(false);
						arrayQuantity.get(arrayDrawableElements.indexOf(drawableElement)).set_isselected(false);
					}
				}
				//Si no hay ningun elemento seleccionado al levantar el dedo podemos dibujar uno nuevo
				if(!thereIsOneSelected)
				{
					System.out.println("\nEvent DOWN Elements_:" + arrayDrawableElements.size());
					if(arrayDrawableElements.size()>0)
                    {
						for (DrawableElement drawableElement : arrayDrawableElements)
						{
							System.out.println("DW element pos: " + drawableElement.get_x() );
						}
                        DrawableElement drawableElement = arrayDrawableElements.get(arrayDrawableElements.size() - 1);
                        ///TODO permito multiples dibujos de una tropa:
                        if (!drawableElement.isDrawn())
                        {
                            drawableElement.set_homeposition(new Point(center_X, current_y));
                            drawableElement.set_x(current_x);
                            drawableElement.set_y(current_y);
                            drawableElement.isDrawn(true);
                            //despues:
                            lastClickedDrawn = true;
                        }
						addPreviousElement(drawableElement);

                    	/*
						DrawableElement drawableElement = arrayDrawableElements.get(
								arrayDrawableElements.size() - 1);
						arrayDrawableElements.add(drawableElement);
                        invalidate();
                        */
                    	/*
						addPreviousElement(drawableElement);
						*/
                    }
				}

			break;
		}
	}
	private void borderElementPositionFix(DrawableElement pointer)
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
