package com.endiar.anyrecipes.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.endiar.anyrecipes.core.data.source.local.entity.FavoriteRecipeEntity

@Database(entities = [FavoriteRecipeEntity::class], version = 1, exportSchema = false)
abstract class AnyRecipeDatabase : RoomDatabase() {
    abstract fun favoriteRecipeDao(): FavoriteRecipeDao
}