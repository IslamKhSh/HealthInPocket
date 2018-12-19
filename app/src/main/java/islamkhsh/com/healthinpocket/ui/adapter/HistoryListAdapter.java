package islamkhsh.com.healthinpocket.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import islamkhsh.com.healthinpocket.R;
import islamkhsh.com.healthinpocket.common.Constants;
import islamkhsh.com.healthinpocket.data.model.MeasuredItem;
import islamkhsh.com.healthinpocket.ui.fragment.history.HistoryViewModel;


/**
 * Created by ESLAM on 6/23/2017.
 */

public class HistoryListAdapter extends ArrayAdapter<MeasuredItem> {

    @BindView(R.id.type_img)
    public ImageView imgType;
    @BindView(R.id.item_type)
    public TextView txtType;
    @BindView(R.id.item_value)
    public TextView itemValue;
    @BindView(R.id.item_date)
    public TextView itemDate;
    private Context context;
    private HistoryViewModel historyViewModel;
    private MeasuredItem currentItem;

    public HistoryListAdapter(Context context, List<MeasuredItem> measuredItems, HistoryViewModel viewModel) {
        super(context, 0, measuredItems);
        this.context = context;
        this.historyViewModel = viewModel;
    }

    @OnClick(R.id.item_menu)
    public void onMenueClick(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share_external_item:
                        String message = "Email: " + historyViewModel.getCurrentUser().getValue().getEmail() + ".\n" +
                                txtType.getText() + ": " + itemValue.getText() + ".\n" +
                                "Date: " + itemDate.getText() + ".";

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                        sendIntent.setType("text/plain");
                        context.startActivity(sendIntent);

                        break;
                    case R.id.delete_item:
                        historyViewModel.removeValue(currentItem.getItemKey());
                        break;
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null)
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);

        ButterKnife.bind(this, itemView);

        currentItem = getItem(position);


        switch (currentItem.getItemType()) {
            case Constants.HEART_RATE:
                imgType.setImageResource(R.drawable.heart_rate_ic);
                txtType.setText(context.getString(R.string.heart_rate));
                itemValue.setText(currentItem.getItemValue() + " " + context.getString(R.string.heart_rate_unit));
                break;
            case Constants.HEART_SIGNAL:
                imgType.setImageResource(R.drawable.heart_signal_ic);
                txtType.setText(context.getString(R.string.heart_signal));
                itemValue.setText(currentItem.getItemValue());
                break;
            case Constants.BLOOD_PRESSURE:
                imgType.setImageResource(R.drawable.blood_presure_ic);
                txtType.setText(context.getString(R.string.blood_pressure));
                itemValue.setText(currentItem.getItemValue() + " " + context.getString(R.string.blood_pressure_unit));
                break;
            case Constants.TEMPERATURE:
                imgType.setImageResource(R.drawable.temperature_ic);
                txtType.setText(context.getString(R.string.temperature));
                itemValue.setText(currentItem.getItemValue() + " " + context.getString(R.string.temperature_unit));
                break;
        }

        itemDate.setText(currentItem.getItemDate());


        return itemView;
    }


}
