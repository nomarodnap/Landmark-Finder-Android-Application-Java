package witcomram.thaitour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Item> {
    Context context;
    List<Item> tours;

    public ListAdapter(Context context, List<Item> tours) {
        super(context, android.R.id.content, tours);
        this.context = context;
        this.tours = tours;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.listitem, null);

        Item tour = tours.get(position);

        TextView tv = (TextView) view.findViewById(R.id.titleText);
        tv.setText(tour.getTitle());

        tv = (TextView) view.findViewById(R.id.cityText);
        tv.setText("จังหวัด"+tour.getCity());

        ImageView iv = (ImageView) view.findViewById(R.id.imageView1);
        int imageResource = context.getResources().getIdentifier(
                tour.getImage(), "drawable", context.getPackageName());
        if (imageResource != 0) {
            iv.setImageResource(imageResource);
        }

        return view;
    }

}