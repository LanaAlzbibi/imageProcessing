// BV Ue1 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-14


package bv_ws2122;

public class GaussFilter {

	private double[][] kernel;

	public double[][] getKernel() {
		return kernel;
	}

	public void apply(RasterImage src, RasterImage dst, int kernelSize, double sigma) {
		int srcSize=src.width*src.height;

		kernel=new double [kernelSize][kernelSize];
		double[][] kernelSrc=new double [kernelSize][kernelSize];
		int hotspot=kernelSize/2;
		float sum=0;
		for (int k=0;k<kernelSize;k++) {
			for(int l=0;l<kernelSize;l++) {

				int ln=(int) Math.pow(l-hotspot, 2);
				int kn=(int) Math.pow(k-hotspot, 2);


				float h= (float) ((ln+kn)/(2*Math.pow(sigma,2)));
				h=-h;
				float g= (float) Math.exp(h);

				sum+=g;
				kernel[k][l]=g;
			}

		}
		for (int k=0;k<kernelSize;k++) {
			//normalize
			for(int l=0;l<kernelSize;l++) {
				kernel[k][l]=kernel[k][l]/sum;
			}
		}

//		int[] argb= new int[src.width * src.height];
//		int  sumr=0;
//		int  sumb=0;
//		int  sumg=0;
//
//		for(int x=0;x<src.width;x++) {
//			for(int y=0;y<src.height;x++) {
//			//int r = (argb[pix] >> 16) & 0xff;
//			//int g = (argb[pix] >>  8) & 0xff;
//			//int b =  argb [pix]       & 0xff;
//
//		for (int k=0;k<kernelSize;k++) {
//			for(int l=0;l<kernelSize;l++) {
//
//				sumr+=kernel[k][l]*r;
//				sumb+=kernel[k][l]*b;
//				sumg+=kernel[k][l]*g;
//			}
//			}
//
		double [][]pos=new double [src.width][src.height];

		for (int x=0;x<src.width-1;x++) {
			for(int y=0;y<src.height-1;y++) {
				double sumF=0;
				//pos[x][y]=src.argb[y*src.width+x];
				for (int k=0;k<kernelSize;k++) {
					for(int l=0;l<kernelSize;l++) {

						int px=x-(kernelSize/2)+k;
						int py=y-(kernelSize/2)+l;

						if(px<0) {
							px=0;
							if(py<0)
								py=0;
							if (py>=src.height)
								py=src.height-1;
						}
						if(px>=src.width) {
							px=src.width-1;
							if(py<0)
								py=0;
							if(py>src.height)
								py=src.height-1;
						}

						if(py<0) {
							py=0;
							if(px<0)
								px=0;
							if (px>=src.width)
								px=src.width-1;
						}
						if(py>=src.height) {
							py=src.height-1;
							if(px<0)
								px=0;
							if(px>src.width)
								px=src.width-1;
						}


						int pix=py*src.width+px;

						int b = src.argb[pix]& 0xff;
						//int grau=(r+g+b)/3;



						sumF+=b *kernel[k][l];


					}
				}
				int gray=(int)sumF;
				dst.argb[y*src.width+x]=(0xFF<<24) | (gray<<16) | (gray<<8) | gray;
			}
		}
		// TODO: Implement a Gauss filter of size "kernelSize" x "kernelSize" with given "sigma"

		// Step 1: Allocate appropriate memory for the field variable "kernel" representing a 2D array.

		// Step 2: Fill in appropriate values into the "kernel" array.
		// Hint:
		// Use g(d) = e^(- d^2 / (2 * sigma^2)), where d is the distance of a coefficient's position to the hot spot.
		// Note that in this comment e^ denotes the exponential function and ^2 the square. In Java ^ is a different operator.

		// Step 3: Normalize the "kernel" such that the sum of all its values is one.

		// Step 4: Apply the filter given by "kernel" to the source image "src". The result goes to image "dst".
		// Use "constant continuation" for boundary processing.


	}
}
