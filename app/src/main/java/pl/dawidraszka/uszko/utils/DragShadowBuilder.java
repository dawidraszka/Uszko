package pl.dawidraszka.uszko.utils;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import pl.dawidraszka.uszko.R;

public class DragShadowBuilder extends View.DragShadowBuilder {

    private static Drawable shadow;

    // Defines the constructor for myDragShadowBuilder
    public DragShadowBuilder(View v) {

        // Stores the View parameter passed to myDragShadowBuilder.
        super(v);

        shadow = v.getContext().getDrawable(R.drawable.ic_circle_shadow);
    }

    // Defines a callback that sends the drag shadow dimensions and touch point back to the
    // system.
    @Override
    public void onProvideShadowMetrics (Point size, Point touch) {

        int width = shadow.getIntrinsicWidth();
        int height = shadow.getIntrinsicHeight();

        shadow.setBounds(0, 0, width, height);
        size.set(width, height);
        touch.set(width / 2, height / 2);
    }

    // Defines a callback that draws the drag shadow in a Canvas that the system constructs
    // from the dimensions passed in onProvideShadowMetrics().
    @Override
    public void onDrawShadow(Canvas canvas) {

        shadow.draw(canvas);
    }
}
