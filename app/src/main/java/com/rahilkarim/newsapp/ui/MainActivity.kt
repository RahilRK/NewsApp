package com.rahilkarim.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rahilkarim.newsapp.R
import com.rahilkarim.newsapp.database.Database
import com.rahilkarim.newsapp.network.RetrofitClient
import com.rahilkarim.newsapp.ui.fragments.BreakingNews.BreakingNewsVPF
import com.rahilkarim.newsapp.ui.fragments.BreakingNews.BreakingNewsViewModel
import com.rahilkarim.newsapp.util.GlobalClass
import com.rahilkarim.newsapp.util.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var tag = "MainActivity"
    private var activity = this

    lateinit var globalClass: GlobalClass

    lateinit var navController: NavController

    lateinit var breakingNewsViewModel: BreakingNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        navController = findNavController(R.id.newsNavHostFragment)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

//            globalClass.log(tag, "onDestinationChanged: "+destination.label)
        }

//        val repository = Repository(Database.getInstance(activity))
//        val breakingNewsVPF = BreakingNewsVPF(repository)
//        breakingNewsViewModel = ViewModelProvider(this,breakingNewsVPF).get(BreakingNewsViewModel::class.java)
    }

    fun init() {
        globalClass = GlobalClass.getInstance(activity)
    }
}