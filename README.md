<h1>Image Processor</h1>

 ### [Video Demonstration](https://youtu.be/7eJexJVCqJo)

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
The clean and user-friendly GUI was created using Swing and is not the only method through which the
image processor is usable. Depending on how the program is initialized, the you may enter commands for
the processor into the terminal, or provide a file containing a script of commands for the program to
execute one after another.
</br>
</br>
Starter Image Citations:
- https://www.flickr.com/photos/albertofarzas/7007546026/sizes/o/
- https://frozenpediathepenguin.fandom.com/wiki/Frozenpedia,_The_Penguin_Wiki
- https://www.hsph.harvard.edu/jellyfish/
<br />

<h2>Using the JAR file</h2>

When using the JAR file, if no command line arguments are entered, the image program GUI will open
to be interacted with. Conversely, if "-text" is entered as a command line argument, the image
program will open in text form in the console. Additionally, you may enter "-file" followed by a
script filename to execute the file in text form in the console. Two provided scripts to run are
"DownscaleCommandScript.txt", which creates two downscaled images of an original image, and
"MaskCommandScript.txt", which creates edited images for every operation usable with a mask, and
their counterparts created with a mask.

<h2>Languages and Utilities Used</h2>

- <b>Java</b> 
- <b>Swing</b>

<h2>Environments Used</h2>

- <b>IntelliJ IDEA</b> 

<h2>Program walk-through:</h2>

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
