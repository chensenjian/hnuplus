package me.zhaoweihao.hnuplus.SQL

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by Administrator on 2017/11/13.
 */

class MySqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "mydb") {

        companion object {
            private var instance: MySqlHelper? = null

            @Synchronized
            fun getInstance(ctx: Context): MySqlHelper {
                if (instance == null) {
                    instance = MySqlHelper(ctx.applicationContext)
                }
                return instance!!
            }
        }

        override fun onCreate(db: SQLiteDatabase) {
//            db.createTable("Person", true, "id" to INTEGER + PRIMARY_KEY + UNIQUE,
//                    "name" to TEXT,
//                    "age" to INTEGER,
//                    "address" to TEXT)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        }

    }

    // Access property for Context
   val Context.database: MySqlHelper
        get() = MySqlHelper.getInstance(applicationContext)