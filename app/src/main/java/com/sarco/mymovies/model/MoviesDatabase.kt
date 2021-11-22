package com.sarco.mymovies.model

import android.app.Application
import android.content.Context

class MoviesDatabase(private val context: Context) {

    companion object{
       private var moviesDatabase: MoviesDatabase? = null

        fun getInstance(context: Application): MoviesDatabase{
            if(moviesDatabase == null){
                moviesDatabase = MoviesDatabase(context.applicationContext)
            }
            return moviesDatabase!!
        }
    }

}