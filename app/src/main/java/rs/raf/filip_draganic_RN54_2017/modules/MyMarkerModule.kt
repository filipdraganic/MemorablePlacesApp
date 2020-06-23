package rs.raf.filip_draganic_RN54_2017.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.filip_draganic_RN54_2017.data.datasources.local.MyMarkerDatabase
import rs.raf.filip_draganic_RN54_2017.data.repositories.MyMarkerRepository
import rs.raf.filip_draganic_RN54_2017.data.repositories.MyMarkerRepositoryImpl
import rs.raf.filip_draganic_RN54_2017.presentation.viewmodel.MyMarkerViewModel

val myMarkerModule = module{

    viewModel{ MyMarkerViewModel(get()) }

    single<MyMarkerRepository> {MyMarkerRepositoryImpl(get())}

    single {get<MyMarkerDatabase>().myMarkerDao()}
}
