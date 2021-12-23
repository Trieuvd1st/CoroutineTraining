package com.example.mvvm_baseproject.ui.splash

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm_baseproject.data.sharedPrefs.RxPreferences
import com.example.mvvm_baseproject.domain.network.api.ApiService
import com.example.mvvm_baseproject.domain.network.pojo.response.Product
import com.example.mvvm_baseproject.domain.network.pojo.response.Rating
import com.example.mvvm_baseproject.rx.RxSchedulers
import com.example.mvvm_baseproject.ui.base.BaseViewModel
import com.example.mvvm_baseproject.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val apiService: ApiService,
    private val rxPreferences: RxPreferences,
    private val rxSchedulers: RxSchedulers
) : BaseViewModel() {

    val actionSPlash = SingleLiveEvent<SplashActionState>()

    private val handler = Handler(Looper.getMainLooper())

    val liveText = MutableLiveData<String>()

    fun testCoroutineWithRx() {
        viewModelScope.launch(Dispatchers.IO) {
            apiService.getListProduct()
                .subscribe {
                    Timber.d("get product id: ${it[0].id}")
                    //liveText.value = it.firstOrNull()?.title
                }
        }
    }

    @SuppressLint("CheckResult")
    fun testMergeObservable() {
        val listOb = arrayListOf<Observable<Product>>()
//        apiService.getListProduct().subscribeOn(rxSchedulers.io())
//            .observeOn(rxSchedulers.androidThread())
//            .subscribe {
//                Timber.d("get product id: ${it[0].id}")
//            }
        listOb.add(apiService.getProductById(1))
        listOb.add(apiService.getProductById(2))
        listOb.add(apiService.getProductById(3))
//        listOb.add(apiService.getProductById(4).delay(10000L, TimeUnit.MILLISECONDS))
//        listOb.add(apiService.getProductById(5))
//        listOb.add(apiService.getProductById(6))
//        listOb.add(apiService.getProductById(7))
        Observable.zip(
            apiService.getProductById(1),
            apiService.getProductById(2).delay(10000L, TimeUnit.MILLISECONDS),
            apiService.getProductById(3),
            { it1, it2, it3 ->
                "đúng cmnr"
            })
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { it ->
                    Timber.d("data result: ${it}")
                }, {}, {}
            )
//            ?.observeOn(AndroidSchedulers.mainThread())?
//        Observable.merge(listOb).subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
//                {
//                    Timber.d("product id: ${it.id}")
//                }, {
//
//                }
//            )
    }

    @SuppressLint("CheckResult")
    fun researchThrottle() {
        Single.create<Product> { emitter ->
            val product = Product(1, "fgsf", 23.3, "ffjdkshf", "fslkj", "jhsdfkjsdfhk", Rating(23.2, 4))
            emitter.onSuccess(product) }
            .subscribe { product ->
                Timber.d("result product: ${product.id}")
            }
//        apiService.getProductById(1).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .throttleLatest(3000, TimeUnit.MILLISECONDS)
//            .subscribe(
//                { it ->
//                    Timber.d("product id: ${it.id}")
//                }, {}
//            )
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(null)
        super.onCleared()
    }
}

sealed class SplashActionState {
    object Finish : SplashActionState()
}