package ir.sanags.interviewTask.presenter.base

sealed class UiState {

    object IDLE : UiState()
    object Loading : UiState()
    data class Error(val message: String) : UiState()
    data class Data(val data: Any) : UiState()
}
