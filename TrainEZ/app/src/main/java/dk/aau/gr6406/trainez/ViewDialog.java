package dk.aau.gr6406.trainez;

/*
  Created by marti on 5/5/2017.
 */
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ViewDialog {


    public void showDialog(final Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.view_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_view_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_view_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
            }
        });
        dialog.show();
    }
}