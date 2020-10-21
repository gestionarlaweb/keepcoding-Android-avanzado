package com.tapp.dog_app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapp.dog_app.R
import com.tapp.dog_app.repository.model.DogResponse
import com.tapp.dog_app.ui.detail.DetailActivity
import com.tapp.dog_app.ui.detail.DetailActivity.Companion.LOCAL_DOG
import com.tapp.dog_app.ui.detail.DetailActivity.Companion.OBJECT_DOG
import com.tapp.dog_app.ui.detail.DetailActivity.Companion.REQUEST_CODE
import com.tapp.dog_app.utils.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment(), CallbackItemClick {

    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    private var mAdapter: MainAdapter? = null

    private val mViewModel: MainFragmentViewModel by lazy {
        val factory = CustomViewModelFactory(requireActivity().application)
        ViewModelProvider(this, factory).get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getLocalAllDog()
    }

    private fun init() {
        recyclerViewMainList.layoutManager = LinearLayoutManager(activity)
        recyclerViewMainList.isNestedScrollingEnabled = false
        recyclerViewMainList.setHasFixedSize(false)
    }

    private fun getLocalAllDog() {
        mViewModel.gelLocalAllDog().observe(viewLifecycleOwner, Observer { dogList ->

            mAdapter = MainAdapter(requireActivity().applicationContext, this, dogList)
            recyclerViewMainList.adapter = mAdapter
        })
    }

    override fun onItemClick(dogResponse: DogResponse) {
        activity?.let { fragment ->
            Intent(fragment, DetailActivity::class.java).apply {

                arguments = Bundle().apply {
                    putSerializable(OBJECT_DOG, dogResponse)
                }

                arguments?.let { args ->
                    putExtras(args)
                }

                putExtra("EXTRA_DOG", LOCAL_DOG)
                putExtra("EXTRA_DOG_ID", dogResponse.id)
                fragment.startActivityForResult(this, REQUEST_CODE)
            }
        }
    }

    fun updateList() {
        mAdapter!!.notifyDataSetChanged()
    }
}