package com.tapp.dog_app.ui.detail

import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tapp.dog_app.R
import com.tapp.dog_app.base.BaseDog
import com.tapp.dog_app.repository.model.DogResponse
import com.tapp.dog_app.repository.network.DogService
import com.tapp.dog_app.utils.CustomViewModelFactory
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Response

class DetailActivity : BaseDog.BaseActivity() {

    companion object {
        const val TAG = "DetailActivity"
        const val LOCAL_DOG = "LOCAL_DOG"
        const val OBJECT_DOG = "OBJECT_DOG"
        const val SERVER_DOG = "SERVER_DOG"
        const val REQUEST_CODE = 100
    }

    private var mDogResponse: DogResponse? = null
    private var localDog = false

    private val mViewModel: DetailViewModel by lazy {
        val factory = CustomViewModelFactory(application)
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }

    override fun getXmlLayout(): Int {
        return R.layout.activity_detail
    }

    override fun initValues() {
        setSupportActionBar(findViewById(R.id.toolbar))
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        intent?.let {
            if (it.getStringExtra("EXTRA_DOG") == LOCAL_DOG) {
                
                localDog = true
                btnDogDetail.setImageResource(android.R.drawable.ic_menu_delete)
                mViewModel.getLocalDogId(it.getStringExtra("EXTRA_DOG_ID")!!)
                    .observe(this, Observer { apod ->

                        if (apod != null) {
                            mDogResponse = apod
                            mViewModel.showDog(this@DetailActivity, txtDescription, imageDogDetail, apod)
                        }
                    })

            } else {
                localDog = false
                btnDogDetail.setImageResource(android.R.drawable.ic_menu_save)
                getServerApod()
            }
        } ?: getServerApod()
    }

    override fun initListeners() {
        btnDogDetail.setOnClickListener {

            if (localDog) {
                mViewModel.deleteDog(mDogResponse!!)
            } else {

                Completable.fromAction {
                    mViewModel.insertDog(mDogResponse!!)
                }.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : CompletableObserver {
                        override fun onComplete() {
                            Log.w(TAG, "Insert OK")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.w(TAG, "Subscribe OK")
                        }

                        override fun onError(e: Throwable) {
                            Log.w(TAG, e.printStackTrace().toString())
                        }
                    })
            }

            finish()
        }
    }

    private fun getServerApod() {
        mViewModel.getApod(object : DogService.CallbackResponse<DogResponse> {
            override fun onResponse(response: DogResponse) {
                mDogResponse = response
                mViewModel.showDog(this@DetailActivity, txtDescription, imageDogDetail, mDogResponse!!)
            }

            override fun onFailure(t: Throwable, res: Response<*>?) {
                txtDescription.text = res.toString()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Log.w(TAG, "")
    }
}