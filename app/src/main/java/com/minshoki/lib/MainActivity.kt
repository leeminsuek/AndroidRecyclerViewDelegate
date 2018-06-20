package com.minshoki.lib

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var testAdapter: TestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecycler()
        setupData()
    }


    private fun setupRecycler() {
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            testAdapter = TestAdapter(this@MainActivity)
            adapter = testAdapter
        }
    }

    private fun setupData() {
        testAdapter.setting {
            emptyView(btn, 0)
            endlessScroll {
                Log.e("shokitest", "Errorr")
            }
        }

        btn.setOnClickListener {
            testAdapter.startLoadMore()

            Handler().postDelayed({
                var test = mutableListOf<Any>()
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(TestVO("d"))
                test.add(ListTestVo(
                        mutableListOf<String>().apply {
                            add("dcc")
                            add("dcc")
                            add("dcc")
                            add("dcc")
                            add("dcc")
                            add("dcc")
                        }
                ))

                testAdapter.endLoadMore(test)
            }, 3000)
        }
        testAdapter.notifyDataSetChanged()
    }
}
