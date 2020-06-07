/*
    CITask.kt
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

import androidx.databinding.ObservableField
import kotlinx.coroutines.*
import kotlin.random.Random


interface CITaskListener {
    fun finished(task: CITask)
}


// Represents a running coroutine. The coroutine is run on whatever CoroutineScope is passed
// when startAsync is called.
class CITask {

    private var _count:Int = Random.nextInt(0, 30)

    var listener: CITaskListener? = null

    // Observable field the layout will bind to so the display is updated when this changes
    var countString = ObservableField<String>()
    var count:Int
        get() = _count
        set(value) {
            _count = value
            this.countString.set(String.format("%d", _count))
        }

    fun startAsync(scope: CoroutineScope) {

        this.countString.set(String.format("%d", count))

        // Start a coroutine
        scope.launch {

            while(this@CITask.count > 0 ) {

                // use delay as it suspends the coroutine but not the thread
                // multiple coroutines run on a single thread so using Thread.sleep would
                // halt all of them
                delay(1000)
                this@CITask.count--
            }
            // when the count is 0, delay again so it shows before reporting finished
            delay(1000)
            this@CITask.listener?.finished(this@CITask)
        }

    }
}