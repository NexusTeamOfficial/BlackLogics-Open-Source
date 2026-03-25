package com.nexusteam.blacklogics.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nexusteam.blacklogics.R;
import android.app.Activity;
import android.util.Log;
import java.util.Arrays;
import java.util.List;

public class AdLayout extends FrameLayout {
	
	private AdView bannerAdView;
	private InterstitialAd interstitialAd;
	private String bannerAdUnitId;
	private String interstitialAdUnitId;
	private AdSize adSize = AdSize.SMART_BANNER;
	private AdListener adListener;
	private OnAdLoadedListener onAdLoadedListener;
	private OnAdFailedListener onAdFailedListener;
	private static final String TAG = "AdLayout";
	
	public static final String REAL_BANNER_AD_UNIT_ID = "ca-app-pub-2903137488772866/3754424748";
	public static final String REAL_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-2903137488772866/3754424748";
	public static final String REAL_APP_ID = "ca-app-pub-2903137488772866~4592346770";
	
	public static final String TEST_DEVICE_ID = "D4516BAC0B080049F0FABC1AD69374C4";
	
	public interface OnAdLoadedListener {
		void onLoaded();
	}
	
	public interface OnAdFailedListener {
		void onFailed(String error);
	}
	
	public AdLayout(Context context) {
		super(context);
		init(context, null);
	}
	
