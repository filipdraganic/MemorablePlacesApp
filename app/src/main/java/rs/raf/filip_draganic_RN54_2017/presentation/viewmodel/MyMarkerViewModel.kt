package rs.raf.filip_draganic_RN54_2017.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.filip_draganic_RN54_2017.data.models.MyMarkerFilter
import rs.raf.filip_draganic_RN54_2017.data.models.MyMarker
import rs.raf.filip_draganic_RN54_2017.data.repositories.MyMarkerRepository
import rs.raf.filip_draganic_RN54_2017.presentation.contract.MyMarkerContract
import rs.raf.filip_draganic_RN54_2017.presentation.view.states.AddMyMarkerState
import rs.raf.filip_draganic_RN54_2017.presentation.view.states.MyMarkerState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MyMarkerViewModel(private val myMarkerRepository: MyMarkerRepository): ViewModel(), MyMarkerContract.ViewModel{

    private val subscriptions = CompositeDisposable()

    override val myMarkerState: MutableLiveData<MyMarkerState> = MutableLiveData()

    override val addDone: MutableLiveData<AddMyMarkerState> = MutableLiveData()

    private val publishSubject: PublishSubject<MyMarkerFilter> = PublishSubject.create()

    init{
        Timber.e("Init u MyMarkerViewModel")

        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                myMarkerRepository
                    .getBySearchQuery(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in public subject u init{}")
                        Timber.e(it)
                    }

            }
            .subscribe(
                {
                    myMarkerState.value = MyMarkerState.Success(it)
                },
                {
                    myMarkerState.value = MyMarkerState.Error("Error pri getovanju svih u init()")
                }
            )
        subscriptions.add(subscription)

    }

    override fun getAll() {
        val subscription = myMarkerRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e(it.toString())
                    myMarkerState.value = MyMarkerState.Success(it)
                },
                {
                    Timber.e(it.toString())
                    myMarkerState.value = MyMarkerState.Error("Error happened when fetching from db")
                }

            )
        subscriptions.add(subscription)

    }

    override fun insert(myMarker: MyMarker) {
        val subscription = myMarkerRepository
            .insert(myMarker)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddMyMarkerState.Success
                },
                {
                    addDone.value = AddMyMarkerState.Error("Error when adding Marker")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)

    }

    override fun getSearchQuery(searchQuery: String, fabGore: Boolean) {
        val filter = MyMarkerFilter(
            searchQuery,
            fabGore
        )
        publishSubject.onNext(filter)
    }

    override fun updateMyMarker(myMarker: MyMarker) {
        val subscription = myMarkerRepository
            .updateMyMarker(myMarker)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    myMarkerState.value = MyMarkerState.Update
                },
                {
                    myMarkerState.value = MyMarkerState.Error("Error while updating marker")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

}