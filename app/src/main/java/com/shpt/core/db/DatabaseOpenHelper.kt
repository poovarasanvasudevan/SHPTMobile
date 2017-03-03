package com.shpt.core.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by poovarasanv on 28/2/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 28/2/17 at 9:49 AM
 */

class DatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "mydb", null, 1) {
    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {

        //stores all the layout regarding to all the changes made in server
        db?.createTable("Layout", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "page" to TEXT,
                "structure" to TEXT
        )

        //stores online configurations
        db?.createTable("Settings", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "group" to TEXT,
                "settingkey" to TEXT,
                "settingvalue" to TEXT
        )

        //stores recently viewed products
        db?.createTable("RecentlyViewed", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "productid" to INTEGER,
                "productname" to TEXT,
                "viewedtime" to TEXT
        )

        //stores recently searched products
        db?.createTable("RecentlySearched", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "productid" to INTEGER,
                "productname" to TEXT,
                "searchtime" to TEXT
        )

        //stores all the notification that received from our system
        db?.createTable("Notifications", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "title" to TEXT,
                "message" to TEXT,
                "operation" to TEXT,
                "image" to TEXT,
                "status" to INTEGER,
                "sensitive" to INTEGER, //this for for system generated messages
                "createdtime" to TEXT
        )

        //stores location in realtime
        db?.createTable("Location", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "lat" to TEXT,
                "lng" to TEXT,
                "updated" to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}

val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)
