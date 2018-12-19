package islamkhsh.com.healthinpocket.ui.fragment.history;

import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import islamkhsh.com.healthinpocket.data.model.MeasuredItem;
import islamkhsh.com.healthinpocket.ui.activity.base.BaseViewModel;

/**
 * Created by ESLAM on 12/19/2018.
 */

public class HistoryViewModel extends BaseViewModel {
    private DatabaseReference mRef;
    private MutableLiveData<ArrayList<MeasuredItem>> measuredItemList;

    public HistoryViewModel() {
        this.measuredItemList = new MutableLiveData<>();
    }

    public void setDatabaseRef(FirebaseUser firebaseUser) {
        this.mRef = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
        fetchDataFromFirebase();

    }

    private void fetchDataFromFirebase() {
        this.mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    HashMap<String, MeasuredItem> results = dataSnapshot.getValue(
                            new GenericTypeIndicator<HashMap<String, MeasuredItem>>() {
                            });

                    ArrayList<MeasuredItem> temp = new ArrayList<>(results.values());
                    ArrayList<String> measuredItemListKeys = new ArrayList<>(results.keySet());

                    for (int i = 0; i < temp.size(); i++) {
                        temp.get(i).setItemKey(measuredItemListKeys.get(i));
                    }

                    Collections.sort(temp, new Comparator<MeasuredItem>() {
                        @Override
                        public int compare(MeasuredItem o1, MeasuredItem o2) {
                            return o1.getItemDate().compareTo(o2.getItemDate());
                        }
                    });

                    measuredItemList.postValue(temp);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void removeValue(String itemKey) {
        mRef.child(itemKey).removeValue();
    }

    public MutableLiveData<ArrayList<MeasuredItem>> getMeasuredItemList() {
        return measuredItemList;
    }
}
