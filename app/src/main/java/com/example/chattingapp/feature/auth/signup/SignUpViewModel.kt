package com.example.chattingapp.feature.auth.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private var _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)
    var state = _state.asStateFlow()

    fun createUser(fullName: String, email: String, password: String){
        _state.value = SignUpState.Loading
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    task.result.user?.let {
                        it.updateProfile(UserProfileChangeRequest.Builder()
                            .setDisplayName(fullName)
                            .build()
                        )?.addOnCompleteListener {
                            _state.value = SignUpState.Success
                        }
                        return@addOnCompleteListener
                    }
                    _state.value = SignUpState.Error
                }else{
                    _state.value = SignUpState.Error
                }
            }
    }
}
sealed class SignUpState{
    object Nothing: SignUpState()
    object Loading: SignUpState()
    object Success: SignUpState()
    object Error: SignUpState()
}