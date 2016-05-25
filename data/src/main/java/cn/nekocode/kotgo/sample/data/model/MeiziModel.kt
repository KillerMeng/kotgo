package cn.nekocode.kotgo.sample.data.model

import cn.nekocode.kotgo.lib.store.Store
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.data.exception.GankServiceException
import cn.nekocode.kotgo.sample.data.service.GankService
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by nekocode on 2016/1/13.
 */
object MeiziModel {

    // Bussines Logic
    fun getMeizis(count: Int, pageNum: Int): Observable<List<Meizi>> =
            GankService.api.getMeizi(count, pageNum)
                    .subscribeOn(Schedulers.io())
                    .map {
                        Store["meizis"] = it.results
                        it.results
                    }
                    .onErrorResumeNext {
                        // Fetech data from local cache
                        val meiziList: List<Meizi> = Store["meizis"]
                                ?: throw GankServiceException(it.message)
                        Observable.just(meiziList)
                    }

}