package pe.gdg.workshops.devfesttalks.presenters.catalog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import pe.gdg.workshops.devfesttalks.R;
import pe.gdg.workshops.devfesttalks.data.models.Event;

/**
 *
 */
public class CatalogCardPresenter extends Presenter {

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ImageCardView cardView = new ImageCardView(parent.getContext());
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(parent.getContext().getResources().getColor(R.color.color_app_fastlane_background));
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        final Event eventItem = (Event) item;
        holder.bind(eventItem);
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        /*TODO onUnbindViewHolder*/
    }

    static class ViewHolder extends Presenter.ViewHolder {
        private final int CARD_WIDTH = 313;
        private final int CARD_HEIGHT = 176;
        private ImageCardView mCardView;

        ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView) view;
        }

        void bind(@NonNull Event eventItem) {
            Context mCardViewContext = mCardView.getContext();

            mCardView.setTitleText(eventItem.getTitle());
            mCardView.setContentText(eventItem.getLocation().concat(", ").concat(eventItem.getLevel()));
            mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            final int areaBackgroundColor = eventItem.getType().equalsIgnoreCase("talk")
                    ? R.color.color_app_catalog_item_area_bg : R.color.color_app_catalog_item_area_bg_alt;

            mCardView.setInfoAreaBackgroundColor(ContextCompat.getColor(mCardViewContext, areaBackgroundColor));

            /*Cargar imagen con Glide */
            Glide.with(mCardViewContext)
                    .load(eventItem.getCardImage())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            mCardView.setMainImage(resource);
                        }
                    });
        }
    }
}
