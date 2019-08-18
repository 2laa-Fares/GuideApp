package com.example.a10.guideapplication.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Offer;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.presenter.OffersPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.OffersRepositoryImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class OffersFragment extends Fragment implements OffersListener{

    ImageView expandedImageView;
    int mShortAnimationDuration;
    AnimatorSet mCurrentAnimator;
    @BindView(R.id.menuRecyclerView)
    RecyclerView offersRecyclerView;
    @BindView(R.id.noMenuTextView)
    TextView noMenuTextView;
    @BindView(R.id.container)
    FrameLayout container;
    OffersListener listener;
    OffersPresenter presenter;
    List<Offer> offers;
    private ProgressDialog progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        ButterKnife.bind(this, view);
        listener = this;
        presenter = new OffersPresenter(listener, new OffersRepositoryImp());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.getContext());
        offersRecyclerView.setLayoutManager(manager);
        if (offers!=null){
            offersRecyclerView.setVisibility(View.VISIBLE);
            noMenuTextView.setVisibility(View.GONE);
            OffersAdapter adapter = new OffersAdapter(offers, new OffersAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(ImageView imageView) {
                    zoomImageFromThumb(imageView);
                }
            });
            offersRecyclerView.setAdapter(adapter);
        }else {
            progressBar = new ProgressDialog(App.getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("جارى التحميل ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();
            String placeType = getArguments().getString(getString(R.string.placeType));
            if (placeType.equals(getString(R.string.doctor))){
                Doctor doctor = getArguments().getParcelable(getString(R.string.place));
                presenter.getSectionOffers(doctor.getID(), 2);
            }else {
                Section section = getArguments().getParcelable(getString(R.string.place));
                presenter.getSectionOffers(section.getID(), 1);
            }
        }
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        return view;
    }

    @Override
    public void sectionOffers(List<Offer> offers) {
        progressBar.dismiss();
        this.offers = offers;
        if (offers!=null && offers.size()!=0){
            offersRecyclerView.setVisibility(View.VISIBLE);
            noMenuTextView.setVisibility(View.GONE);
            OffersAdapter adapter = new OffersAdapter(offers, new OffersAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(ImageView imageView) {
                    zoomImageFromThumb(imageView);
                }
            });
            offersRecyclerView.setAdapter(adapter);
        }else {
            offersRecyclerView.setVisibility(View.GONE);
            noMenuTextView.setVisibility(View.VISIBLE);
            noMenuTextView.setText("لا يوجد عروض");
        }
    }

    @Override
    public void allOffers(List<Offer> offers) {

    }

    @Override
    public void offerAdded(Offer offer) {

    }

    @Override
    public void offerUpdated(Boolean b) {

    }

    @Override
    public void offerDeleted(Boolean b) {

    }


    private void zoomImageFromThumb(final ImageView thumbView) {
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        final LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.image_popup, null);
        expandedImageView = layout.findViewById(R.id.expandedImageView);
        expandedImageView.setImageBitmap(((BitmapDrawable)thumbView.getDrawable()).getBitmap());
        final PopupWindow pw = new PopupWindow(layout, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT, true);
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

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

        layout.setPivotX(0f);
        layout.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(layout, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(layout, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(layout, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(layout,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(layout, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(layout,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(layout,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(layout,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        pw.dismiss();
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });

    }
}
