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


import com.fuelbuddy.data.net.RetrofitException;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.model.ErrorResponse;

import retrofit2.Response;


/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
    }

    public static ErrorResponse create(Context context, Exception exception) {
        ErrorResponse errorResponse = null;
        Response response;
        if (exception instanceof RetrofitException) {
            RetrofitException error = (RetrofitException) exception;
           response  = error.getResponse();
            error.getKind();
            switch (error.getKind()) {
                case HTTP:
                    errorResponse = new ErrorResponse(response.code(),response.message());
                    break;
                case NETWORK:
                    errorResponse = new ErrorResponse(context.getString(R.string.exception_message_no_connection));
                    break;
                case UNEXPECTED:
                    errorResponse = new ErrorResponse(context.getString(R.string.exception_message_generic));
                    break;
            }
        }
        return errorResponse;
    }
}