	public AdLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	public AdLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AdLayout);
			bannerAdUnitId = ta.getString(R.styleable.AdLayout_bannerAdUnitId);
			interstitialAdUnitId = ta.getString(R.styleable.AdLayout_interstitialAdUnitId);
			
			int adSizeValue = ta.getInt(R.styleable.AdLayout_adSize, 0);
			switch (adSizeValue) {
				case 1:
				adSize = AdSize.BANNER;
				break;
				case 2:
				adSize = AdSize.LARGE_BANNER;
				break;
				case 3:
				adSize = AdSize.MEDIUM_RECTANGLE;
				break;
				case 4:
				adSize = AdSize.FULL_BANNER;
				break;
				case 5:
				adSize = AdSize.LEADERBOARD;
				break;
				default:
				adSize = AdSize.SMART_BANNER;
				break;
			}
			ta.recycle();
		}
		
		if (bannerAdUnitId == null) {
			bannerAdUnitId = REAL_BANNER_AD_UNIT_ID;
		}
		if (interstitialAdUnitId == null) {
			interstitialAdUnitId = REAL_INTERSTITIAL_AD_UNIT_ID;
		}
		
		// Initialize Mobile Ads and set test devices
		MobileAds.initialize(context, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
				Log.d(TAG, "Mobile Ads initialized");
			}
		});
		
		List<String> testDeviceIds = Arrays.asList(TEST_DEVICE_ID);
		MobileAds.setRequestConfiguration(
			new RequestConfiguration.Builder()
				.setTestDeviceIds(testDeviceIds)
				.build()
		);
		
		setupAdListener();
	}
	
	private void setupAdListener() {
		adListener = new AdListener() {
			@Override
			public void onAdClicked() {
				super.onAdClicked();
				Log.d(TAG, "Ad clicked");
			}
			
			@Override
			public void onAdClosed() {
				super.onAdClosed();
				Log.d(TAG, "Ad closed");
			}
			
			@Override
			public void onAdFailedToLoad(LoadAdError loadAdError) {
				super.onAdFailedToLoad(loadAdError);
				int errorCode = loadAdError.getCode();
				String errorMessage = getErrorMessage(errorCode);
				Log.e(TAG, "Ad failed to load: " + errorMessage + " (Code: " + errorCode + ")");
				if (onAdFailedListener != null) {
					onAdFailedListener.onFailed(errorMessage);
				}
			}
			
			@Override
			public void onAdImpression() {
				super.onAdImpression();
				Log.d(TAG, "Ad impression");
			}
			
			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				Log.d(TAG, "Ad loaded successfully");
				if (onAdLoadedListener != null) {
					onAdLoadedListener.onLoaded();
				}
			}
			
			@Override
			public void onAdOpened() {
				super.onAdOpened();
				Log.d(TAG, "Ad opened");
			}
		};
	}
	
	private String getErrorMessage(int errorCode) {
		switch (errorCode) {
			case AdRequest.ERROR_CODE_INTERNAL_ERROR:
			return "Internal error";
			case AdRequest.ERROR_CODE_INVALID_REQUEST:
			return "Invalid request";
			case AdRequest.ERROR_CODE_NETWORK_ERROR:
			return "Network error";
			case AdRequest.ERROR_CODE_NO_FILL:
			return "No fill";
			default:
			return "Unknown error";
		}
	}
	
	public void loadBannerAd() {
		removeAllViews();
		
		bannerAdView = new AdView(getContext());
		bannerAdView.setAdSize(adSize);
		bannerAdView.setAdUnitId(bannerAdUnitId);
		bannerAdView.setAdListener(adListener);
		
		LayoutParams params = new LayoutParams(
		LayoutParams.MATCH_PARENT,
		LayoutParams.WRAP_CONTENT
		);
		bannerAdView.setLayoutParams(params);
		addView(bannerAdView);
		
		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
		
		// Test device add karo - now handled by global configuration
		// adRequestBuilder.addTestDevice(TEST_DEVICE_ID);
		
		AdRequest adRequest = adRequestBuilder.build();
		bannerAdView.loadAd(adRequest);
	}
	
	public void loadInterstitialAd() {
		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
		
		// Test device add karo - now handled by global configuration
		// adRequestBuilder.addTestDevice(TEST_DEVICE_ID);
		
		AdRequest adRequest = adRequestBuilder.build();
		
		InterstitialAd.load(getContext(), interstitialAdUnitId, adRequest,
		new InterstitialAdLoadCallback() {
			@Override
			public void onAdLoaded(InterstitialAd ad) {
				interstitialAd = ad;
				Log.d(TAG, "Interstitial ad loaded");
				
				interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
					@Override
					public void onAdClicked() {
						Log.d(TAG, "Interstitial ad clicked");
					}
					
					@Override
					public void onAdDismissedFullScreenContent() {
						Log.d(TAG, "Interstitial ad dismissed");
						interstitialAd = null;
						// Ad dismiss hone par naya load karo
						loadInterstitialAd();
					}
					
					@Override
					public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
						Log.e(TAG, "Interstitial ad failed to show: " + adError.getMessage());
						interstitialAd = null;
					}
					
					@Override
					public void onAdImpression() {
						Log.d(TAG, "Interstitial ad impression");
					}
					
					@Override
					public void onAdShowedFullScreenContent() {
						Log.d(TAG, "Interstitial ad showed");
					}
				});
				
				if (onAdLoadedListener != null) {
					onAdLoadedListener.onLoaded();
				}
			}
			
			@Override
			public void onAdFailedToLoad(LoadAdError loadAdError) {
				interstitialAd = null;
				String errorMessage = getErrorMessage(loadAdError.getCode());
				Log.e(TAG, "Interstitial ad failed to load: " + errorMessage);
				if (onAdFailedListener != null) {
					onAdFailedListener.onFailed(errorMessage);
				}
			}
		});
	}
	
	/**
	* Interstitial ad show karo
	*/	
	public void showInterstitialAd() {
		if (interstitialAd != null) {
			interstitialAd.show((Activity) getContext());
		} else {
			Log.d(TAG, "Interstitial ad not loaded yet");
		}
	}
	
	/**
	* Banner ad destroy karo
	*/	
	public void destroyBannerAd() {
		if (bannerAdView != null) {
			bannerAdView.destroy();
			bannerAdView = null;
		}
	}
	
	/**
	* Banner ad pause karo
	*/	
	public void pauseBannerAd() {
		if (bannerAdView != null) {
			bannerAdView.pause();
		}
	}
	
	/**
	* Banner ad resume karo
	*/	
	public void resumeBannerAd() {
		if (bannerAdView != null) {
			bannerAdView.resume();
		}
	}
	
	// Getters and Setters
	public void setBannerAdUnitId(String adUnitId) {
		this.bannerAdUnitId = adUnitId;
	}
	
	public void setInterstitialAdUnitId(String adUnitId) {
		this.interstitialAdUnitId = adUnitId;
	}
	
	public void setAdSize(AdSize adSize) {
		this.adSize = adSize;
	}
	
	public void setOnAdLoadedListener(OnAdLoadedListener listener) {
		this.onAdLoadedListener = listener;
	}
	
	public void setOnAdFailedListener(OnAdFailedListener listener) {
		this.onAdFailedListener = listener;
	}
	
	public boolean isInterstitialAdLoaded() {
		return interstitialAd != null;
	}
	
	public String getBannerAdUnitId() {
		return bannerAdUnitId;
	}
	
	public String getInterstitialAdUnitId() {
		return interstitialAdUnitId;
	}
}