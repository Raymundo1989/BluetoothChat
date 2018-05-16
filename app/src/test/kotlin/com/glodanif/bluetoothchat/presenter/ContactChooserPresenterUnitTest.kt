package com.glodanif.bluetoothchat.presenter

import com.glodanif.bluetoothchat.data.entity.Conversation
import com.glodanif.bluetoothchat.data.model.ConversationsStorage
import com.glodanif.bluetoothchat.ui.presenter.ContactChooserPresenter
import com.glodanif.bluetoothchat.ui.view.ContactChooserView
import com.glodanif.bluetoothchat.ui.viewmodel.ContactViewModel
import com.glodanif.bluetoothchat.ui.viewmodel.converter.ContactConverter
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.experimental.EmptyCoroutineContext

class ContactChooserPresenterUnitTest {

    @RelaxedMockK
    lateinit var storage: ConversationsStorage
    @RelaxedMockK
    lateinit var view: ContactChooserView
    @RelaxedMockK
    lateinit var converter: ContactConverter

    lateinit var presenter: ContactChooserPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        presenter = ContactChooserPresenter(view, storage, converter, EmptyCoroutineContext, EmptyCoroutineContext)
    }

    @Test
    fun loading_empty() {
        coEvery { storage.getContacts() } returns ArrayList()
        presenter.loadContacts()
        verify { view.showNoContacts() }
    }

    @Test
    fun loading_notEmpty() {
        val list = arrayListOf<Conversation>(mockk())
        val viewModels = arrayListOf<ContactViewModel>(mockk())
        coEvery { storage.getContacts() } returns list
        every { converter.transform(list) } returns viewModels
        presenter.loadContacts()
        verify { view.showContacts(viewModels) }
    }
}
