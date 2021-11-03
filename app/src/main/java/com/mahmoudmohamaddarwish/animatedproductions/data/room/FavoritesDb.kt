package com.mahmoudmohamaddarwish.animatedproductions.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production

@Database(entities = [Production::class], version = 1)
abstract class FavoritesDb : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

}