package islamkhsh.com.healthinpocket.ui.fragment.history;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import islamkhsh.com.healthinpocket.R;
import islamkhsh.com.healthinpocket.data.model.MeasuredItem;
import islamkhsh.com.healthinpocket.ui.activity.base.BaseFragment;
import islamkhsh.com.healthinpocket.ui.adapter.HistoryListAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends BaseFragment {

    private static String fragmentTitle;
    @BindView(R.id.history_list)
    public ListView listView;
    private HistoryViewModel historyViewModel;
    private HistoryListAdapter adapter;
    private List<MeasuredItem> measuredItemList;


    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String title) {
        HistoryFragment fragment = new HistoryFragment();
        fragmentTitle = title;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, root);
        setupListView();

        historyViewModel.getCurrentUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(@Nullable FirebaseUser firebaseUser) {
                if (firebaseUser != null)
                    historyViewModel.setDatabaseRef(firebaseUser);
            }
        });

        historyViewModel.getMeasuredItemList().observe(this, new Observer<ArrayList<MeasuredItem>>() {
            @Override
            public void onChanged(@Nullable ArrayList<MeasuredItem> measuredItems) {
                adapter = new HistoryListAdapter(getContext(), measuredItems, historyViewModel);
                listView.setAdapter(adapter);

//               measuredItemList=measuredItems;
//               adapter.notifyDataSetChanged();
            }
        });


        return root;
    }

    private void setupListView() {
        this.measuredItemList = new ArrayList<>();
        adapter = new HistoryListAdapter(getContext(), measuredItemList, historyViewModel);
        listView.setAdapter(adapter);
    }

    @Override
    public void setupViewModel() {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
    }

    @Override
    public String toString() {
        return fragmentTitle;
    }
}

