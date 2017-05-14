package dk.aau.gr6406.trainez;


/**
 * Created by marti on 4/29/2017.
 */



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dk.aau.gr6406.trainez.listviewitems.ChartItem;
import dk.aau.gr6406.trainez.listviewitems.LineChartItem;


public class MultiSelectionSpinner extends android.support.v7.widget.AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener
{
    private static final String TAG = "dk.aau.trainez";
    String[] _items = null;
    boolean[] mSelection = null;

    ArrayAdapter<String> simple_adapter;

    ListView lv;
    Context fragmentContext;
    boolean firstTimeToogle = true;
    private ArrayList<Integer> nonZeroColumns;

    public MultiSelectionSpinner(Context context)
    {
        super(context);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
        Log.i(TAG,"in the constructor in  mulitselctionsspinner");
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        Log.i(TAG,"OnClick in multiselction spinner");

        if (mSelection != null && which < mSelection.length ) {
            mSelection[which] = isChecked;

            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }







    @Override
    public boolean performClick() {


        if(firstTimeToogle==true){
            _items=Arrays.copyOfRange(_items, 1, _items.length);
            firstTimeToogle = false;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(_items, mSelection, this);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                Log.i(TAG,"OK  - onClick in multiSelction: " + getSelectedItemsAsString());
                Log.i(TAG,"getSelcetedIndeices: " + getSelectedIndicies() + "nonzero" + nonZeroColumns);
                StatHelper statHelper = new StatHelper(fragmentContext, getSelectedIndicies(),lv, nonZeroColumns);
                //changeStat();



            }
        });
        builder.show();
        return true;
    }


    public void setInfo(Context fragmentContext, ListView lv, ArrayList<Integer> nonZeroColumns){
        this.lv=lv;
        this.fragmentContext = fragmentContext;
        this.nonZeroColumns = nonZeroColumns;
    }





    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        Log.i(TAG,"in the setADapater");
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(String[] items) {
        _items = items;
        mSelection = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0]);
        Arrays.fill(mSelection, false);
    }





    public void setItems(List<String> items) {

        _items = items.toArray(new String[items.size()]);
        mSelection = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0]);
        Arrays.fill(mSelection, false);
    }

    public void setSelection(String[] selection) {
        for (String cell : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(cell)) {
                    mSelection[j] = true;
                }
            }
        }
    }

    public void setSelection(List<String> selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        for (String sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(sel)) {
                    mSelection[j] = true;
                }
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int index) {



        for (int i = 0; i < mSelection.length; i++) {


            mSelection[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int[] selectedIndicies) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        for (int index : selectedIndicies) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        + " is out of bounds.");
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<String>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(_items[i]);
            }
        }
        return selection;
    }

    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList<Integer>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }
}
