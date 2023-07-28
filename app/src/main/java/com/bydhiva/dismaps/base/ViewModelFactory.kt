package com.bydhiva.dismaps.base

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.bydhiva.dismaps.ui.main.MainViewModel
import com.bydhiva.dismaps.ui.setting.SettingViewModel

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.viewModelBuilder(): Lazy<VM> {
    return ViewModelLazy(
        viewModelClass = VM::class,
        storeProducer = { viewModelStore },
        factoryProducer = {
            return@ViewModelLazy object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val appContainer = LibraryModule.application.appContainer
                    @Suppress("UNCHECKED_CAST")// Casting T as ViewModel
                    return when {
                        modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(appContainer.mainContainer?.disasterUseCases) as T
                        else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
                    }
                }
            }
        }
    )
}

@MainThread
inline fun <reified VM : ViewModel> Fragment.activityViewModelBuilder(): Lazy<VM> {
    return ViewModelLazy(
        viewModelClass = VM::class,
        storeProducer = { requireActivity().viewModelStore },
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val appContainer = LibraryModule.application.appContainer
                    @Suppress("UNCHECKED_CAST")// Casting T as ViewModel
                    return when {
                        modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(appContainer.mainContainer?.disasterUseCases) as T
                        else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
                    }
                }
            }
        }
    )
}

@MainThread
inline fun <reified VM : ViewModel> Fragment.viewModelBuilder(): Lazy<VM> {
    return ViewModelLazy(
        viewModelClass = VM::class,
        storeProducer = { this.viewModelStore },
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val appContainer = LibraryModule.application.appContainer
                    @Suppress("UNCHECKED_CAST")// Casting T as ViewModel
                    return when {
                        modelClass.isAssignableFrom(SettingViewModel::class.java) -> SettingViewModel(appContainer.settingContainer?.settingPreferences) as T
                        else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
                    }
                }
            }
        }
    )
}
