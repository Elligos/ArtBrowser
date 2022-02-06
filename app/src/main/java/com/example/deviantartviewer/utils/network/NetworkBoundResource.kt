package com.example.deviantartviewer.utils.network

import com.example.deviantartviewer.data.model.Post
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


abstract class NetworkBoundResource() {

    private val result: Single<List<Post>>

    init {
        // Lazy disk observable.
        val diskObservable = Single.defer {
            loadFromDb()
                    .subscribeOn(Schedulers.computation())

        }

        // Lazy network observable.
        val networkObservable = Single.defer {
            createCall()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .doOnSuccess { request ->

                        saveCallResult(request)

                    }
                    .doOnError {
                        it
                    }
            //.flatMap { loadFromDb() }
        }

        result = if (shouldFetch()) {
            networkObservable
                    .map { it }
                    .doOnError { it }
                    .observeOn(AndroidSchedulers.mainThread())

        }
        else { diskObservable
                .map { it }
                .doOnError { it }
                .observeOn(AndroidSchedulers.mainThread())


        }
    }

    fun asSingle(): Single<List<Post>> {
        return result
    }

    protected abstract fun saveCallResult(request: List<Post>)

    protected abstract fun loadFromDb(): Single<List<Post>>

    protected abstract fun createCall(): Single<List<Post>>

    protected abstract fun shouldFetch(): Boolean


}