package com.example.simpletodo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object: TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // Remove item from list
                listOfTasks.removeAt(position)
                // Notify the adapter of change
                adapter.notifyDataSetChanged()

                saveItems()

            }

        }

        // Detecting when user clicks on the add button
 //       findViewById<Button>(R.id.button).setOnClickListener{
            // Executes code in here when button is clicked
  //          Log.i("Caren", "User clicked on button")
  //      }

        loadItems()

        // Look up recyclerview in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Set up button, input field for user to enter task to add to the list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //Get reference to button and set an onClickListener
        findViewById<Button>(R.id.button).setOnClickListener{
            // Grab the text the user has inputted in addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // Add the string to list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            //Notify the adapter that data has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            // Reset text field
            inputTextField.setText("")

            saveItems()





        }
    }

    //Save data user inputted
    //Saving data by writing and reading from a file

    //Get data file
    fun getDataFile() : File{

        // Lines are specific tasks in listOfTasks
        return File(filesDir, "data.txt")
    }

    //Method to load the item by reading every line in file
    fun loadItems() {
        try{
        listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    //Save items by writing them into data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }
    }



}