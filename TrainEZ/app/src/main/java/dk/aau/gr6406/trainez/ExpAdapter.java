package dk.aau.gr6406.trainez;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ExpAdapter extends BaseExpandableListAdapter {

    private final String TAG = " dk.aau.trainez";
    private Context context;
    private static String groupelements[];
    public static Exercise childelements[][];

    private boolean mHighlightedPositions[] = {false, false, false, false, false, false};


    public ExpAdapter(Context context, String[] groupelements, Exercise[][] childelements) {
        this.context = context;
        this.groupelements = groupelements;
        this.childelements = childelements;


      //  updateCategories();


        Log.i(TAG, "Adapter created. (Expandend viewlist)");
    }

    private void updateCategories() {
        int k = 0;
        int n = 1;
        SharedPreferences sharedPref2 = context.getSharedPreferences("firstTimeCategories", Context.MODE_PRIVATE);
        for (String exercise : groupelements) {
            mHighlightedPositions[k] = sharedPref2.getBoolean("cat" + n, true);

            if (mHighlightedPositions[k]) {
                setDefaultVal(k);
            }
            Log.i(TAG, "retrived from sp:" + String.valueOf(mHighlightedPositions[k]));
            k++;
            n++;
        }
        notifyDataSetChanged();

        int n1 = 1;
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("firstTimeCategories", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        for (String exercise : groupelements) {
            editor2.putBoolean("cat" + n1, false);
            n1++;
        }
        editor2.apply();

    }

    private boolean validateRepetition(int repetitions) {
        if (repetitions <= 0) {
            Toast.makeText(context, "Repetitions must be greater than 0!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childelements[groupPosition].length;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.elv_child, null);
        }

        TextView exerciseName = (TextView) convertView.findViewById(R.id.excName);
        ImageButton addRepetition = (ImageButton) convertView.findViewById(R.id.plus_button_compose);
        ImageButton subtractRepetetion = (ImageButton) convertView.findViewById(R.id.minus_button_compose);
        ImageButton videoThumbnail = (ImageButton) convertView.findViewById(R.id.video_thumb_compose);
        TextView repetitions = (TextView) convertView.findViewById(R.id.repetition_text_compose);

        addRepetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, String.valueOf(childPosition) + " " + groupPosition);
                int getRepetitions = childelements[groupPosition][childPosition].getRepetitions();
                childelements[groupPosition][childPosition].setRepetitions(getRepetitions + 1);
                notifyDataSetChanged();
            }

        });

        subtractRepetetion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, String.valueOf(childPosition) + " " + groupPosition);
                int getRepetitions = childelements[groupPosition][childPosition].getRepetitions();
                if (validateRepetition(getRepetitions)) {
                    childelements[groupPosition][childPosition].setRepetitions(getRepetitions - 1);
                }
                notifyDataSetChanged();
            }
        });

        videoThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayer.class);
                // send the information about the name of the video/picture to the new activity. The key
                // is the first argumentment and the information is the second argument.
                intent.putExtra("position_key", childelements[groupPosition][childPosition]);
                context.startActivity(intent);
            }
        });

        videoThumbnail.setImageResource(convertView.getResources().getIdentifier(childelements[groupPosition][childPosition].getVideoPath(), "drawable", "dk.aau.gr6406.trainez"));
        exerciseName.setText(childelements[groupPosition][childPosition].getExcName());
        repetitions.setText(String.valueOf(childelements[groupPosition][childPosition].getRepetitions()));

        return convertView;
    }


    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return groupelements.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.elv_group, null);
            //final ImageButton ibDeleteRcipe = (ImageButton) convertView.findViewById(R.id.ibDeleteRecipe);

        }

        TextView tvItem = (TextView) convertView.findViewById(R.id.tvRecipeName);
        final ImageButton categoryButton = (ImageButton) convertView.findViewById(R.id.ibDeleteRecipe);
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "%%%%%%" + String.valueOf(groupPosition));
                if (categoryToggle(groupPosition, categoryButton)) {
                    setDefaultVal(groupPosition);
                }
            }
        });
        categoryButton.setFocusable(false);
        tvItem.setText(groupelements[groupPosition]);

        categoryLock(groupPosition, categoryButton);

        return convertView;

    }

    private boolean categoryToggle(int groupPosition, ImageButton img) {
        if (mHighlightedPositions[groupPosition]) {
            img.setImageResource(android.R.drawable.btn_star_big_off);
            mHighlightedPositions[groupPosition] = false;
            return false;
        } else {

            img.setImageResource(android.R.drawable.btn_star_big_on);
            mHighlightedPositions[groupPosition] = true;
            return true;
        }
    }

    private void categoryLock(int groupPosition, ImageButton img) {
        if (mHighlightedPositions[groupPosition]) {
            img.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            img.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    private void setDefaultVal(int groupPosition) {
        for (int i = 0; i < childelements[groupPosition].length; i++) {
            int defValue = childelements[groupPosition][i].getDefaultVal();
            childelements[groupPosition][i].setRepetitions(defValue);
        }
        notifyDataSetChanged();
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}