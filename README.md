# MPlayer
Assignment for WeavedIn
Itunes Music Preview Player

Android application

Challenge
Design and Develop “Itune music preview music player” Android application which allows users to search and play preview music tracks available in itunes store

GOALS
Application has four screens as described below

Search and list tracks

Stream and play musics, and add to favorites

Application only works in portrait mode and supports mdpi to xxxhdpi

SPECIFICATIONS
Splash  Screen

Shows for 2 sec on application start and then shows search bar

Clicking on search bar takes user to Search screen



Search Screen

Has search bar which fetches tracks from http://itunes.apple.com/search?term=jack&limit=4

Limit results to size of the screen, no vertical scrolling

Sliding left takes to next page of results and sliding right takes to prev page of results (of the same search), dashes at the bottom shows the page position 

Clear previous results on new search

Autocomplete search bar from previous searches

Taping the search result, highlight the track and go to Player Screen

The favorites button on top takes the user to favorites list screen

		

Player Screen

Track starts playing automatically

Shows the artist/album picture

Shows progress bar which updated as music plays with remaining time as min:sec

Back & List button takes to search screen with same item highlighted or to the favorites screen if the tack picked from favorites

There should be buttons to pause/play/add to favorites(white & red)

		 

Favorites Screen

Shows the list of tracks added to the favorites list

Tap on a track shows the player screen

It’s a vertical scrollable list

Swipe on the track removes track from favorites (no confirmation required)

		

