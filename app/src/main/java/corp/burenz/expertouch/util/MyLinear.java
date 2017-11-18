package corp.burenz.expertouch.util;

/**
 * Created by xperTouch on 11/28/2016.
 */

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by xperTouch on 10/16/2016.
 */

public class MyLinear extends CoordinatorLayout.Behavior<View> {
    private int toolbarHeight;

    public MyLinear(Context context, AttributeSet attrs) {
        super();
        this.toolbarHeight = Utils.getToolbarHeight(context);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        boolean returnValue = super.onDependentViewChanged(parent, child, dependency);

        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = child.getHeight() + fabBottomMargin;
            float ratio = (float)dependency.getY()/(float)toolbarHeight;
            child.setTranslationY(-distanceToScroll * ratio);
        }
        return returnValue;


    }
}
