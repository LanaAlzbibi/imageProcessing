// BV Ue6 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-12-10
 		   		     	

package bv_ws2122;

import java.io.File;
import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class RasterImage {
	 		   		     	
	private static final int gray  = 0xffa0a0a0;
	    
	public int[] argb;	// pixels represented as ARGB values in scanline order
	public int width;	// image width in pixels
	public int height;	// image height in pixels
	
	public RasterImage(int width, int height) {
		// creates an empty RasterImage of given size
		this.width = width;
		this.height = height;
		argb = new int[width * height];
		Arrays.fill(argb, gray);
	}
	
	public RasterImage(RasterImage src) {
		// copy constructor
		this.width = src.width;
		this.height = src.height;
		argb = src.argb.clone();
	}
	
	public RasterImage(File file) {
		// creates an RasterImage by reading the given file
		Image image = null;
		if(file != null && file.exists()) {
			image = new Image(file.toURI().toString());
		}
		if(image != null && image.getPixelReader() != null) {
			width = (int)image.getWidth();
			height = (int)image.getHeight();
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
		width = (int)image.getWidth();
		height = (int)image.getHeight();
		argb = new int[width * height];
		image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
	}
	
	public void setToView(ImageView imageView) {
		// sets the current argb pixels to be shown in the given ImageView
		if(argb != null) {
			WritableImage wr = new WritableImage(width, height);
			PixelWriter pw = wr.getPixelWriter();
			pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
			imageView.setImage(wr);
		}
	}
		
	// image point operations to be added here
	
	public void convertToGray() {
		
		// TODO: convert the image to grayscale
		for (int i = 0; i < argb.length; i++) {
			// TODO: convert the image to grayscale
			int r = (argb[i] >> 16) & 0xff;
			int g = (argb[i] >> 8) & 0xff;
			int b = argb[i] & 0xff;
			int grau = (r + g + b) / 3;

			argb[i] = (0xFF << 24) | (grau << 16) | (grau << 8) | grau;
		}
		
	}
	
	public double getEntropy() {
		int[] histogram = new int[256];
		double entropy=0;

		Arrays.fill(histogram, 0);
		int size = height * width;
		for (int y = 0; y <height; y++) {
			for (int x = 0; x < width; x++) {
				int pos = y * width + x;

				int r = (argb[pos] >> 16) & 0xff;

				histogram[r] += 1;

			}
		}

			// TODO: calculate and return the entropy of the image

        int temp[] = histogram;
		double[] p = new double[temp.length + 2];
		double sum = 0;
		for (int x = 0; x < temp.length; x++) {
			p[x] = (double) temp[x] / size;
			if (p[x] != 0)
				sum += p[x] * (Math.log(p[x]) / Math.log(2));

		}
		sum = -sum;
		entropy = sum;

		return entropy;
	}


} 		   		     	




 		   		     	


