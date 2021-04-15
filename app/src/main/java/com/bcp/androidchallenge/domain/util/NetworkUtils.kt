package com.bcp.androidchallenge.domain.util

import com.bcp.androidchallenge.data.remote.api.DoNothing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

suspend fun <T : Any> safeApiCall(call: suspend () -> ResultType<T>): ResultType<T> {
    return withContext(Dispatchers.IO) {
        try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            if(e is DoNothing){
                ResultType.Error(IOException(e.message))
            }else{
                ResultType.Error(ErrorGeneric())
            }

        }
    }
}