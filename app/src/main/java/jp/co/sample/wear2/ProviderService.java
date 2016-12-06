/*
 * Copyright 2016 Kenji Matsuoka(Kenz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
