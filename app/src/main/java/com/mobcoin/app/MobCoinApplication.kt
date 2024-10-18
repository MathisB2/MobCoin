package com.mobcoin.app

import android.app.Application
import com.mobcoin.app.domain.database.DBDataSource
import com.mobcoin.app.domain.database.MobCoinDataBase

class MobCoinApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialise la base de données ici, car l'application est maintenant complètement initialisée
        DBDataSource.createDatabase(applicationContext)
    }
}