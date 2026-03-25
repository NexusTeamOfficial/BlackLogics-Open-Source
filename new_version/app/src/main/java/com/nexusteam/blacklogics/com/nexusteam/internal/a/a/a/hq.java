package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.ViewBean;

public interface hq {
    ViewBean getBean();

    boolean getFixed();

    void setBean(ViewBean viewBean);

    void setSelection(boolean z);
}
