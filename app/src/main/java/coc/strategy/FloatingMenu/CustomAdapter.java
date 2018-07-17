package coc.strategy.FloatingMenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import coc.strategy.MainPage;
import coc.strategy.R;

/**
 * Created by XTO on 18/01/2018.
 */

public class CustomAdapter extends ArrayAdapter<CocElementsRow>
{
    private View currentClickedView = null;
    private int currentClickedViewBackGround = Color.WHITE;
    private ArrayList<CocElementsRow> elementsRows;
    private Activity activity;
    Context context;
    HashMap map;

    public CustomAdapter(Activity activity, ArrayList<CocElementsRow> elements, Context context) {
        super(context, R.layout.floating_row_item_x3, elements);
        this.elementsRows = elements;
        this.context = context;
        map = new HashMap();
        this.activity = activity;
    }
    // View lookup cache
    public static class ViewHolder
    {
        public ImageButton imb1;
        public ImageButton imb2;
        public ImageButton imb3;
        public TextView    tv1;
        public TextView    tv2;
        public TextView    tv3;
        int lastPosition = 0;
    }
    /*
    @Override
    public void onClick(View v)
    {
        int position=(Integer) v.getTag();
        Object object = getItem(position);
        CocElementDM element =(CocElementDM)object;

        switch (v.getId())
        {
            default:

            break;
        }
    }
    */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        CocElementDM element1 = getItem(position).element1;
        CocElementDM element2 = getItem(position).element2;
        CocElementDM element3 = getItem(position).element3;
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder = null; // view lookup cache stored in tag

        final View result;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.floating_row_item_x3, parent, false);
            viewHolder.imb1 = (ImageButton) convertView.findViewById(R.id.imb1);
            viewHolder.imb2 = (ImageButton) convertView.findViewById(R.id.imb2);
            viewHolder.imb3 = (ImageButton) convertView.findViewById(R.id.imb3);
            setViewProperties(viewHolder.imb1, element1);
            setViewProperties(viewHolder.imb2, element2);
            setViewProperties(viewHolder.imb3, element3);
            /*
            viewHolder.imb1.setOnClickListener(onElementClickListener1);
            viewHolder.imb2.setOnClickListener(onElementClickListener2);
            viewHolder.imb3.setOnClickListener(onElementClickListener3);
            */
            if(element1 != null)
            {

            }
            /*
            else
            {
                System.out.println("Situacion a corregir, elemento 1 vacio en arrayRow de elementos");
            }
            if(element2 != null)
            {
                viewHolder.imb2.setImageDrawable(context.getResources().getDrawable(element2.getDrawableResource()));
            }
            else
            {
                //viewHolder.imb2.setImageDrawable(context.getResources().getDrawable(R.drawable.empty));
            }
            viewHolder.imb2.setMaxHeight(5);
            viewHolder.imb2.setMaxWidth(5);
            if(element3 != null)
            {
                viewHolder.imb3.setImageDrawable(context.getResources().getDrawable(element3.getDrawableResource()));
            }
            else
            {
                //viewHolder.imb3.setImageDrawable(context.getResources().getDrawable(R.drawable.empty));
            }
            viewHolder.imb3.setMaxHeight(5);
            viewHolder.imb3.setMaxWidth(5);
            */


            //viewHolder.tv1.setText(String.valueOf(position));
            /*
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
            viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
            */
            result = convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        /*
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        */
        return convertView;
    }
    public void setViewProperties(View view, CocElementDM element)
    {
        ((ImageButton)view).setImageDrawable(context.getResources().getDrawable(element.getDrawableResource()));
        ((ImageButton)view).setMaxHeight(5);
        ((ImageButton)view).setMaxWidth(5);
        ((ImageButton)view).setTag(String.valueOf(element.getDrawableResource()));
        ((ImageButton)view).setOnClickListener(onElementClickListener);
    }
    /**
     * These handle the case where you want different types of view for different rows.
     * For instance, in a contacts application you may want even rows to have pictures
     * on the left side and odd rows to have pictures on the right. In that case,
     * you would use:
     * */
    private View.OnClickListener onElementClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            String viewTag = v.getTag().toString();
            int clickedViewDrawableID = Integer.valueOf(viewTag);
            if(clickedViewDrawableID!=R.drawable.empty)
            {
                ((MainPage)activity).drawElementByDrawableResourceID(clickedViewDrawableID);
                if(currentClickedView!=null)
                {
                    currentClickedView.setBackgroundColor(Color.WHITE);
                    v.setBackgroundColor(Color.YELLOW);
                    currentClickedView = v;
                }
                else
                {
                    currentClickedView = v;
                    currentClickedView.setBackgroundColor(Color.YELLOW);
                }
            }
            System.out.println("Clicked : " + viewTag);
        }
    };

    //Para evitar replicados al hacer scroll
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }
}
