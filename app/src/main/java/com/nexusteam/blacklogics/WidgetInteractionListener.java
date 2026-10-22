package com.nexusteam.blacklogics;

import android.view.View;

public interface WidgetInteractionListener {
    void onWidgetClicked(View widget);
    void onWidgetLongClicked(View widget);
    void onWidgetSelected(View widget);
    void onWidgetDeselected();
}
