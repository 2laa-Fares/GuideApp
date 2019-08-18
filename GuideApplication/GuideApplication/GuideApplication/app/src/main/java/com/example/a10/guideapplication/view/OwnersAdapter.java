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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Branch;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.BranchPresenter;
import com.example.a10.guideapplication.presenter.UserPresenter;
import com.example.a10.guideapplication.utils.RoundedCornerImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class OwnersAdapter extends RecyclerView.Adapter<OwnersAdapter.OwnersViewHolder> {

    List<User> users;
    UserPresenter userPresenter;

    OwnersAdapter(List<User> users, UserPresenter userPresenter){
        this.users = users;
        this.userPresenter = userPresenter;
    }

    @NonNull
    @Override
    public OwnersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.owner_item, viewGroup, false);
        return new OwnersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OwnersViewHolder holder, final int i) {

        holder.ownerName.setText(users.get(i).getUserName());
        holder.jobTextView.setText(users.get(i).getJob());
        if(users.get(i).getImage() != null && !users.get(i).getImage().equals("")) {
            byte[] decodedString = Base64.decode(users.get(i).getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            decodedByte = RoundedCornerImage.getRoundedCornerBitmap(decodedByte);
            holder.ownerImage.setImageBitmap(decodedByte);
        }

        holder.ownerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.ownerMenu,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (users==null)
            return 0;
        return users.size();
    }

    public class OwnersViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ownerImage)
        de.hdodenhof.circleimageview.CircleImageView ownerImage;
        @BindView(R.id.menuOwner)
        ImageButton ownerMenu;
        @BindView(R.id.jobTextView)
        TextView jobTextView;
        @BindView(R.id.ownerName)
        TextView ownerName;
        public OwnersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.edit_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;
        public MyMenuItemClickListener(int positon) {
            this.position=positon;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.menu_preview:
                    // Reload current fragment
                    Fragment frg = null;
                    frg = getSupportFragmentManager().findFragmentByTag("Your_Fragment_TAG");
                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();
                    return true;
                case R.id.menu_edit:
                    mDataSet.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,mDataSet.size());
                    Toast.makeText(MainActivity.context, "Done for now", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_delete:
                    mySharedPreferences.deletePrefs(Constants.REMOVE_CTAGURY,MainActivity.context);
                default:
            }
            return false;
        }
    }
}
