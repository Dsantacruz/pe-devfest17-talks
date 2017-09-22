package pe.gdg.workshops.devfesttalks.presenters.details;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.text.Html;

import pe.gdg.workshops.devfesttalks.R;
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
            final String eventSubtitle = eventItem.getDate().concat(" - ")
                    .concat(eventItem.getLocation()).concat(" - ")
                    .concat(eventItem.getLevel());
            vh.getSubtitle().setText(eventSubtitle);
            final StringBuilder bodyTextBuilder = new StringBuilder();
            if (!eventItem.getSpeaker().isEmpty()) {
                StringBuilder speakersInfo = new StringBuilder();
                speakersInfo.append("<b>Speakers:</b> ");
                int pos = 0;
                for (final String speaker : eventItem.getSpeaker()) {
                    speakersInfo.append(speaker);
                    if (pos <= eventItem.getSpeaker().size() - 1) {
                        speakersInfo.append(", ");
                    }
                    pos++;
                }
                bodyTextBuilder.append(speakersInfo.toString());
            }
            bodyTextBuilder.append(String.format("<br><br>%s<br>", vh.view.getContext().getString(R.string.detail_default_description)));
            vh.getBody().setText(Html.fromHtml(bodyTextBuilder.toString()));
        }
    }
}
