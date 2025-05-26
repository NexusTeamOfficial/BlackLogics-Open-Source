package com.nexusteam.internal.os.layouteditor.custom;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.view.*;
import android.widget.*;
//import com.nexusteam.internal.os.layouteditor.*;
import com.besome.blacklogics.*;
import com.nexusteam.internal.os.layouteditor.util.*;

public class MyDragWidget
extends LinearLayout {
    public MyDragWidget(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int n = this.getId();
        int n2 = TheBlockLogicsUtil.getDip(this.getContext(), 18);
        int n3 = TheBlockLogicsUtil.getDip(this.getContext(), 18);
        int n4 = TheBlockLogicsUtil.getDip(this.getContext(), 0);
        int n5 = TheBlockLogicsUtil.getDip(this.getContext(), 2);
        GradientDrawable gradientDrawable = TheBlockLogicsUtil.getGradient(this.getContext(), 1, Color.parseColor((String)"#DDDDDD"), -1);
        gradientDrawable.setCornerRadius((float)n4);
        this.setElevation((float)2);
        this.setGravity(16); 
        this.setPadding(n5, n5, n5, n5);
        this.setBackground((Drawable)gradientDrawable);
        ImageView imageView = new ImageView(context);
        imageView.setPadding(n5, n5, n5, n5);
        this.addView((View)imageView, n2, n3);
        TextView textView = new TextView(context);
        textView.setTextColor(Color.parseColor((String)"#555555"));
        textView.setTextSize(2, (float)12);
        textView.setPadding(n5, n5, n5, n5);
        textView.setSingleLine();
        this.addView((View)textView);

        if (this.getId() == R.id.widget_textview) {
            imageView.setImageResource(R.drawable.widget_text_view);
            textView.setText((CharSequence)"TextView");
        }
        if (this.getId() == R.id.widget_edit_text) {
            imageView.setImageResource(R.drawable.widget_edit_text);
            textView.setText((CharSequence)"EditText");
        }
		if (this.getId() == R.id.widget_checkbox) {
			imageView.setImageResource(R.drawable.widget_check_box);
			textView.setText((CharSequence)"CheckBox");
		}
        if (this.getId() == R.id.widget_switch) {
			imageView.setImageResource(R.drawable.widget_switch);
			textView.setText((CharSequence)"Switch");
		}
		if (this.getId() == R.id.widget_button) {
            imageView.setImageResource(R.drawable.widget_button);
            textView.setText((CharSequence)"Button");
        }
		if (this.getId() == R.id.widget_image_view) {
            imageView.setImageResource(R.drawable.widget_image_view);
            textView.setText((CharSequence)"ImageView");
        }
        if (this.getId() == R.id.widget_radio_button) {
            imageView.setImageResource(R.drawable.widget_radio_button);
            textView.setText((CharSequence)"RadioButton");
        }
        if (this.getId() == R.id.widget_seek_bar) {
            imageView.setImageResource(R.drawable.widget_seek_bar);
            textView.setText((CharSequence)"SeekBar");
        }
        if (this.getId() == R.id.widget_progress_bar) {
            imageView.setImageResource(R.drawable.widget_progress_bar);
            textView.setText((CharSequence)"ProgressBar");
        }
		if (this.getId() == R.id.widget_video_view) {
            imageView.setImageResource(R.drawable.widget_video_view);
            textView.setText((CharSequence)"VideoView");
        }
    	if (this.getId() == R.id.widget_linear) {
	   	imageView.setImageResource(R.drawable.widget_linear_horizontal);
	   	textView.setText((CharSequence)"LinearLayout");
	    }
        if (this.getId() == R.id.widget_frame) {
	   	imageView.setImageResource(R.drawable.widget_linear_horizontal);
	   	textView.setText((CharSequence)"FrameLayout");
	    }
        if (this.getId() == R.id.viewpager) {
			imageView.setImageResource(R.drawable.widget_view_pager);
			textView.setText((CharSequence)"ViewPager");
		}
        if (this.getId() == R.id.widget_video_view) {
	   	imageView.setImageResource(R.drawable.item_video_view);
		   textView.setText((CharSequence)"VideoView");
	    }
        if (this.getId() == R.id.widget_web_view) {
	   	imageView.setImageResource(R.drawable.widget_web_view);
		   textView.setText((CharSequence)"WebView");
	    }
        if (this.getId() == R.id.widget_list_view) {
	   	imageView.setImageResource(R.drawable.widget_list_view);
		   textView.setText((CharSequence)"ListView");
	    }
        if (this.getId() == R.id.widget_search_view) {
	   	imageView.setImageResource(R.drawable.search_icon_grey);
		   textView.setText((CharSequence)"SearchView");
	    }
        if (this.getId() == R.id.widget_rating_view) {
	   	imageView.setImageResource(R.drawable.star_blank);
		   textView.setText((CharSequence)"RatingBar");
	    }
        if (this.getId() == R.id.widget_circle_image_view) {
	   	imageView.setImageResource(R.drawable.widget_circle_image);
		   textView.setText((CharSequence)"CircleImageView");
	    }
        if (this.getId() == R.id.widget_digital_clock) {
	   	imageView.setImageResource(R.drawable.widget_timer);
		   textView.setText((CharSequence)"DigitalClock");
	    }
        if (this.getId() == R.id.widget_time_picker) {
	   	imageView.setImageResource(R.drawable.widget_time_picker_dialog);
		   textView.setText((CharSequence)"TimePicker");
	    }
        if (this.getId() == R.id.widget_scrollview) {
	   	imageView.setImageResource(R.drawable.widget_scrollview);
		   textView.setText((CharSequence)"Scroll(V)");
	    }
        if (this.getId() == R.id.widget_horizontalscrollview) {
	   	imageView.setImageResource(R.drawable.widget_horizontalscrollview);
		   textView.setText((CharSequence)"Scroll(H)");
	    }
 }
}


