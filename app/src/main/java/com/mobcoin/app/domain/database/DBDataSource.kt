package com.mobcoin.app.domain.database

import android.content.Context
import androidx.room.Room

object DBDataSource {
    @Volatile
    private var INSTANCE: MobCoinDataBase? = null

    fun createDatabase(context: Context): MobCoinDataBase {
        synchronized(MobCoinDataBase::class){
            if (INSTANCE == null){
                    INSTANCE = buildDatabase(context)
            }
        }

        return INSTANCE!!
    }

    fun getDatabase(): MobCoinDataBase {
        if (INSTANCE == null){
            error("No context created")
        }

        return INSTANCE!!
    }

    private fun buildDatabase(context: Context): MobCoinDataBase{
        val instance = Room.databaseBuilder(
            context.applicationContext,
            MobCoinDataBase::class.java,
            "mobcoin_database"
        ).build()

        return instance
    }
}