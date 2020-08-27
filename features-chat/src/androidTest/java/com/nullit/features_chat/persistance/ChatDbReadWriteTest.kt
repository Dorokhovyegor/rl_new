package com.nullit.features_chat.persistance

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.nullit.features_chat.persistance.dao.ChatMemberDao
import com.nullit.features_chat.persistance.dao.DialogDao
import com.nullit.features_chat.persistance.dao.MessageDao
import com.nullit.features_chat.persistance.entity.DialogEntity
import com.nullit.features_chat.persistance.entity.MemberEntity
import com.nullit.features_chat.persistance.entity.MessageEntity
import com.nullit.features_chat.utils.DialogFactory
import com.nullit.features_chat.utils.MemberFactory
import com.nullit.features_chat.utils.MessageFactory
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.random.Random

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
    fun test_writeDialogsWithMembersAndDeleteDialog() = runBlocking {
        val dialog = dialogFactory.generateDialog()
        val members = memberFactory.generateUsersInChat(dialog.chatId, 3)
        dialogDao.insertDialogWithMembers(dialog)
        chatMemberDao.insertMembersIntoDialog(members)
        dialogDao.deleteDialogWithId(dialog.chatId)
        assert(chatMemberDao.getMembers(dialog.chatId).size == 0)
    }

    @Test
    @Throws(Exception::class)
    fun test_writeManyDialogsAndDeleteDialogWithIdThenCheckItInDb() = runBlocking {
        val dialogs = dialogFactory.getDialogs(100)
        dialogDao.insertDialogsWithMembers(dialogs)
        val deleteId = dialogs[Random.nextInt(0, 100)].chatId
        dialogDao.deleteDialogWithId(deleteId)
        val newDialogs = dialogDao.getDialogs()
        var doesNotHaveDeletedDialog = true
        newDialogs.forEach {
            if (it.dialog.chatId == deleteId) {
                doesNotHaveDeletedDialog = false
            }
        }
        val result = (newDialogs.size == 99) && doesNotHaveDeletedDialog
        assert(result)
    }

    @Test
    @Throws(Exception::class)
    fun test_writeManyDialogsWithMembersAndMessagesAndDeleleDialogAndCheckThatMessagesAndMembersGoOutFromDb() =
        runBlocking {
            // creating dialogs, members and messages
            val dialogs = dialogFactory.getDialogs(10)
            val members = ArrayList<MemberEntity>()
            for (i in 0..100) {
                members.add(memberFactory.generateMember(dialogs.random().chatId))
            }
            val messages = ArrayList<MessageEntity>()
            for (i in 0..1000) {
                val dialogId = dialogs.random().chatId
                val membersInThisDialog = (members.clone() as ArrayList<MemberEntity>).filter {
                    it.chatId == dialogId
                }
                messages.add(
                    messageFactory.generateMessage(
                        dialogs.random().chatId,
                        membersInThisDialog.random().userId
                    )
                )
            }

            // inserting dialogs, messages and members
            dialogDao.insertDialogsWithMembers(dialogs)
            chatMemberDao.insertMembersIntoDialog(members)
            messageDao.insertMessagesIntoDialog(messages)

            // checking data
            val dialogResult = dialogDao.getDialogs()
            val dialogSizeCorrect = if (dialogResult.size == 10) true else false
            val newDialogId = dialogDao.getDialogs().random().dialog.chatId
            dialogDao.deleteDialogWithId(newDialogId)
            val dialogSizeAfterDeleteCorrect = if (dialogDao.getDialogs().size == 9) true else false
            val messageArrayIsEmpty =
                if (messageDao.getLastMessages(newDialogId).isEmpty()) true else false
            val memberArrayIsEmpty =
                if (chatMemberDao.getMembers(newDialogId).isEmpty()) true else false
            assert(dialogSizeCorrect && dialogSizeAfterDeleteCorrect && messageArrayIsEmpty && memberArrayIsEmpty)
        }
}