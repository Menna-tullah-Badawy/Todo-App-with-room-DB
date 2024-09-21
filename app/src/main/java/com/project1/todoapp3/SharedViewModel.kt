//package com.project1.todoapp3
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//
//class SharedViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val taskDao: TaskDao = TaskDatabase.getDatabase(application).taskDao()
//
//    private val _userList = MutableLiveData<List<UserModel>>()
//    val userList: LiveData<List<UserModel>> get() = _userList
//
//    private val _userToEdit = MutableLiveData<UserModel?>()
//    val userToEdit: LiveData<UserModel?> get() = _userToEdit
//
//    private val _editPosition = MutableLiveData<Int>(-1)
//    val editPosition: LiveData<Int> get() = _editPosition
//
//    init {
//        loadUserList()
//    }
//
//    private fun loadUserList() {
//        viewModelScope.launch {
//            _userList.value = taskDao.getAllTasks().map { task ->
//                UserModel(
//                    today_Tasks = task.todayTasks,
//                    weekly_Tasks = task.weeklyTasks,
//                    monthly_Tasks = task.monthlyTasks,
//                    id = task.idTasks
//                )
//            }
//        }
//    }
//
//    private fun updateUserListInDb() {
//        viewModelScope.launch {
//            _userList.value?.let { userList ->
//                val tasks = userList.map { user ->
//                    Task(
//                        idTasks = user.id,
//                        todayTasks = user.today_Tasks,
//                        weeklyTasks = user.weekly_Tasks,
//                        monthlyTasks = user.monthly_Tasks
//                    )
//                }
//
//                if (tasks.isNotEmpty()) {
//                    taskDao.insertTasks(tasks)
//                }
//            }
//        }
//    }
//    fun addUser(user: UserModel) {
//        val updatedList = _userList.value.orEmpty().toMutableList()
//        updatedList.add(user)
//        _userList.value = updatedList
//        updateUserListInDb()
//    }
//
//    fun updateUser(position: Int, user: UserModel) {
//        val updatedList = _userList.value.orEmpty().toMutableList()
//        if (position in updatedList.indices) {
//            updatedList[position] = user
//            _userList.value = updatedList
//            updateUserListInDb()
//        }
//    }
//
//    fun removeUser(position: Int) {
//        val updatedList = _userList.value.orEmpty().toMutableList()
//        if (position in updatedList.indices) {
//            updatedList.removeAt(position)
//            _userList.value = updatedList
//            updateUserListInDb()
//        }
//    }
//
//    fun setUserToEdit(user: UserModel, position: Int) {
//        _userToEdit.value = user
//        _editPosition.value = position
//    }
//
//    fun clearUserToEdit() {
//        _userToEdit.value = null
//        _editPosition.value = -1
//    }
//}
