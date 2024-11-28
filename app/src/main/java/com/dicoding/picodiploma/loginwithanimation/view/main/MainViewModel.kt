package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import kotlinx.coroutines.launch
import com.dicoding.picodiploma.loginwithanimation.utils.Result

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    private val _stories = MutableLiveData<Result<List<ListStoryItem>>>()
    val stories: LiveData<Result<List<ListStoryItem>>> = _stories

    fun getStories(page: Int? = null, size: Int? = null, location: Int = 0) {
        viewModelScope.launch {
            _stories.postValue(Result.Loading)
            try {
                repository.getSession().collect { user ->
                    val response = repository.getStories(user.token, page, size, location)
                    val nonNullStories = response.listStory?.filterNotNull() ?: emptyList()
                    _stories.postValue(Result.Success(nonNullStories))
                }
            } catch (e: Exception) {
                _stories.postValue(Result.Error(e.message.toString()))
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}