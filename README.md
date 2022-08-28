# ArtBrowser

I made this **ArtBrowser** application to study current technologies and approaches in Android mobile development. 
The application was made as part of self-education process and has no commercial purposes.
## About the App
The application interacts with the DeviantArt website and allows the user to view the latest images uploaded to the site. 
It allows user to scale images and add/remove them to/from favorites list. 
The application also displays user information and statistics in a separate window.
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
