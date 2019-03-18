package training.learn.lesson.ruby

import com.intellij.testGuiFramework.framework.GuiTestUtil
import com.intellij.testGuiFramework.util.Key
import training.learn.interfaces.Module
import training.learn.lesson.kimpl.KLesson
import training.learn.lesson.kimpl.LessonContext
import training.learn.lesson.kimpl.parseLessonSample

class RubyPostfixCompletionLesson(module: Module) : KLesson("Postfix Completion", module, "ruby") {
  private val sample = parseLessonSample("""class UsersController
  def create
    @user = User.new(user_params)
    @user.save<caret>
  end
end
""".trimIndent())
  private val result = parseLessonSample("""class UsersController
  def create
    @user = User.new(user_params)
    if @user.save
      <caret>
    end
  end
end
""".trimIndent()).text

  override val lessonContent: LessonContext.() -> Unit
    get() = {
      prepareSample(sample)
      task {
        text("TODO RubyMine propose postfix shortcuts. Type <code>.if</code> and press <action>EditorEnter</action>.")
        typeForTest(".if")
        testAction {
//          Thread.sleep(1000)
        }
        trigger("EditorChooseLookupItem")
      }
      task {
        text("TODO Now choose second item <code>@user.save</code>")
        stateCheck { editor.document.text == result }
        testAction {
//          Thread.sleep(500)
          GuiTestUtil.shortcut(Key.DOWN)
          GuiTestUtil.shortcut(Key.ENTER)
        }
      }
    }

  override val existedFile: String
    get() = "app/Completions.rb"
}