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
