package com.example.testdialog.dagger

import com.example.testdialog.ui.FirstFragment
import com.example.testdialog.ui.MainActivity
import dagger.Component
import dagger.Module


@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: FirstFragment)
}

@Module
class AppModule {}
