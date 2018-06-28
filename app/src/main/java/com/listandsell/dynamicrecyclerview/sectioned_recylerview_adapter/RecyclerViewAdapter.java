package com.listandsell.dynamicrecyclerview.sectioned_recylerview_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.listandsell.dynamicrecyclerview.MainActivity;
import com.listandsell.dynamicrecyclerview.R;
import com.listandsell.dynamicrecyclerview.model_class.Products;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

    ///////// dynamic recycler view with headers
    public class RecyclerViewAdapter extends StatelessSection {

        Context context;
        String title;
        List<Products> list;

        public RecyclerViewAdapter(Context context, String title, List<Products> list) {
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.section_ex1_item)
                    .headerResourceId(R.layout.section_ex1_header)
                    .build());

            this.title = title;
            this.list = list;
            this.context = context;
        }

        @Override
        public int getContentItemsTotal() {

            System.out.println("ListSize : "+list.size());
            return list.size();
        }


        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            //////// return a custom instance of ViewHolder for the items of this section

            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {


            // bind your view here
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;



            Products products = list.get(position);

            itemHolder.tvItem.setText(products.getProduct_code());
            Glide.with(context).load(products.getProduct_image().replaceAll("localhost:8000","192.168.2.1:81")).into(itemHolder.imgItem);

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, (context.sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition())), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            //////// return a custom instance of ViewHolder for the header of this section
            return new HeaderViewHolder(view);
        }


        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

            // bind your header view here
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

                headerHolder.tvTitle.setText(title);

        }

        ///// creating class for Items

        class ItemViewHolder extends RecyclerView.ViewHolder{

            private final View rootView;
            private final ImageView imgItem;
            private final TextView tvItem;

            ItemViewHolder(View view) {
                super(view);

                rootView = view;
                imgItem = (ImageView) view.findViewById(R.id.imgItem);
                tvItem = (TextView) view.findViewById(R.id.tvItem);
            }
        }

        ///// creating class for header
        class HeaderViewHolder extends RecyclerView.ViewHolder{

            private final TextView tvTitle;

            HeaderViewHolder(View view) {
                super(view);

                tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            }
        }


    }

