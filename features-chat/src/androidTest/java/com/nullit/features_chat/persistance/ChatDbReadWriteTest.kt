package com.nullit.features_chat.persistance

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.nullit.features_chat.persistance.dao.ChatMemberDao
import com.nullit.features_chat.persistance.dao.DialogDao
import com.nullit.features_chat.persistance.dao.MessageDao
import com.nullit.features_chat.persistance.entity.DialogEntity
import com.nullit.features_chat.utils.DialogFactory
import com.nullit.features_chat.utils.MemberFactory
import com.nullit.features_chat.utils.MessageFactory
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ChatDbReadWriteTest {
    private lateinit var dialogDao: DialogDao
    private lateinit var messageDao: MessageDao
    private lateinit var chatMemberDao: ChatMemberDao
    private lateinit var dialogFactory: DialogFactory
    private lateinit var messageFactory: MessageFactory
    private lateinit var memberFactory: MemberFactory

    private lateinit var db: ChatDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ChatDataBase::class.java
        ).build()
        dialogDao = db.getDialogDao()
        messageDao = db.getMessage()
        chatMemberDao = db.catMemberDao()
        dialogFactory = DialogFactory()
        memberFactory = MemberFactory()
        messageFactory = MessageFactory()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeDialogAndRead() = runBlocking {
        val dialog = dialogFactory.generateDialog()
        dialogDao.insertDialogWithMembers(dialog)
        val result = dialogDao.getDialogs()
        assert(result.size == 1)
        assert(dialog.equals(result[0]))
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun writeEqualsDialogsAndRead() = runBlocking {
        val dialog1 = DialogEntity(1, "Chat 1", DialogEntity.GROUP_TYPE, "222", "222", null)
        val dialog2 = DialogEntity(1, "Chat 1", DialogEntity.GROUP_TYPE, "222", "222", null)
        dialogDao.insertDialogsWithMembers(listOf<DialogEntity>(dialog1, dialog2))
        val result = dialogDao.getDialogs()
        assert(result.size == 1)
        assert(result[0].equals(dialog1))
        assert(result[0].equals(dialog2))
    }

    @Test
    @Throws(Exception::class)
    fun writeDialogsWithMembers() = runBlocking {
        val dialog = dialogFactory.generateDialog()
        val members = memberFactory.generateUsersInChat(dialog.chatId, 3)
        dialogDao.insertDialogWithMembers(dialog)
        chatMemberDao.insertMembersIntoDialog(members)
        val dialogFromDb = dialogDao.getDialogs()
        assert(dialogFromDb.size == 1)
        Log.e("ChatDbTest", dialogFromDb.toString())
        val membersFromDb = chatMemberDao.getMembers(dialogFromDb[0].dialog.chatId)
        assert(membersFromDb.size == 3)
        assert(dialog.equals(dialogFromDb[0]))
        dialogDao.deleteDialogWithId(dialog.chatId)
        assert(chatMemberDao.getMembers(dialog.chatId).size == 0)
    }

}