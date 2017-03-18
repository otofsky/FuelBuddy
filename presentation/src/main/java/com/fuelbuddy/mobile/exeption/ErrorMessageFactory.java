/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuelbuddy.mobile.exeption;

import android.content.Context;

import com.fuelbuddy.data.exeption.GasStationsNotFoundException;
import com.fuelbuddy.data.exeption.NetworkConnectionException;
import com.fuelbuddy.data.exeption.UserNotFoundException;
import com.fuelbuddy.mobile.R;


/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
    }

    public static String create(Context context, Exception exception) {

        String message = context.getString(R.string.exception_message_generic);

        if (exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection);
        } else if (exception instanceof UserNotFoundException) {
            message = context.getString(R.string.exception_message_user_not_found);
        } else if (exception instanceof GasStationsNotFoundException) {
            message = context.getString(R.string.exception_message_user_not_found);
        }

        return message;
    }
}
