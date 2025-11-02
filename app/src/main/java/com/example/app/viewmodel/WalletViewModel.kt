package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.remote.NetworkModule
import com.example.app.data.repository.WalletRepository
import com.example.app.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val loading: Boolean = false,
    val balance: BalanceResponse? = null,
    val recent: List<Transaction> = emptyList(),
    val error: String? = null
)

class WalletViewModel(
    private val repo: WalletRepository = WalletRepository(NetworkModule.api)
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    fun loadHome(token: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            try {
                val bal = repo.balance(token)
                val tx = repo.transactions(token).items
                _state.value = HomeUiState(loading = false, balance = bal, recent = tx)
            } catch (e: Exception) {
                _state.value = HomeUiState(loading = false, error = e.message)
            }
        }
    }

    fun send(token: String, to: String, amount: Double, concept: String?) {
        viewModelScope.launch {
            try {
                repo.send(token, to, amount, concept)
                loadHome(token)
            } catch (_: Exception) { }
        }
    }
}
