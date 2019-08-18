package com.example.a10.guideapplication.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
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
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.User;
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

        if(users.get(i).getCategory().equals(App.getContext().getString(R.string.hotel))) holder.responsibleTextView.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.hotels_grey, 0, 0, 0);
        else if(users.get(i).getCategory().equals(App.getContext().getString(R.string.coffee))) holder.responsibleTextView.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.coffee_grey, 0, 0, 0);
        else if(users.get(i).getCategory().equals(App.getContext().getString(R.string.doctor))) holder.responsibleTextView.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.doctor_grey, 0, 0, 0);
        else if(users.get(i).getCategory().equals(App.getContext().getString(R.string.store))) holder.responsibleTextView.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.shop_grey, 0, 0, 0);

        holder.ownerName.setText(users.get(i).getUserName());

        if(users.get(i).getJob() != null && !users.get(i).getJob().equals("")) {
            holder.jobTextView.setVisibility(View.VISIBLE);
            holder.jobTextView.setText(users.get(i).getJob());
        }

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
        if (users !=null && users.size() > 0)
            return users.size();
        return 0;
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
        @BindView(R.id.responsibleTextView)
        TextView responsibleTextView;
        public OwnersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.users_edit_menu, popup.getMenu());
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
                    Intent intent = new Intent(App.getContext(), OwnerPlacesActivity.class);
                    intent.putExtra(App.getContext().getString(R.string.place), users.get(position));
                    App.getContext().startActivity(intent);
                    return true;

                case R.id.menu_edit:
                    Intent registrationIntent = new Intent(App.getContext(), RegistrationActivity.class);
                    registrationIntent.putExtra("EditableUser", users.get(position));
                    App.getContext().startActivity(registrationIntent);
                    return true;

                case R.id.menu_delete:
                    final LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.delete_popup, null);
                    final Button deleteSectionButton = layout.findViewById(R.id.deleteSectionsButton);
                    final Button deleteButton = layout.findViewById(R.id.deleteButton);
                    final Button cancleButton = layout.findViewById(R.id.cancleButton);
                    final PopupWindow pw = new PopupWindow(layout, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
                    deleteSectionButton.setVisibility(View.VISIBLE);

                    if(users.get(position).getCategory().equals(App.getContext().getString(R.string.doctor))) deleteSectionButton.setText("حذف المستخدم مع جميع الأطباء الذى قام بتسجيلهم");
                    else if(users.get(position).getCategory().equals(App.getContext().getString(R.string.hotel))) deleteSectionButton.setText("حذف المستخدم مع جميع الفنادق الذى قام بتسجيلهم");
                    else if(users.get(position).getCategory().equals(App.getContext().getString(R.string.coffee))) deleteSectionButton.setText("حذف المستخدم مع جميع المطاعم الذى قام بتسجيلهم");
                    else if(users.get(position).getCategory().equals(App.getContext().getString(R.string.store))) deleteSectionButton.setText("حذف المستخدم مع جميع المتاجر الذى قام بتسجيلهم");

                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //your deleting code
                            userPresenter.deleteUser(users.get(position).getID(), false);

                            users.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,users.size());

                            pw.dismiss();
                        }
                    });
                    deleteSectionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //your deleting code
                            userPresenter.deleteUser(users.get(position).getID(), true);

                            users.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,users.size());

                            pw.dismiss();
                        }
                    });
                    cancleButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pw.dismiss();
                        }
                    });

                    float density = App.getContext().getResources().getDisplayMetrics().density;
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
                    return true;
                default:
            }
            return false;
        }
    }
}
