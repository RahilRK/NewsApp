package com.rahilkarim.newsapp.util

import android.app.Application
import com.rahilkarim.newsapp.database.Database

class Application: Application() {

    lateinit var repository: Repository
    lateinit var globalClass: GlobalClass
    lateinit var myDatabase: Database

    override fun onCreate() {
        super.onCreate()

        init()
    }

    fun init() {

        globalClass = GlobalClass.getInstance(applicationContext)
        myDatabase = Database.getInstance(applicationContext)
        repository = Repository(myDatabase)
    }
}