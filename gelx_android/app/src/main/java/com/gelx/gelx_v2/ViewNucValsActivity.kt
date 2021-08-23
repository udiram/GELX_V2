package com.gelx.gelx_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gelx.gelx_v2.NameAdapter
import com.gelx.gelx_v2.reposotories.DataProvider
import java.util.*

class ViewNucValsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val rclNames = findViewById<RecyclerView>(R.id.rclNames)
        val btnUpdateList = findViewById<Button>(R.id.btnUpdateList)

        // If size of the all items are equal and won't change for a better performance it's better to set setHasFixedSize to true
        rclNames.setHasFixedSize(true)

        // Creating an instance of our NameAdapter class and setting it to our RecyclerView
        val nameList = getNucData()
        val namesAdapter = NameAdapter(nameList)
        rclNames.adapter = namesAdapter
        // Setting our RecyclerView's layout manager equal to LinearLayoutManager
        rclNames.layoutManager = LinearLayoutManager(this)

        // Updating the last item of our list by clicking on the btnUpdateList
        btnUpdateList.setOnClickListener {
            nameList[nameList.size - 1] = "Hello! I've been updated right now :)"
            namesAdapter.notifyDataSetChanged()
        }

        // Initializing namesAdapter.itemClickListener
        namesAdapter.itemClickListener = { position, name ->
//            Toast.makeText(this, "position: $position - name: $name", Toast.LENGTH_SHORT)
//                    .show()
        }

    }

    private fun getNucData() : MutableList<String> {

        val nucData = DataProvider.getNucData(this)
        val list = mutableListOf<String>()

        if (nucData == null || nucData.isEmpty()) {
            return ArrayList()
        }

        for (lane in nucData) {
            list.add("Column number: " .plus(lane.column.toString()) .plus(" \n Peak Values: ") .plus(lane.dataAsInts))
        }
        return list
    }

    // This function just creates a list of names for us
    private fun getListOfNames(): MutableList<String> {
        val nameList = mutableListOf<String>()


        return nameList
    }
}