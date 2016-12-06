package jp.co.sample.wear2;

import android.support.wearable.complications.ComplicationData;
import android.support.wearable.complications.ComplicationManager;
import android.support.wearable.complications.ComplicationProviderService;
import android.support.wearable.complications.ComplicationText;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * データ提供を行う
 * Created by kenz on 12/6/16.
 */
public class ProviderService extends ComplicationProviderService {

    @Override
    public void onComplicationUpdate(int complicationId, int dataType, ComplicationManager complicationManager) {
        // 福岡市のデータを取得
        Network.api.getWeather("400010").
                subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.forecasts.size() < 0) {
                        return; // データを取れなかったら何もしない
                    }
                    ResultWeather.Forecast forecast = result.forecasts.get(0);
                    ComplicationData complicationData;
                    // データを取得できたら定型文に整形して
                    if (dataType == ComplicationData.TYPE_SHORT_TEXT) {
                        complicationData = new ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
                                .setShortText(ComplicationText.plainText(forecast.telop))
                                .build();
                    } else if (dataType == ComplicationData.TYPE_LONG_TEXT) {
                        complicationData = new ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
                                .setLongTitle(ComplicationText.plainText(forecast.telop))
                                .setLongText(ComplicationText.plainText(result.description.text))
                                .build();
                    } else {
                        return;
                    }
                    // Complications に投げる
                    complicationManager.updateComplicationData(complicationId, complicationData);

                });

    }
}
