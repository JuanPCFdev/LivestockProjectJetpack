package com.example.livestockjetpackcompose.data.di

import com.example.livestockjetpackcompose.data.datasource.FirebaseDataSource
import com.example.livestockjetpackcompose.data.datasource.FirebaseDataSourceImpl
import com.example.livestockjetpackcompose.data.repository.cattle.CattleRepository
import com.example.livestockjetpackcompose.data.repository.cattle.CattleRepositoryImpl
import com.example.livestockjetpackcompose.data.repository.farm.FarmRepository
import com.example.livestockjetpackcompose.data.repository.farm.FarmRepositoryImpl
import com.example.livestockjetpackcompose.data.repository.user.UserRepository
import com.example.livestockjetpackcompose.data.repository.user.UserRepositoryImpl
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        firebaseDataSource: FirebaseDataSource
    ): UserRepository {
        return UserRepositoryImpl(firebaseDataSource)
    }

    @Provides
    @Singleton
    fun provideFarmRepository(
        firebaseDataSource: FirebaseDataSource
    ): FarmRepository {
        return FarmRepositoryImpl(firebaseDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabaseSource(
        firebaseDatabase: FirebaseDatabase
    ): FirebaseDataSource {
        return FirebaseDataSourceImpl(firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideCattleRepository(
        firebaseDataSource: FirebaseDataSource
    ): CattleRepository {
        return CattleRepositoryImpl(firebaseDataSource)
    }

}