package coc.strategy.FloatingMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

import coc.strategy.R;

/**
 * Created by XTO on 18/01/2018.
 */

public class CustomAdapter extends ArrayAdapter<CocElementsRow> implements View.OnClickListener
{
    private ArrayList<CocElementsRow> elementsRows;
    Context context;
    HashMap map;

    public CustomAdapter(ArrayList<CocElementsRow> elements, Context context) {
        super(context, R.layout.floating_row_item_x3, elements);
        this.elementsRows = elements;
        this.context = context;
        map = new HashMap();
    }
    // View lookup cache
    private static class ViewHolder
    {
        public ImageButton imb1;
        public ImageButton imb2;
        public ImageButton imb3;
        public TextView    tv1;
        public TextView    tv2;
        public TextView    tv3;
    }

    @Override
    public void onClick(View v)
    {
        int position=(Integer) v.getTag();
        Object object = getItem(position);
        CocElementDM element =(CocElementDM)object;
        System.out.println(String.valueOf(position));
        switch (v.getId())
        {
            default:
                System.out.println(v.getTag().toString());
                /*Snackbar.make(v, "Release date " +element.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                        */
            break;
        }
    }

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

            if(element1 != null)
            {
                viewHolder.imb1.setImageDrawable(context.getResources().getDrawable(element1.getDrawableResource()));
                viewHolder.imb1.setMaxHeight(5);
                viewHolder.imb1.setMaxWidth(5);
            }
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

    /**
     * These handle the case where you want different types of view for different rows.
     * For instance, in a contacts application you may want even rows to have pictures
     * on the left side and odd rows to have pictures on the right. In that case,
     * you would use:
     * */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }
}
