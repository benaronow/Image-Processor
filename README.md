<h1>Image Processor</h1>

 ### [Video Demonstration](https://youtu.be/f3xVIMbfPdg)

<h2>Description</h2>
The image processor functions as a basic photo editor, with functionality including color tone
transformations, horizontal and vertical flipping, blurring and sharpening, dimension alterations,
as well as creating mosaic versions of images. Starter images are provided; however, any image can
be imported to the image processor from your personal directory, and any picture stored within the
program can be exported out. Masking functionality allows for only sections of a given image to be
edited, and also serves to provide edit previews that display an edits changes on a smaller scrollable
window of the full image.
</br>
</br>
The clean and user-friendly GUI was created using Java Swing and is not the only method through which the
image processor is usable. Depending on how the program is initialized, the you may enter commands for
the processor into the terminal, or provide a file containing a script of commands for the program to
execute one after another.
</br>
</br>
Starter Image Citations:

- <a href="https://www.flickr.com/photos/albertofarzas/7007546026/sizes/o/">Koala</a>
- <a href="https://frozenpediathepenguin.fandom.com/wiki/Frozenpedia,_The_Penguin_Wiki">Penguins</a>
- <a href="https://www.hsph.harvard.edu/jellyfish/">Jellyfish</a>

<h2>Using the JAR file</h2>

When using the JAR file, if no command line arguments are entered, the image program GUI will open
to be interacted with. Conversely, if "-text" is entered as a command line argument, the image
program will open in text form in the console. Additionally, you may enter "-file" followed by a
script filename to execute the file in text form in the console. Two provided scripts to run are
"DownscaleCommandScript.txt", which creates two downscaled images of an original image, and
"MaskCommandScript.txt", which creates edited images for every operation usable with a mask, and
their counterparts created with a mask.

<h2>GUI Tutorial</h2>

Click the "Load Image" button and select an image from your file explorer to use, then enter a name for your image and click "Confirm". Alternatively, you can choose a default image already loaded in by opening the dropdown box and selecting one.
Select the corresponding button on the left side of the screen that represents which operation you want to run on the image that is currently selected.
</br>
</br>
Selecting the "Red Greyscale", "Green Greyscale", "BlueGreyscale", "Max-Value Greyscale", "Luma Greyscale", "Intensity Greyscale", "Sepia Tone", "Horizontal Flip", "Vertical Flip", "Blur Filter", and "Sharpen Filter" buttons will prompt you to enter a name for the new image, while the "Brighten" button will ask for a number to brighten the image by, as well as a name.
</br>
</br>
The new image will be displayed. From here, you can continue modifying this image, select another image already loaded in from the dropdown button, load in a new image, or save the image currently being displayed.

<h2>Languages and Utilities Used</h2>

- <b>Java</b> 
- <b>Swing</b>

<h2>Environments Used</h2>

- <b>IntelliJ IDEA</b> 

<h2>Program walk-through:</h2>

Ensure you are using Java SE 11
<p align="center">
Launch the program: <br/>
<img src="https://imgur.com/QQUIePe.png" height="80%" width="80%"/>
<br />
<img src="https://imgur.com/l0JLVNd.png" height="80%" width="80%"/>
<br />
<br />
Choose an image to edit:  <br/>
<img src="https://imgur.com/AWBHmBX.png" height="80%" width="80%"/>
<br />
<br />
Edit an image: <br/>
<img src="https://imgur.com/DW2AcXw.png" height="80%" width="80%"/>
<br />
<img src="https://imgur.com/U7zuIO1.png" height="80%" width="80%"/>
<br />
<br />
Preview an edit:  <br/>
<img src="https://imgur.com/6xvYM5X.png" height="80%" width="80%"/>
<br />
<br />
Choose a mask to edit with:  <br/>
<img src="https://imgur.com/A4xeuW1.png" height="80%" width="80%"/>
<br />
<br />
Edit an image with a mask:  <br/>
<img src="https://imgur.com/Af0CoBP.png" height="80%" width="80%"/>
<br />
<br />
Import an image:  <br/>
<img src="https://imgur.com/hzRUwzq.png" height="80%" width="80%"/>
<br />
<img src="https://imgur.com/bmYybfB.png" height="80%" width="80%"/>
<br />
<br />
Imported images can be edited in the same manner:  <br/>
<img src="https://imgur.com/IXdXbWz.png" height="80%" width="80%"/>
<br />
<br />
Export an image:  <br/>
<img src="https://imgur.com/XIG1Yko.png" height="80%" width="80%"/>
<br />
<br />
Exported images can be viewed from your personal directory:  <br/>
<img src="https://imgur.com/xRpLw4d.png" height="80%" width="80%"/>
</p>

<!--
 ```diff
- text in red
+ text in green
! text in orange
# text in gray
@@ text in purple (and bold)@@
```
--!>
