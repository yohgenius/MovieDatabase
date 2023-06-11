package com.example.moviedatabase.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [DetailMovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDB : RoomDatabase() {
    abstract fun dao(): MovieDao
}