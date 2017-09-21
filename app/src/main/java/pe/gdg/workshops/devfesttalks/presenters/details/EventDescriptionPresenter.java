package pe.gdg.workshops.devfesttalks.presenters.details;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import pe.gdg.workshops.devfesttalks.data.models.Event;

/**
 * The type Event description presenter.
 */
public class EventDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder vh, Object item) {
        if (item instanceof Event) {
            final Event eventItem = (Event) item;
            vh.getTitle().setText(eventItem.getTitle());
            vh.getSubtitle().setText(eventItem.getLocation().concat(", ").concat(eventItem.getLevel()));
            final StringBuilder bodyTextBuilder = new StringBuilder();
            if (!eventItem.getSpeaker().isEmpty()) {
                StringBuilder speakersInfo = new StringBuilder();
                speakersInfo.append("Speakers: ");
                int pos = 0;
                for (final String speaker : eventItem.getSpeaker()) {
                    speakersInfo.append(speaker);
                    if (pos < eventItem.getSpeaker().size() - 1) {
                        speakersInfo.append(", ");
                    }

                }
                bodyTextBuilder.append(speakersInfo.toString());
            }
            bodyTextBuilder.append("");
            vh.getBody().setText(bodyTextBuilder.toString());
        }
    }
}
