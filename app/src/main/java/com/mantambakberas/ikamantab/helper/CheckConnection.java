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

package com.mantambakberas.ikamantab.helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by winnerawan on 11/11/16.
 */

public class CheckConnection {

    ConnectivityManager cm;
    NetworkInfo networkInfo;
    int type=0;

    public Context context;
    public CheckConnection(Context _context) {
        this.context=_context;
        cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = cm.getActiveNetworkInfo();
    }

    public int connectionType() {
        networkInfo = cm.getActiveNetworkInfo();
        type = networkInfo.getType();

        return type;
    }
}
