package com.enderx.jobsearcher.core.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type

class MultipleConverter : Converter.Factory() {

    private val scalarFactory = ScalarsConverterFactory.create()
    private val jsonFactory = GsonConverterFactory.create()

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation?>,
        methodAnnotations: Array<Annotation?>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val defaultConverter = jsonFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
        methodAnnotations.forEach { annotation ->
            if (annotation is RequestFormat) {
                return when (annotation.value) {
                    ConverterFormat.JSON -> defaultConverter
                    ConverterFormat.SCALAR -> scalarFactory.requestBodyConverter(
                        type,
                        parameterAnnotations,
                        methodAnnotations,
                        retrofit
                    )
                }
            }
        }
        return defaultConverter
    }


    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation?>,
        retrofit: Retrofit
    ): Converter<ResponseBody?, *>? {
        val defaultConverter = jsonFactory.responseBodyConverter(
            type,
            annotations,
            retrofit
        )
        annotations.forEach { annotation ->
            if (annotation is ResponseFormat) {
                return when (annotation.value) {
                    ConverterFormat.JSON -> defaultConverter
                    ConverterFormat.SCALAR -> scalarFactory.responseBodyConverter(
                        type,
                        annotations,
                        retrofit
                    )
                }
            }
        }
        return defaultConverter
    }
}