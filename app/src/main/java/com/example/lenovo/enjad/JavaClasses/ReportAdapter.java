package com.example.lenovo.enjad.JavaClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.lenovo.enjad.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    private Context mContext;
    private List<Report> reportList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  reportNo, reportLoc, reportLevel, reportType;
        Spinner statSpinner;
        public CheckBox selectedcard;


        public MyViewHolder(View view) {
            super(view);
            reportType = (TextView) view.findViewById(R.id.reportType);
            reportLoc = (TextView) view.findViewById(R.id.reportLoc);
            reportLevel = (TextView) view.findViewById(R.id.reportLevel);
            reportNo = (TextView) view.findViewById(R.id.reportNo);
            selectedcard =(CheckBox) view.findViewById(R.id.selectcard);


            //selectedcard.setChecked(isChecked)
            statSpinner = (Spinner) view.findViewById(R.id.reportstat1);
            statSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    //to do if item selected

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });






        }
    }


    public ReportAdapter(Context mContext, List<Report> reportList) {
        this.mContext = mContext;
        this.reportList = reportList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_card, parent, false);

        return new MyViewHolder(itemView);
    }
    //retrive object from firebase to set them
    //check ig crrent user is the same is report key
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Double[] lat = new Double[1];
        final Double[] lng = new Double[1];
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        DatabaseReference users = FirebaseDatabase.getInstance().getReference("user");
        Query query = users.orderByChild(currentuser.getUid()).orderByChild("l");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                                     User user = dataSnapshot.getValue(User.class);
                                                     lat[0] = user.getLocation_lat();
                                                     lng[0] = user.getLocation_lang();
                                                 }

                                                 @Override
                                                 public void onCancelled(DatabaseError databaseError) {

                                                 }
                                             });

        Report report = reportList.get(position);
        holder.reportType.setText("نوع الحالة: "+ report.getEmerg_type());
        holder.reportLoc.setText("الموقع: "+"http://www.google.com/maps/place/"+ lat[0] +","+ lng[0]);
        holder.reportNo.setText("رقم البلاغ: "+report.getReport_id());
        holder.reportLevel.setText("المستوى: "+report.getSeverity());
        //setting the Array
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.stat_array, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.statSpinner.setAdapter(dataAdapter);




    }

    /**
     * Showing popup menu when tapping on 3 dots
     */


    /**
     * Click listener for popup menu items
     */


    @Override
    public int getItemCount() {
        return reportList.size();
    }
}
