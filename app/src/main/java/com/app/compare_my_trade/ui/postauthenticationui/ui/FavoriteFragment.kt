package com.app.compare_my_trade.ui.postauthenticationui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.R
import com.example.kotlin_project1.Favorite.FavoriteAdapter
import com.example.kotlin_project1.Favorite.FavoriteModel


class FavoriteFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_favorite, container, false)


        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview4)


        recyclerview.layoutManager = LinearLayoutManager(activity)

        val data = ArrayList<FavoriteModel>()


//        data.add(FavoriteModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden"))
//        data.add(FavoriteModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden"))
//        data.add(FavoriteModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden"))
//        data.add(FavoriteModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden"))


        val adapter = FavoriteAdapter(data)


        recyclerview.adapter = adapter

        return view

    }

}