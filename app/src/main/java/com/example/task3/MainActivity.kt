package com.example.task3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import android.util.Log


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ProductAdapter(productList) { product ->

            Toast.makeText(
                this,
                "${product.name} - ${product.price}",
                Toast.LENGTH_SHORT
            ).show()
        }
        recyclerView.adapter = adapter

        val ref = FirebaseDatabase.getInstance().getReference("products/products")


        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("REALTIME", "onDataChange CALLED")
                Log.d("REALTIME", "children = ${snapshot.childrenCount}")

                productList.clear()

                for (child in snapshot.children) {
                    Log.d("REALTIME", "key = ${child.key}")
                    val product = child.getValue(Product::class.java)
                    Log.d("REALTIME", "product = $product")

                    if (product != null) {
                        productList.add(product)
                    }
                }

                adapter.notifyDataSetChanged()
            }



            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "DB Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
