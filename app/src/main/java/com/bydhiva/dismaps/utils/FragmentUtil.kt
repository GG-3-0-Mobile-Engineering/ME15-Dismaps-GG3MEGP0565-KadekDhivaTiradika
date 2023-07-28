package com.bydhiva.dismaps.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

inline  fun <reified F : Fragment> FragmentManager.removeIfExist() {
    this.commit {
        this@removeIfExist.fragments.find { it is F}?.let {
            remove(it)
            setReorderingAllowed(true)
        }
    }
}