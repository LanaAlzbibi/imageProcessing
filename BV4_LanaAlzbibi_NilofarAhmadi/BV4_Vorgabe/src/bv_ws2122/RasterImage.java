// BV Ue4 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-23

package bv_ws2122;

import java.io.File;
import java.util.Arrays;

import bv_ws2122.ImageAnalysisAppController.Visualization;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class RasterImage {

	private static final int gray = 0xffa0a0a0;

	public int[] argb; // pixels represented as ARGB values in scanline order
	public int width; // image width in pixels
	public int height; // image height in pixels

	public RasterImage(int width, int height) {
		// creates an empty RasterImage of given size
		this(width, height, gray);
	}

	public RasterImage(int width, int height, int argbColor) {
		// creates an empty RasterImage of given size and color
		this.width = width;
		this.height = height;
		argb = new int[width * height];
		Arrays.fill(argb, argbColor);
	}

	public RasterImage(RasterImage image) {
		// copy constructor
		this.width = image.width;
		this.height = image.height;
		argb = image.argb.clone();
	}

	public RasterImage(File file) {
		// creates a RasterImage by reading the given file
		Image image = null;
		if (file != null && file.exists()) {
			image = new Image(file.toURI().toString());
		}
		if (image != null && image.getPixelReader() != null) {
			width = (int) image.getWidth();
			height = (int) image.getHeight();
			argb = new int[width * height];
			image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
		} else {
			// file reading failed: create an empty RasterImage
			this.width = 256;
			this.height = 256;
			argb = new int[width * height];
			Arrays.fill(argb, gray);
		}
	}

	public RasterImage(ImageView imageView) {
		// creates a RasterImage from that what is shown in the given ImageView
		Image image = imageView.getImage();
		width = (int) image.getWidth();
		height = (int) image.getHeight();
		argb = new int[width * height];
		image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
	}

	public Image getImage() {
		// returns a JavaFX image
		if (argb != null) {
			WritableImage wr = new WritableImage(width, height);
			PixelWriter pw = wr.getPixelWriter();
			pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
			return wr;
		}
		return null;
	}

	public void setToView(ImageView imageView) {
		// sets the current argb pixels to be shown in the given ImageView
		Image image = getImage();
		if (image != null) {
			imageView.setImage(image);
		}
	}

	// image point operations to be added here

	public void convertToGray() {

		for (int i = 0; i < argb.length; i++) {
			// TODO: convert the image to grayscale
			int r = (argb[i] >> 16) & 0xff;
			int g = (argb[i] >> 8) & 0xff;
			int b = argb[i] & 0xff;
			int grau = (r + g + b) / 3;

			argb[i] = (0xFF << 24) | (grau << 16) | (grau << 8) | grau;
		}

	}

	public RasterImage getOverlayImage(int regionSize, Visualization visualization, double threshold) {

		double ent;
		Histogram h=new Histogram();
		//System.out.println("ent green " + ent);
		// Will be used in Exercise 5. Nothing to do in Exercise 4.
		RasterImage greenImage = new RasterImage(width, height);
		int size = regionSize * regionSize;
		int yd = 0;
		int xd = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int pos = i * width + j;
				greenImage.argb[pos] = 0x8000FF00;
			}
		}
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pos = y * width + x;
				int xr=x-(regionSize/2);
				int yr=y-(regionSize/2);
				if (xr < 0) {
					xr=0;

				}
				if (xr >= width) {
					xr=width-1;

				}

				if (yr < 0) {
					yr=0;

				}
				if (yr >= height) {
					yr=height-1;

				}
				h.setImageRegion(this,xr , yr, regionSize, regionSize);
				ent=h.getEntropy();

				for (int yk = 0; yk < regionSize; yk++) {
					for (int xk = 0; xk < regionSize; xk++) {

						int px = x - (regionSize / 2) + xk;
						int py = y - (regionSize / 2) + yk;
						if (px < 0) {
							continue;

						}
						if (px >= width) {
							continue;

						}

						if (py < 0) {
							continue;

						}
						if (py >= height) {
							continue;

						}
						int posd = py * width + px;

						switch (visualization) {
							case ENTROPY: {

								if (ent>threshold)
									greenImage.argb[posd] = 0x0000FF00;
								else
									greenImage.argb[posd] = 0x8000FF00;

							}
							case VARIANCE:

						}
						greenImage.argb[posd] = 0x8000FF00;

					}
				}
			}
		}

		// Create an overlay image that contains half transparent green pixels where a
		// statistical property locally exceeds the given threshold.
		// Use a sliding window of size regionSize x regionSize.
		// Use "switch(visualization)" to determine, what statistical property should be
		// used

		return greenImage;
	}

}
