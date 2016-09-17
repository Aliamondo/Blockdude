from PIL import Image

def changeSize(imgName, i):
    im = Image.open(imgName)
    im2 = im.resize((128, 128), Image.ANTIALIAS)
    pixel = (255, 0, 0, 255)
    
    pixels = im.load()
    pixels2 = im2.load()
    width, height = im.size#it's 96x96 atm, we need 128x128
    
    for y in range(height):
        for x in range(width):
            pixels2[x, y] = pixels[x, y]
    for y in range(height, 128):
        for x in range(128):
            pixels2[x, y] = pixel
    for y in range(height):
        for x in range(width, 128):
            pixels2[x, y] = pixel

    im2.save("block0"+str(i)+".png")

imgName = "obj_stoneblock00"
for i in range(1, 10):
    changeSize(imgName + str(i) + ".png", i)
    #if (i==10): changeSize(imgName + str(i) + ".png", i)
