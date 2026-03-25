package com.nexusteam.internal;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.*;
import androidx.core.content.FileProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.blacklogics.R;
import java.io.File;
import java.util.ArrayList;

public class gr extends RelativeLayout implements View.OnClickListener {
    private String f226a, b, c;
    private boolean d=false;
    private TextView e,f;
    private ImageView g,h;
    private RadioGroup i;
    public RadioButton selectedButton;
    private LinearLayout j;
    private View k,l;
    private int m;
    private gi n;

    public gr(Context context, boolean z, String str, boolean z2){
        super(context);
        f226a=str;
        init(context,z,z2);
    }

    private void init(Context c, boolean z, boolean z2){
        kp.a(c,this,R.layout.property_resource_item);
        e=findViewById(R.id.tv_name);
        f=findViewById(R.id.tv_value);
        g=findViewById(R.id.view_image);
        h=findViewById(R.id.img_left_icon);
        k=findViewById(R.id.property_item);
        l=findViewById(R.id.property_menu_item);
        d=z2;
        if(z){
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public void setOrientationItem(int i2){
        k.setVisibility(i2==0?GONE:VISIBLE);
        l.setVisibility(i2==0?VISIBLE:GONE);
    }

    public void setKey(String str){
        b=str;
        int id=getResources().getIdentifier(str,"string",getContext().getPackageName());
        if(id>0){
            e.setText(kq.a().a(getResources(),id));
            if("property_image".equals(str)) m=R.drawable.ic_picture_48dp;
            else if("property_background_resource".equals(str)) m=R.drawable.variation_48;
            if(l.getVisibility()==VISIBLE){
                ((ImageView)findViewById(R.id.img_icon)).setImageResource(m);
                ((TextView)findViewById(R.id.tv_title)).setText(kq.a().a(getContext(),id));
            } else h.setImageResource(m);
        }
    }

    public String getKey(){return b;}

    public void setValue(String str){
        Uri uri;
        c=str;
        if(str==null||"NONE".equalsIgnoreCase(str)){
            f.setText("NONE");
            g.setImageDrawable(null);
            g.setBackgroundColor(0xFFFFFF);
            return;
        }
        f.setText(str);
        if(ma.c(f226a).a(str)==ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE||"default_image".equals(str)){
            g.setImageResource(getContext().getResources().getIdentifier(str,"drawable",getContext().getPackageName()));
            return;
        }
        File f1=new File(ma.c(f226a).b(str));
        if(f1.exists()){
            uri=Build.VERSION.SDK_INT>=24?FileProvider.getUriForFile(getContext(),getContext().getPackageName()+".provider",f1):Uri.fromFile(f1);
            Glide.with(getContext()).load(uri).signature(mb.b()).error(R.drawable.ic_remove_grey600_24dp).into(g);
        } else g.setImageResource(getContext().getResources().getIdentifier(str,"drawable",getContext().getPackageName()));
    }

    public String getValue(){return c;}
    public void setOnPropertyValueChangeListener(gi giVar){n=giVar;}

    @Override public void onClick(View v){if(!ki.a()) showDialog();}

    private void showDialog(){
        final com.nexusteam.internal.kd dialog=new com.nexusteam.internal.kd((Activity)getContext());
        dialog.a(e.getText().toString());
        dialog.a(m);
        View view=kp.a(getContext(),R.layout.property_popup_selector_color);
        final ScrollView scrollView=view.findViewById(R.id.scroll_view);
        i=view.findViewById(R.id.rg);
        j=view.findViewById(R.id.content);
        ArrayList<String> items=ma.c(f226a).m();
        if(ff.a(f226a)||ff.b(f226a)) items.add(0,d?"default_image":"NONE");
        selectedButton=null;

        for(final String item:items){
            final RadioButton rb=new RadioButton(getContext());
            rb.setText("");
            rb.setTag(item);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-2,(int)(kp.a(getContext(),1f)*60));
            rb.setGravity(19);
            rb.setLayoutParams(lp);
            i.addView(rb);
            if(item.equals(c)){rb.setChecked(true); selectedButton=rb;}

            final LinearLayout entry=new LinearLayout(getContext());
            entry.setLayoutParams(new LinearLayout.LayoutParams(-1,(int)(60*kp.a(getContext(),1f))));
            entry.setGravity(19);
            entry.setOrientation(0);

            TextView tv=new TextView(getContext());
            LinearLayout.LayoutParams tvlp=new LinearLayout.LayoutParams(0,-2,1f);
            tvlp.rightMargin=(int)(8*kp.a(getContext(),1f));
            tv.setLayoutParams(tvlp);
            tv.setText(item);
            entry.addView(tv);

            ImageView iv=new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int sz=(int)(48*kp.a(getContext(),1f));
            iv.setLayoutParams(new LinearLayout.LayoutParams(sz,sz));

            if(!"NONE".equalsIgnoreCase(item)){
                if(ff.a(f226a)||ff.b(f226a)||"default_image".equals(item)){
                    iv.setImageResource(getContext().getResources().getIdentifier(item,"drawable",getContext().getPackageName()));
                } else{
                    File file=new File(ma.c(f226a).b(item));
                    if(file.exists()){
                        Uri uri=Build.VERSION.SDK_INT>=24?FileProvider.getUriForFile(getContext(),getContext().getPackageName()+".provider",file):Uri.fromFile(file);
                        Glide.with(getContext()).load(uri).signature(mb.b()).error(R.drawable.ic_remove_grey600_24dp).into(iv);
                    } else iv.setImageResource(getContext().getResources().getIdentifier(item,"drawable",getContext().getPackageName()));
                }
                iv.setBackgroundResource(R.drawable.bg_outline);
            } else iv.setBackgroundResource(R.drawable.bg_outline);
            entry.addView(iv);


            entry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rb.setChecked(true);
                    c=item;
                }
            });

            j.addView(entry);
        }

        if(selectedButton==null && i.getChildCount()>0){
            selectedButton=(RadioButton)i.getChildAt(0);
            selectedButton.setChecked(true);
        }

        dialog.a(view);


        dialog.a(kq.a().a(getContext(),R.string.common_word_select), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e.setText(c);
                dialog.dismiss();
            }
        });


        dialog.b(kq.a().a(getContext(),R.string.common_word_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.setOnShowListener(new android.content.DialogInterface.OnShowListener() {
            @Override
            public void onShow(android.content.DialogInterface dialogInterface) {
                if(selectedButton!=null){
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, selectedButton.getTop());
                        }
                    });
                }
            }
        });

        dialog.show();
    }
}
