package com.tapp.dog_app.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tapp.dog_app.R
import com.tapp.dog_app.repository.model.DogResponse
import kotlinx.android.synthetic.main.item_list.view.*

class MainAdapter(private val context: Context, private val callbackItemClick: CallbackItemClick, private val dogList: List<DogResponse>?) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    class MainHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var view = v
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        dogList?.get(position).let { dog ->

            Glide.with(context)
                .load(dog?.message) // url
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                )
                .into(holder.view.imageCardView)

            holder.view.carView.setOnClickListener {
                callbackItemClick.onItemClick(dog!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return  dogList!!.size
    }
}