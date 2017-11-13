package me.zhaoweihao.hnuplus

import android.os.Bundle

import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.contacts_layout.*
import me.zhaoweihao.hnuplus.SQL.database
import org.jetbrains.anko.attempt
import org.jetbrains.anko.db.*



/**
 * Created by Administrator on 2017/11/9.
 */

/**
 * There will be something here
 * so I call it contacts first,not real contacts function in the future
 */

class ContactsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val contactsLayout = inflater!!.inflate(R.layout.contacts_layout, container, false)

        return contactsLayout
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        using kotlin-anko-sqlite & kotlin-android-extensions in fragment
        thanks to those posts:
        http://blog.csdn.net/cysion1989/article/details/72903002
        https://antonioleiva.com/databases-anko-kotlin/
        https://antonioleiva.com/kotlin-android-extensions/
         */
//        insert.setOnClickListener {
//            activity.database.use {
//                if (attempt {
//                    var name: String = "John"
//                    var age: Int = 13
//                    insert("Person",
//                            "age" to age,
//                            "name" to name
//                    )
//                    display.text = "insert(Person,age to $age,name to $name,address to )"
//                }.isError) {
//                    display.text = "错误,可能不存在表"
//                }
//            }
//        }
//        query.setOnClickListener {
//            activity.database.use {
//                if (attempt {
//                    val whereArgs = select("Person", "name", "age")
//                    val parseList = whereArgs.parseList(classParser<Person>())
//
//                    display.text = parseList.toString()
//                }.isError) {
//                    display.text = "错误,可能不存在表"
//                }
//            }
//        }

    }

}


