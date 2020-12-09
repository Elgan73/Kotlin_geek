package com.aisgorod.kotlin_geek.di

import com.aisgorod.kotlin_geek.data.NotesRepository
import com.aisgorod.kotlin_geek.data.NotesRepositoryImpl
import com.aisgorod.kotlin_geek.data.db.DatabaseProvider
import com.aisgorod.kotlin_geek.data.db.FireStoreDatabaseProvider
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.presentation.NoteViewModel
import com.aisgorod.kotlin_geek.presentation.NotesViewModel
import com.aisgorod.kotlin_geek.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

object DependencyGraph {

    private val repositoryModule by lazy {
        module {
            single { NotesRepositoryImpl(get()) } bind NotesRepository::class
            single { FireStoreDatabaseProvider() } bind DatabaseProvider::class
        }
    }

    private val viewModelModule by lazy {
        module {
            viewModel { NotesViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { (note: Note?) -> NoteViewModel(get(), note) }
        }
    }

    val modules: List<Module> = listOf(repositoryModule, viewModelModule)

}