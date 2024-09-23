@file:JvmSynthetic

package com.worldline.connect.sdk.client.android.network.apicalls.product

import android.graphics.drawable.Drawable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

internal class DrawableObserver(
    private val update: (Drawable) -> Unit,
    private val error: () -> Unit
) :
    Observer<Drawable> {
    private var disposable: Disposable? = null
    override fun onNext(t: Drawable) {
        update(t)
        disposable?.dispose()
    }

    override fun onSubscribe(d: Disposable) {
        disposable?.dispose()
        disposable = d
    }

    override fun onError(e: Throwable) {
        error()
        disposable?.dispose()
    }

    override fun onComplete() {
        disposable?.dispose()
    }
}
