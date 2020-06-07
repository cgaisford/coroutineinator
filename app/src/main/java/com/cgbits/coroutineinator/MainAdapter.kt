/*
    MainAdapter.kt
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
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cgbits.coroutineinator.databinding.TaskLayoutBinding

class MainAdapter :

    RecyclerView.Adapter<MainAdapter.TaskViewHolder>() {

    private var _tasks: List<CITask>? = null

    var tasks: List<CITask>?
        get() {
            return _tasks
        }
        set(value) {
            _tasks = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TaskViewHolder {
        val taskItemBinding: TaskLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context), R.layout.task_layout, viewGroup, false
        )
        return TaskViewHolder(taskItemBinding)
    }

    override fun onBindViewHolder(deviceViewHolder: TaskViewHolder, i: Int) {
        val currentTask: CITask = _tasks!![i]
        deviceViewHolder.taskBinding.task = currentTask
    }

    override fun getItemCount(): Int {
        return if (_tasks != null) {
            _tasks!!.size
        } else {
            0
        }
    }

    inner class TaskViewHolder(tskBinding: TaskLayoutBinding) : RecyclerView.ViewHolder(tskBinding.getRoot()) {
        val taskBinding: TaskLayoutBinding = tskBinding
    }
}