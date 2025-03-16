package com.kotian.countryview


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotian.countryview.databinding.ActivityMainBinding
import com.kotian.countryview.data.CountryRepository
import com.kotian.countryview.network.RetrofitClient
import com.kotian.countryview.ui.CountryAdapter
import com.kotian.countryview.ui.CountryViewModel
import com.kotian.countryview.ui.CountryViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CountryViewModel by viewModels {
        CountryViewModelFactory(
            CountryRepository(
                RetrofitClient.apiService,
                applicationContext
            )
        )
    }
    private val adapter = CountryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        setupRetryButton()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is CountryViewModel.UIState.Loading -> showLoading()
                    is CountryViewModel.UIState.Success -> {
                        adapter.submitList(state.countries)
                        showContent()
                    }
                    is CountryViewModel.UIState.Empty -> showEmptyView()
                    is CountryViewModel.UIState.Error -> showError(state.message)
                }
            }
        }
    }

    private fun setupRetryButton() {
        binding.btnRetry.setOnClickListener {
            viewModel.loadCountries()
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.btnRetry.visibility = View.GONE
        binding.tvEmpty.visibility = View.GONE
    }

    private fun showContent() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
        binding.btnRetry.visibility = View.GONE
        binding.tvEmpty.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.btnRetry.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.GONE
        binding.tvError.text = message
    }

    private fun showEmptyView() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.btnRetry.visibility = View.GONE
        binding.tvEmpty.visibility = View.VISIBLE
    }
}