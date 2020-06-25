package rs.raf.projekat3.filip_draganic_RN54_2017.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat3.filip_draganic_RN54_2017.data.models.MyMarkerFilter
import rs.raf.projekat3.filip_draganic_RN54_2017.data.datasources.local.MyMarkerDao
import rs.raf.projekat3.filip_draganic_RN54_2017.data.extra.Converters
import rs.raf.projekat3.filip_draganic_RN54_2017.data.models.MyMarker
import rs.raf.projekat3.filip_draganic_RN54_2017.data.models.MyMarkerEntity
import timber.log.Timber

class MyMarkerRepositoryImpl(private val localDataSource: MyMarkerDao) : MyMarkerRepository{



    override fun getAll(): Observable<List<MyMarker>> {
        Timber.e("Getovanje markera")

        return localDataSource
            .getAll()
            .map {
                it.map {
                    MyMarker(it.uid, it.title, it.note, it.latitude, it.longitude, Converters().fromTimestamp(it.dateCreated))
                }
            }

    }

    override fun insert(myMarker: MyMarker): Completable {
        Timber.e("Insertovanje markera")

        val myMarkerEntity = MyMarkerEntity(myMarker.id, myMarker.title, myMarker.note, myMarker.latitude, myMarker.longitude, Converters().dateToTimestamp(myMarker.date))

        return localDataSource
            .insert(myMarkerEntity)
    }

    override fun getBySearchQuery(myMarkerFilter: MyMarkerFilter): Observable<List<MyMarker>> {
        var orijentacija = "ASC"
        if (myMarkerFilter.fabGore)
            {
                return localDataSource
                .getSearchQueryASC(myMarkerFilter.searchQuery)
                .map {
                    it.map{
                        MyMarker(it.uid, it.title, it.note, it.latitude, it.longitude, Converters().fromTimestamp(it.dateCreated))
                    }
                }
            }
        else{
            return localDataSource
                .getSearchQueryDESC(myMarkerFilter.searchQuery)
                .map {
                    it.map{
                        MyMarker(it.uid, it.title, it.note, it.latitude, it.longitude, Converters().fromTimestamp(it.dateCreated))
                    }
                }
        }
    }

    override fun updateMyMarker(myMarker: MyMarker): Completable {
        val mymarkerEntity = MyMarkerEntity(myMarker.id, myMarker.title, myMarker.note, myMarker.latitude, myMarker.longitude, Converters().dateToTimestamp(myMarker.date))

        Timber.e("UPDATEOVANJE U TOKU")
        Timber.e(myMarker.toString())
        return localDataSource
            .updateMyMarker(mymarkerEntity)

    }

    override fun delete(myMarker: MyMarker): Completable {
        val mymarkerEntity = MyMarkerEntity(myMarker.id, myMarker.title, myMarker.note, myMarker.latitude, myMarker.longitude, Converters().dateToTimestamp(myMarker.date))

        return localDataSource
            .delete(mymarkerEntity)

    }

}