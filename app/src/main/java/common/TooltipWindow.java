package common;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bottle_caps_adminapp.R;

/**
 * Created by ashish.kumar on 19-06-2018.
 */

public class TooltipWindow {
    private static final int MSG_DISMISS_TOOLTIP = 100;
    private Context ctx;
    private PopupWindow tipWindow;
    private View contentView;
    private LayoutInflater inflater;
String[]value;
    public TooltipWindow(Context ctx,String[]value) {
        this.ctx = ctx;
        tipWindow = new PopupWindow(ctx);
this.value=value;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.tooltip_layout, null);
    }

    public void showToolTip(View anchor) {

        tipWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        tipWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);

        tipWindow.setOutsideTouchable(true);
        tipWindow.setTouchable(true);
        tipWindow.setFocusable(true);
        tipWindow.setBackgroundDrawable(new BitmapDrawable());

        tipWindow.setContentView(contentView);
        common.DetailsCustomTextView sale_saving=(common.DetailsCustomTextView)contentView.findViewById(R.id.sale_saving);
        common.DetailsCustomTextView sale_saving_title=(common.DetailsCustomTextView)contentView.findViewById(R.id.sale_saving_title);
        LinearLayout sale_saving_Layout=(LinearLayout) contentView.findViewById(R.id.sale_saving_Layout);
        common.DetailsCustomTextView case_discount=(common.DetailsCustomTextView)contentView.findViewById(R.id.case_discount);
        common.DetailsCustomTextView case_discount_title=(common.DetailsCustomTextView)contentView.findViewById(R.id.case_discount_title);
        LinearLayout case_discount_Layout=(LinearLayout) contentView.findViewById(R.id.case_discount_Layout);
        common.DetailsCustomTextView  club_saving=(common.DetailsCustomTextView)contentView.findViewById(R.id.club_saving);
        common.DetailsCustomTextView  club_saving_title=(common.DetailsCustomTextView)contentView.findViewById(R.id.club_saving_title);
        LinearLayout  club_saving_Layout=(LinearLayout) contentView.findViewById(R.id. club_saving_Layout);
        if(value[0].length()==0)
        {
            sale_saving_Layout.setVisibility(View.GONE);
        }
        if(value[1].length()==0)
        {
            case_discount_Layout.setVisibility(View.GONE);
        }
        if(value[2].length()==0)
        {
            club_saving_Layout.setVisibility(View.GONE);
        }
        sale_saving.setText(value[0]);
        case_discount.setText(value[1]);
        club_saving.setText(value[2]);
        int screen_pos[] = new int[2];
// Get location of anchor view on screen
        anchor.getLocationOnScreen(screen_pos);

// Get rect for anchor view
        Rect anchor_rect = new Rect(screen_pos[0], screen_pos[1], screen_pos[0]
                + anchor.getWidth(), screen_pos[1] + anchor.getHeight());

// Call view measure to calculate how big your view should be.
        contentView.measure(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        int contentViewHeight = contentView.getMeasuredHeight();
        int contentViewWidth = contentView.getMeasuredWidth();
// In this case , i dont need much calculation for x and y position of
// tooltip
// For cases if anchor is near screen border, you need to take care of
// direction as well
// to show left, right, above or below of anchor view
        int position_x = anchor_rect.centerX() - (contentViewWidth / 2);
        int position_y = anchor_rect.bottom - (anchor_rect.height() / 2);
        position_x=position_x-180;
        position_y=position_y-35;
        tipWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, position_x,position_y);

// send message to handler to dismiss tipWindow after X milliseconds
        handler.sendEmptyMessageDelayed(MSG_DISMISS_TOOLTIP, 6000);
    }

   public boolean isTooltipShown() {
        if (tipWindow != null && tipWindow.isShowing())
            return true;
        return false;
    }

    public void dismissTooltip() {
        if (tipWindow != null && tipWindow.isShowing())
            tipWindow.dismiss();
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_DISMISS_TOOLTIP:
                    if (tipWindow != null && tipWindow.isShowing())
                        tipWindow.dismiss();
                    break;
            }
        };
    };
}
