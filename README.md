# Showcase

[![](https://jitpack.io/v/trendyol/showcase.svg)](https://jitpack.io/#trendyol/showcase)

<img src="screenshots/1.png" width="240"/>    <img src="screenshots/2.png" width="240"/>    <img src="screenshots/3.png" width="240"/>    <img src="screenshots/4.png" width="240"/>    <img src="screenshots/5.png" width="240"/>   <img src="screenshots/6.png" width="240"/>

With **Showcase**, you can easily show tooltips. **Showcase** will highlight the view and show tooltip on it. You can
customize title and description text fields, backgrounds and arrow positions. You can also find out how the user closed
the showcase and in multi focus situations you can find out which view was clicked. The library supports both system fonts
and custom fonts for tooltip text, allowing you to maintain brand consistency across your app.

## Installation

- To implement **Showcase** to your Android project via Gradle, you need to add Jitpack repository to your
  root `build.gradle`.

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

- After adding Jitpack repository, you can add **Showcase** dependency to your app level `build.gradle`.

```
dependencies {
    implementation "com.github.trendyol::showcase:2.0.2"
}
```

## Usage

You can easily use ShowcaseManager.Builder to create **Showcase**.

```
val showcaseManager = ShowcaseManager.Builder()  
    .focus(myView)  
    .titleText("Title about myView")  
    .titleTextSize(22F) 
    .titleTextColor(ContextCompat.getColor(this, R.color.blue)) 
    .titleTextFontFamily("sans-serif-medium")  // Using system font
    .titleTextFontFamilyResId(R.font.custom_font)  // Using custom font
    .titleTextStyle(Typeface.BOLD)  
    .descriptionText("Little bit info for my lovely myView")   
    .windowBackgroundAlpha(127)  
    .arrowPosition(ArrowPosition.DOWN)
    .textPosition(TextPosition.START)
    .resId(R.style.Showcase_Theme)
    .build()  

showcaseManager.show(context) // or showcaseManager.show(context, REQUEST_CODE_SHOWCASE_CLICKED)
```

You can also provide layout resource id for more complex **Showcase** needs with `customContent`.

```
val showcaseManager = ShowcaseManager.Builder()
    .focus(myView)
    .customContent(R.layout.view_custom_content)
    .arrowPosition(ArrowPosition.UP)
    .build()

showcaseManager.show(context) // or showcaseManager.show(context, REQUEST_CODE_SHOWCASE_CLICKED)
```

### Builder Configuration

| Usage                                                   | Description                                                                     | Optional | Default Value               | StyleRes |
|:--------------------------------------------------------|:--------------------------------------------------------------------------------|:---------|:----------------------------|:---------|
| `builder.focus(View)`                                   | view to be focused on                                                           | no       | null                        | no       |
| `builder.focus(Array<View>)`                            | view array to be focused on                                                     | no       | null                        | no       |
| `builder.resId(Int)`                                    | Showcase.Theme style                                                            | yes      | null                        | yes      |
| `builder.titleText(String)`                             | text to be showed on top of the tooltip                                         | yes      | ""                          | no       |
| `builder.descriptionText(String)`                       | description text will be displayed on tooltip                                   | yes      | ""                          | no       |
| `builder.titleTextColor(Int)`                           | titleText's color                                                               | yes      | Color.BLACK                 | yes      |
| `builder.descriptionTextColor(Int)`                     | descriptionText's color                                                         | yes      | Color.BLACK                 | yes      |
| `builder.titleTextSize(Int)`                            | titleText's text size in SP                                                     | yes      | 18 SP                       | no       |
| `builder.descriptionTextSize(Int)`                      | descriptionText's text size in SP                                               | yes      | 14 SP                       | no       |
| `builder.titleTextFontFamily(String)`                   | titleText's fontFamily (system fonts)                                           | yes      | sans-serif                  | yes      |
| `builder.titleTextFontFamilyResId(Int)`                 | titleText's custom font resource ID                                             | yes      | null                        | yes      |
| `builder.descriptionTextFontFamily(String)`             | descriptionText's fontFamily (system fonts)                                     | yes      | sans-serif                  | yes      |
| `builder.descriptionTextFontFamilyResId(Int)`           | descriptionText's custom font resource ID                                       | yes      | null                        | yes      |
| `builder.titleTextStyle(Int)`                           | titleText's textStyle                                                           | yes      | Typeface.NORMAL             | yes      |
| `builder.descriptionTextStyle(Int)`                     | descriptionText's textStyle                                                     | yes      | Typeface.NORMAL             | yes      |
| `builder.backgroundColor(Int)`                          | background color of tooltip                                                     | yes      | Color.WHITE                 | yes      |
| `builder.closeButtonColor(Int)`                         | closeButton's color                                                             | yes      | Color.BLACK                 | yes      |
| `builder.showCloseButton(Boolean)`                      | show close button on tooltip                                                    | yes      | true                        | yes      |
| `builder.setArrowVisibility(Boolean)`                   | controls whether arrow icon should be shown or not                              | yes      | true                        | no       |
| `builder.arrowResource(Int)`                            | custom icon resource for the arrow rotates 180° if the arrow position is bottom | yes      | ic_arrow_up                 | no       |
| `builder.arrowPosition(ArrowPosition)`                  | arrow can be placed under or over the tooltip                                   | yes      | ArrowPosition.AUTO          | no       |
| `builder.arrowPercentage(Int)`                          | arrow position percentage can be decided                                        | yes      | null                        | no       |
| `builder.highlightType(HighlightType)`                  | view can be highlighted with a circle shape or rectangle                        | yes      | HighlightType.RECTANGLE     | no       |
| `builder.cancelListener(CancelListener)`                | will be called after user quit from tooltip                                     | yes      | null                        | no       |
| `builder.windowBackgroundColor(Int)`                    | background of the window's color can be decided                                 | yes      | Color.BLACK                 | yes      |
| `builder.windowBackgroundTint(Int)`                     | alpha value of window's background color                                        | yes      | 204                         | no       |
| `builder.titleTextSize(Int)`                            | titleText's text size in SP                                                     | yes      | 18                          | no       |
| `builder.cancellableFromOutsideTouch(Boolean)`          | outside touch from tooltip will act as close click                              | yes      | false                       | yes      |
| `builder.showcaseViewClickable(Boolean)`                | makes the showcase view clickable or not                                        | yes      | false                       | yes      |
| `builder.isDebugMode(Boolean)`                          | tooltip won't be presented                                                      | yes      | false                       | no       |
| `builder.attachOnParentLifecycle(Boolean)`              | observe parent lifecycle and dismiss showcase                                   | yes      | false                       | no       |
| `builder.textPosition(TextPosition)`                    | text can be positioning center, end and start                                   | yes      | TextPosition.START          | no       |
| `builder.imageUrl(String)`                              | show image on tooltip                                                           | yes      | null                        | no       |
| `builder.customContent(Int)`                            | show given layout                                                               | yes      | null                        | no       |
| `builder.statusBarVisible(Boolean)`                     | statusBar visibility of window                                                  | yes      | true                        | no       |
| `builder.toolTipVisible(Boolean)`                       | tooltip visibility                                                              | yes      | true                        | no       |
| `builder.highlightRadius(Float, Float, Float, Float)`   | tooltip visibility                                                              | yes      | 0f, 0f, 0f, 0f              | no       |
| `builder.setSlidableContentList(List<SlidableContent>)` | show slidable content                                                           | yes      | null                        | no       |  
| `builder.showDurationMillis(Long)`                      | duration of the tooltip visibility                                              | yes      | 2000L                       | no       |
| `builder.showcaseViewVisibleIndefinitely(Boolean)`      | controls tooltip visibility condition                                           | yes      | true                        | no       |
| `builder.build()`                                       | will return ShowcaseManager instance                                            | no       |                             |          |
| `showcaseManager.show(Context)`                         | show the tooltip with set attributes on                                         | no       |                             |          |

### Action Result

By overriding `onActivityResult` you can get feedback based on the types in the ActionType class.

If the actionType is `HIGHLIGHT_CLICKED`, the `KEY_SELECTED_VIEW_INDEX` parameter returns the index of the clicked view.
If no view is clicked, the index will be -1.

```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SHOWCASE_CLICKED && data != null) {
            val actionType = data.getSerializableExtra(ShowcaseView.KEY_ACTION_TYPE) as ActionType
            val selectedViewIndex = data.getIntExtra(ShowcaseView.KEY_SELECTED_VIEW_INDEX, -1)
            Log.i("MainActivity", "onActivityResult: actionType=${actionType.name} and selected view index=${selectedViewIndex}")
        }
    }
```

License
--------

    Copyright 2023 Trendyol.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
