package com.zedorff.yobabooker.app.di.annotations

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.Documented
import kotlin.reflect.KClass


@kotlin.annotation.MustBeDocumented
@kotlin.annotation.Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)