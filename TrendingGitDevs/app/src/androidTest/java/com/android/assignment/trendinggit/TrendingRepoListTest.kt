package com.android.assignment.trendinggit


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TrendingRepoListTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun trendingGitDevTest() {

        //switching to Repository Tab
        Thread.sleep(5000)
        val tabViewRepoList = onView(
            allOf(
                withContentDescription("Repository"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabs),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        tabViewRepoList.perform(click())

        Thread.sleep(5000)
        //perform swipe
        testSwipeToRefresh()

        //Testing Repo List Item Click Listener
        Thread.sleep(3000)
        testOnItemClickRepoListItem()

        //Testing Contributor List Item Click Listener
        Thread.sleep(3000)
        testOnContributorItemClickRepoListItem()
    }

    private fun testOnItemClickRepoListItem() {
        val cardView = Espresso.onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.rv_repo_list)
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        cardView.perform(ViewActions.click())
    }

    private fun testOnContributorItemClickRepoListItem() {
        val cardView = Espresso.onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.rv_repo_dev_list)
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        cardView.perform(ViewActions.click())
    }

    private fun testSwipeToRefresh() {
        Espresso.onView(ViewMatchers.withId(R.id.swipe_refresh_layout))
            .perform(
                withCustomConstraints(
                    ViewActions.swipeDown(),
                    ViewMatchers.isDisplayingAtLeast(85)
                )
            )

        Thread.sleep(3000)
    }

    private fun withCustomConstraints(
        action: ViewAction,
        constraints: Matcher<View>
    ): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController?, view: View?) {
                action.perform(uiController, view)
            }
        }
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
