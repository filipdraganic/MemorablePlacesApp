package rs.raf.filip_draganic_RN54_2017.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import rs.raf.filip_draganic_RN54_2017.data.models.MyMarkerEntity
import io.reactivex.Observable

@Dao
interface MyMarkerDao{

    @Query("SELECT * FROM mymarkers")
    fun getAll(): Observable<List<MyMarkerEntity>>

    @Query("SELECT * FROM mymarkers WHERE title LIKE '%' || :searchQuery ||'%' OR note LIKE '%' ||:searchQuery || '%' ORDER BY date ASC")
    fun getSearchQueryASC(searchQuery: String): Observable<List<MyMarkerEntity>>

    @Query("SELECT * FROM mymarkers WHERE title LIKE '%' || :searchQuery ||'%' OR note LIKE '%' ||:searchQuery || '%'  ORDER BY date DESC")
    fun getSearchQueryDESC(searchQuery: String): Observable<List<MyMarkerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(myMarker: MyMarkerEntity): Completable

    @Insert
    fun insertAll(vararg myMarker: MyMarkerEntity): Completable

    @Delete
    fun delete(myMarker: MyMarkerEntity): Completable

    @Delete
    fun deleteAll(vararg myMarker: MyMarkerEntity): Completable

    @Update
    fun updateMyMarker(vararg myMarker: MyMarkerEntity): Completable

}