## Space Dodger 🚀

A top down style video 

![compressedgifdemo](https://github.com/BurhanNaveed0/SpaceDodger/assets/81490717/d91f54cd-fc52-49f2-ae6a-5d0c4e1c3016)

## Technologies Used 👨‍💻
<details>
  <summary> App 📱</summary>
  <ul>
    <li><a href="https://www.java.com/en/">Java</a></li>
    <li><a href="">XML</a></li>
    <li><a href="https://developer.android.com/studio?gad_source=1&gclid=CjwKCAjw-O6zBhASEiwAOHeGxXeWZgT9muC50iZgfEeWoWRSc1p7O5V8lqIsRCIpYqx4VqIfEuMYvBoCR6AQAvD_BwE&gclsrc=aw.ds">Android Studio</a></li>
  </ul>

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

Motion control using onboard rotation sensor 🔄
![tiltingdemo](https://github.com/BurhanNaveed0/SpaceDodger/assets/81490717/b918f18c-7e24-487f-8e71-96c579fe8791)

## Reflection 📝

### What inspired you to develop this project?
The GPU Radar app was created for my Mobile Application Development class's final project. As part of the requirements of the final project, all projects had to include a database element and data exchange with an API. Being a Gamer and PC building enthusiest, I saw a chance to work on a project that could assist other like me in finding cheaper PC parts online. With the inflation of GPU prices due to scalpers being at an all time high at the time of development, I decided to put a focus specifically on GPUs. 

### What did you set out to build?
With that in mind, I set out to build an App that would allow users to be able to see the current lowest price listings for Nvidea's RTX 3 series cards across the Amazon Marketplace with updates to data collection every 5-10 minutes. Originally, I planned on just creating a standalone Mobile App however, I realized very soon that this would lead to performance issues as having a mobile app retrieve and filter through larges quantity of Amazon Marketplace listings every few minutes would cause much slow down. Instead, I decided to create a separate backend python automation script that would run on a different server and cache the data to a firebase database. This way, data retreival from the database would be instant on the mobile app with the more intesnive data retrieval being done separately. 

### What were some setbacks you faced that made this a learning experience?
In the end, the project turned out to be a fun yet challenging learning experience. For the first time ever, I integrated a page view with tabs aswell as fragments in my app. Integrating these new features required a good deal of research and experimentation to get working without crashing. Alongside this, getting the app to retrieve data from firebase and update the fragaments with the data was frsutrationg to say the least. Any time a fragment was updated, it would crash the app. For the backend side of things, it was a nightmare optimizing the retrieval of marketplace listing data. Unforunately, when grabbing the lowest price market listings for GPU's from the Rainforest API service, many unrelated items would appear as sellers would use GPU names in the product title to get there product to show up on people's feeds. Because of this, I was forced to put muliple search parameters into my API calls (minimum price, product categoryu, etc.) in order to make the search more relevant. Even after filtering out lots of junk data from the API calls, I was left with many results showing the wrong GPU's, forcing me to even further filter through data. These actions had to be optimized as much as possible as uploading data to the firebase database also took considerable time.
