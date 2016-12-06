package jp.co.sample.wear2;

import java.util.List;

/**
 * 天気情報のレスポンス
 * Created by kenz on 12/6/16.
 */
class ResultWeather {
    List<Forecast> forecasts;
    Description description;
    static class Forecast{
        String telop;
    }
    static class Description{
        String text;
    }
}
