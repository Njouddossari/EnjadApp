package com.example.lenovo.enjad.JavaClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.enjad.R;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    private Context mContext;
    private List<Report> reportList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, reportNo, reportLoc, reportLevel, reportType;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.reportType);
            count = (TextView) view.findViewById(R.id.reportstat);
            reportLevel = (TextView) view.findViewById(R.id.reportLevel);
            reportNo = (TextView) view.findViewById(R.id.reportNo);



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
        Report report = reportList.get(position);
        holder.title.setText("نوع الحالة: "+ report.getEmerg_type());
        holder.count.setText("حالة البلاغ: "+report.getEmerg_status());


        // loading report cover using Glide library


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
