package com.example.weathertest.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weathertest.model.api.ApiService
import com.example.weathertest.model.api.MAIN_API_URL
import com.example.weathertest.model.data_sources.*
import com.example.weathertest.model.repository.WeatherRepository
import com.example.weathertest.model.repository.WeatherRepositoryImpl
import com.example.weathertest.viewmodel.MainViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RequiresApi(Build.VERSION_CODES.M)
val dataSourceModule = module {
    single<ApiDataSource> { ApiDataSourceImpl(get()) }
    //single<ApiDataSource> { FakeApiDataSource() }
    single { LocationDataSourceImpl() }
    single<LocationDataSource> { get<LocationDataSourceImpl>() }
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
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ApiService::class.java)
    }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}