package com.example.testdialog.dagger

import com.example.testdialog.data.MessagesInteractor
import com.example.testdialog.data.Repository
import dagger.Component
import dagger.Module
import dagger.Provides


@Component(modules = [AppModule::class])
interface AppComponent {
    val messagesInteractor: MessagesInteractor
}

@Module
class AppModule {
    @Provides
    fun provideMessagesInteractor() : MessagesInteractor {
        return MessagesInteractor(Repository())
    }
}
