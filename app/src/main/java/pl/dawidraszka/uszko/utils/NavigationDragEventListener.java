package pl.dawidraszka.uszko.utils;

import android.graphics.Typeface;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pl.dawidraszka.uszko.R;
import pl.dawidraszka.uszko.MainActivity;

public class NavigationDragEventListener implements View.OnDragListener {

    MainActivity activity;
    ImageView shadowImageView;

    public NavigationDragEventListener(MainActivity activity, ImageView shadowImageView) {
        this.activity = activity;
        this.shadowImageView = shadowImageView;
    }

    // This is the method that the system calls when it dispatches a drag event to the
    // listener.
    public boolean onDrag(View v, DragEvent event) {

        final int action = event.getAction();

        TextView view = (TextView) v;

        // Handles each of the expected events
        switch (action) {

            case DragEvent.ACTION_DRAG_STARTED:

                v.setAlpha(1f);
                v.invalidate();

                shadowImageView.setImageResource(R.color.shadow);

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return true;

            case DragEvent.ACTION_DRAG_ENTERED:

                view.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                view.setBackground(activity.getResources().getDrawable(R.drawable.rounded_rectangle_sender));
                v.invalidate();

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                view.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                view.setBackground(activity.getResources().getDrawable(R.drawable.rounded_rectangle_receiver));
                v.invalidate();

                return true;

            case DragEvent.ACTION_DROP:

                activity.sendMessage(view.getText().toString(), true);

                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:

                v.setAlpha(0f);
                v.invalidate();
                shadowImageView.setImageResource(R.color.transparent);
                view.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                view.setBackground(activity.getResources().getDrawable(R.drawable.rounded_rectangle_receiver));

                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }
}