/*
 * Copyright (c) 2016. Ikamantab (Ikatan Keluarga Alumni Man Tambakberas).
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file   except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License..
 */

package com.mantambakberas.ikamantab.request;

import com.mantambakberas.ikamantab.config.AppConfig;

import retrofit.RestAdapter;

/**
 * Created by winnerawan on 11/11/16.
 */

public class RequestWithoutKey {

    public RestAdapter RequestWithoutKey() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AppConfig.BASE_URL).build();
        return restAdapter;
    }
}
