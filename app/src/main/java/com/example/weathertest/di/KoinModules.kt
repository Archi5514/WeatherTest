package com.example.weathertest.di

import com.example.weathertest.model.api.ApiService
import com.example.weathertest.model.api.MAIN_API_URL
import com.example.weathertest.model.data_sources.ApiDataSource
import com.example.weathertest.model.data_sources.ApiDataSourceImpl
import com.example.weathertest.model.data_sources.FakeApiDataSource
import com.example.weathertest.model.repository.WeatherRepository
import com.example.weathertest.model.repository.WeatherRepositoryImpl
import com.example.weathertest.viewmodel.MainViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataSourceModule = module {
    //single<ApiDataSource> { ApiDataSourceImpl(get()) }
    single<ApiDataSource> { FakeApiDataSource() }
}

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}

val retrofitModule = module {
    single {

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        Retrofit.Builder()
            .baseUrl(MAIN_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService::class.java)
    }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}