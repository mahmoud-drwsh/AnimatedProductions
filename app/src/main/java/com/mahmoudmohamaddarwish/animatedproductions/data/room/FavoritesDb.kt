package com.mahmoudmohamaddarwish.animatedproductions.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production

@Database(entities = [Production::class], version = 3)
abstract class FavoritesDb : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

}