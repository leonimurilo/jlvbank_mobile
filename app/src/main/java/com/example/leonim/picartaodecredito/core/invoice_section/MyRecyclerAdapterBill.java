package com.example.leonim.picartaodecredito.core.invoice_section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leonim.picartaodecredito.R;
import com.example.leonim.picartaodecredito.dbo.Invoice;
import com.example.leonim.picartaodecredito.dbo.Release;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by leonim on 13/10/2016.
 */

public class MyRecyclerAdapterBill extends RecyclerView.Adapter {

    private ArrayList<Invoice> invoiceArrayList;
    private Context context;
    private OnReleaseInteractionListener onReleaseInteractionListener;
    private ArrayList<Integer> itemTypes;

    private static final int COMMON = 1;
    private static final int SEPARATOR = 2;

    public MyRecyclerAdapterBill(ArrayList<Invoice> invoiceArrayList, Context context, OnReleaseInteractionListener onReleaseInteractionListener){
        this.invoiceArrayList = invoiceArrayList;
        this.context = context;
        this.onReleaseInteractionListener = onReleaseInteractionListener;

        itemTypes = new ArrayList<>();

        for(int i=0;i<invoiceArrayList.size();i++){
            itemTypes.add(SEPARATOR);
            for(int j=0;j<invoiceArrayList.get(i).getReleases().size();j++){
                itemTypes.add(COMMON);
            }
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder ret = null;

        if(viewType==COMMON){
            View view = LayoutInflater.from(context).inflate(R.layout.custom_recycler_view_row, parent, false);

            MyRecyclerViewHolderBill viewHolder = new MyRecyclerViewHolderBill(view);
            viewHolder.setOnReleaseInteractionListener(onReleaseInteractionListener);

            ret = viewHolder;
        }else if(viewType==SEPARATOR){
            View view = LayoutInflater.from(context).inflate(R.layout.custom_recycler_view_separator, parent, false);
            MyRecyclerViewHolderSeparator viewHolder = new MyRecyclerViewHolderSeparator(view);

            ret = viewHolder;
        }


        return ret;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        int subtractor = 0;
        for(int i=0;i<=position;i++){
            if(getItemViewType(i)==SEPARATOR)
                subtractor++;
        }

        if(h.getClass()==MyRecyclerViewHolderBill.class){

            Log.d("CRASH",""+subtractor);
            //retirar quantidade de lancamentos anteriores alem de quantidade de trecos
            int previousReleases = 0;
            for(int i=0;i<subtractor-1;i++){
                previousReleases = invoiceArrayList.get(i).getReleases().size();
            }
            Release release =  invoiceArrayList.get(subtractor-1).getReleases().get(position-subtractor-previousReleases);


            MyRecyclerViewHolderBill holder = (MyRecyclerViewHolderBill) h;


            holder.setDescription(release.getDescription());

            holder.setDateText(new SimpleDateFormat("dd/MM/yy").format(release.getDate()));
            holder.setValueTextView(release.getValue()+"");
            holder.setReleaseId(release.getId());

        }else if(h.getClass()==MyRecyclerViewHolderSeparator.class){
            MyRecyclerViewHolderSeparator holder = (MyRecyclerViewHolderSeparator) h;

            holder.setStartDateText(new SimpleDateFormat("dd/MM/yy").format(invoiceArrayList.get(subtractor-1).getStartDate()));
            holder.setEndDateText(new SimpleDateFormat("dd/MM/yy").format(invoiceArrayList.get(subtractor-1).getEndDate()));

        }
    }

    @Override
    public int getItemViewType(int position) {
        return itemTypes.get(position);
    }

    @Override
    public int getItemCount() {
        int releasesCount = 0;

        for(Invoice i:invoiceArrayList){
            releasesCount+=i.getReleases().size();
        }

        return invoiceArrayList.size()+releasesCount;
    }
}
