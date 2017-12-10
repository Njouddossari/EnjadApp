package com.example.lenovo.enjad.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.lenovo.enjad.JavaClasses.Report;
import com.example.lenovo.enjad.JavaClasses.ReportAdapter;
import com.example.lenovo.enjad.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportlistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReportAdapter adapter;

    public CheckBox selectall;
    List<Report> reportList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reportlist);

        //Hold the coolection of reports
        reportList = new ArrayList<Report>();
        adapter = new ReportAdapter(this, reportList);


        selectall = (CheckBox) findViewById(R.id.selectcard);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

       /* for (int i = 0; i < recyclerView.getChildCount(); i++) {
            selectedcard = (CheckBox)recyclerView.getChildAt(i).findViewById(R.id.selectall);
            selectedcard.setChecked(selectall.isChecked());
        }*/

        prepareAlbums();


        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void prepareAlbums() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("Report").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            /**
             * This method will be called anytime user add a report
             *
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get all reports
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                //handle each report
                for(DataSnapshot child : children){
                   // Report a = child.getValue(Report.class);
                    String emergstat = dataSnapshot.child("emerg_status").getValue(String.class);
                    String emergtype = dataSnapshot.child("Emerg_type").getValue(String.class);
                    String severity = dataSnapshot.child("severity").getValue(String.class);
                    String username = dataSnapshot.child("username").getValue(String.class);
                    Report a = new Report(severity,emergtype, emergstat, username);
                    reportList.add(a);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


        /**
         * Adding report manually for testing
         *

     Report a = new Report("عالي","حريق", "نشط", "علي");
        reportList.add(a);

        a = new Report("متوسط","سرقة", "انتهى", "علي");
        reportList.add(a);

        a = new Report("عالي","خطف", "قيد المعالجة", "علي");
        reportList.add(a);

        a = new Report("عالي","خطف", "انتهى", "علي");
        reportList.add(a);

    adapter.notifyDataSetChanged();*/
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void onCheckboxClicked(View view) {
        Intent i = new Intent(this,ReportAdapter.class);
        i.putExtra("check","true");

    }
}
