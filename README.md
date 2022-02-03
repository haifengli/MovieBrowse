
IMDB Movie search example
=======================================================
This is the SalesForce homework to show case how to search the movie using OMDB API and persist the favorite movies in local database.

### Build
AS version: Arctic Fox 2020.3.1
gradle: 7.0.0
minSDK: 27
targetSDK: 32
Tested Device:  Pixel 3a with Android 11(API level 30)

### Architecture
The app adopts the 'MVVM' design pattern which consists of 2 layers : UI layer and data layer.
The classes under 'ui' package belong to the UI layer. 'MovieContainerFragment' is the parent fragment for 'SearchFragment' and
'FavoritesFragment'. 'DetailFragment' display the Movie detail. Each fragment is accompanied 
The data layers consist of 'SearchAPIServiceRepo' and 'MovieDB'. One consumes the 'omdbapi' data source, while the other
consumes the local database as the source.


### How to use the app
When app loads, it displays the 'peace' sample word in the input box. Put the focus in the input box to trigger the IME, then
tap on the 'Search' icon of the keyboard. The search results would display. Tap on any movie item would go
to the detail page. Toggling the star on the right side of the Movie item in search page would insert and remove the movie into the favorites list.
The favorite list persists across the app launches.

In case the app does not display the results, try the following URL to make sure that response does come back:

"https://www.omdbapi.com/?s=monster&apikey=e5606376"
"https://www.omdbapi.com/?s=monster&apikey=d350b2c7"

######  

### Git repo
https://github.com/haifengli/MovieBrowse.git

### Command line build
./gradlew assembleDebug
./gradlew assembleRelease


### TODO

Pagination for the search result
Add Instrumental test cases
Introduce Dependency Injection
Clean up the hardcoded resource values

