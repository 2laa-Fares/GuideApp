package com.example.a10.guideapplication.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Offer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.notificationViewHolder> {

    private List<Offer> offers;

    public NotificationAdapter(List<Offer> offers) {
        this.offers = offers;
    }

    @NonNull
    @Override
    public notificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item, viewGroup, false);
        return new notificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notificationViewHolder notificationViewHolder, final int position) {

        if (offers.get(position).getDescription() != null)
            notificationViewHolder.dealNoteTV.setText(offers.get(position).getDescription());

        if (offers.get(position).getSectionName() != null)
            notificationViewHolder.dealPlaceTV.setText(offers.get(position).getSectionName());

        Date now = Calendar.getInstance().getTime();
        if (offers.get(position).getMEndDate() != null && offers.get(position).getMEndDate().after(now))
            notificationViewHolder.dealTimeTV.setText(getCountOfDays(now, offers.get(position).getMEndDate()));
    }


    @Override
    public int getItemCount() {
        if (offers == null) {
            return 0;
        }
        return offers.size();
    }

    class notificationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dealNote)
        TextView dealNoteTV;
        @BindView(R.id.dealPlace)
        TextView dealPlaceTV;
        @BindView(R.id.dealTime)
        TextView dealTimeTV;

        private notificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String getCountOfDays(Date createdDate, Date expireDate) {

        Calendar cCal = Calendar.getInstance();
        cCal.setTime(createdDate);
        int cYear = cCal.get(Calendar.YEAR);
        int cMonth = cCal.get(Calendar.MONTH);
        int cDay = cCal.get(Calendar.DAY_OF_MONTH);

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireDate);
        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ("العرض ساري حتى " + (int) dayCount + " أيام");
    }
}
