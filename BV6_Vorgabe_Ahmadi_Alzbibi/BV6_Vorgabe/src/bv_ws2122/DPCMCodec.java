// BV Ue6 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-12-10
 		   		     	

package bv_ws2122;


public class DPCMCodec {
	 		   		     	
	public enum PredictionType { 
		A("A (horizontal)"), 
		B("B (vertical)"), 
		C("C (diagonal)"),
		ABC("A+B-C"), 
		AB_MEAN("(A+B)/2"),
		ADAPTIVE("adaptive");
		
		private final String name;       
	    private PredictionType(String s) { name = s; }
	    public String toString() { return this.name; }
	};


	public void processDPCM(RasterImage originalImage, RasterImage errorImage, RasterImage reconstructedImage, double quantizationDelta, PredictionType type) {
		
		// TODO: Encode the originalImage with DPCM using the given prediction type, 
		// visualize the prediction error in errorImage, and
		// decode the prediction error into reconstructedImage.
		
		// Hint: You can implement encoding and decoding with a single iteration over the pixels of the given image.
		
		// Optional: Implement DPCM with quantization. The quantization step size is given in quantizationDelta.

		int pixelA ,pixelB ,pixelC;
		int prediction=0;
		int error;
		int pixelSignal = 0;
		int  errorPixel=0;
		for(int x = 0 ; x < originalImage.width ; x ++){
			for ( int y = 0 ; y < originalImage.height; y++){
				int pos = y* originalImage.width+x;
				int orginalPixel = (originalImage.argb[pos]>>16)&0xff;
				//pixel a
				if (x < 1){
					pixelA = 128;
				} else {
					pixelA = (originalImage.argb[pos - 1]>>16)&0xff;
				}

				//pixel b
				if (y < 1){
					pixelB = 128;
				} else {
					pixelB = (originalImage.argb[pos - originalImage.width]>>16)&0xff;
				}

				//pixel c
				if (x < 1 || y < 1){
					pixelC = 128;
				} else {
					pixelC= (originalImage.argb[pos - originalImage.width - 1]>>16)&0xff;
				}
                //cases
				if (type == PredictionType.A) {
                   prediction = pixelA;
				}
				if (type == PredictionType.B) {
					prediction = pixelB;
				}
				if (type == PredictionType.C) {
					prediction = pixelC;
				}
				if (type == PredictionType.ABC) {
					//	P = A+B-C
					prediction = pixelA + pixelB -pixelC;
				}
				if (type == PredictionType.AB_MEAN) {
					//P = (A+B)/2
					prediction = (pixelA + pixelB) /2;
				}
				if (type == PredictionType.AB_MEAN) {
					//Falls |A-C| < |B-C|, dann P = B, andernfalls P = A.
					if ( pixelA - pixelC < pixelB - pixelC) {
						prediction = pixelB;
					} else{
						prediction = pixelA;
					}
				}



				//BERCHNUNGEN
				//the idea is: predection:
				// e = s - s1

				error = orginalPixel - prediction ;
				if (error + 128 > 255) {
					errorPixel = 255;
				} else if (error +128 < 0) {
					errorPixel = 0;
				} else {
					errorPixel = error +128;
				}

				pixelSignal = prediction + error;

				errorImage.argb[pos]=(0xFF<<24) | (errorPixel<<16) | (errorPixel<<8) | errorPixel;
				reconstructedImage.argb[pos]=(0xFF<<24) | (pixelSignal<<16) | (pixelSignal<<8) | pixelSignal;

			}
		}

	}
	
	public double getMSE(RasterImage originalImage, RasterImage reconstructedImage) {
		
		double mse = Double.NaN;
		
		// TODO: calculate and return the Mean Square Error between the given images
		
		return mse;
	}
	
 		   		     	
}
 		   		     	







