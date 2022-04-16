package ir.sanags.interviewTask.presenter.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel() {

    protected var _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.IDLE)
    open val uiState get() = _uiState

}