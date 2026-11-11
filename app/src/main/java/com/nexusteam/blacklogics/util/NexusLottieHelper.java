package com.nexusteam.blacklogics.util;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public final class NexusLottieHelper {

    private static final String TAG = "NexusLottieHelper";
    private static final String ASSET_FILE = "nexusteam_blacklogics.json";

    private NexusLottieHelper() {
    }

    public static void apply(LottieAnimationView animationView) {
        if (animationView == null || animationView.getContext() == null) {
            return;
        }

        final Context context = animationView.getContext();
        final LottieAnimationView view = animationView;

        try {
            String originalJson = loadFromAssets(context);
            if (originalJson == null) {
                Log.e(TAG, "Asset not found: " + ASSET_FILE);
                return;
            }

            String themedJson = applyMaterial3Theme(context, originalJson);

            LottieCompositionFactory.fromJsonString(themedJson, "nexus_brand_themed")
                    .addListener(new LottieListener<LottieComposition>() {
                        @Override
                        public void onResult(LottieComposition composition) {
                            view.setComposition(composition);
                            view.setRepeatCount(0);
                            view.playAnimation();
                        }
                    })
                    .addFailureListener(new LottieListener<Throwable>() {
                        @Override
                        public void onResult(Throwable result) {
                            Log.e(TAG, "Failed to create composition", result);
                        }
                    });

        } catch (Exception e) {
            Log.e(TAG, "Error applying animation", e);
        }
    }


    private static String loadFromAssets(Context context) {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = context.getAssets().open(ASSET_FILE);
            reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "Failed to load asset", e);
            return null;
        } finally {
            try {
                if (reader != null) reader.close();
                if (is != null) is.close();
            } catch (Exception ignored) {
            }
        }
    }

    private static String applyMaterial3Theme(Context context, String json) throws JSONException {
        JSONObject root = new JSONObject(json);

        float[] primary = toFloatArray(getThemeColor(context, com.google.android.material.R.attr.colorPrimary));
        float[] tertiary = toFloatArray(getThemeColor(context, com.google.android.material.R.attr.colorTertiary));
        float[] onSurface = toFloatArray(getThemeColor(context, com.google.android.material.R.attr.colorOnSurface));
        float[] onPrimaryContainer = toFloatArray(getThemeColor(context, com.google.android.material.R.attr.colorOnPrimaryContainer));
        float[] onTertiaryContainer = toFloatArray(getThemeColor(context, com.google.android.material.R.attr.colorOnTertiaryContainer));
        float[] primaryContainer = toFloatArray(getThemeColor(context, com.google.android.material.R.attr.colorPrimaryContainer));

        JSONArray layers = root.getJSONArray("layers");
        for (int i = 0; i < layers.length(); i++) {
            JSONObject layer = layers.getJSONObject(i);
            String name = layer.optString("nm", "");

            if ("bg".equals(name)) {
                makeBackgroundTransparent(layer);
                continue;
            }

            replaceColors(layer, primary, tertiary, onSurface,
                    onPrimaryContainer, onTertiaryContainer, primaryContainer);
        }

        return root.toString();
    }

    private static int getThemeColor(Context context, int attr) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value.data;
    }

    private static void makeBackgroundTransparent(JSONObject layer) throws JSONException {
        JSONObject ks = layer.optJSONObject("ks");
        if (ks != null) {
            JSONObject opacity = ks.optJSONObject("o");
            if (opacity != null) {
                opacity.put("a", 0);
                opacity.put("k", 0);
            }
        }

        JSONArray shapes = layer.optJSONArray("shapes");
        if (shapes == null) return;

        for (int i = 0; i < shapes.length(); i++) {
            JSONObject group = shapes.getJSONObject(i);
            JSONArray items = group.optJSONArray("it");
            if (items == null) continue;

            for (int j = 0; j < items.length(); j++) {
                JSONObject item = items.getJSONObject(j);
                if ("fl".equals(item.optString("ty"))) {
                    JSONObject o = item.optJSONObject("o");
                    if (o != null) {
                        o.put("a", 0);
                        o.put("k", 0);
                    }
                }
            }
        }
    }

    private static void replaceColors(JSONObject layer,
                                      float[] primary, float[] tertiary, float[] onSurface,
                                      float[] onPrimaryContainer, float[] onTertiaryContainer,
                                      float[] primaryContainer) throws JSONException {

        JSONArray shapes = layer.optJSONArray("shapes");
        if (shapes == null) return;

        for (int i = 0; i < shapes.length(); i++) {
            walk(shapes.getJSONObject(i), primary, tertiary, onSurface,
                    onPrimaryContainer, onTertiaryContainer, primaryContainer);
        }
    }

    private static void walk(JSONObject shape,
                             float[] primary, float[] tertiary, float[] onSurface,
                             float[] onPrimaryContainer, float[] onTertiaryContainer,
                             float[] primaryContainer) throws JSONException {

        String type = shape.optString("ty");
        String name = shape.optString("nm", "");

        if ("st".equals(type) || "fl".equals(type)) {
            JSONObject colorObj = shape.optJSONObject("c");
            if (colorObj != null) {
                applyColor(colorObj, name, primary, tertiary, onSurface,
                        onPrimaryContainer, onTertiaryContainer, primaryContainer);
            }
        }

        if ("gr".equals(type)) {
            JSONArray items = shape.optJSONArray("it");
            if (items != null) {
                for (int i = 0; i < items.length(); i++) {
                    walk(items.getJSONObject(i), primary, tertiary, onSurface,
                            onPrimaryContainer, onTertiaryContainer, primaryContainer);
                }
            }
        }
    }

    private static void applyColor(JSONObject colorObj, String name,
                                   float[] primary, float[] tertiary, float[] onSurface,
                                   float[] onPrimaryContainer, float[] onTertiaryContainer,
                                   float[] primaryContainer) throws JSONException {

        float[] target = resolveColor(name, primary, tertiary, onSurface,
                onPrimaryContainer, onTertiaryContainer, primaryContainer);
        if (target == null) return;

        if (colorObj.optInt("a", 0) == 0) {
            JSONArray k = colorObj.optJSONArray("k");
            if (k != null && k.length() >= 3) {
                k.put(0, target[0]);
                k.put(1, target[1]);
                k.put(2, target[2]);
                if (k.length() > 3) k.put(3, 1.0);
            }
        } else {
            JSONArray keyframes = colorObj.optJSONArray("k");
            if (keyframes != null) {
                for (int i = 0; i < keyframes.length(); i++) {
                    JSONObject kf = keyframes.optJSONObject(i);
                    if (kf != null) {
                        JSONArray s = kf.optJSONArray("s");
                        if (s != null && s.length() >= 3) {
                            s.put(0, target[0]);
                            s.put(1, target[1]);
                            s.put(2, target[2]);
                            if (s.length() > 3) s.put(3, 1.0);
                        }
                    }
                }
            }
        }
    }

    private static float[] resolveColor(String name,
                                        float[] primary, float[] tertiary, float[] onSurface,
                                        float[] onPrimaryContainer, float[] onTertiaryContainer,
                                        float[] primaryContainer) {

        if (name.contains("c_primary")) return primary;
        if (name.contains("c_tertiary")) return tertiary;
        if (name.contains("c_onSurface")) return onSurface;
        if (name.contains("c_onPrimaryContainer")) return onPrimaryContainer;
        if (name.contains("c_onTertiaryContainer")) return onTertiaryContainer;
        if (name.contains("c_container")) return primaryContainer;
        return null;
    }

    private static float[] toFloatArray(int color) {
        return new float[]{
                Color.red(color) / 255f,
                Color.green(color) / 255f,
                Color.blue(color) / 255f,
                1f
        };
    }
}