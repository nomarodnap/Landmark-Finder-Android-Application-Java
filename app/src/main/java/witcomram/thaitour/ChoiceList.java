package witcomram.thaitour;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.List;

public class ChoiceList extends DialogFragment {

    final CharSequence[] items = {"แสดงทั้งหมด","วัด โบราณสถาน และประวัติศาสตร์","ป่าเขา น้ำตก","ทะเล เกาะ และหมู่เกาะ","กิจกรรม งานวัฒนธรรม ประเพณี","ชุมชนวิถีชีวิต","ที่คุณชื่นชอบ"};
    String selection = "null";

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("เลือกประเภทของสถานที่ที่คุณต้องการ...").setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                switch (arg1) {

                    case 0:
                        selection = (String) items[arg1];
                        break;
                    case 1:
                        selection = (String) items[arg1];
                        break;
                    case 2:
                        selection = (String) items[arg1];
                        break;
                    case 3:
                        selection = (String) items[arg1];
                        break;
                    case 4:
                        selection = (String) items[arg1];
                        break;
                    case 5:
                        selection = (String) items[arg1];
                        break;
                    case 6:
                        selection = (String) items[arg1];
                        break;
                    case 7:
                        selection = (String) items[arg1];
                        break;
                    default:
                        break;
                }

            }
        }).setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.setChoice(selection);
                dialog.dismiss();

            }
        });
        return builder.create();
    }


}
