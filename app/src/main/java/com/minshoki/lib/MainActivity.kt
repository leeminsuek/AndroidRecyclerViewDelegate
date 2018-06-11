package com.minshoki.lib

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
            testAdapter = TestAdapter(this@MainActivity)
            adapter = testAdapter
        }
    }

    private fun setupData() {
        testAdapter.add(TestVO("d"), 1)
        testAdapter.add(TestVO("d"), 2)
        testAdapter.add(TestVO("d"), 2)
        testAdapter.add(TestVO("d"), 2)
        testAdapter.add(TestVO("d"), 2)
        testAdapter.add(TestVO("d"), 1)
        testAdapter.add(TestVO("d"), 1)
        testAdapter.add(TestVO("d"), 1)
        testAdapter.add(TestVO("d"), 2)
        testAdapter.add(TestVO("d"), 2)
        testAdapter.add(TestVO("d"), 2)
        testAdapter.add(TestVO("d"), 1)
        testAdapter.add(TestVO("d"), 1)
        testAdapter.add(TestVO("d"), 1)
        testAdapter.add(TestVO("d"), 1)
        testAdapter.add(TestVO("d"), 1)
        testAdapter.notifyDataSetChanged()
    }
}
