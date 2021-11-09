Since Compose and Hilt are used in this project, the way testing is done differs completely from the way testing is taught in the course, for example, when testing a Composable function, test tags are used in place of View ID's, and those test tags are kept in Kotlin files and gathered in enum classes to help with organizing the code in a way that is manageable and to help prevent using the same string for more than one Composable. The Home screen (MainActivity) has in its subpackage, a file named MainActivityTestTags that in it, all the test tags that are for the Home screen are placed in an enum class. As for the other screens, for each there is a file for keeping its test tags in and it is found in its subpackage.

Since Hilt is used for dependency injection, most tests had to be written in the androidTest folder since Hilt depends on the Android framework. And since Flows are used for asynchronous data fetching, the tests differ in that regard as well.

Please refer to this page for a detailed description of how testing is done with Compose: https://developer.android.com/jetpack/compose/testing

I have applied the concept of an IdlingResource object in this project, but due to problems with one of the Compose testing framework libraries, the implementation of the IdlingResource interface for Compose was possible to be written only in the androidTest folder. So, the IdlingResource interface is implemented in the androidTest folder, and it depends on an object in the main folder to determine whether the screen is idle or not at a given moment. The problem with that library is that whenever it's added as an implementation dependency rather than an androidTestImplementation one, the app wouldn't compile since it seems it has a bug that has not been removed yet.

--------------------------------------------------

The following libraries were used in the writing of the animatedProductionsApp:
1. Jetpack Hilt (for dependency injection).
2. Jetpack Compose (for the UI).
3. Jetpack ViewModel.
4. Kotlin Flow.
5. Compose Coil.
6. Retrofit (networking).
7. Google's Gson (for JSON parsing).
8. Jetpack DataStore.
9. Jetpack Room.
10. And more.

--------------------------------------------------

The Test Scenarios:

Since I have background operations that sometimes need to be waited for to be finished before the test can start, I have applied the concept of an IdlingResource object that is used by the Compose testing framework to pause the test while the background operation is still being executed.

It is crucial to keep in mind that Compose differs from the View UI system even though there might be similarities like the concept of an IdlingResource object which is not applied exactly as when it is applied when testing Views.

Each of the Composables that are targeted in the tests has a distinct test tag that denotes for which Composable it is. For example, for the Details Screen root Composable, there is a test tag for it only.

--------------------------------------------------

The different scenarios for the Home screen:

IdlingResource dependant tests:
1. Ensuring the bottom navigation bar is shown by asserting that the Composable with its test tag is displayed.
2. Ensuring the movies list destination bottom navigation bar item is shown by asserting that the Composable which has its tag is shown.
3. Ensuring the shows list destination bottom navigation bar item is shown by asserting that the Composable which has its tag is shown.
4. Ensuring the favorite movies list destination bottom navigation bar item is shown by asserting that the Composable which has its tag is shown.
5. Ensuring the favorite shows list destination bottom navigation bar item is shown by asserting that the Composable which has its tag is shown.
6. Ensuring that the a production details can be navigated to from the movies list destination by asserting that the composable with the test tag for the details root composable is displayed after clicking on the shown poster for the production.
7. Ensuring that the a production details can be navigated to from the shows list destination by asserting that the composable with the test tag for the details root composable is displayed after clicking on the shown poster for the production.
8. Ensuring that the a production details can be navigated to from the favorite movies list destination by asserting that the composable with the test tag for the details root composable is displayed after clicking on the shown poster for the production and after adding dummy favorites.
9. Ensuring that the a production details can be navigated to from the favorite shows list destination by asserting that the composable with the test tag for the details root composable is displayed after clicking on the shown poster for the production and after adding dummy favorites.
10. Ensuring the button for toggling the UI mode between night and light is shown by asserting that the composable with the test tag for the UI mode toggling is displayed.
11. Ensuring that the UI mode toggling feature works by checking the stored data for UI mode after clicking on the button for toggling the UI mode.
12. Ensuring that the top bar is shown by asserting that the composable with its corresponding test tag is displayed.


For the Details screen:

Success state:
1. Ensuring the backdrop image is shown by asserting that the Composable with the test tag for the backdrop image is displayed.
2. Ensuring the poster image is shown by asserting that the Composable with the test tag for the poster image is displayed.
3. Ensuring the title text is shown by asserting that the Composable with the test tag for the title text is displayed.
4. Ensuring the overview text is shown by asserting that the Composable with the test tag for the overview text is displayed.

Loading state:
1. Ensuring the loading indicator is shown by invoking the details screen Composable with a loading state object which will cause the loading indicator to be displayed then an assertion will be made to ensure that it is displayed by using the test tag for the loading indicator as the identifier for the Composable.

Failure state:
1. Ensuring the error message is displayed by asserting that the Composable with the test tag for the error message Composable is displayed after invoking the Composable with the error state object.