/*
    MainViewModel.kt
    coroutineinator

    Created by Calvin Gaisford on 6/6/20.

    MIT License

    Copyright (c) 2020 Calvin Gaisford

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
 */

package com.cgbits.coroutineinator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

class MainViewModel : ViewModel(), CITaskListener {

    // weak reference to the adapter because it will come and go with the fragment
    private var weakAdapter: WeakReference<MainAdapter?>? = null

    // job for all coroutines started by MainViewModel
    private val viewModelJob = SupervisorJob()
    // scope for all coroutines started by MainViewModel
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val taskList = mutableListOf<CITask>()


    var adapter: MainAdapter?
        get() = weakAdapter?.get()
        set(value) {
            weakAdapter = WeakReference(value)
            weakAdapter?.get()?.tasks = this.taskList
        }


    override fun onCleared() {
        super.onCleared()

        // cancel scope and all jobs in it
        viewModelScope.cancel()
    }


    fun addTask() {
        val upcomingTask = CITask()
        upcomingTask.listener = this
        taskList.add(upcomingTask)
        this.weakAdapter?.get()?.notifyItemInserted(taskList.size - 1)
        upcomingTask.startAsync(viewModelScope)
    }


    // CITaskListener methods
    override fun finished(task: CITask) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                val index = taskList.indexOf(task)
                if(index >= 0) {
                    taskList.removeAt(index)
                    weakAdapter?.get()?.notifyItemRemoved(index)
                }
            }
        }
    }

}
