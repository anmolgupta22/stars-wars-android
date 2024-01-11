package com.example.starwars.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.starwars.databinding.CharactersItemsBinding
import com.example.starwars.model.Results

class StarCharactersAdapter(
    private var lastPositionScrollView: LastPositionScrollView,
) :
   BaseAdapter() {
    private var peoples: ArrayList<Results> = arrayListOf()

    var onLastItemVisibleListener: (() -> Unit)? = null

    fun setData(peoplesList: ArrayList<Results>) {
        peoples.clear()
        peoples.addAll(peoplesList)

        Log.d("TAG", "setData: get the data peoples $peoples")
    }

    interface LastPositionScrollView {
        fun lastScrollView()
    }
    override fun getCount(): Int = peoples.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val binding: CharactersItemsBinding
        Log.d("TAG", "getView: check the aaa")
        if (convertView == null) {
            Log.d("TAG", "getView: check the if")
            binding =
                CharactersItemsBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            Log.d("TAG", "getView: check the else")
            binding = convertView.tag as CharactersItemsBinding
            view = convertView
        }
        with(peoples[position]) {
            binding.name.text = "name - $name"
            binding.height.text = "height- $height"
            binding.mass.text = "mass - $mass"
            binding.hairColor.text = "hair color - $hair_color"
            binding.eyeColor.text = "eye color - $eye_color"
            binding.gender.text = "gender - $gender"
            binding.birthYear.text = "birth year - $birth_year"
        }
        if (position == count - 1) {
            lastPositionScrollView.lastScrollView()
        }
        return view
    }
}
