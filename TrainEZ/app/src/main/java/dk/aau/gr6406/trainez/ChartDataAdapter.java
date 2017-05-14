package dk.aau.gr6406.trainez;


/**
 * Created by marti on 4/30/2017.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import dk.aau.gr6406.trainez.listviewitems.ChartItem;

/** adapter that supports 3 different item types */
public class ChartDataAdapter extends ArrayAdapter<ChartItem> {

    public ChartDataAdapter(Context context, List<ChartItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(position, convertView, getContext());
    }

    @Override
    public int getItemViewType(int position) {
        // return the views type
        return getItem(position).getItemType();
    }

    @Override
    public int getViewTypeCount() {
        return 3; // we have 3 different item-types
    }
}
