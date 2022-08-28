# ArtBrowser

**ArtBrowser** application was made by me to study current technologies and approaches in Android mobile development. 
The application was made as part of self-education process and has no commercial purpose.
## About the App
The application uses DeviantArt API and allows the user to check for the latest images uploaded to the website. 
It allows the user to select the image and view it in a separate window.
The image can be zoomed, added to the user favorites list or removed from it.
The favorites window shows which images are already in the list.
The profile window displays user info and statistics.
## Built by
* Kotlin
* MVVM
* AppAuth
* Dagger 2
* RxJava2
* Room
* Retrofit
* LiveData
* Glide
* TSnackBar
* PhotoView

## Screenshots

### Authorization
<img src="https://user-images.githubusercontent.com/22412695/187079136-b680b3cb-b77f-4839-9637-c32ecbc5122c.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187079159-bc4ee9c4-8dab-4432-ab35-419487110ac4.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187079165-8e48ca70-31ce-45a7-8613-adc001d81f29.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187079172-50f84660-79f8-424f-b949-b264eca25427.png" width="216">

### Profile
<img src="https://user-images.githubusercontent.com/22412695/187079265-654fb4bd-3b7a-4984-9fb5-b716c1ceb803.png" width="216">

### Favorites
<img src="https://user-images.githubusercontent.com/22412695/187079378-473bfc9c-3ffd-452c-a425-ccdc555c4613.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187079382-04595c27-19a9-4743-8dca-8494340b349e.png" width="216">

### Zoom
<img src="https://user-images.githubusercontent.com/22412695/187079393-516c3e64-ea2c-451c-aa61-70e3c8d48c30.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187079399-3b5e21be-48cd-4f14-8517-a000f6a04133.png" width="216">

### Browse
<img src="https://user-images.githubusercontent.com/22412695/187079815-c105d449-03a5-4889-a038-757ddd5e83ee.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187079817-d2fd2e23-3000-4025-8eed-679db7f56707.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187079820-b92f9a64-1acc-42c6-82fd-c34a7764bf99.png" width="216">

### Add to favorites
Statistic before:

<img src="https://user-images.githubusercontent.com/22412695/187079876-0c79d478-e876-4c47-9813-3db2631f3f88.png" width="216">

Favorites list before:

<img src="https://user-images.githubusercontent.com/22412695/187079913-4b0007d6-1e0f-4610-8d8b-2ead028feab0.png" width="216">

Select and add image to the favorites:

<img src="https://user-images.githubusercontent.com/22412695/187079971-b2d6f5f2-b777-45f5-9f10-ea2f4b8b5d7d.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187079978-04364422-c4c4-43e6-b1ff-654efbc76c79.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187079984-70c83c7a-8564-487f-98c7-50ea9e5d6d06.png" width="216">

Favorites list after:

<img src="https://user-images.githubusercontent.com/22412695/187080015-16e0949c-12ca-4bb4-986f-84ce62304917.png" width="216">

Statistic after:

<img src="https://user-images.githubusercontent.com/22412695/187080049-5bdf9a6d-98d7-45e0-b6c3-4531da6a550e.png" width="216">

### Remove from favorites
<img src="https://user-images.githubusercontent.com/22412695/187080116-a82dfa90-913a-44a2-ad5e-32dcf7a7f45e.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187080120-c40cde68-ea50-40a1-94f7-057305fee08a.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187080123-07653723-b4b6-4d49-93a3-f5c1af4a7e3e.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187080129-a88b4758-9a60-4455-8076-7c4909583e9d.png" width="216">

### Loading
<img src="https://user-images.githubusercontent.com/22412695/187080145-296ac6d4-7156-4ad2-b50d-175d4f9ab64c.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187080148-e0e566a1-d29e-40b9-a204-2430664fafb8.png" width="216">

### No connection error
<img src="https://user-images.githubusercontent.com/22412695/187080208-6be36672-50c2-4a94-a21e-436494def6fe.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187080212-830e6735-f2b4-4b43-841a-2ed311e5e3bd.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187080220-f6e7c7a5-338f-411a-b6a8-37f8f616ca25.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187080225-fe588d20-3d2d-45c2-b266-50a84c264072.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187080230-4dcaf1f4-cfde-4ca2-9c45-9bcfa1b7eb0f.png" width="216">   <img src="https://user-images.githubusercontent.com/22412695/187080234-61ca21ea-05ae-4c77-a994-bccb04cc2c2d.png" width="216">

### Navigation graph
![navigation_graph](https://user-images.githubusercontent.com/22412695/187080267-1ce6be15-05c1-4c9b-b400-9d60e34b42c6.png)


## Project Structure

    com.example.deviantartviewer    # Root Package
    ├── data                        # For data handling
    |   ├── authorization           # Authorization and authentication
    |   ├── converter               # Model converters
    │   ├── local                   # Local 
    |   │   ├── db                  # Persistence Database. Room database
    |   |   |    ├── dao            # Dao related classes
    |   |   |    └── entity         # Local Database tables
    |   |   └──prefs                # Shared Prefrences
    |   |   └──storage              # Internal storage
    │   ├── remote                  # Remote Data Handlers and Retrofit API for remote end point     
    |   |   └── response            # Response Objects for Api
    │   ├── repository              # Sources of data.
    |   └── model                   # Model classes required for the app
    |
    ├── di                          # Dagger 2 Dependency Injection             
    │   ├── component               # DI Components       
    │   └── module                  # DI Modules
    |
    ├── ui                          # Activity/View layer
    │   ├── base                    # Base Classes for Activity/Fragment/ViewHolder/Adapter/Dialog
    |   ├── browse                  # Browse Fragment & ViewModel
    |   ├── favorites               # Favorites Fragment & ViewModel    
    |   ├── fullimage               # Image Fragment & ViewModel   
    |   │── images                  # ViewHolder & RecyclerView.Adapter for images    
    |   │── login                   # Login Fragment & ViewModel              
    │   ├── main                    # Main Activity & ViewModel   
    │   └── profile                 # Profile Fragment & ViewModel     
    |
    ├── utils                       # Utility Class for Utility Functions
    │   ├── common                  # helper functions
    │   ├── log                     # related to Logging
    |   ├── network                 # related to networking
    |   └── rx                      # related to RxAndroid
    |
    └── DeviantArtApp               # Application Class
    
    
## License
```MIT License

Copyright (c) 2022

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```
