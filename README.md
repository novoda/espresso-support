# espresso-support [![Download](https://api.bintray.com/packages/novoda/maven/espresso-support/images/download.svg)](https://bintray.com/novoda/maven/espresso-support/_latestVersion) [![License](https://raw.githubusercontent.com/novoda/novoda/master/assets/btn_apache_lisence.png)](LICENSE)

Includes custom rules for [testing Views in isolation](https://www.novoda.com/blog/testing-views-in-isolation-with-espresso/) and [running Espresso tests with Google TalkBack enabled](https://www.novoda.com/blog/testing-talkback-in-isolation-with-espresso/).

The library is split into two components:

- core - main espresso dependency (`androidTestCompile`)
- extras - optional, depending on which classes you use (`debugCompile`/`espressoCompile`)

The `extras` dependency adds some components to your app (hence it's not `androidTestCompile`), e.g. the `ViewActivity`.

## Usage

The artifacts are available on JCenter:

```
debugCompile 'com.novoda:espresso-support-extras:<latest-version>'
androidTestCompile 'com.novoda:espresso-support:<latest-version>'
```

See `demo/androidTest` for examples.

## Testing views in isolation

Use the `ViewTestRule`, passing in a layout resource. It'll inflate the resource into the `ViewActivity` with `MATCH_PARENT` for both dimensions. You can use `rule.getView()` to obtain an instance of the View and it'll be typed to the class you specified.

```java
@Rule
public ViewTestRule<MovieItemView> rule = new ViewTestRule<>(R.layout.test_movie_item_view);
```

You can write BDD style tests here, highlighting the expected behavior for your custom views, using a mixture of Espresso ViewActions and Mockito verifies:

```java
@Test
public void givenViewIsUpdatedWithDifferentMovie_whenClicking_thenListenerDoesNotGetFiredForOriginalMovie() {
    givenMovieItemViewIsBoundTo(EDWARD_SCISSORHANDS);
    givenMovieItemViewIsBoundTo(NOT_EDWARD_SCISSORHANDS);

    onView(withId(R.id.movie_item_button_play)).perform(click());

    verify(movieItemListener, never()).onClickPlay(eq(EDWARD_SCISSORHANDS));
}

private void givenMovieItemViewIsBoundTo(final Movie movie) {
    viewTestRule.runOnUiThread(new ViewTestRule.Runner<MovieItemView>() {
        @Override
        public void run(MovieItemView view) {
            view.bind(movie);
        }
    });
}
```

:warning: This rule requires the `extras` module to be included for the app under test, so it can open the `ViewActivity`.

## Testing behavior with TalkBack enabled

Often, our apps will behave differently when TalkBack is enabled to offer a more streamlined experience for users of screen readers.

Use either `TalkBackTestRule` wrapped around a `ViewTestRule`/`ActivityTestRule` to start/stop TalkBack before and after each test.

```java
private ViewTestRule<MovieItemView> viewTestRule = new ViewTestRule<>(R.layout.test_movie_item_view);

@Rule
public RuleChain chain = RuleChain.outerRule(new TalkBackTestRule()).around(viewTestRule);
```

TalkBack will be enabled before each test is run and disabled after each test finishes.

:warning: This rule requires the `extras` module to be included for the app under test, so it can open the `TalkBackStateSettingActivity`.

:warning: Toggling TalkBack state requires the `WRITE_SECURE_SETTINGS` permission being set for the app under test.

## Demo

You can run the demo tests with the following command:

```bash
./gradlew demo:installDebug;\
adb shell pm grant com.novoda.movies android.permission.WRITE_SECURE_SETTINGS;\
adb shell am start -a "com.novoda.espresso.DISABLE_TALKBACK";\
./gradlew demo:cAT;
```

1. First the app is installed
2. The `WRITE_SECURE_SETTINGS` permission is set for the app (`com.novoda.movies`)
3. Disable TalkBack (does nothing if it wasn't running)
4. Run all the connected Android tests

## Links

Here are a list of useful links:

 * We always welcome people to contribute new features or bug fixes, [here is how](https://github.com/novoda/novoda/blob/master/CONTRIBUTING.md)
 * If you have a problem check the [Issues Page](https://github.com/novoda/espresso-support/issues) first to see if we are working on it


