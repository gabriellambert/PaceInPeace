package dominando.android.paceinpeacemvp.di

import dominando.android.paceinpeacemvp.details.RunningDetailsViewModel
import dominando.android.paceinpeacemvp.form.RunningFormViewModel
import dominando.android.paceinpeacemvp.list.RunningListViewModel
import dominando.android.paceinpeacemvp.repository.RunningRepository
import dominando.android.paceinpeacemvp.repository.room.RoomRepository
import dominando.android.paceinpeacemvp.repository.room.RunningDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    single { this }
    single {
        RoomRepository(RunningDatabase.getDatabase(context = get())) as RunningRepository
    }
    viewModel {
        RunningListViewModel(repository = get())
    }
    viewModel {
        RunningDetailsViewModel(repository = get())
    }
    viewModel {
        RunningFormViewModel(repository = get())
    }
}