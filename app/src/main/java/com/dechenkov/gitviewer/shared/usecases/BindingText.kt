package com.dechenkov.gitviewer.shared

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

fun EditText.bindText(liveData: MutableLiveData<String>, lifecycleOwner: LifecycleOwner){
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            liveData.value = s.toString()
        }
        override fun afterTextChanged(s: Editable?) = Unit
    }

    this.addTextChangedListener(textWatcher)

    liveData.observe(lifecycleOwner) { text ->
        if (this.text.toString() == text) return@observe

        this.setText(text)
    }
}