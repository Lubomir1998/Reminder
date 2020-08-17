package com.example.reminder.di

import android.content.Context
import androidx.room.Room
import com.example.reminder.db.DbHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        DbHelper::class.java,
        "event_db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDao(db: DbHelper) = db.getEventDao()

}