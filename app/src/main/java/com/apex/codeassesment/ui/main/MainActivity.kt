package com.apex.codeassesment.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityMainBinding
import com.apex.codeassesment.ui.details.DetailsActivity
import com.apex.codeassesment.ui.main.adapter.UsersAdapter
import com.sample.extn.hide
import com.sample.extn.loadImage
import com.sample.extn.navigateToScreen
import com.sample.extn.show
import com.sample.extn.showToast
import dagger.hilt.android.AndroidEntryPoint

// TODO (5 points): Move calls to repository to Presenter or ViewModel.
// TODO (5 points): Use combination of sealed/Dataclasses for exposing the data required by the view from viewModel .
// TODO (3 points): Add tests for viewmodel or presenter.
// TODO (1 point): Add content description to images
// TODO (3 points): Add tests
// TODO (Optional Bonus 10 points): Make a copy of this activity with different name and convert the current layout it is using in
//  Jetpack Compose.

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // TODO (2 points): Convert to view binding
    private val viewModel by viewModels<MainViewModel>()

     var binding: ActivityMainBinding? = null
    private val userAdapter by lazy { UsersAdapter { navigateToDetailScreen(it) } }


    private var randomUser: User = User()
        set(value) {
            // TODO (1 point): Use Glide to load images after getting the data from endpoints mentioned in RemoteDataSource
            binding?.userImageView?.loadImage(value.picture?.thumbnail ?: "")
            binding?.userNameTextView?.text = value.name?.first
            binding?.userEmailTextView?.text = value.email
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        sharedContext = this
        setRecylerViewAdapter()
        setClickListeners()
        observeData()
    }


    private fun setRecylerViewAdapter() {
        binding?.userListView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun observeData() {
        viewModel.userList.observe(this) {
            userAdapter.updateList(it)
        }
        viewModel.uiState.observe(this) { uiState ->
            if (uiState.isLoading) binding?.progessBar?.show() else binding?.progessBar?.hide()
            uiState.errorMessage.takeIf { it.isBlank().not() }?.apply { showToast(this@apply) }

        }
        viewModel.userData.observe(this) { user ->
            user?.let { randomUser = it }
        }
    }

    private fun setClickListeners() {
        binding?.seeDetailsButton?.setOnClickListener {
            navigateToDetailScreen(randomUser)
        }
        binding?.refreshUserButton?.setOnClickListener {
            viewModel.getUpdatedUSer()
        }
        binding?.showUserListButton?.setOnClickListener {
            viewModel.getUserList()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // TODO (2 points): Convert to extenstion function.
    private fun navigateToDetailScreen(user: User) {
        navigateToScreen(DetailsActivity::class.java) {
            putParcelable("saved-user-key", user)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var sharedContext: Context? = null
    }
}

