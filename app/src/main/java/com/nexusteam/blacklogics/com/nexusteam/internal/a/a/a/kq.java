/* Decompiler 668ms, total 1696ms, lines 1234 */
package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class kq {
    // BlackLogics professional block labels (Sketchware-style full specs)
    public static final java.util.HashMap<String, String> BL_PROFESSIONAL_LABELS = new java.util.HashMap<>();
    static {
        _initLabels1();
        _initLabels2();
        _initLabels3();
        _initLabels4();
        _initLabels5();
        _initLabels6();
        _initLabels7();
        _initLabels8();
    }
    private static void _initLabels1() {
		BL_PROFESSIONAL_LABELS.put("importStatement", "import %s.inputOnly");
		BL_PROFESSIONAL_LABELS.put("menuInflate", "Menu get menu from file %s.menuName");
		BL_PROFESSIONAL_LABELS.put("menuAdd", "Menu add id %d.id title %s.title");
		BL_PROFESSIONAL_LABELS.put("menuAddIcon", "Menu add id %d.id title %s.title icon %m.icon");
		BL_PROFESSIONAL_LABELS.put("menuAddSubMenu", "Menu add submenu %s.title");
		BL_PROFESSIONAL_LABELS.put("subMenuAdd", "submenu add id %d.id title %s.title");
		BL_PROFESSIONAL_LABELS.put("menuItemSetVisible", "Menuitem %m.menuitem setVisible %b.visible");
		BL_PROFESSIONAL_LABELS.put("menuItemSetEnabled", "Menuitem %m.menuitem setEnabled %b.enabled");
		BL_PROFESSIONAL_LABELS.put("menuFindItem", "MenuItem findItem %d.id");
		BL_PROFESSIONAL_LABELS.put("menuItemGetItemId", "Menuitem %m.menuitem getItemId");
		BL_PROFESSIONAL_LABELS.put("menuItemGetTitle", "Menuitem %m.menuitem getTitle");
		BL_PROFESSIONAL_LABELS.put("contextMenuAdd", "ContextMenu add id %d.id title %s.title");
		BL_PROFESSIONAL_LABELS.put("activityResultGetRequestCode", "requestCode");
		BL_PROFESSIONAL_LABELS.put("activityResultGetResultCode", "resultCode");
		BL_PROFESSIONAL_LABELS.put("activityResultGetIntent", "intent data");
		BL_PROFESSIONAL_LABELS.put("listBindGetPosition", "position");
		BL_PROFESSIONAL_LABELS.put("recyclerSetLayoutManagerLinear", "%m.recyclerview setLayoutManager LinearLayoutManager");
		BL_PROFESSIONAL_LABELS.put("recyclerSetLayoutManagerGrid", "%m.recyclerview setLayoutManager GridLayoutManager span %d.span");
        BL_PROFESSIONAL_LABELS.put("faReturnFragment", "return Fragment %m.activity");
        BL_PROFESSIONAL_LABELS.put("faReturnTitle", "return Title %s");
        BL_PROFESSIONAL_LABELS.put("faReturnCount", "return %d");
        BL_PROFESSIONAL_LABELS.put("listBindGetData", "%m.map._data");
        BL_PROFESSIONAL_LABELS.put("listBindGetPosition", "%d._position");
        BL_PROFESSIONAL_LABELS.put("faReturnFragment", "return Fragment %m.activity");
BL_PROFESSIONAL_LABELS.put("faReturnTitle", "return Title %s.title");
BL_PROFESSIONAL_LABELS.put("faReturnCount", "return Count %d.count");
BL_PROFESSIONAL_LABELS.put("linearLayoutSetOrientation", "%m.linearlayout setOrientation %m.orientation");
BL_PROFESSIONAL_LABELS.put("gridLayoutSetColumnCount", "%m.gridlayout setColumnCount %d.count");
BL_PROFESSIONAL_LABELS.put("gridLayoutSetRowCount", "%m.gridlayout setRowCount %d.count");
BL_PROFESSIONAL_LABELS.put("nestedScrollScrollTo", "%m.nestedscrollview scrollTo x %d.x y %d.y");
BL_PROFESSIONAL_LABELS.put("materialCardSetRadius", "%m.cardview setRadius %d.radius");
BL_PROFESSIONAL_LABELS.put("materialCardSetElevation", "%m.cardview setCardElevation %d.elevation");
BL_PROFESSIONAL_LABELS.put("materialCardSetBackgroundColor", "%m.cardview setCardBackgroundColor %m.color");
BL_PROFESSIONAL_LABELS.put("materialSwitchSetChecked", "%m.materialswitch setChecked %b.checked");
BL_PROFESSIONAL_LABELS.put("materialSwitchGetChecked", "%m.materialswitch isChecked");
BL_PROFESSIONAL_LABELS.put("collapsingToolbarSetTitle", "%m.collapsingtoolbarlayout setTitle %s.title");
BL_PROFESSIONAL_LABELS.put("collapsingToolbarSetExpanded", "%m.collapsingtoolbarlayout setExpanded %b.expanded");
BL_PROFESSIONAL_LABELS.put("collapsingToolbarSetContentScrimColor", "%m.collapsingtoolbarlayout setContentScrimColor %m.color");
BL_PROFESSIONAL_LABELS.put("motionLayoutTransitionToStart", "%m.motionlayout transitionToStart");
BL_PROFESSIONAL_LABELS.put("motionLayoutTransitionToEnd", "%m.motionlayout transitionToEnd");
BL_PROFESSIONAL_LABELS.put("motionLayoutSetProgress", "%m.motionlayout setProgress %d.progress");
BL_PROFESSIONAL_LABELS.put("motionLayoutGetProgress", "%m.motionlayout getProgress");
BL_PROFESSIONAL_LABELS.put("spinnerSetSelection", "%m.spinner setSelection %d.position");
BL_PROFESSIONAL_LABELS.put("spinnerGetSelectedItemPosition", "%m.spinner getSelectedItemPosition");
BL_PROFESSIONAL_LABELS.put("spinnerGetSelectedItem", "%m.spinner getSelectedItem");
    }

    private static void _initLabels2() {
BL_PROFESSIONAL_LABELS.put("videoViewGetDuration", "%m.videoview getDuration");
BL_PROFESSIONAL_LABELS.put("videoViewGetCurrentPosition", "%m.videoview getCurrentPosition");
BL_PROFESSIONAL_LABELS.put("chipGroupGetCheckedChipId", "%m.chipgroup getCheckedChipId");
BL_PROFESSIONAL_LABELS.put("materialButtonSetIcon", "%m.materialbutton setIcon %m.resource");
BL_PROFESSIONAL_LABELS.put("ratingBarSetIsIndicator", "%m.ratingbar setIsIndicator %b.isIndicator");
BL_PROFESSIONAL_LABELS.put("textInputLayoutSetHelperText", "%m.textinputlayout setHelperText %s.text");
BL_PROFESSIONAL_LABELS.put("textInputLayoutGetEditText", "%m.textinputlayout getEditText");
BL_PROFESSIONAL_LABELS.put("tabLayoutSetupWithViewPager", "%m.tablayout setupWithViewPager %m.viewpager");
        BL_PROFESSIONAL_LABELS.put("recyclerSetCustomViewData", "%m.recyclerview setAdapter %m.adapter");
        BL_PROFESSIONAL_LABELS.put("recyclerSetHasFixedSize", "%m.recyclerview setHasFixedSize %b.fixed");
		BL_PROFESSIONAL_LABELS.put("recyclerSetCustomViewData", "%m.recyclerview setAdapter %m.adapter");
		BL_PROFESSIONAL_LABELS.put("recyclerNotifyDataSetChanged", "%m.recyclerview notifyDataSetChanged");
		BL_PROFESSIONAL_LABELS.put("recyclerNotifyItemInserted", "%m.recyclerview notifyItemInserted position %d.position");
		BL_PROFESSIONAL_LABELS.put("recyclerNotifyItemRemoved", "%m.recyclerview notifyItemRemoved position %d.position");
		BL_PROFESSIONAL_LABELS.put("recyclerNotifyItemChanged", "%m.recyclerview notifyItemChanged position %d.position");
		BL_PROFESSIONAL_LABELS.put("recyclerScrollToPosition", "%m.recyclerview scrollToPosition %d.position");
		BL_PROFESSIONAL_LABELS.put("recyclerSmoothScrollToPosition", "%m.recyclerview smoothScrollToPosition %d.position");
		BL_PROFESSIONAL_LABELS.put("listSetSelection", "%m.listview setSelection %d.position");
		BL_PROFESSIONAL_LABELS.put("gridSetNumColumns", "%m.gridview setNumColumns %d.columns");
		BL_PROFESSIONAL_LABELS.put("expandableExpandGroup", "%m.expandablelistview expandGroup %d.group");
		BL_PROFESSIONAL_LABELS.put("expandableCollapseGroup", "%m.expandablelistview collapseGroup %d.group");
		BL_PROFESSIONAL_LABELS.put("viewPagerSetCurrentItem", "%m.viewpager setCurrentItem %d.item");
		BL_PROFESSIONAL_LABELS.put("viewPagerGetCurrentItem", "%m.viewpager getCurrentItem");
		BL_PROFESSIONAL_LABELS.put("viewPager2SetCurrentItem", "%m.viewpager2 setCurrentItem %d.item");
		BL_PROFESSIONAL_LABELS.put("viewPager2GetCurrentItem", "%m.viewpager2 getCurrentItem");
		BL_PROFESSIONAL_LABELS.put("viewPager2SetUserInputEnabled", "%m.viewpager2 setUserInputEnabled %b.enabled");
		BL_PROFESSIONAL_LABELS.put("viewPagerSetAdapter", "%m.viewpager setAdapter %m.fragmentadapter");
		BL_PROFESSIONAL_LABELS.put("viewPagerNotifyDataSetChanged", "%m.viewpager notifyDataSetChanged");
		BL_PROFESSIONAL_LABELS.put("viewPager2SetAdapter", "%m.viewpager2 setAdapter %m.fragmentadapter");
		BL_PROFESSIONAL_LABELS.put("viewPager2NotifyDataSetChanged", "%m.viewpager2 notifyDataSetChanged");
		BL_PROFESSIONAL_LABELS.put("tabLayoutAddTab", "%m.tablayout addTab %s.title");
		BL_PROFESSIONAL_LABELS.put("tabLayoutGetSelected", "%m.tablayout getSelectedTabPosition");
		BL_PROFESSIONAL_LABELS.put("tabLayoutRemoveAllTabs", "%m.tablayout removeAllTabs");
		BL_PROFESSIONAL_LABELS.put("tabLayoutSelectTab", "%m.tablayout selectTab %d.position");
		BL_PROFESSIONAL_LABELS.put("bottomNavSetSelectedItemId", "%m.bottomnavigationview setSelectedItemId %d.id");
		BL_PROFESSIONAL_LABELS.put("bottomNavGetSelectedItemId", "%m.bottomnavigationview getSelectedItemId");
		BL_PROFESSIONAL_LABELS.put("navViewSetCheckedItem", "%m.navigationview setCheckedItem %d.id");
		BL_PROFESSIONAL_LABELS.put("cardViewSetRadius", "%m.cardview setRadius %d.radius");
		BL_PROFESSIONAL_LABELS.put("cardViewSetCardElevation", "%m.cardview setCardElevation %d.elevation");
		BL_PROFESSIONAL_LABELS.put("cardViewSetCardBackgroundColor", "%m.cardview setCardBackgroundColor %m.color");
		BL_PROFESSIONAL_LABELS.put("ratingBarSetRating", "%m.ratingbar setRating %d.rating");
		BL_PROFESSIONAL_LABELS.put("ratingBarGetRating", "%m.ratingbar getRating");
		BL_PROFESSIONAL_LABELS.put("ratingBarSetNumStars", "%m.ratingbar setNumStars %d.stars");
		BL_PROFESSIONAL_LABELS.put("progressBarSetProgress", "%m.progressbar setProgress %d.progress");
		BL_PROFESSIONAL_LABELS.put("progressBarGetProgress", "%m.progressbar getProgress");
    }

    private static void _initLabels3() {
		BL_PROFESSIONAL_LABELS.put("progressBarSetMax", "%m.progressbar setMax %d.max");
		BL_PROFESSIONAL_LABELS.put("progressBarSetIndeterminate", "%m.progressbar setIndeterminate %b.indeterminate");
		BL_PROFESSIONAL_LABELS.put("seekBarSetProgress", "%m.seekbar setProgress %d.progress");
		BL_PROFESSIONAL_LABELS.put("seekBarGetProgress", "%m.seekbar getProgress");
		BL_PROFESSIONAL_LABELS.put("seekBarSetMax", "%m.seekbar setMax %d.max");
		BL_PROFESSIONAL_LABELS.put("switchSetChecked", "%m.switch setChecked %b.checked");
		BL_PROFESSIONAL_LABELS.put("switchGetChecked", "%m.switch getChecked");
		BL_PROFESSIONAL_LABELS.put("checkBoxSetChecked", "%m.checkbox setChecked %b.checked");
		BL_PROFESSIONAL_LABELS.put("checkBoxGetChecked", "%m.checkbox getChecked");
		BL_PROFESSIONAL_LABELS.put("radioButtonSetChecked", "%m.radiobutton setChecked %b.checked");
		BL_PROFESSIONAL_LABELS.put("radioButtonGetChecked", "%m.radiobutton getChecked");
		BL_PROFESSIONAL_LABELS.put("toggleButtonSetChecked", "%m.togglebutton setChecked %b.checked");
		BL_PROFESSIONAL_LABELS.put("toggleButtonGetChecked", "%m.togglebutton getChecked");
		BL_PROFESSIONAL_LABELS.put("videoViewSetPath", "%m.videoview setVideoPath %s.path");
		BL_PROFESSIONAL_LABELS.put("videoViewStart", "%m.videoview Start");
		BL_PROFESSIONAL_LABELS.put("videoViewPause", "%m.videoview Pause");
		BL_PROFESSIONAL_LABELS.put("videoViewStop", "%m.videoview stopPlayback");
		BL_PROFESSIONAL_LABELS.put("videoViewSeekTo", "%m.videoview seekTo %d.msec");
		BL_PROFESSIONAL_LABELS.put("videoViewIsPlaying", "%m.videoview isPlaying");
		BL_PROFESSIONAL_LABELS.put("searchViewSetQuery", "%m.searchview setQuery %s.query submit %b.submit");
		BL_PROFESSIONAL_LABELS.put("searchViewGetQuery", "%m.searchview getQuery");
		BL_PROFESSIONAL_LABELS.put("searchViewSetIconified", "%m.searchview setIconified %b.iconified");
		BL_PROFESSIONAL_LABELS.put("chipSetText", "%m.chip setText %s.text");
		BL_PROFESSIONAL_LABELS.put("chipGetText", "%m.chip getText");
		BL_PROFESSIONAL_LABELS.put("chipSetCheckable", "%m.chip setCheckable %b.checkable");
		BL_PROFESSIONAL_LABELS.put("chipSetChecked", "%m.chip setChecked %b.checked");
		BL_PROFESSIONAL_LABELS.put("chipIsChecked", "%m.chip isChecked");
		BL_PROFESSIONAL_LABELS.put("chipGroupSetSingleSelection", "%m.chipgroup setSingleSelection %b.single");
		BL_PROFESSIONAL_LABELS.put("chipGroupClearCheck", "%m.chipgroup clearCheck");
		BL_PROFESSIONAL_LABELS.put("textInputLayoutSetHint", "%m.textinputlayout setHint %s.hint");
		BL_PROFESSIONAL_LABELS.put("textInputLayoutSetError", "%m.textinputlayout setError %s.error");
		BL_PROFESSIONAL_LABELS.put("textInputLayoutSetErrorEnabled", "%m.textinputlayout setErrorEnabled %b.enabled");
		BL_PROFESSIONAL_LABELS.put("materialButtonSetText", "%m.materialbutton setText %s.text");
		BL_PROFESSIONAL_LABELS.put("materialButtonGetText", "%m.materialbutton getText");
		BL_PROFESSIONAL_LABELS.put("materialButtonSetEnabled", "%m.materialbutton setEnabled %b.enabled");
		BL_PROFESSIONAL_LABELS.put("lottieSetAnimation", "%m.lottieanimationview setAnimation %s.asset");
		BL_PROFESSIONAL_LABELS.put("lottiePlayAnimation", "%m.lottieanimationview playAnimation");
		BL_PROFESSIONAL_LABELS.put("lottiePauseAnimation", "%m.lottieanimationview pauseAnimation");
		BL_PROFESSIONAL_LABELS.put("lottieCancelAnimation", "%m.lottieanimationview cancelAnimation");
		BL_PROFESSIONAL_LABELS.put("lottieSetRepeatCount", "%m.lottieanimationview setRepeatCount %d.count");
		BL_PROFESSIONAL_LABELS.put("lottieSetSpeed", "%m.lottieanimationview setSpeed %d.speed");
		BL_PROFESSIONAL_LABELS.put("circleImageSetImageResource", "%m.circleimageview setImageResource %m.resource");
		BL_PROFESSIONAL_LABELS.put("imageButtonSetImageResource", "%m.imagebutton setImageResource %m.resource");
		BL_PROFESSIONAL_LABELS.put("nestedScrollSmoothScrollTo", "%m.nestedscrollview smoothScrollTo x %d.x y %d.y");
		BL_PROFESSIONAL_LABELS.put("scrollViewSmoothScrollTo", "%m.scrollview smoothScrollTo x %d.x y %d.y");
    }

    private static void _initLabels4() {
		BL_PROFESSIONAL_LABELS.put("hScrollSmoothScrollTo", "%m.horizontalscrollview smoothScrollTo x %d.x y %d.y");
		BL_PROFESSIONAL_LABELS.put("webViewLoadData", "%m.webview loadData data %s.data mime %s.mime encoding %s.encoding");
		BL_PROFESSIONAL_LABELS.put("webViewEvaluateJavascript", "%m.webview evaluateJavascript %s.script");
		BL_PROFESSIONAL_LABELS.put("AsyncTaskExecute", "%m.asynctask execute");
		BL_PROFESSIONAL_LABELS.put("AsyncTaskPublishProgress", "%m.asynctask publishProgress progress %d.progress");
		BL_PROFESSIONAL_LABELS.put("CommandBlockJava", "Java Command Block reference %s distance %d frontend %d backend %d command %m.Command");
		BL_PROFESSIONAL_LABELS.put("CommandBlockXML", "XML Command Block reference %s distance %d frontend %d backend %d command %m.Command");
		BL_PROFESSIONAL_LABELS.put("Dialog SetIcon", "%m.dialog setIcon %m.icon");
		BL_PROFESSIONAL_LABELS.put("EditTextLines", "%m.edittext setLines %d.lines");
		BL_PROFESSIONAL_LABELS.put("EditTextSelectAll", "%m.edittext selectAll");
		BL_PROFESSIONAL_LABELS.put("EditTextSetMaxLines", "%m.edittext setMaxLines %d.max");
		BL_PROFESSIONAL_LABELS.put("EditTextSetSelection", "%m.edittext setSelection start %d.start end %d.end");
		BL_PROFESSIONAL_LABELS.put("EditTextShowError", "%m.edittext setError %s.error");
		BL_PROFESSIONAL_LABELS.put("EditTextSingleLine", "%m.edittext setSingleLine %b.single");
		BL_PROFESSIONAL_LABELS.put("EditTextdiableSuggestion", "%m.edittext disableSuggestions");
		BL_PROFESSIONAL_LABELS.put("EdittextGetselectionEnd", "%m.edittext getSelectionEnd");
		BL_PROFESSIONAL_LABELS.put("EdittextGetselectionStart", "%m.edittext getSelectionStart");
		BL_PROFESSIONAL_LABELS.put("LightStatusBar", "setLightStatusBar %b.light");
		BL_PROFESSIONAL_LABELS.put("ListViewAddFooter", "%m.listview addFooterView %m.view");
		BL_PROFESSIONAL_LABELS.put("ListViewAddHeader", "%m.listview addHeaderView %m.view");
		BL_PROFESSIONAL_LABELS.put("ViewPagerNotifyOnDtatChange", "%m.viewpager notifyDataSetChanged");
		BL_PROFESSIONAL_LABELS.put("YTPVLifecycle", "%m.youtubeplayer lifecycle");
		BL_PROFESSIONAL_LABELS.put("YTPVSetListener", "%m.youtubeplayer setListener");
		BL_PROFESSIONAL_LABELS.put("addCustomVariable", "addCustomVariable name %s.name type %s.type");
		BL_PROFESSIONAL_LABELS.put("addInitializer", "addInitializer code %s.code");
		BL_PROFESSIONAL_LABELS.put("addPermission", "addPermission %s.permission");
		BL_PROFESSIONAL_LABELS.put("addTab", "%m.tablayout addTab %s.title");
		BL_PROFESSIONAL_LABELS.put("addView", "%m.viewgroup addView %m.view");
		BL_PROFESSIONAL_LABELS.put("addViews", "%m.viewgroup addViews");
		BL_PROFESSIONAL_LABELS.put("autoComSetData", "%m.autocomplete setData %m.listMap");
		BL_PROFESSIONAL_LABELS.put("bannerAdViewLoadAd", "%m.adview loadAd");
		BL_PROFESSIONAL_LABELS.put("bottomMenuAddItem", "%m.bottomnavigationview addItem id %d.id title %s.title icon %m.icon");
		BL_PROFESSIONAL_LABELS.put("changeStatebarColour", "setStatusBarColor %m.color");
		BL_PROFESSIONAL_LABELS.put("checkViewVisibility", "%m.view isVisible");
		BL_PROFESSIONAL_LABELS.put("codeviewApply", "%m.codeview apply");
		BL_PROFESSIONAL_LABELS.put("codeviewSetCode", "%m.codeview setCode %s.code");
		BL_PROFESSIONAL_LABELS.put("codeviewSetLanguage", "%m.codeview setLanguage %s.language");
		BL_PROFESSIONAL_LABELS.put("codeviewSetTheme", "%m.codeview setTheme %s.theme");
		BL_PROFESSIONAL_LABELS.put("copyAssetFile", "copyAssetFile from %s.name to %s.dest");
		BL_PROFESSIONAL_LABELS.put("customImport", "customImport %s.import");
		BL_PROFESSIONAL_LABELS.put("customImport2", "customImport2 %s.import");
		BL_PROFESSIONAL_LABELS.put("customToast", "CustomToast %s.message textColor %m.color textSize %d.textSize bgColor %m.color radius %d.radius gravity %d.gravity");
		BL_PROFESSIONAL_LABELS.put("customToastWithIcon", "CustomToastWithIcon %s.message textColor %m.color textSize %d.textSize bgColor %m.color radius %d.radius gravity %d.gravity icon %m.icon");
		BL_PROFESSIONAL_LABELS.put("datePickerDialogShow", "%m.datepickerdialog show");
		BL_PROFESSIONAL_LABELS.put("fabIcon", "%m.floatingactionbutton setImageResource %m.icon");
    }

    private static void _initLabels5() {
		BL_PROFESSIONAL_LABELS.put("fabSize", "%m.floatingactionbutton setSize %d.size");
		BL_PROFESSIONAL_LABELS.put("fabVisibility", "%m.floatingactionbutton setVisibility %b.visible");
		BL_PROFESSIONAL_LABELS.put("fileContainsData", "SharedPreferences contains key %s.key");
		BL_PROFESSIONAL_LABELS.put("fileutilcopydir", "FileUtil copyDir from %s.src to %s.dest");
		BL_PROFESSIONAL_LABELS.put("finishAffinity", "finishAffinity");
		BL_PROFESSIONAL_LABELS.put("getAssetFile", "getAssetFile name %s.name");
		BL_PROFESSIONAL_LABELS.put("getBadgeCount", "%m.badge getBadgeCount");
		BL_PROFESSIONAL_LABELS.put("getHeight", "%m.view getHeight");
		BL_PROFESSIONAL_LABELS.put("getRating", "%m.ratingbar getRating");
		BL_PROFESSIONAL_LABELS.put("getResStr", "getResStr name %s.name");
		BL_PROFESSIONAL_LABELS.put("getResString", "getResString name %s.name");
		BL_PROFESSIONAL_LABELS.put("getWidth", "%m.view getWidth");
		BL_PROFESSIONAL_LABELS.put("gridSetColumnWidth", "%m.gridview setColumnWidth %d.width");
		BL_PROFESSIONAL_LABELS.put("gridSetCustomViewData", "%m.gridview setCustomViewData listMap %m.listMap customView %m.customview");
		BL_PROFESSIONAL_LABELS.put("gridSetHorizontalSpacing", "%m.gridview setHorizontalSpacing %d.spacing");
		BL_PROFESSIONAL_LABELS.put("gridSetStretchMode", "%m.gridview setStretchMode %d.mode");
		BL_PROFESSIONAL_LABELS.put("gridSetVerticalSpacing", "%m.gridview setVerticalSpacing %d.spacing");
		BL_PROFESSIONAL_LABELS.put("hideKeyboard", "hideKeyboard");
		BL_PROFESSIONAL_LABELS.put("imageCrop", "imageCrop path %s.path");
		BL_PROFESSIONAL_LABELS.put("intentHasExtra", "Intent hasExtra key %s.key");
		BL_PROFESSIONAL_LABELS.put("intentRemoveExtra", "Intent removeExtra key %s.key");
		BL_PROFESSIONAL_LABELS.put("intentSetType", "Intent setType %s.type");
		BL_PROFESSIONAL_LABELS.put("interstitialAdIsLoaded", "%m.interstitialad isLoaded");
		BL_PROFESSIONAL_LABELS.put("interstitialAdLoad", "%m.interstitialad load");
		BL_PROFESSIONAL_LABELS.put("interstitialAdShow", "%m.interstitialad show");
		BL_PROFESSIONAL_LABELS.put("isConnected", "isNetworkConnected");
		BL_PROFESSIONAL_LABELS.put("launchApp", "launchApp package %s.package");
		BL_PROFESSIONAL_LABELS.put("listAddCustom", "%m.listview add %s.item");
		BL_PROFESSIONAL_LABELS.put("listGetCheckedCount", "%m.listview getCheckedItemCount");
		BL_PROFESSIONAL_LABELS.put("listGetCheckedPosition", "%m.listview getCheckedItemPosition");
		BL_PROFESSIONAL_LABELS.put("listGetCheckedPositions", "%m.listview getCheckedItemPositions");
		BL_PROFESSIONAL_LABELS.put("listRefresh", "%m.listview refresh");
		BL_PROFESSIONAL_LABELS.put("listSetCustomViewData", "%m.listview setCustomViewData listMap %m.listMap customView %m.customview");
		BL_PROFESSIONAL_LABELS.put("listSetData", "%m.listview setData %m.listMap");
		BL_PROFESSIONAL_LABELS.put("listSetItemChecked", "%m.listview setItemChecked position %d.position checked %b.checked");
		BL_PROFESSIONAL_LABELS.put("listSetStackFromBottom", "%m.listview setStackFromBottom %b.stack");
		BL_PROFESSIONAL_LABELS.put("listSetTranscriptMode", "%m.listview setTranscriptMode %d.mode");
		BL_PROFESSIONAL_LABELS.put("listSmoothScrollTo", "%m.listview smoothScrollToPosition %d.position");
		BL_PROFESSIONAL_LABELS.put("listViewRemoveFooter", "%m.listview removeFooterView");
		BL_PROFESSIONAL_LABELS.put("listViewRemoveHeader", "%m.listview removeHeaderView");
		BL_PROFESSIONAL_LABELS.put("listViewSetSelection", "%m.listview setSelection %d.position");
		BL_PROFESSIONAL_LABELS.put("lottieSetAnimationFromAsset", "%m.lottieanimationview setAnimation %s.asset");
		BL_PROFESSIONAL_LABELS.put("lottieSetAnimationFromJson", "%m.lottieanimationview setAnimationFromJson %s.json");
		BL_PROFESSIONAL_LABELS.put("lottieSetAnimationFromUrl", "%m.lottieanimationview setAnimationFromUrl %s.url");
		BL_PROFESSIONAL_LABELS.put("multiAutoComSetData", "%m.multiautocomplete setData %m.listMap");
    }

    private static void _initLabels6() {
		BL_PROFESSIONAL_LABELS.put("onSwipeRefreshLayout", "%m.swiperefreshlayout setOnRefreshListener");
		BL_PROFESSIONAL_LABELS.put("openResourcesEditor", "openResourcesEditor");
		BL_PROFESSIONAL_LABELS.put("otpViewGetOTPText", "%m.otpview getOTP");
		BL_PROFESSIONAL_LABELS.put("otpViewSetFieldCount", "%m.otpview setFieldCount %d.count");
		BL_PROFESSIONAL_LABELS.put("otpViewSetOTPListener", "%m.otpview setOTPListener");
		BL_PROFESSIONAL_LABELS.put("otpViewSetOTPText", "%m.otpview setOTPText %s.text");
		BL_PROFESSIONAL_LABELS.put("pagerGetCurrentItem", "%m.viewpager getCurrentItem");
		BL_PROFESSIONAL_LABELS.put("pagerGetOffscreenPageLimit", "%m.viewpager getOffscreenPageLimit");
		BL_PROFESSIONAL_LABELS.put("pagerSetCurrentItem", "%m.viewpager setCurrentItem %d.item");
		BL_PROFESSIONAL_LABELS.put("pagerSetCustomViewData", "%m.viewpager setCustomViewData listMap %m.listMap customView %m.customview");
		BL_PROFESSIONAL_LABELS.put("pagerSetFragmentAdapter", "%m.viewpager setAdapter %m.fragmentadapter");
		BL_PROFESSIONAL_LABELS.put("pagerSetOffscreenPageLimit", "%m.viewpager setOffscreenPageLimit %d.limit");
		BL_PROFESSIONAL_LABELS.put("patternLockClear", "%m.patternlockview clearPattern");
		BL_PROFESSIONAL_LABELS.put("patternSetCorrectStateColor", "%m.patternlockview setCorrectStateColor %m.color");
		BL_PROFESSIONAL_LABELS.put("patternSetDotCount", "%m.patternlockview setDotCount %d.count");
		BL_PROFESSIONAL_LABELS.put("patternSetNormalStateColor", "%m.patternlockview setNormalStateColor %m.color");
		BL_PROFESSIONAL_LABELS.put("patternSetViewMode", "%m.patternlockview setViewMode %d.mode");
		BL_PROFESSIONAL_LABELS.put("patternSetWrongStateColor", "%m.patternlockview setWrongStateColor %m.color");
		BL_PROFESSIONAL_LABELS.put("patternToMD5", "%m.patternlockview patternToMD5");
		BL_PROFESSIONAL_LABELS.put("patternToSha1", "%m.patternlockview patternToSha1");
		BL_PROFESSIONAL_LABELS.put("patternToString", "%m.patternlockview patternToString");
		BL_PROFESSIONAL_LABELS.put("performClick", "%m.view performClick");
		BL_PROFESSIONAL_LABELS.put("progressdialogCreate", "%m.progressdialog = new ProgressDialog");
		BL_PROFESSIONAL_LABELS.put("progressdialogDismiss", "%m.progressdialog dismiss");
		BL_PROFESSIONAL_LABELS.put("progressdialogSetCancelable", "%m.progressdialog setCancelable %b.cancelable");
		BL_PROFESSIONAL_LABELS.put("progressdialogSetCanceledOutside", "%m.progressdialog setCanceledOnTouchOutside %b.outside");
		BL_PROFESSIONAL_LABELS.put("progressdialogSetMax", "%m.progressdialog setMax %d.max");
		BL_PROFESSIONAL_LABELS.put("progressdialogSetMessage", "%m.progressdialog setMessage %s.message");
		BL_PROFESSIONAL_LABELS.put("progressdialogSetProgress", "%m.progressdialog setProgress %d.progress");
		BL_PROFESSIONAL_LABELS.put("progressdialogSetStyle", "%m.progressdialog setProgressStyle %d.style");
		BL_PROFESSIONAL_LABELS.put("progressdialogSetTitle", "%m.progressdialog setTitle %s.title");
		BL_PROFESSIONAL_LABELS.put("progressdialogShow", "%m.progressdialog show");
		BL_PROFESSIONAL_LABELS.put("recyclerScrollToPositionWithOffset", "%m.recyclerview scrollToPositionWithOffset position %d.position offset %d.offset");
		BL_PROFESSIONAL_LABELS.put("recyclerSetLayoutManager", "%m.recyclerview setLayoutManager Linear");
		BL_PROFESSIONAL_LABELS.put("recyclerSetLayoutManagerHorizontal", "%m.recyclerview setLayoutManager Horizontal");
		BL_PROFESSIONAL_LABELS.put("refreshingList", "%m.listview refreshing");
		BL_PROFESSIONAL_LABELS.put("removePermission", "removePermission %s.permission");
		BL_PROFESSIONAL_LABELS.put("removeView", "%m.viewgroup removeView %m.view");
		BL_PROFESSIONAL_LABELS.put("removeViews", "%m.viewgroup removeAllViews");
		BL_PROFESSIONAL_LABELS.put("renameFile", "FileUtil rename from %s.old to %s.new");
		BL_PROFESSIONAL_LABELS.put("rewardedVideoAdLoad", "%m.rewardedvideoad load");
		BL_PROFESSIONAL_LABELS.put("rewardedVideoAdShow", "%m.rewardedvideoad show");
		BL_PROFESSIONAL_LABELS.put("sendBroadcast", "sendBroadcast");
		BL_PROFESSIONAL_LABELS.put("setBadgeBackground", "%m.badge setBadgeBackground %m.color");
		BL_PROFESSIONAL_LABELS.put("setBadgeNumber", "%m.badge setBadgeNumber %d.number");
    }

    private static void _initLabels7() {
		BL_PROFESSIONAL_LABELS.put("setBadgeString", "%m.badge setBadgeString %s.text");
		BL_PROFESSIONAL_LABELS.put("setBadgeTextColor", "%m.badge setBadgeTextColor %m.color");
		BL_PROFESSIONAL_LABELS.put("setBadgeTextSize", "%m.badge setBadgeTextSize %d.size");
		BL_PROFESSIONAL_LABELS.put("setBgDrawable", "%m.view setBackground %m.drawable");
		BL_PROFESSIONAL_LABELS.put("setBubbleArrowHeight", "%m.bubble setBubbleArrowHeight %d.height");
		BL_PROFESSIONAL_LABELS.put("setBubbleArrowPosition", "%m.bubble setBubbleArrowPosition %d.position");
		BL_PROFESSIONAL_LABELS.put("setBubbleArrowWidth", "%m.bubble setBubbleArrowWidth %d.width");
		BL_PROFESSIONAL_LABELS.put("setBubbleColor", "%m.bubble setBubbleColor %m.color");
		BL_PROFESSIONAL_LABELS.put("setBubbleCornerRadius", "%m.bubble setBubbleCornerRadius %d.radius");
		BL_PROFESSIONAL_LABELS.put("setBubbleStrokeColor", "%m.bubble setBubbleStrokeColor %m.color");
		BL_PROFESSIONAL_LABELS.put("setBubbleStrokeWidth", "%m.bubble setBubbleStrokeWidth %d.width");
		BL_PROFESSIONAL_LABELS.put("setCardBackgroundColor", "%m.cardview setCardBackgroundColor %m.color");
		BL_PROFESSIONAL_LABELS.put("setCardElevation", "%m.cardview setCardElevation %d.elevation");
		BL_PROFESSIONAL_LABELS.put("setCardRadius", "%m.cardview setRadius %d.radius");
		BL_PROFESSIONAL_LABELS.put("setColorFilterView", "%m.view setColorFilter %m.color");
		BL_PROFESSIONAL_LABELS.put("setCornerRadiusView", "%m.view setCornerRadius %d.radius");
		BL_PROFESSIONAL_LABELS.put("setCustomLetter", "%m.view setCustomLetter %s.letter");
		BL_PROFESSIONAL_LABELS.put("setElevation", "%m.view setElevation %d.elevation");
		BL_PROFESSIONAL_LABELS.put("setGradientBackground", "%m.view setGradient startColor %m.color endColor %m.color");
		BL_PROFESSIONAL_LABELS.put("setGravity", "%m.view setGravity %m.gravity");
		BL_PROFESSIONAL_LABELS.put("setImageCustomRes", "%m.imageview setImageResource %m.resource");
		BL_PROFESSIONAL_LABELS.put("setImageIdentifier", "%m.imageview setImage %s.name");
		BL_PROFESSIONAL_LABELS.put("setInlineLabel", "%m.tablayout setInlineLabel %b.inline");
		BL_PROFESSIONAL_LABELS.put("setNumStars", "%m.ratingbar setNumStars %d.stars");
		BL_PROFESSIONAL_LABELS.put("setPreventCornerOverlap", "%m.cardview setPreventCornerOverlap %b.prevent");
		BL_PROFESSIONAL_LABELS.put("setRadiusAndStrokeView", "%m.view setRadiusAndStroke radius %d.radius strokeWidth %d.strokeWidth strokeColor %m.color");
		BL_PROFESSIONAL_LABELS.put("setRating", "%m.ratingbar setRating %d.rating");
		BL_PROFESSIONAL_LABELS.put("setRecyclerViewLayoutParams", "%m.recyclerview setLayoutParams width %d.width height %d.height");
		BL_PROFESSIONAL_LABELS.put("setRefreshing", "%m.swiperefreshlayout setRefreshing %b.refreshing");
		BL_PROFESSIONAL_LABELS.put("setSelectedTabIndicatorColor", "%m.tablayout setSelectedTabIndicatorColor %m.color");
		BL_PROFESSIONAL_LABELS.put("setSelectedTabIndicatorHeight", "%m.tablayout setSelectedTabIndicatorHeight %d.height");
		BL_PROFESSIONAL_LABELS.put("setStepSize", "%m.ratingbar setStepSize %d.step");
		BL_PROFESSIONAL_LABELS.put("setStrokeView", "%m.view setStroke width %d.width color %m.color");
		BL_PROFESSIONAL_LABELS.put("setTabRippleColor", "%m.tablayout setTabRippleColor %m.color");
		BL_PROFESSIONAL_LABELS.put("setTabTextColors", "%m.tablayout setTabTextColors normal %m.color selected %m.color");
		BL_PROFESSIONAL_LABELS.put("setTextSize", "%m.view setTextSize %d.size");
		BL_PROFESSIONAL_LABELS.put("setThreshold", "%m.autocomplete setThreshold %d.threshold");
		BL_PROFESSIONAL_LABELS.put("setTokenizer", "%m.multiautocomplete setTokenizer");
		BL_PROFESSIONAL_LABELS.put("setUseCompatPadding", "%m.cardview setUseCompatPadding %b.use");
		BL_PROFESSIONAL_LABELS.put("setupWithViewPager", "%m.tablayout setupWithViewPager %m.viewpager");
		BL_PROFESSIONAL_LABELS.put("showKeyboard", "showKeyboard %m.view");
		BL_PROFESSIONAL_LABELS.put("showSnackbar", "Snackbar view %m.view message %s.message duration %d.duration");
		BL_PROFESSIONAL_LABELS.put("signInButtonSetColorScheme", "%m.signinbutton setColorScheme %d.scheme");
		BL_PROFESSIONAL_LABELS.put("signInButtonSetSize", "%m.signinbutton setSize %d.size");
		BL_PROFESSIONAL_LABELS.put("spnSetCustomViewData", "%m.spinner setCustomViewData listMap %m.listMap customView %m.customview");
    }

    private static void _initLabels8() {
		BL_PROFESSIONAL_LABELS.put("startActivityWithChooser", "startActivityWithChooser title %s.title");
		BL_PROFESSIONAL_LABELS.put("startService", "startService");
		BL_PROFESSIONAL_LABELS.put("stopService", "stopService");
		BL_PROFESSIONAL_LABELS.put("tilGetCounterMaxLength", "%m.textinputlayout getCounterMaxLength");
		BL_PROFESSIONAL_LABELS.put("tilSetBoxBgColor", "%m.textinputlayout setBoxBackgroundColor %m.color");
		BL_PROFESSIONAL_LABELS.put("tilSetBoxBgMode", "%m.textinputlayout setBoxBackgroundMode %d.mode");
		BL_PROFESSIONAL_LABELS.put("tilSetBoxCornerRadii", "%m.textinputlayout setBoxCornerRadii topStart %d.topStart topEnd %d.topEnd bottomStart %d.bottomStart bottomEnd %d.bottomEnd");
		BL_PROFESSIONAL_LABELS.put("tilSetBoxStrokeColor", "%m.textinputlayout setBoxStrokeColor %m.color");
		BL_PROFESSIONAL_LABELS.put("tilSetCounterEnabled", "%m.textinputlayout setCounterEnabled %b.enabled");
		BL_PROFESSIONAL_LABELS.put("tilSetCounterMaxLength", "%m.textinputlayout setCounterMaxLength %d.max");
		BL_PROFESSIONAL_LABELS.put("tilSetError", "%m.textinputlayout setError %s.error");
		BL_PROFESSIONAL_LABELS.put("tilSetErrorEnabled", "%m.textinputlayout setErrorEnabled %b.enabled");
		BL_PROFESSIONAL_LABELS.put("timePickerDialogShow", "%m.timepickerdialog show");
		BL_PROFESSIONAL_LABELS.put("timepickerSetCurrentHour", "%m.timepicker setCurrentHour %d.hour");
		BL_PROFESSIONAL_LABELS.put("timepickerSetCurrentMinute", "%m.timepicker setCurrentMinute %d.minute");
		BL_PROFESSIONAL_LABELS.put("timepickerSetHour", "%m.timepicker setHour %d.hour");
		BL_PROFESSIONAL_LABELS.put("timepickerSetIs24Hour", "%m.timepicker setIs24HourView %b.is24Hour");
		BL_PROFESSIONAL_LABELS.put("timepickerSetMinute", "%m.timepicker setMinute %d.minute");
		BL_PROFESSIONAL_LABELS.put("variableAddNew", "addVariable name %s.name type %m.type");
		BL_PROFESSIONAL_LABELS.put("videoviewCanPause", "%m.videoview canPause");
		BL_PROFESSIONAL_LABELS.put("videoviewCanSeekBackward", "%m.videoview canSeekBackward");
		BL_PROFESSIONAL_LABELS.put("videoviewCanSeekForward", "%m.videoview canSeekForward");
		BL_PROFESSIONAL_LABELS.put("videoviewGetCurrentPosition", "%m.videoview getCurrentPosition");
		BL_PROFESSIONAL_LABELS.put("videoviewGetDuration", "%m.videoview getDuration");
		BL_PROFESSIONAL_LABELS.put("videoviewIsPlaying", "%m.videoview isPlaying");
		BL_PROFESSIONAL_LABELS.put("videoviewPause", "%m.videoview pause");
		BL_PROFESSIONAL_LABELS.put("videoviewSetVideoUri", "%m.videoview setVideoURI %s.uri");
		BL_PROFESSIONAL_LABELS.put("videoviewStart", "%m.videoview start");
		BL_PROFESSIONAL_LABELS.put("videoviewStop", "%m.videoview stopPlayback");
		BL_PROFESSIONAL_LABELS.put("viewGetChildAt", "%m.viewgroup getChildAt %d.index");
		BL_PROFESSIONAL_LABELS.put("viewOnLongClick", "%m.view setOnLongClickListener");
		BL_PROFESSIONAL_LABELS.put("viewOnTouch", "%m.view setOnTouchListener");
		BL_PROFESSIONAL_LABELS.put("webviewGetProgress", "%m.webview getProgress");
    }

    private static kq a;
    private HashMap<String, String> b;
    private HashMap<String, String> c;
    private String d = fe.g();
    private boolean e;
    private final String f = "block";
    private final String g = "root_spec";
    
    private kq() {
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        if (this.c == null) {
            this.c = new HashMap();
        }
        
    }
    
    public static kq a() {
        if (a == null) {
            synchronized(kq.class){}
            
            Throwable var10000;
            boolean var10001;
            label191: {
                label190: {
                    try {
                        if (a != null) {
                            break label190;
                        }
                    } catch (Throwable var20) {
                        var10000 = var20;
                        var10001 = false;
                        break label191;
                    }
                    
                    try {
                        kq var0 = new kq();
                        a = var0;
                    } catch (Throwable var19) {
                        var10000 = var19;
                        var10001 = false;
                        break label191;
                    }
                }
                
                label184:
                try {
                    return a;
                } catch (Throwable var18) {
                    var10000 = var18;
                    var10001 = false;
                    break label184;
                }
            }
            
            while(true) {
                Throwable var21 = var10000;
                
                try {
                    throw var21;
                } catch (Throwable var17) {
                    var10000 = var17;
                    var10001 = false;
                    continue;
                }
            }
        } else {
            return a;
        }
    }
    
    private HashMap<String, String> b(String path) {
        HashMap<String, String> map = new HashMap<>();
        InputStreamReader reader = null;
        FileInputStream fis = null;
        try {
            File file = new File(path);
            if (!file.exists()) return map;
            
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            fis = new FileInputStream(file);
            reader = new InputStreamReader(fis, "UTF-8");
            parser.setInput(reader);
            
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "string".equals(parser.getName())) {
                    String key = parser.getAttributeValue(null, "name");
                    String value = parser.nextText();
                    map.put(key, value);
                }
                eventType = parser.next();
            }
            e = true; // <-- Boolean
            if (fis != null) fis.close();
            if (reader != null) reader.close();
        } catch (Exception ex) {
            e = false; // <-- Boolean
            try {
                if (fis != null) fis.close();
            } catch (Exception ignored) {}
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
        } catch (Throwable t) {
            try {
                if (fis != null) fis.close();
            } catch (Exception ignored) {}
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
            throw t;
        }
        return map;
    }
    
    public String a(Context var1, int var2) {
        return this.a(var1.getResources(), var2);
    }
    
    public String a(Context var1, int var2, Object... var3) {
        return this.a(var1.getResources(), var2, var3);
    }
    
    public String a(Context var1, String var2) {
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        if (this.b.isEmpty()) {
            this.e = false;
            this.b = this.a(this.d);
        }
        
        if (this.c == null) {
            this.c = new HashMap();
        }
        
        if (this.c.isEmpty()) {
            this.b(var1);
        }
        
        var2 = (String)this.c.get(var2);
        String var3 = var2;
        if (var2 == null) {
            var3 = "";
        }
        
        return var3;
    }
    
    public String a(Context var1, String var2, String var3) {
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        boolean var5 = this.b.isEmpty();
        byte var4 = 0;
        if (var5) {
            this.e = false;
            this.b = this.a(this.d);
        }
        
        if (this.c == null) {
            this.c = new HashMap();
        }
        
        if (this.c.isEmpty()) {
            this.b(var1);
        }
        
        label159: {
            switch(var3.hashCode()) {
                case -2117913147:
                if (var3.equals("onStartTrackingTouch")) {
                    var4 = 9;
                    break label159;
                }
                break;
                case -2067423513:
                if (var3.equals("onSpeechError")) {
                    var4 = 33;
                    break label159;
                }
                break;
                case -1865337024:
                if (var3.equals("onResponse")) {
                    var4 = 30;
                    break label159;
                }
                break;
                case -1809154262:
                if (var3.equals("onDataReceived")) {
                    var4 = 35;
                    break label159;
                }
                break;
                case -1779618840:
                if (var3.equals("onProgressChanged")) {
                    var4 = 8;
                    break label159;
                }
                break;
                case -1358405466:
                if (var3.equals("onMapReady")) {
                    var4 = 39;
                    break label159;
                }
                break;
                case -1351902487:
                if (var3.equals("onClick")) {
                    break label159;
                }
                break;
                case -1215328199:
                if (var3.equals("onDeleteSuccess")) {
                    var4 = 24;
                    break label159;
                }
                break;
                case -1153785290:
                if (var3.equals("onAnimationEnd")) {
                    var4 = 12;
                    break label159;
                }
                break;
                case -891988931:
                if (var3.equals("onDateChange")) {
                    var4 = 15;
                    break label159;
                }
                break;
                case -837428873:
                if (var3.equals("onChildChanged")) {
                    var4 = 17;
                    break label159;
                }
                break;
                case -821066400:
                if (var3.equals("onLocationChanged")) {
                    var4 = 41;
                    break label159;
                }
                break;
                case -749253875:
                if (var3.equals("onUploadProgress")) {
                    var4 = 20;
                    break label159;
                }
                break;
                case -732782352:
                if (var3.equals("onConnectionStopped")) {
                    var4 = 38;
                    break label159;
                }
                break;
                case -719893013:
                if (var3.equals("onConnectionError")) {
                    var4 = 37;
                    break label159;
                }
                break;
                case -672992515:
                if (var3.equals("onAnimationStart")) {
                    var4 = 11;
                    break label159;
                }
                break;
                case -609996822:
                if (var3.equals("onConnected")) {
                    var4 = 34;
                    break label159;
                }
                break;
                case -584901992:
                if (var3.equals("onCheckedChange")) {
                    var4 = 1;
                    break label159;
                }
                break;
                case -507667891:
                if (var3.equals("onItemSelected")) {
                    var4 = 2;
                    break label159;
                }
                break;
                case -505277536:
                if (var3.equals("onPageFinished")) {
                    var4 = 7;
                    break label159;
                }
                break;
                case -484536541:
                if (var3.equals("onChildRemoved")) {
                    var4 = 18;
                    break label159;
                }
                break;
                case -376002870:
                if (var3.equals("onErrorResponse")) {
                    var4 = 31;
                    break label159;
                }
                break;
                case 80616227:
                if (var3.equals("onUploadSuccess")) {
                    var4 = 22;
                    break label159;
                }
                break;
                case 136827711:
                if (var3.equals("onAnimationCancel")) {
                    var4 = 13;
                    break label159;
                }
                break;
                case 162093458:
                if (var3.equals("onBindCustomView")) {
                    var4 = 14;
                    break label159;
                }
                break;
                case 249705131:
                if (var3.equals("onFailure")) {
                    var4 = 25;
                    break label159;
                }
                break;
                case 264008033:
                if (var3.equals("onDataSent")) {
                    var4 = 36;
                    break label159;
                }
                break;
                case 372583555:
                if (var3.equals("onChildAdded")) {
                    var4 = 16;
                    break label159;
                }
                break;
                case 378110312:
                if (var3.equals("onTextChanged")) {
                    var4 = 5;
                    break label159;
                }
                break;
                case 445802034:
                if (var3.equals("onCancelled")) {
                    var4 = 19;
                    break label159;
                }
                break;
                case 694589214:
                if (var3.equals("onSpeechResult")) {
                    var4 = 32;
                    break label159;
                }
                break;
                case 805710389:
                if (var3.equals("onItemClicked")) {
                    var4 = 3;
                    break label159;
                }
                break;
                case 1170737640:
                if (var3.equals("onPictureTaken")) {
                    var4 = 26;
                    break label159;
                }
                break;
                case 1348442836:
                if (var3.equals("onDownloadProgress")) {
                    var4 = 21;
                    break label159;
                }
                break;
                case 1348605570:
                if (var3.equals("onPictureTakenCancel")) {
                    var4 = 27;
                    break label159;
                }
                break;
                case 1395209852:
                if (var3.equals("onDownloadSuccess")) {
                    var4 = 23;
                    break label159;
                }
                break;
                case 1586033095:
                if (var3.equals("onStopTrackingTouch")) {
                    var4 = 10;
                    break label159;
                }
                break;
                case 1710477203:
                if (var3.equals("onPageStarted")) {
                    var4 = 6;
                    break label159;
                }
                break;
                case 1757061906:
                if (var3.equals("onFilesPickedCancel")) {
                    var4 = 29;
                    break label159;
                }
                break;
                case 1803231982:
                if (var3.equals("onMarkerClicked")) {
                    var4 = 40;
                    break label159;
                }
                break;
                case 1979400473:
                if (var3.equals("onItemLongClicked")) {
                    var4 = 4;
                    break label159;
                }
                break;
                case 2087273080:
                if (var3.equals("onFilesPicked")) {
                    var4 = 28;
                    break label159;
                }
            }
            
            var4 = -1;
        }
        
        switch(var4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            String var6 = (String)this.c.get(var3);
            var3 = var6;
            if (var6 == null) {
                var3 = "";
            }
            
            StringBuilder var8 = new StringBuilder();
            var8.append(this.a(var1, R.string.root_spec_common_when));
            var8.append(" ");
            var8.append(var2);
            var8.append(" ");
            var8.append(var3);
            return var8.toString();
            default:
            var2 = (String)this.c.get(var3);
            String var7 = var2;
            if (var2 == null) {
                var7 = "";
            }
            
            return var7;
        }
    }
    
    public String a(Context var1, String var2, ArrayList<String> var3) {
        int var4 = var3.size();
        boolean var8 = true;
        byte var7 = 0;
        if (var4 > 1) {
            var4 = var3.size() - 1;
        } else {
            var4 = 0;
        }
        
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        if (this.b.isEmpty()) {
            this.e = false;
            this.b = this.a(this.d);
        }
        
        boolean var5;
        int var6;
        StringBuilder var9;
        StringBuilder var11;
        String var12;
        String var17;
        String var18;
        label99: {
            var11 = new StringBuilder(1024);
            var9 = new StringBuilder();
            var9.append(var2);
            var9.append("_head");
            var17 = var9.toString();
            StringBuilder var10 = new StringBuilder();
            var10.append(var2);
            var10.append("_tail");
            var12 = var10.toString();
            if (this.b != null && this.b.containsKey(var17) && this.b.containsKey(var12)) {
                var6 = 0;
                
                do {
                    var5 = var8;
                    if (var6 >= var4) {
                        break label99;
                    }
                    
                    var10 = new StringBuilder();
                    var10.append(var2);
                    var10.append("_body_");
                    ++var6;
                    var10.append(var6);
                    var18 = var10.toString();
                } while(this.b.containsKey(var18));
            }
            
            var5 = false;
        }
        
        if (var5) {
            var17 = (String)this.b.get(var17);
        } else {
            try {
                var17 = var1.getResources().getString(var1.getResources().getIdentifier(var17, "string", var1.getPackageName()));
            } catch (Exception var15) {
                var17 = "";
            }
        }
        
        var18 = var17;
        if (var17 == null) {
            var18 = "";
        }
        
        var11.append(var18);
        var6 = var7;
        if (var3.size() > 0) {
            if (var11.length() > 0) {
                var11.append(" ");
            }
            
            var11.append((String)var3.get(0));
            var6 = var7;
        }
        
        for(; var6 < var4; var11.append((String)var3.get(var6))) {
            var9 = new StringBuilder();
            var9.append(var2);
            var9.append("_body_");
            ++var6;
            var9.append(var6);
            var17 = var9.toString();
            if (var5) {
                var17 = (String)this.b.get(var17);
            } else {
                try {
                    var17 = var1.getResources().getString(var1.getResources().getIdentifier(var17, "string", var1.getPackageName()));
                } catch (Exception var14) {
                    var17 = "";
                }
            }
            
            var18 = var17;
            if (var17 == null) {
                var18 = "";
            }
            
            if (var18.length() > 0) {
                var11.append(" ");
            }
            
            var11.append(var18);
            if (var11.length() > 0) {
                var11.append(" ");
            }
        }
        
        String var16;
        if (var5) {
            var16 = (String)this.b.get(var12);
        } else {
            try {
                var16 = var1.getResources().getString(var1.getResources().getIdentifier(var12, "string", var1.getPackageName()));
            } catch (Exception var13) {
                var16 = "";
            }
        }
        
        var2 = var16;
        if (var16 == null) {
            var2 = "";
        }
        
        if (var11.length() > 0 && var2.length() > 0) {
            var11.append(" ");
        }
        
        var11.append(var2);
        return var11.toString();
    }
    
    public String a(Resources var1, int var2) {
        String var3 = var1.getResourceEntryName(var2);
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        try {
            if (this.b.isEmpty()) {
                this.e = false;
                this.b = this.a(this.d);
            }
            
            if (this.b.containsKey(var3) && this.b.get(var3) != null && ((String)this.b.get(var3)).length() > 0) {
                var3 = ((String)this.b.get(var3)).replaceAll("\\\\\\'", "'").replaceAll("\\\\\\\"", "\"").replaceAll("\\\\n", "\\\n");
                return var3;
            }
        } catch (Exception var4) {
            return var1.getString(var2);
        }
        
        return var1.getString(var2);
    }
    
    public String a(Resources var1, int var2, Object... var3) {
        String var12 = var1.getResourceEntryName(var2);
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        boolean var8 = this.b.isEmpty();
        int var4 = 0;
        if (var8) {
            this.e = false;
            this.b = this.a(this.d);
        }
        
        String var9;
        label111: {
            Exception var10000;
            label99: {
                boolean var10001;
                try {
                    if (!this.b.containsKey(var12) || this.b.get(var12) == null || ((String)this.b.get(var12)).length() <= 0) {
                        return var1.getString(var2, var3);
                    }
                } catch (Exception var20) {
                    var10000 = var20;
                    var10001 = false;
                    break label99;
                }
                
                try {
                    var9 = ((String)this.b.get(var12)).replaceAll("\\\\\\'", "'").replaceAll("\\\\\\\"", "\"").replaceAll("\\\\n", "\\\n");
                } catch (Exception var19) {
                    var10000 = var19;
                    var10001 = false;
                    break label99;
                }
                
                int var7;
                try {
                    var7 = var3.length;
                } catch (Exception var18) {
                    var10000 = var18;
                    var10001 = false;
                    break label99;
                }
                
                int var5;
                String var10;
                for(var5 = 0; var4 < var7; var9 = var10) {
                    Object var11 = var3[var4];
                    int var6 = var5;
                    var10 = var9;
                    
                    label102: {
                        try {
                            if (!var9.contains("%")) {
                                break label102;
                            }
                        } catch (Exception var17) {
                            var10000 = var17;
                            var10001 = false;
                            break label99;
                        }
                        
                        String var22;
                        try {
                            var22 = var11.toString();
                        } catch (Exception var15) {
                            var10000 = var15;
                            var10001 = false;
                            break label99;
                        }
                        
                        var10 = var22;
                        
                        label71: {
                            try {
                                if (!var22.equals("\\n")) {
                                    break label71;
                                }
                            } catch (Exception var16) {
                                var10000 = var16;
                                var10001 = false;
                                break label99;
                            }
                            
                            var10 = "\\\\n";
                        }
                        
                        try {
                            var10 = var9.replaceFirst("%s", var10);
                        } catch (Exception var14) {
                            var10000 = var14;
                            var10001 = false;
                            break label99;
                        }
                        
                        var6 = var5 + 1;
                    }
                    
                    ++var4;
                    var5 = var6;
                }
                
                try {
                    if (var5 != var3.length) {
                        return var1.getString(var2, var3);
                    }
                    
                    var8 = var9.contains("%");
                    break label111;
                } catch (Exception var13) {
                    var10000 = var13;
                    var10001 = false;
                }
            }
            
            Exception var23 = var10000;
            StringBuilder var21 = new StringBuilder();
            var21.append("Faild to load (");
            var21.append(var12);
            var21.append(")");
            Log.e("ERROR", var21.toString(), var23);
            return var1.getString(var2, var3);
        }
        
        if (!var8) {
            return var9;
        } else {
            return var1.getString(var2, var3);
        }
    }
    
    public HashMap<String, String> a(String var1) {
        HashMap var2 = new HashMap();
        kk var4 = new kk();
        lb var3 = new lb();
        if (!(new File(var1)).exists()) {
            return var2;
        } else {
            HashMap var8;
            HashMap var10;
            try {
                byte[] var11 = var4.f(var1);
                byte[] var9 = var3.b(var11);
                var10 = this.a(var9);
            } catch (Exception var7) {
                var8 = var2;
                return var8;
            }
            
            
            var8 = var10;
            return var8;
        }
    }
    
    public HashMap<String, String> a(byte[] bytes) {
        HashMap<String, String> map = new HashMap<>();
        InputStreamReader reader = null;
        ByteArrayInputStream bais = null;
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            bais = new ByteArrayInputStream(bytes);
            reader = new InputStreamReader(bais, "UTF-8");
            parser.setInput(reader);
            
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "string".equals(parser.getName())) {
                    String key = parser.getAttributeValue(null, "name");
                    String value = parser.nextText();
                    map.put(key, value);
                }
                eventType = parser.next();
            }
            e = true; // <-- changed from 1 to true
            if (bais != null) bais.close();
            if (reader != null) reader.close();
        } catch (Exception ex) {
            e = false; // <-- changed from 0 to false
            try {
                if (bais != null) bais.close();
            } catch (Exception ignored) {}
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
        } catch (Throwable t) {
            try {
                if (bais != null) bais.close();
            } catch (Exception ignored) {}
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
            throw t;
        }
        return map;
    }
    
    
    public boolean a(Context var1) {
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        if (this.b != null) {
            this.b.clear();
        }
        
        this.b = this.a(this.d);
        this.b(var1);
        boolean var2;
        if (!this.b.isEmpty() && this.e) {
            var2 = true;
        } else {
            var2 = false;
        }
        
        return var2;
    }
    
    public void b() {
        if (this.b != null) {
            this.b.clear();
            this.b = null;
        }
        
        if (this.c != null) {
            this.c.clear();
            this.c = null;
        }
        
    }
    
    protected void b(Context var1) {
        this.c = new HashMap();
        this.c(var1, "initializeLogic");
        this.c(var1, "onBackPressed");
        this.c(var1, "onPostCreate");
        this.c(var1, "onStart");
        this.c(var1, "onStop");
        this.c(var1, "onDestroy");
        this.c(var1, "onResume");
        this.c(var1, "onPause");
        this.c(var1, "moreBlock");
        this.c(var1, "onClick");
        this.c(var1, "onCheckedChange");
        this.c(var1, "onItemSelected");
        this.c(var1, "onItemClicked");
        this.c(var1, "onItemLongClicked");
        this.c(var1, "onTextChanged");
        this.c(var1, "onPageStarted");
        this.c(var1, "onPageFinished");
        this.c(var1, "onProgressChanged");
        this.c(var1, "onStartTrackingTouch");
        this.c(var1, "onStopTrackingTouch");
        this.c(var1, "onAnimationStart");
        this.c(var1, "onAnimationEnd");
        this.c(var1, "onAnimationCancel");
        this.c(var1, "onBindCustomView");
        this.c(var1, "onDateChange");
        this.c(var1, "onChildAdded");
        this.c(var1, "onChildChanged");
        this.c(var1, "onChildRemoved");
        this.c(var1, "onCancelled");
        this.c(var1, "onSensorChanged");
        this.c(var1, "onCreateUserComplete");
        this.c(var1, "onSignInUserComplete");
        this.c(var1, "onResetPasswordEmailSent");
        this.c(var1, "onUploadProgress");
        this.c(var1, "onDownloadProgress");
        this.c(var1, "onUploadSuccess");
        this.c(var1, "onDownloadSuccess");
        this.c(var1, "onDeleteSuccess");
        this.c(var1, "onFailure");
        this.c(var1, "onPictureTaken");
        this.c(var1, "onPictureTakenCancel");
        this.c(var1, "onFilesPicked");
        this.c(var1, "onFilesPickedCancel");
        this.c(var1, "onAdLoaded");
        this.c(var1, "onAdFailedToLoad");
        this.c(var1, "onAdOpened");
        this.c(var1, "onAdClosed");
        this.c(var1, "onResponse");
        this.c(var1, "onErrorResponse");
        this.c(var1, "onSpeechResult");
        this.c(var1, "onSpeechError");
        this.c(var1, "onConnected");
        this.c(var1, "onDataReceived");
        this.c(var1, "onDataSent");
        this.c(var1, "onConnectionError");
        this.c(var1, "onConnectionStopped");
        this.c(var1, "onMapReady");
        this.c(var1, "onMarkerClicked");
        this.c(var1, "onLocationChanged");
        this.b(var1, "viewOnClick");
        this.b(var1, "setVarBoolean");
        this.b(var1, "setVarInt");
        this.b(var1, "increaseInt");
        this.b(var1, "decreaseInt");
        this.b(var1, "setVarString");
        this.b(var1, "mapCreateNew");
        this.b(var1, "mapPut");
        this.b(var1, "mapGet");
        this.b(var1, "mapContainKey");
        this.b(var1, "mapRemoveKey");
        this.b(var1, "mapSize");
        this.b(var1, "mapClear");
        this.b(var1, "mapIsEmpty");
        this.b(var1, "mapGetAllKeys");
        this.b(var1, "addListInt");
        this.b(var1, "insertListInt");
        this.b(var1, "deleteList");
        this.b(var1, "getAtListInt");
        this.b(var1, "indexListInt");
        this.b(var1, "lengthList");
        this.b(var1, "containListInt");
        this.b(var1, "clearList");
        this.b(var1, "addListStr");
        this.b(var1, "insertListStr");
        this.b(var1, "getAtListStr");
        this.b(var1, "indexListStr");
        this.b(var1, "containListStr");
        this.b(var1, "addListMap");
        this.b(var1, "insertListMap");
        this.b(var1, "getAtListMap");
        this.b(var1, "setListMap");
        this.b(var1, "containListMap");
        this.b(var1, "addMapToList");
        this.b(var1, "insertMapToList");
        this.b(var1, "getMapInList");
        this.b(var1, "repeat");
        this.b(var1, "forever");
        this.b(var1, "break");
        this.b(var1, "if");
        this.b(var1, "ifElse");
        this.b(var1, "else");
        this.b(var1, "true");
        this.b(var1, "false");
        this.b(var1, "<");
        this.b(var1, "=");
        this.b(var1, ">");
        this.b(var1, "&&");
        this.b(var1, "||");
        this.b(var1, "not");
        this.b(var1, "+");
        this.b(var1, "-");
        this.b(var1, "*");
        this.b(var1, "/");
        this.b(var1, "%");
        this.b(var1, "random");
        this.b(var1, "stringLength");
        this.b(var1, "stringJoin");
        this.b(var1, "stringIndex");
        this.b(var1, "stringLastIndex");
        this.b(var1, "stringSub");
        this.b(var1, "stringEquals");
        this.b(var1, "stringContains");
        this.b(var1, "stringReplace");
        this.b(var1, "stringReplaceFirst");
        this.b(var1, "stringReplaceAll");
        this.b(var1, "toNumber");
        this.b(var1, "trim");
        this.b(var1, "toUpperCase");
        this.b(var1, "toLowerCase");
        this.b(var1, "toString");
        this.b(var1, "toStringWithDecimal");
        this.b(var1, "toStringFormat");
        this.b(var1, "addSourceDirectly");
        this.b(var1, "importStatement");
        this.b(var1, "listBindGetPosition");
        this.b(var1, "faReturnFragment");
this.b(var1, "faReturnTitle");
this.b(var1, "faReturnCount");
// Extra Widgets
this.b(var1, "linearLayoutSetOrientation");
this.b(var1, "gridLayoutSetColumnCount");
this.b(var1, "gridLayoutSetRowCount");
this.b(var1, "nestedScrollScrollTo");
this.b(var1, "materialCardSetRadius");
this.b(var1, "materialCardSetElevation");
this.b(var1, "materialCardSetBackgroundColor");
this.b(var1, "materialSwitchSetChecked");
this.b(var1, "materialSwitchGetChecked");
this.b(var1, "collapsingToolbarSetTitle");
this.b(var1, "collapsingToolbarSetExpanded");
this.b(var1, "collapsingToolbarSetContentScrimColor");
this.b(var1, "motionLayoutTransitionToStart");
this.b(var1, "motionLayoutTransitionToEnd");
this.b(var1, "motionLayoutSetProgress");
this.b(var1, "motionLayoutGetProgress");
this.b(var1, "spinnerSetSelection");
this.b(var1, "spinnerGetSelectedItemPosition");
this.b(var1, "spinnerGetSelectedItem");
this.b(var1, "videoViewGetDuration");
this.b(var1, "videoViewGetCurrentPosition");
this.b(var1, "chipGroupGetCheckedChipId");
this.b(var1, "materialButtonSetIcon");
this.b(var1, "ratingBarSetIsIndicator");
this.b(var1, "textInputLayoutSetHelperText");
this.b(var1, "textInputLayoutGetEditText");
this.b(var1, "tabLayoutSetupWithViewPager");
        this.b(var1, "activityResultGetIntent");
        this.b(var1, "activityResultGetResultCode");
        this.b(var1, "activityResultGetRequestCode");
        this.b(var1, "contextMenuAdd");
        this.b(var1, "menuItemGetTitle");
        this.b(var1, "menuItemGetItemId");
        this.b(var1, "menuFindItem");
        this.b(var1, "menuItemSetEnabled");
        this.b(var1, "menuItemSetVisible");
        this.b(var1, "subMenuAdd");
        this.b(var1, "menuAddSubMenu");
        this.b(var1, "menuAddIcon");
        this.b(var1, "menuAdd");
        this.b(var1, "menuInflate");
        this.b(var1, "mapToStr");
        this.b(var1, "strToMap");
        this.b(var1, "listMapToStr");
        this.b(var1, "strToListMap");
        this.b(var1, "mathGetDip");
        this.b(var1, "mathGetDisplayWidth");
        this.b(var1, "mathGetDisplayHeight");
        this.b(var1, "mathPi");
        this.b(var1, "mathE");
        this.b(var1, "mathPow");
        this.b(var1, "mathMin");
        this.b(var1, "mathMax");
        this.b(var1, "mathSqrt");
        this.b(var1, "mathAbs");
        this.b(var1, "mathRound");
        this.b(var1, "mathCeil");
        this.b(var1, "mathFloor");
        this.b(var1, "mathSin");
        this.b(var1, "mathCos");
        this.b(var1, "mathTan");
        this.b(var1, "mathAsin");
        this.b(var1, "mathAcos");
        this.b(var1, "mathAtan");
        this.b(var1, "mathExp");
        this.b(var1, "mathLog");
        this.b(var1, "mathLog10");
        this.b(var1, "mathToRadian");
        this.b(var1, "mathToDegree");
        this.b(var1, "isDrawerOpen");
        this.b(var1, "openDrawer");
        this.b(var1, "closeDrawer");
        this.b(var1, "viewOnClick");
        this.b(var1, "setEnable");
        this.b(var1, "getEnable");
        this.b(var1, "setVisible");
        this.b(var1, "setClickable");
        this.b(var1, "setText");
        this.b(var1, "setTypeface");
        this.b(var1, "getText");
        this.b(var1, "setBgColor");
        this.b(var1, "setBgResource");
        this.b(var1, "setTextColor");
        this.b(var1, "setHint");
        this.b(var1, "setHintTextColor");
        this.b(var1, "setImage");
        this.b(var1, "setColorFilter");
        this.b(var1, "requestFocus");
        this.b(var1, "setRotate");
        this.b(var1, "getRotate");
        this.b(var1, "setAlpha");
        this.b(var1, "getAlpha");
        this.b(var1, "setTranslationX");
        this.b(var1, "getTranslationX");
        this.b(var1, "setTranslationY");
        this.b(var1, "getTranslationY");
        this.b(var1, "setScaleX");
        this.b(var1, "getScaleX");
        this.b(var1, "setScaleY");
        this.b(var1, "getScaleY");
        this.b(var1, "getLocationX");
        this.b(var1, "getLocationY");
        this.b(var1, "setChecked");
        this.b(var1, "getChecked");
        this.b(var1, "setThumbResource");
        this.b(var1, "setTrackResource");
        this.b(var1, "listSetData");
        this.b(var1, "listSetCustomViewData");
        this.b(var1, "listRefresh");
        this.b(var1, "listSetItemChecked");
        this.b(var1, "listGetCheckedPosition");
        this.b(var1, "listGetCheckedPositions");
        this.b(var1, "listGetCheckedCount");
        this.b(var1, "listSmoothScrollTo");
        // BlackLogics built-in views.json widgets
        this.b(var1, "recyclerSetLayoutManagerLinear");
        this.b(var1, "recyclerSetLayoutManagerGrid");
        this.b(var1, "recyclerSetHasFixedSize");
        this.b(var1, "recyclerSetCustomViewData");
        this.b(var1, "recyclerNotifyDataSetChanged");
        this.b(var1, "recyclerNotifyItemInserted");
        this.b(var1, "recyclerNotifyItemRemoved");
        this.b(var1, "recyclerNotifyItemChanged");
        this.b(var1, "recyclerScrollToPosition");
        this.b(var1, "recyclerSmoothScrollToPosition");
        this.b(var1, "listSetSelection");
        this.b(var1, "gridSetNumColumns");
        this.b(var1, "expandableExpandGroup");
        this.b(var1, "expandableCollapseGroup");
        this.b(var1, "viewPagerSetAdapter");
        this.b(var1, "viewPagerNotifyDataSetChanged");
        this.b(var1, "viewPagerSetCurrentItem");
        this.b(var1, "viewPagerGetCurrentItem");
        this.b(var1, "viewPager2SetAdapter");
        this.b(var1, "viewPager2NotifyDataSetChanged");
        this.b(var1, "viewPager2SetCurrentItem");
        this.b(var1, "viewPager2GetCurrentItem");
        this.b(var1, "viewPager2SetUserInputEnabled");
        this.b(var1, "tabLayoutAddTab");
        this.b(var1, "tabLayoutGetSelected");
        this.b(var1, "tabLayoutRemoveAllTabs");
        this.b(var1, "tabLayoutSelectTab");
        this.b(var1, "bottomNavSetSelectedItemId");
        this.b(var1, "bottomNavGetSelectedItemId");
        this.b(var1, "navViewSetCheckedItem");
        this.b(var1, "cardViewSetRadius");
        this.b(var1, "cardViewSetCardElevation");
        this.b(var1, "cardViewSetCardBackgroundColor");
        this.b(var1, "ratingBarSetRating");
        this.b(var1, "ratingBarGetRating");
        this.b(var1, "ratingBarSetNumStars");
        this.b(var1, "progressBarSetProgress");
        this.b(var1, "progressBarGetProgress");
        this.b(var1, "progressBarSetMax");
        this.b(var1, "progressBarSetIndeterminate");
        this.b(var1, "seekBarSetProgress");
        this.b(var1, "seekBarGetProgress");
        this.b(var1, "seekBarSetMax");
        this.b(var1, "switchSetChecked");
        this.b(var1, "switchGetChecked");
        this.b(var1, "checkBoxSetChecked");
        this.b(var1, "checkBoxGetChecked");
        this.b(var1, "radioButtonSetChecked");
        this.b(var1, "radioButtonGetChecked");
        this.b(var1, "toggleButtonSetChecked");
        this.b(var1, "toggleButtonGetChecked");
        this.b(var1, "videoViewSetPath");
        this.b(var1, "videoViewStart");
        this.b(var1, "videoViewPause");
        this.b(var1, "videoViewStop");
        this.b(var1, "videoViewSeekTo");
        this.b(var1, "videoViewIsPlaying");
        this.b(var1, "searchViewSetQuery");
        this.b(var1, "searchViewGetQuery");
        this.b(var1, "searchViewSetIconified");
        this.b(var1, "chipSetText");
        this.b(var1, "chipGetText");
        this.b(var1, "chipSetCheckable");
        this.b(var1, "chipSetChecked");
        this.b(var1, "chipIsChecked");
        this.b(var1, "chipGroupSetSingleSelection");
        this.b(var1, "chipGroupClearCheck");
        this.b(var1, "textInputLayoutSetHint");
        this.b(var1, "textInputLayoutSetError");
        this.b(var1, "textInputLayoutSetErrorEnabled");
        this.b(var1, "materialButtonSetText");
        this.b(var1, "materialButtonGetText");
        this.b(var1, "materialButtonSetEnabled");
        this.b(var1, "lottieSetAnimation");
        this.b(var1, "lottiePlayAnimation");
        this.b(var1, "lottiePauseAnimation");
        this.b(var1, "lottieCancelAnimation");
        this.b(var1, "lottieSetRepeatCount");
        this.b(var1, "lottieSetSpeed");
        this.b(var1, "circleImageSetImageResource");
        this.b(var1, "imageButtonSetImageResource");
        this.b(var1, "nestedScrollSmoothScrollTo");
        this.b(var1, "scrollViewSmoothScrollTo");
        this.b(var1, "hScrollSmoothScrollTo");
        this.b(var1, "webViewLoadData");
        this.b(var1, "webViewEvaluateJavascript");
        this.b(var1, "spnSetData");
        this.b(var1, "spnRefresh");
        this.b(var1, "spnSetSelection");
        this.b(var1, "spnGetSelection");
        this.b(var1, "webViewLoadUrl");
        this.b(var1, "webViewGetUrl");
        this.b(var1, "webViewSetCacheMode");
        this.b(var1, "webViewCanGoBack");
        this.b(var1, "webViewCanGoForward");
        this.b(var1, "webViewGoBack");
        this.b(var1, "webViewGoForward");
        this.b(var1, "webViewClearCache");
        this.b(var1, "webViewClearHistory");
        this.b(var1, "webViewStopLoading");
        this.b(var1, "webViewZoomIn");
        this.b(var1, "webViewZoomOut");
        this.b(var1, "calendarViewGetDate");
        this.b(var1, "calendarViewSetDate");
        this.b(var1, "calendarViewSetMinDate");
        this.b(var1, "calnedarViewSetMaxDate");
        this.b(var1, "adViewLoadAd");
        this.b(var1, "mapViewSetMapType");
        this.b(var1, "mapViewMoveCamera");
        this.b(var1, "mapViewZoomTo");
        this.b(var1, "mapViewZoomIn");
        this.b(var1, "mapViewZoomOut");
        this.b(var1, "mapViewAddMarker");
        this.b(var1, "mapViewSetMarkerInfo");
        this.b(var1, "mapViewSetMarkerPosition");
        this.b(var1, "mapViewSetMarkerColor");
        this.b(var1, "mapViewSetMarkerIcon");
        this.b(var1, "mapViewSetMarkerVisible");
        this.b(var1, "intentSetAction");
        this.b(var1, "intentSetData");
        this.b(var1, "intentSetScreen");
        this.b(var1, "intentPutExtra");
        this.b(var1, "intentSetFlags");
        this.b(var1, "startActivity");
        this.b(var1, "intentGetString");
        this.b(var1, "finishActivity");
        this.b(var1, "fileGetData");
        this.b(var1, "fileSetData");
        this.b(var1, "fileRemoveData");
        this.b(var1, "calendarGetNow");
        this.b(var1, "calendarAdd");
        this.b(var1, "calendarSet");
        this.b(var1, "calendarFormat");
        this.b(var1, "calendarDiff");
        this.b(var1, "calendarGetTime");
        this.b(var1, "calendarSetTime");
        this.b(var1, "vibratorAction");
        this.b(var1, "timerAfter");
        this.b(var1, "timerEvery");
        this.b(var1, "timerCancel");
        this.b(var1, "dialogSetTitle");
        this.b(var1, "dialogSetMessage");
        this.b(var1, "dialogOkButton");
        this.b(var1, "dialogCancelButton");
        this.b(var1, "dialogNeutralButton");
        this.b(var1, "dialogShow");
        this.b(var1, "dialogDismiss");
        this.b(var1, "mediaplayerCreate");
        this.b(var1, "mediaplayerStart");
        this.b(var1, "mediaplayerPause");
        this.b(var1, "mediaplayerSeek");
        this.b(var1, "mediaplayerGetCurrent");
        this.b(var1, "mediaplayerGetDuration");
        this.b(var1, "mediaplayerIsPlaying");
        this.b(var1, "mediaplayerSetLooping");
        this.b(var1, "mediaplayerIsLooping");
        this.b(var1, "mediaplayerReset");
        this.b(var1, "mediaplayerRelease");
        this.b(var1, "soundpoolCreate");
        this.b(var1, "soundpoolLoad");
        this.b(var1, "soundpoolStreamPlay");
        this.b(var1, "soundpoolStreamStop");
        this.b(var1, "objectanimatorSetTarget");
        this.b(var1, "objectanimatorSetProperty");
        this.b(var1, "objectanimatorSetValue");
        this.b(var1, "objectanimatorSetFromTo");
        this.b(var1, "objectanimatorSetDuration");
        this.b(var1, "objectanimatorSetRepeatMode");
        this.b(var1, "objectanimatorSetRepeatCount");
        this.b(var1, "objectanimatorSetInterpolator");
        this.b(var1, "objectanimatorStart");
        this.b(var1, "objectanimatorCancel");
        this.b(var1, "objectanimatorIsRunning");
        this.b(var1, "firebaseAdd");
        this.b(var1, "firebasePush");
        this.b(var1, "firebaseGetPushKey");
        this.b(var1, "firebaseDelete");
        this.b(var1, "firebaseGetChildren");
        this.b(var1, "firebaseauthCreateUser");
        this.b(var1, "firebaseauthSignInUser");
        this.b(var1, "firebaseauthSignInAnonymously");
        this.b(var1, "firebaseauthIsLoggedIn");
        this.b(var1, "firebaseauthGetCurrentUser");
        this.b(var1, "firebaseauthGetUid");
        this.b(var1, "firebaseauthResetPassword");
        this.b(var1, "firebaseauthSignOutUser");
        this.b(var1, "firebaseStartListen");
        this.b(var1, "firebaseStopListen");
        this.b(var1, "gyroscopeStartListen");
        this.b(var1, "gyroscopeStopListen");
        this.b(var1, "interstitialadCreate");
        this.b(var1, "interstitialadLoadAd");
        this.b(var1, "interstitialadShow");
        this.b(var1, "firebasestorageUploadFile");
        this.b(var1, "firebasestorageDownloadFile");
        this.b(var1, "firebasestorageDelete");
        this.b(var1, "camerastarttakepicture");
        this.b(var1, "filepickerstartpickfiles");
        this.b(var1, "requestnetworkSetParams");
        this.b(var1, "requestnetworkSetHeaders");
        this.b(var1, "requestnetworkStartRequestNetwork");
        this.b(var1, "fileutildelete");
        this.b(var1, "fileutilcopy");
        this.b(var1, "fileutilwrite");
        this.b(var1, "fileutilread");
        this.b(var1, "fileutilmove");
        this.b(var1, "fileutilisexist");
        this.b(var1, "fileutilmakedir");
        this.b(var1, "fileutillistdir");
        this.b(var1, "fileutilisdir");
        this.b(var1, "fileutilisfile");
        this.b(var1, "fileutillength");
        this.b(var1, "fileutilStartsWith");
        this.b(var1, "fileutilEndsWith");
        this.b(var1, "fileutilGetLastSegmentPath");
        this.b(var1, "doToast");
        this.b(var1, "copyToClipboard");
        this.b(var1, "setTitle");
        this.b(var1, "seekBarGetMax");
        this.b(var1, "seekBarGetProgress");
        this.b(var1, "seekBarSetMax");
        this.b(var1, "seekBarSetProgress");
        this.b(var1, "getExternalStorageDir");
        this.b(var1, "getPackageDataDir");
        this.b(var1, "getPublicDir");
        this.b(var1, "resizeBitmapFileRetainRatio");
        this.b(var1, "resizeBitmapFileToSquare");
        this.b(var1, "resizeBitmapFileToCircle");
        this.b(var1, "resizeBitmapFileWithRoundedBorder");
        this.b(var1, "cropBitmapFileFromCenter");
        this.b(var1, "rotateBitmapFile");
        this.b(var1, "scaleBitmapFile");
        this.b(var1, "skewBitmapFile");
        this.b(var1, "setBitmapFileColorFilter");
        this.b(var1, "setBitmapFileBrightness");
        this.b(var1, "setBitmapFileContrast");
        this.b(var1, "setImageFilePath");
        this.b(var1, "setImageUrl");
        this.b(var1, "getJpegRotate");
        this.b(var1, "progressBarSetIndeterminate");
        this.b(var1, "textToSpeechSetPitch");
        this.b(var1, "textToSpeechSetSpeechRate");
        this.b(var1, "textToSpeechSpeak");
        this.b(var1, "textToSpeechIsSpeaking");
        this.b(var1, "textToSpeechStop");
        this.b(var1, "textToSpeechShutdown");
        this.b(var1, "speechToTextStartListening");
        this.b(var1, "speechToTextStopListening");
        this.b(var1, "speechToTextShutdown");
        this.b(var1, "bluetoothConnectReadyConnection");
        this.b(var1, "bluetoothConnectReadyConnectionToUuid");
        this.b(var1, "bluetoothConnectStartConnection");
        this.b(var1, "bluetoothConnectStartConnectionToUuid");
        this.b(var1, "bluetoothConnectStopConnection");
        this.b(var1, "bluetoothConnectSendData");
        this.b(var1, "bluetoothConnectIsBluetoothEnabled");
        this.b(var1, "bluetoothConnectIsBluetoothActivated");
        this.b(var1, "bluetoothConnectActivateBluetooth");
        this.b(var1, "bluetoothConnectGetPairedDevices");
        this.b(var1, "bluetoothConnectGetRandomUuid");
        this.b(var1, "locationManagerRequestLocationUpdates");
        this.b(var1, "locationManagerRemoveUpdates");
    }
    
    protected void b(Context var1, String var2) {
        // 1) Professional Sketchware-style full label (preferred)
        if (BL_PROFESSIONAL_LABELS.containsKey(var2)) {
            this.c.put(var2, BL_PROFESSIONAL_LABELS.get(var2));
            return;
        }
        // 2) Default resource-based assembly
        StringBuilder var3 = new StringBuilder();
        var3.append("block_");
        var3.append(eu.b(var2));
        String var5 = var3.toString();
        ArrayList var4 = eu.d(var2);
        if (var4 == null) {
            var4 = new ArrayList();
        }
        String label = this.a(var1, var5, var4);
        if (label == null || label.trim().length() == 0) {
            StringBuilder fb = new StringBuilder(var2);
            for (int i = 0; i < var4.size(); i++) {
                fb.append(" ").append(var4.get(i));
            }
            label = fb.toString();
        }
        this.c.put(var2, label);
    }
    
    protected void c(Context var1, String var2) {
        StringBuilder var3 = new StringBuilder();
        var3.append("root_spec_");
        var3.append(eu.a(var2));
        String var5 = var3.toString();
        ArrayList var4 = eu.c(var2);
        this.c.put(var2, this.a(var1, var5, var4));
    }
}
