package subclass;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ysy on 2014/12/1.
 */
public class MarqueeTextview extends TextView {
    public MarqueeTextview(Context context) {
        super(context);
    }

    public MarqueeTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
