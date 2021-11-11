Since Hilt and Compose are used in this project, the way testing is done differs completely from the way Views are tested, so when testing a Composable function, test tags are used in place of View ID's, and those test tags are kept in Kotlin files to help with organizing the code in a way that is manageable. The Home screen (MainActivity) has in its subpackage, a file named MainActivityTestTags that in it, all the test tags that are for the Home screen are placed. As for the other screens, for each there is a file for keeping its test tags in and it is found in its subpackage.

One more thing to keep in mind is that since Flows are used for asynchronous data fetching, the tests differ in that regard as well. And since Hilt is used for dependency injection, most tests had to be written in the androidTest folder since Hilt depends on the Android framework.

I have placed just one test for a ViewModel under the tests directory, but the other one, due to it depending on Hilt, I had to place it under the androidTest directory since it needed access to the Android framework. 

Please refer to this page for a detailed description of how testing is done with Compose: https://developer.android.com/jetpack/compose/testing

I have applied the concept of an IdlingResource object in this project, but due to problems with one of the Compose testing framework libraries, the implementation of the IdlingResource interface for Compose was possible to be written only in the androidTest folder. So, the IdlingResource interface is implemented in the androidTest folder, and it depends on an object in the main folder to determine whether the screen is idle or not at a given moment.  

--------------------------------------------------

The following libraries were used in the writing of the app:
1. Jetpack Hilt (for dependency injection).
2. Jetpack Compose (for the UI).
3. Jetpack ViewModel.
4. Kotlin Flow.
5. Compose Coil.
6. Retrofit (networking).
7. Google's Gson (for JSON parsing).
8. Google's Accompanist (for paging Composables).
9. Jetpack DataStore.
10. And more.

--------------------------------------------------

The Test Scenarios:

For each of the screens, I have written 3 different collections of tests for the success, loading, and error states. I have been blessed with architecting the app in such a way that by providing the desired state representation object to the root Composable, I have the UI state that I want to test.

Since I have background operations that sometimes need to be waited for to be finished before the test can start, I have applied the concept of an IdlingResource object which is used by the Compose testing framework to pause the test while the background operation is still being executed.

It is crucial to keep in mind that Compose differs from the View UI system even though there might be similarities like the concept of an IdlingResource object which is not exactly like how it is applied when testing Views.

Each of the Composables that are targeted in the tests has a distinct test tag that denotes for which Composable it is. For example, for the Details Screen root Composable, there is a test tag for it only. 

--------------------------------------------------

The different scenarios for the Home screen:

Success state:
1. Ensure the TabLayout is shown by asserting that the Composable that has the test tag of tabLayout is displayed.
2. Ensure the movies tab is shown by asserting that the Composable that has the test tag of the movies tab is displayed.
3. Ensure the shows tab is shown by asserting that the Composable that has the test tag of the shows tab is displayed.
4. Ensure the movies list is shown by asserting that the Composable that has the test tag of the movies list is displayed.
5. Ensure the shows list is shown by asserting that the Composable that has the test tag of the shows list is displayed.
6. Ensure the movies list has clickable descendants by asserting that the Composable that has the test tag of the shows list has clickable descendants which are the images of the movies posters.
7. Ensure the shows list has clickable descendants by asserting that the Composable that has the test tag of the shows list has clickable descendants which are the images of the shows posters.
8. Click on a movie and ensure that the details screen is displayed by asserting that the Composable with the test tag for the details screen root Composable is displayed.
9. Navigate to the shows list and click on a show and ensure that the details screen is displayed by asserting that the Composable with the test tag for the details screen root Composable is displayed.
10. Ensure the icon button for the sorting options dialog is shown by asserting that the composable with the test tag for the sorting options icon button is shown.
11. Ensure the options shown are all shown by asserting that all the sorting option labels are shown.

Loading state:
1. Ensure the loading indicator is shown by invoking the home screen Composable with a loading state object which will cause the loading indicator to be displayed then an assertion will be made to ensure that it is displayed by using the test tag for the loading indicator as the identifier for the Composable.

Failure state:
1. Ensure the error message is displayed by asserting that the Composable with the test tag for the error message Composable is displayed after invoking the Composable with the error state object.


For the Details screen:

Success state:
1. Ensure the backdrop image is shown by asserting that the Composable with the test tag for the backdrop image is displayed.
2. Ensure the poster image is shown by asserting that the Composable with the test tag for the poster image is displayed.
3. Ensure the title text is shown by asserting that the Composable with the test tag for the title text is displayed.
4. Ensure the overview text is shown by asserting that the Composable with the test tag for the overview text is displayed.

Loading state:
1. Ensure the loading indicator is shown by invoking the details screen Composable with a loading state object which will cause the loading indicator to be displayed then an assertion will be made to ensure that it is displayed by using the test tag for the loading indicator as the identifier for the Composable.

Failure state:
1. Ensure the error message is displayed by asserting that the Composable with the test tag for the error message Composable is displayed after invoking the Composable with the error state object.
