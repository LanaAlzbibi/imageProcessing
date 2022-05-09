// BV Ue3 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-22
 		   		     	

package bv_ws2122;

public class MorphologicFilter {
 		   		     	
	// filter implementations go here:
	
	public void copy(RasterImage src, RasterImage dst) {
		// TODO: just copy the image
		for(int i=0; i<src.height;i++) {
			for(int j=0;j<src.width;j++) {
				int pos = i*src.width + j;			
			    dst.argb[pos]=src.argb[pos];
			}
		}
	}
	
	public void dilation(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// kernel's first dimension: y (row), second dimension: x (column)
		// TODO: dilate the image using the given kernel
		int yd=0;
		int xd=0;
		for(int i=0; i<src.height;i++) {
			for(int j=0;j<src.width;j++) {
				int pos = i*src.width + j;			
			    dst.argb[pos]=0xFFFFFFFF;
			}
		}
		for(int y=0;y<src.height;y++) {
			for(int x=0;x<src.width;x++) {
				int pos = y*src.width + x;
				if(src.argb[pos]==0xFFFFFFFF)
				continue;
				for(int yk=0;yk<kernel.length;yk++) {
					for(int xk=0;xk<kernel.length;xk++) {
					if(	kernel[yk][xk]==true) {
				
					int px=x-(kernel.length/2)+xk;
					int py=y-(kernel.length/2)+yk;
					if(px<0) {
						continue;
					
					}
					if(px>=src.width) {
						continue;
					
					}
					
					if(py<0) {
						continue;
					
					}
					if(py>=src.height) {
						continue;
						
					}
					int posd= py*src.width + px;
					dst.argb[posd]=0xFF000000;
					}
						
					}
					}
					}
			}
		}
	
 		   		     	
	public void erosion(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// This is already implemented. Nothing to do.
		// It will function once you implemented dilation and RasterImage invert()
		src.invert();
		dilation(src, dst, kernel);
		dst.invert();
		src.invert();
	}
	
	public void opening(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// TODO: implement opening by using dilation() and erosion()
			RasterImage dstt=new RasterImage(src.width,src.height);
			erosion(src,dstt,kernel);
			dilation(dstt,dst,kernel);
	
	}
	
	public void closing(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// TODO: implement closing by using dilation() and erosion()
	
		RasterImage dstt=new RasterImage(src.width,src.height);
		dilation(src,dstt,kernel);
		erosion(dstt,dst,kernel);
	
	}
	
	
 		   		     		
	

}
 		   		     	




