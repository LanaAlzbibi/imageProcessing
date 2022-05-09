// BV Ue4 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-23
 		   		     	

package bv_ws2122;

import java.util.Arrays;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Histogram {
 		   		     	
	private static final int grayLevels = 256;
	
    private GraphicsContext gc;
    private int maxHeight;
    
    private int[] histogram = new int[grayLevels];
 		   		     	
    public Histogram() {
	}
    
	public Histogram(GraphicsContext gc, int maxHeight) {
		this.gc = gc;
		this.maxHeight = maxHeight;
	}
	
	public int[] getValues() {
		return histogram;
	}

	public void setImageRegion(RasterImage image, int regionStartX, int regionStartY, int regionWidth, int regionHeight) {
		Arrays.fill(histogram,0);
		for (int y=regionStartY; y<regionStartY+regionHeight; y++) {
			for (int x=regionStartX; x<regionStartX+regionWidth; x++) {
				int pos = y*image.width + x;
			
				int r = (image.argb[pos] >> 16) & 0xff;
			
			
				histogram[r]+=1;
				
			
			}
		}
		// TODO: calculate histogram[] out of the gray values found the given image region

	}
	
	public Integer getMinimum() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		     	
	public Integer getMaximum() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		     	
	public Double getMean() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		     	
	public Integer getMedian() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		     	
	public Double getVariance() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		     	
	public Double getEntropy() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		     	
	public void draw(Color lineColor) {
		if(gc == null) return;
		  int maxValue = histogram[0]; 
		for(int i=1;i < 256;i++){ 
		      if(histogram[i] > maxValue){ 
		         maxValue = histogram[i]; 
		      } 
		    } 
		
		double shift = 0.5;
		double greyHäufigkeit=0;
		gc.clearRect(0, 0, grayLevels, maxHeight);
		gc.setStroke(lineColor);
		gc.setLineWidth(1);

		   System.out.println("Maximum Value is: "+maxValue);
		   for(int j=0;j<256;j++) {
			   greyHäufigkeit= (double)((histogram[j]/(float)maxValue)*maxHeight);
			   gc.strokeLine(j, maxHeight-0, j+ shift, maxHeight-greyHäufigkeit + shift);
			   
		   }
 			     	
		// TODO: draw histogram[] into the gc graphic context
		// Note that we need to add 0.5 to all coordinates to align points to pixel centers 
		
	
		
		// Remark: This is some dummy code to give you an idea for line drawing		
		//gc.strokeLine(shift, shift, grayLevels-1 + shift, maxHeight-1 + shift);
		//gc.strokeLine(grayLevels-1 + shift, shift, shift, maxHeight-1 + shift);
		
	}
 		   		     	
}
 		   		     	






