package com.example.a10.guideapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Branch;
import com.example.a10.guideapplication.presenter.BranchPresenter;
import com.example.a10.guideapplication.utils.RoundedCornerImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BranchesAdapter extends RecyclerView.Adapter<BranchesAdapter.BranchesViewHolder> {

    BranchPresenter presenter;
    List<Branch> branches;
    String placeImage;
    String placeType;

    BranchesAdapter(List<Branch> branches, String placeImage, String placeType, BranchPresenter presenter){
        this.branches = branches;
        this.placeImage = placeImage;
        this.placeType = placeType;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public BranchesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.branch_item, viewGroup, false);
        return new BranchesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BranchesViewHolder holder, final int i) {

        if(presenter == null)
        {
            holder.editBranch.setVisibility(View.VISIBLE);
            holder.deleteBranch.setVisibility(View.VISIBLE);
            holder.preview.setVisibility(View.GONE);

            holder.deleteBranch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.delete_popup, null);
                    final Button deleteButton = layout.findViewById(R.id.deleteButton);
                    final Button cancleButton = layout.findViewById(R.id.cancleButton);
                    final PopupWindow pw = new PopupWindow(layout, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //your deleting code
                            presenter.deleteBranch(branches.get(i).getID());

                            branches.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeChanged(i,branches.size());

                            pw.dismiss();
                        }
                    });
                    cancleButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pw.dismiss();
                        }
                    });
                    pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    pw.setTouchInterceptor(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                pw.dismiss();
                                return true;
                            }
                            return false;
                        }
                    });
                    pw.setOutsideTouchable(true);
                    pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                }
            });

            holder.editBranch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(App.getContext(), EditBranchActivity.class);
                    intent.putExtra(App.getContext().getString(R.string.branch), branches.get(holder.getAdapterPosition()));
                    App.getContext().startActivity(intent);
                }
            });
        }

        if (branches.get(i).getName()!=null)
            holder.branchName.setText(branches.get(i).getName());
        if (branches.get(i).getAddress()!=null)
            holder.locationTextView.setText(branches.get(i).getAddress());
        byte[] decodedString = Base64.decode(placeImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
        decodedByte = RoundedCornerImage.getRoundedCornerBitmap(decodedByte);
        holder.branchImage.setImageBitmap(decodedByte);
        holder.branchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getContext(), BranchActivity.class);
                intent.putExtra(App.getContext().getString(R.string.branch), branches.get(holder.getAdapterPosition()));
                intent.putExtra(App.getContext().getString(R.string.placeType), placeType);
                intent.putExtra(App.getContext().getString(R.string.placeImage), placeImage);
                if (presenter==null){
                    intent.putExtra(App.getContext().getString(R.string.edit), true);
                }
                ((Activity)App.getContext()).finish();
                App.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (branches==null)
            return 0;
        return branches.size();
    }

    public class BranchesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.branchImage)
        ImageView branchImage;
        @BindView(R.id.branchName)
        TextView branchName;
        @BindView(R.id.locationTextView)
        TextView locationTextView;
        @BindView(R.id.container)
        ConstraintLayout container;
        @BindView(R.id.imageView3)
        ImageView preview;
        @BindView(R.id.editBranch)
        ImageButton editBranch;
        @BindView(R.id.deleteBranch)
        ImageButton deleteBranch;
        public BranchesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
