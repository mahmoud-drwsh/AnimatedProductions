Since Compose is used in this project, the way testing is done differs completely from the way views
are tested, so when testing a Composable function, test tags are used in place of view ID's, and
those ID's are kept in Kotlin files to help with organizing the code in a way that is manageable.
For the Home screen (MainActivity) in its subpackage, a file named MainActivityTestTags can be found
and in it, all the test tags that are for the Home screen are placed. As for the other screens, for
each there is a file for keeping the test tags in is found in its subpackage.

One more thing to keep in mind is that since Flows are used for asynchronous data fetching, the
tests differ in that regard as well.


---

Please refer to this page to understand in detail how testing is done with Compose:
https://developer.android.com/jetpack/compose/testing

---

Test Scenarios:

For each of the screens, I have written 3 different collections of tests for the success, loading,
and failure states. I have been blessed with architecting the app in such a way that by providing
the desired state representation object to the root composable, I have the UI state that I want
which the test is done to.

Since I have background operations that sometimes need to be waited for to be finished before the
test can start, I have applied the concept of an idlingResource object which is used by the Compose
testing framework to pause the test while the background operation is still being executed.

It is crucial to keep in mind that Compose differs from the view UI system even though there might
be similarities like the concept of an IdlingResource object which is not exactly like how it is
applied when testing views. 

Each of the Composables below has a distinct test tag that denotes for which Composable it is.   

For the Home screen

Success state:
a. Ensure the TabLayout is shown.
b. Ensure the movies tab is shown.
c. Ensure the shows tab is shown.
d. Ensure the movies list is shown.
e. Ensure the shows list is shown.
f. Ensure the movies list has clickable descendants.
g. Ensure the shows list has clickable descendants.
h. Click on a movie and ensure that the details screen is displayed.
i. Navigate to the shows list and click on a show and ensure that the details screen is displayed.


Loading state:
a. Ensure the loading indicator is shown.

Failure state:
a. Ensure the error message is displayed.


For the Details screen

Success state:
a. Ensure the backdrop image is shown.
b. Ensure the poster image is shown.
c. Ensure the title text is shown.
d. Ensure the overview text is shown. 

Loading state:
a. Ensure the loading indicator is shown.

Failure state:
a. Ensure the error message is displayed.


