// BV Ue4 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-23
 		   		     	

package bv_ws2122;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ToneCurve {
 		   		     	
	private static final int grayLevels = 256;
	
    private GraphicsContext gc;
    
    private int[] grayTable = new int[grayLevels];
 		   		     	
	public int[] getGrayTable() {
		return grayTable;
	}

	public ToneCurve(GraphicsContext gc) {
		this.gc = gc;
	}
	
	public void updateTable(int minInput, int maxInput, int minOutput, int maxOutput) {
		double a=(double)(maxOutput-minOutput)/(maxInput-minInput);
		
for (int i=0;i<=255;i++) {
	if (i<=minInput)
		grayTable[i]=minOutput;
	else if(i>=maxInput)
		grayTable[i]=maxOutput;
	else 
	grayTable[i]=(int)(a*(i-minInput))+minOutput;
	
}
		// TODO: Fill the grayTable[] array to map gay input values to gray output values.
		// It will be used as follows: grayOut = grayTable[grayIn].
		//
		// Use minInput, maxInput, minOutput, and maxOutput settings.

	}
	
	public void applyTo(RasterImage image) {

		for (int y=0; y<image.height; y++) {
			for (int x=0; x<image.width; x++) {
				int pos = y*image.width + x;
			
				int r = (image.argb[pos] >> 16) & 0xff;
			
				int rNew=grayTable[r];
				int gNew=grayTable[r];
				int bNew=grayTable[r];
				
				
				image.argb[pos] = (0xFF<<24) | (rNew<<16) | (gNew<<8) | bNew;
			}
		}
		// TODO: apply the gray value mapping to the given image

	}
	
	public void draw(Color lineColor) {
		if(gc == null) return;
		gc.clearRect(0, 0, grayLevels, grayLevels);
		gc.setStroke(lineColor);
		gc.setLineWidth(3);
		
		// TODO: draw the tone curve into the gc graphic context
		// Note that we need to add 0.5 to all coordinates to align points to pixel centers 
		
		double shift = 0.5;

		// Remark: This is some dummy code to give you an idea for graphics drawing using paths		
		gc.beginPath();
		gc.moveTo(0 + shift, 255-grayTable[0] + shift);
		for (int i=1;i<=255;i++) {
			
			gc.lineTo(i + shift, 255-grayTable[i] + shift);
			gc.stroke();
		}
	//	gc.moveTo(64 + shift, 128 + shift);
		//gc.lineTo(128 + shift, 192 + shift);
		//gc.lineTo(192 + shift, 64 + shift);
		
	}

 		   		     	
}
 		   		     	




