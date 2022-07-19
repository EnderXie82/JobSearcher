package com.enderx.jobsearcher.core.converter

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestFormat(val value: ConverterFormat = ConverterFormat.SCALAR)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ResponseFormat(val value: ConverterFormat = ConverterFormat.JSON)