/*
    MainFragment.kt
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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cgbits.coroutineinator.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var mainBinding: MainFragmentBinding
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        this.viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        this.mainBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        this.mainBinding.mainViewModel = this.viewModel

        this.layoutManager = LinearLayoutManager(requireContext()).also {
            it.orientation = LinearLayoutManager.HORIZONTAL
        }

        // Calculate how may columns to span based on screen size
        val displayMetrics = requireContext().resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val taskWidth = requireContext().resources.getDimension(R.dimen.task_size) / displayMetrics.density
        val columns = dpWidth / taskWidth

        this.layoutManager = StaggeredGridLayoutManager(columns.toInt(), StaggeredGridLayoutManager.VERTICAL)
        this.mainBinding.mainRecyclerView.layoutManager = this.layoutManager

        mainAdapter = MainAdapter()
        this.mainBinding.mainRecyclerView.adapter = mainAdapter

        this.viewModel.adapter = mainAdapter

        this.mainBinding.floatingActionButton.setOnClickListener {
            this.viewModel.addTask()
        }

        return mainBinding.root
    }





}

