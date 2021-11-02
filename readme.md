Since Compose is used in this project, the way testing is done differs completely from the way views
are tested, so when testing a Composable function, test tags are used in place of view ID's, and
those test tags are kept in Kotlin files to help with organizing the code in a way that is
manageable. For the Home screen (MainActivity) in its subpackage, a file named MainActivityTestTags
can be found and in it, all the test tags that are for the Home screen are placed. As for the other
screens, for each there is a file for keeping the test tags in is found in its subpackage.

One more thing to keep in mind is that since Flows are used for asynchronous data fetching, the
tests differ in that regard as well. And since Hilt is used for dependency injection, most tests had
to be written in the androidTest package since Hilt depends on the Android framework.

Please refer to this page for a detailed description of how testing is done with Compose:
https://developer.android.com/jetpack/compose/testing

The following libraries were used in the writing of the app:

1. Jetpack Hilt (for dependency injection)
2. Jetpack Compose (for the UI)
3. Jetpack ViewModel
4. Kotlin Flow
5. Compose Coil
6. Retrofit (networking)
7. Google's Gson (for JSON parsing)
8. Google's Accompanist (for paging Composables)
9. Jetpack DataStore

---

The Test Scenarios:

For each of the screens, I have written 3 different collections of tests for the success, loading,
and failure states. I have been blessed with architecting the app in such a way that by providing
the desired state representation object to the root Composable, I have the UI state that I want
which the test is done to.

Since I have background operations that sometimes need to be waited for to be finished before the
test can start, I have applied the concept of an idlingResource object which is used by the Compose
testing framework to pause the test while the background operation is still being executed.

It is crucial to keep in mind that Compose differs from the view UI system even though there might
be similarities like the concept of an IdlingResource object which is not exactly like how it is
applied when testing views.

Each of the Composables below has a distinct test tag that denotes for which Composable it is.

The different scenarios for the Home screen:

Success state:
a. Ensure the TabLayout is shown by asserting that the Composable that has the test tag of tabLayout
is displayed.

b. Ensure the movies tab is shown by asserting that the Composable that has the test tag of the
movies tab is displayed.

c. Ensure the shows tab is shown by asserting that the Composable that has the test tag of the shows
tab is displayed.

d. Ensure the movies list is shown by asserting that the Composable that has the test tag of the
movies list is displayed.

e. Ensure the shows list is shown by asserting that the Composable that has the test tag of the
shows list is displayed.

f. Ensure the movies list has clickable descendants by asserting that the Composable that has the
test tag of the shows list has clickable descendants which are the images of the movies posters.

g. Ensure the shows list has clickable descendants by asserting that the Composable that has the
test tag of the shows list has clickable descendants which are the images of the shows posters.

h. Click on a movie and ensure that the details screen is displayed by asserting that the Composable
with the test tag for the details screen root Composable is displayed.

i. Navigate to the shows list and click on a show and ensure that the details screen is displayed by
asserting that the Composable with the test tag for the details screen root Composable is displayed.

Loading state:

a. Ensure the loading indicator is shown by invoking the home screen Composable with a loading state
object which will cause the loading indicator to be displayed then an assertion will be made to
ensure that it is displayed by using the test tag for the loading indicator as the identifier for
the Composable.

Failure state:

a. Ensure the error message is displayed by asserting that the Composable with the test tag for the
error message Composable is displayed after invoking the Composable with the failure state object.

For the Details screen

Success state:

a. Ensure the backdrop image is shown by asserting that the Composable with the test tag for the
backdrop image is displayed.

b. Ensure the poster image is shown by asserting that the Composable with the test tag for the
poster image is displayed.

c. Ensure the title text is shown by asserting that the Composable with the test tag for the title
text is displayed.

d. Ensure the overview text is shown by asserting that the Composable with the test tag for the
overview text is displayed.

Loading state:

a. Ensure the loading indicator is shown by invoking the details screen Composable with a loading
state object which will cause the loading indicator to be displayed then an assertion will be made
to ensure that it is displayed by using the test tag for the loading indicator as the identifier for
the Composable.

Failure state:

a. Ensure the error message is displayed by asserting that the Composable with the test tag for the
error message Composable is displayed after invoking the Composable with the failure state object.

