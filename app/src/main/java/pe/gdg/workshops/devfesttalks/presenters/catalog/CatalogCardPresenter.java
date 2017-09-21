package pe.gdg.workshops.devfesttalks.presenters.catalog;

import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.View;
import android.view.ViewGroup;

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
            mCardView.setTitleText(eventItem.getTitle());
            mCardView.setContentText(eventItem.getLocation().concat(", ").concat(eventItem.getType()));
            mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            mCardView.setMainImage(mCardView.getContext().getDrawable(R.drawable.catalog_default_card));
        }
    }
}
