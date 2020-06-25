package rs.raf.projekat3.filip_draganic_RN54_2017.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat3.filip_draganic_RN54_2017.data.models.MyMarkerFilter
import rs.raf.projekat3.filip_draganic_RN54_2017.data.models.MyMarker

interface MyMarkerRepository{

    fun getAll(): Observable<List<MyMarker>>
    fun insert(myMarker: MyMarker): Completable
    fun getBySearchQuery(myMarkerFilter: MyMarkerFilter): Observable<List<MyMarker>>
    fun updateMyMarker(myMarker: MyMarker): Completable
    fun delete(myMarker: MyMarker): Completable


}