## Space Dodger 🚀

A top down style video game developed using Android Studio and Java where the player's spaceship must dodge oncoming Asteroids. This game makes use of Android Studio's SurfaceView (Canvas feature) aswell as threads to asynconously handle game data. The game's graphics implement the paralax effect, where movement is created in the visual effects by slowing the speeds of planets in the background depending on how far they are from the camera. 

![compressedgifdemo](https://github.com/BurhanNaveed0/SpaceDodger/assets/81490717/d91f54cd-fc52-49f2-ae6a-5d0c4e1c3016)

## Technologies Used 👨‍💻
<details>
  <summary> App 📱</summary>
  <ul>
    <li><a href="https://www.java.com/en/">Java</a></li>
    <li><a href="">XML</a></li>
    <li><a href="https://developer.android.com/studio?gad_source=1&gclid=CjwKCAjw-O6zBhASEiwAOHeGxXeWZgT9muC50iZgfEeWoWRSc1p7O5V8lqIsRCIpYqx4VqIfEuMYvBoCR6AQAvD_BwE&gclsrc=aw.ds">Android Studio</a></li>
  </ul>
</details>

<details>
  <summary> Assets 🎨</summary>
  <ul>
    <li><a href="https://itch.io/">Itch.io</a></li>
  </ul>
</details>

## Project Status ✔
Project complete; Git repo up to date. 

## Project Demo / Code Reviews 📲

<ul>
    <li><a href="https://youtu.be/TEYuYg3XGRE">Game Demo</a></li>
</ul>

</br>

Motion control using onboard rotation sensor 🔄 </br>
![tiltingdemo](https://github.com/BurhanNaveed0/SpaceDodger/assets/81490717/b918f18c-7e24-487f-8e71-96c579fe8791)

## Reflection 📝

### What inspired you to develop this project?
Space Dodger was created for my Mobile Application Development class's game project. As part of the requirements of the game project, all projects had to include Android's SurfaceView feature in order to render images to a canvas and move them in accordance with user input in live time. All apps were also required to use any sensor on board Android devices. With free Space assets available on Itch.io, a free asset hub for game development, I decided to make a classic top down style game reminicent of the old Space Invaders on the Atari 2600.

### What did you set out to build?
With a Space Invaders theme in mind, I decided that I would create a game where the player could, in similar fashion to the old game, move a space ship laterally using the Android phone's built in rotation sensor. The aim of the game would be to dodge as many asteroids in a short period of time, with the pace of the game getting faster with the amount of time the player survives. The rendering of UI elements would be done using Androids Surface view feature while player data and time left would be updated in a separate thread asynchronously.  

### What were some setbacks you faced that made this a learning experience?
The challenge that took the most time was implementing the rotiation sensor to control the spaceship. I tried implementing sensor data from the accelerometer however that backfired as its input was severerly sensitive to movement and was leading to innacrute input. After messing around, I found out that the rotation sensor was the best option as I only had to worry of 2 axes of rotation and the sensor was not nearly as sensitive. The sensor provided accurate data about the phone's movement which translated directly to player movement. Another issue that I had was with the thread timer not stopping the game when it reached 0 seconds. For some reason, the thread timer was going into negative numbers. Because of this, I opted to user a built in timer feature by Android Studio instead of using my own timer. As for issues with gameplay, a big issue was that when the player died and exploded, there was a small chance that the player could respawn as into anotehr asteroid already in its respawn location. To counter this, I had to modify the game logic to stop the spawning of asteroids for a few seconds to allow the player to respawn without dying. 
