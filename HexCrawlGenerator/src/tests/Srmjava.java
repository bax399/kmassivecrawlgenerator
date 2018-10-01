package tests;
/*
 Statistical Region Merging in Java: SRMj
  
 by Frank Nielsen and Richard Nock 
 
 ***Caution: This is the simplest implementation***
 Java Applet, May 2006.
 
 (Version 1.0 : 6 May 2006) 
 
 For more, check:
 Richard Nock, Frank Nielsen: Statistical Region Merging. IEEE Trans. Pattern Anal. Mach. Intell. 26(11): 1452-1458 (2004)
 
 Find more in the "Visual Computing: Geometry, Graphics, and Vision" book:
 http://www.csl.sony.co.jp/person/nielsen/visualcomputing/
 */
 
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.StringTokenizer;
import java.net.*;

public class Srmjava extends Applet {
	
		// Input image
        Image img;
        PixelGrabber pg;
        
        // Ouput segmented image
        Image imgseg;
        
        // Input image (URL or not)  
        String filenameimg;   
        String filenameimgurl;
         
        URL imgurl;
        
        // Applet dimension
        int appletw, appleth;
        Font helveticafont1;
        Font helveticafont2;
        
    	//
        // For SRM
        //
         int[] raster; 
         int[] rastero;
         int w,h,n;
         double aspectratio;
      
         double Q;
         UnionFind UF;
         double g; // number of levels in a color channel
		 double logdelta;

		// Auxilliary buffers for union-find operations
		int []N;
		double []Ravg;
		double [] Gavg;
		double [] Bavg;
		int [] C; // the class number

		// number of pixels that define a "small region" to be collapsed
		int smallregion;
		int borderthickness;

        //
        // GUI
        //
        TextArea messageArea;
        Label title;
        InputPanel inputPanel;
        ImageCanvas imageCanvas;

        public void init() {
        System.out.println("Welcome to SRMj (Frank Nielsen - May 2006) ! Initializing the applet.");
        
        Dimension  dim= this.getSize();
        appletw=dim.width;
        appleth=dim.height;
        
        helveticafont1=new Font("Helvetica", Font.BOLD, 18);
        helveticafont2=new Font("Helvetica", Font.ITALIC, 40);
        	
        setLayout(new BorderLayout());

		inputPanel = new InputPanel(this);
		inputPanel.setSize(appletw, 200);
		add("North", inputPanel);
			
		imageCanvas = new ImageCanvas(this);
		imageCanvas.setSize(appletw, appleth-260);
		add("Center", imageCanvas);
	
		messageArea = new TextArea("", 3, 1);
		messageArea.setEditable(false);	
		messageArea.setBackground(Color.white);
		add("South", messageArea);
	
	    filenameimg=getParameter("img");
	    filenameimgurl=getParameter("imgurl");
	  
	    System.out.println("IMG TAG="+filenameimg+"\nIMGURL TAG="+filenameimgurl);
        
       if (filenameimgurl!=null)
       {
	System.out.println("Opening image at URL:"+filenameimgurl);
	
	try{
        imgurl = new java.net.URL(filenameimgurl);
        }
        catch(Exception e){
        	System.out.println("URL Error...");
            e.printStackTrace();
        }
	img = getImage(imgurl);
	}
	else
	{
		// Assume image is in the same directory
        img = getImage(getDocumentBase(),filenameimg);
     }
        
        Q=32;
        g=256.0; 
		borderthickness=0; // border thickness of regions

        //
        // Require a valid image for proceeding
        //
        if (img!=null)
        {
         pg = new PixelGrabber(img, 0 , 0 , -1 , -1 , true);
        	try { pg.grabPixels(); }
		catch (InterruptedException e) { }
		
		 raster= (int[])pg.getPixels();
        
        w=pg.getWidth();
        h=pg.getHeight();
       aspectratio=(double)h/(double)w;
        n=w*h;
        logdelta = 2.0*Math.log(6.0*n);
        // small regions are less than 0.1% of image pixels
		smallregion=(int)(0.001*n); 
       
       // One round of segmentation
        OneRound();
        messageArea.setText("R. Nock, F. Nielsen: Statistical Region Merging. IEEE Trans. Pattern Anal. Mach. Intell. 26(11): 1452-1458 (2004)\nF. Nielsen: Visual Computing: Geometry, Graphics, and Vision. Charles River Media: ISBN: 1584504277  (2005)\nAdjust parameter Q with the scrollbar and press the `Segment' button to segment again.");
        }
        else
        {
  messageArea.setText("R. Nock, F. Nielsen: Statistical Region Merging. IEEE Trans. Pattern Anal. Mach. Intell. 26(11): 1452-1458 (2004)\nF. Nielsen: Visual Computing: Geometry, Graphics, and Vision. Charles River Media: ISBN: 1584504277  (2005)\n!!! ERROR: COULD NOT READ IMAGE LOCATION FROM APPLET TAGS !!!");	
        	}
        	
        	} // End of applet initialization
        
        
void OneRound()
{
		UF=new UnionFind(n);
        Ravg=new double[n];
        Gavg=new double[n];
        Bavg=new double[n];
        N=new int[n];
        C=new int[n];
        rastero=new int[n];
       
		InitializeSegmentation();
		FullSegmentation();

    	ImageProducer ip = new MemoryImageSource(
			pg.getWidth() , pg.getHeight() , rastero , 0 , pg.getWidth());
		this.imgseg = createImage(ip);
		}
		
		        
void InitializeSegmentation()
{
//
// Initialization
// 
int x,y,red,green,blue,index;
        for (y = 0; y < h; y++) {
            for (x = 0; x < w ; x++) {
            	index=y*w+x;
            	
                red = (raster[y * w+ x] & 0xFF) ;   
                green = ((raster[y *w+ x] & 0xFF00) >> 8);   
                blue = ((raster[y *w + x] & 0xFF0000) >> 16);
            
            Ravg[index]=red;
            Gavg[index]=green;
            Bavg[index]=blue;
            N[index]=1;
            C[index]=index;
            }
        }	
}       
 
void FullSegmentation()
{
Segmentation();
MergeSmallRegion();
OutputSegmentation();
DrawBorder();
}       

double min(double a, double b)
{
if (a<b) return a; else return b;	
}

double max(double a, double b)
{
if (a>b) return a; else return b;	
}

double max3(double a,double b,double c)
{
return max(a,max(b,c));
}	

	        
boolean MergePredicate(int reg1, int reg2)
{
double dR, dG, dB;
double logreg1, logreg2;
double dev1, dev2, dev;

dR=(Ravg[reg1]-Ravg[reg2]); 
dR*=dR;

dG=(Gavg[reg1]-Gavg[reg2]); 
dG*=dG;

dB=(Bavg[reg1]-Bavg[reg2]); 
dB*=dB;

logreg1 = min(g,N[reg1])*Math.log(1.0+N[reg1]);
logreg2 = min(g,N[reg2])*Math.log(1.0+N[reg2]);

dev1=((g*g)/(2.0*Q*N[reg1]))*(logreg1 + logdelta);
dev2=((g*g)/(2.0*Q*N[reg2]))*(logreg2 + logdelta);

dev=dev1+dev2;

return ( (dR<dev) && (dG<dev) && (dB<dev) );
}

Rmpair[] BucketSort(Rmpair []a, int n)
{
int i;
int[] nbe;
int  [] cnbe;
Rmpair []b;

nbe=new int[256];
cnbe=new int[256];

b=new Rmpair[n];

for(i=0;i<256;i++) nbe[i]=0;
// class all elements according to their family
for(i=0;i<n;i++) nbe[a[i].diff]++;
// cumulative histogram
cnbe[0]=0;
for(i=1;i<256;i++)
 cnbe[i]=cnbe[i-1]+nbe[i-1]; // index of first element of category i

// allocation
for(i=0;i<n;i++) 
{b[cnbe[a[i].diff]++]=a[i];}

return b;
}

//
// Merge two regions
//
void MergeRegions(int C1, int C2)
{
int reg,nreg;
double ravg,gavg,bavg;

			reg=UF.UnionRoot(C1,C2);
			
			nreg=N[C1]+N[C2]; 
			ravg=(N[C1]*Ravg[C1]+N[C2]*Ravg[C2])/nreg;
			gavg=(N[C1]*Gavg[C1]+N[C2]*Gavg[C2])/nreg;
			bavg=(N[C1]*Bavg[C1]+N[C2]*Bavg[C2])/nreg;
			
			N[reg]=nreg;
			
			Ravg[reg]=ravg;
			Gavg[reg]=gavg;
			Bavg[reg]=bavg;
}

//
// Main segmentation procedure here
//        
void Segmentation()
{
int i,j,index;
int reg1,reg2;
Rmpair [] order;
int npair;
int cpair=0;
int C1,C2;
int r1,g1,b1; 
int r2,g2,b2;

// Consider C4-connectivity here
npair=2*(w-1)*(h-1)+(h-1)+(w-1);
order=new Rmpair[npair];
	
System.out.println("Building the initial image RAG ("+npair+" edges)");
	
for(i=0;i<h-1;i++)
{
	for(j=0;j<w-1;j++)
	{
	index=i*w+j;

	// C4  left
	order[cpair]=new Rmpair();
	order[cpair].r1=index;
	order[cpair].r2=index+1;
	
	
	r1=raster[index] & 0xFF;
	g1= ((raster[index] & 0xFF00) >> 8) ;
	b1=((raster[index] & 0xFF0000) >> 16);
	
	r2=raster[index+1] & 0xFF;
	g2= ((raster[index+1] & 0xFF00) >> 8) ;
	b2=((raster[index+1] & 0xFF0000) >> 16);
	
	order[cpair].diff=(int)max3(Math.abs(r2-r1),Math.abs(g2-g1),Math.abs(b2-b1));
	cpair++;
	

	// C4 below
    order[cpair]=new Rmpair();
	order[cpair].r1=index;
	order[cpair].r2=index+w;
	
	r2=raster[index+w] & 0xFF;
	g2= ((raster[index+w] & 0xFF00) >> 8) ;
	b2=((raster[index+w] & 0xFF0000) >> 16);
		
    order[cpair].diff=(int)max3(Math.abs(r2-r1),Math.abs(g2-g1),Math.abs(b2-b1));
	cpair++;
	}
}

//
// The two border lines
//
for(i=0;i<h-1;i++)
{
index=i*w+w-1;
order[cpair]=new Rmpair();
order[cpair].r1=index;
	order[cpair].r2=index+w;
	
	r1=raster[index] & 0xFF;
	g1= ((raster[index] & 0xFF00) >> 8) ;
	b1=((raster[index] & 0xFF0000) >> 16);
	r2=raster[index+w] & 0xFF;
	g2= ((raster[index+w] & 0xFF00) >> 8) ;
	b2=((raster[index+w] & 0xFF0000) >> 16);
order[cpair].diff=(int)max3(Math.abs(r2-r1),Math.abs(g2-g1),Math.abs(b2-b1));
	cpair++;
}

for(j=0;j<w-1;j++)
	{
	index=(h-1)*w+j;

order[cpair]=new Rmpair();
	order[cpair].r1=index;
	order[cpair].r2=index+1;
	
	r1=raster[index] & 0xFF;
	g1= ((raster[index] & 0xFF00) >> 8) ;
	b1=((raster[index] & 0xFF0000) >> 16);
	r2=raster[index+1] & 0xFF;
	g2= ((raster[index+1] & 0xFF00) >> 8) ;
	b2=((raster[index+1] & 0xFF0000) >> 16);	
    order[cpair].diff=(int)max3(Math.abs(r2-r1),Math.abs(g2-g1),Math.abs(b2-b1));
	
	cpair++;
	}

System.out.println("Sorting all "+cpair+" edges using BucketSort");

//
// Sort the edges according to the maximum color channel difference
//
order=BucketSort(order,npair);

// Main algorithm is here!!!
System.out.println("Testing the merging predicate in a single loop");

for(i=0;i<npair;i++)
{
	reg1=order[i].r1;
	 C1=UF.Find(reg1);
	reg2=order[i].r2;
	 C2=UF.Find(reg2);
	if ((C1!=C2)&&(MergePredicate(C1,C2))) MergeRegions(C1,C2);			
}

}

//
// Output the segmentation: Average color for each region
//
void OutputSegmentation()
{
int i,j, index, indexb;
int r,g,b,a,rgba;

index=0;

for(i=0;i<h;i++) // for each row
	for(j=0;j<w;j++) // for each column
	{
	index=i*w+j;
	indexb=UF.Find(index); // Get the root index 
	
	//
	// average color choice in this demo
	//
	r  =(int)Ravg[indexb];
	g = (int)Gavg[indexb];
    b =(int)Bavg[indexb];
    
    rgba=(0xff000000 | b << 16 | g << 8 | r);
    
    rastero[index]=rgba;
	}
}

//
// Merge small regions
//
void MergeSmallRegion()
{
int i,j, C1,C2, index;

index=0;

for(i=0;i<h;i++) // for each row
	for(j=1;j<w;j++) // for each column
	{ 
	index=i*w+j;
	C1=UF.Find(index);
	C2=UF.Find(index-1);
	if (C2!=C1) {if ((N[C2]<smallregion)||(N[C1]<smallregion)) MergeRegions(C1,C2);}	
}
}

// Draw white borders delimiting perceptual regions
void DrawBorder()
{
int i, j, k, l, C1,C2, reg,index;

for(i=1;i<h;i++) // for each row
	for(j=1;j<w;j++) // for each column
	{ 
	index=i*w+j;

	C1=UF.Find(index);
	C2=UF.Find(index-1-w);
	
	if (C2!=C1)
	{
	for(k=-borderthickness;k<=borderthickness;k++)
		for(l=-borderthickness;l<=borderthickness;l++)
		{
		index=(i+k)*w+(j+l);
		if ((index>=0)&&(index<w*h)) {
			
		rastero[index]=	(0xff000000 | 255 << 16 | 255 << 8 | 255);
		}
		}
	}
	}
}

        public void paint(Graphics g) {
        g.setColor(Color.white);
        imageCanvas.repaint();
        }
        
        
    public boolean loadImage(String file, URL url) {
    	
	Image grab = getImage(url, file);

	imageCanvas.repaint();
	messageArea.setText("Image file: " + file + " loaded!");

	return true;
    } 
   
}

//
// The applet control panel
//
class InputPanel extends Panel implements ActionListener, ItemListener, AdjustmentListener {
    
     
    Button applyButton;
    Label title;
    Scrollbar slider;	// for parameter Q
    
    Srmjava applet;

    InputPanel(Srmjava applet) {
	
	this.applet = applet;
	this.setBackground(Color.white);
	
	setLayout(new BorderLayout());

		title = new Label("SRMj - Statistical Region Merging in Java by F. Nielsen and R. Nock", Label.CENTER);

		title.setFont(applet.helveticafont1);
		title.setBackground(Color.white);
		add("North", title);
	
		slider = new Scrollbar(Scrollbar.HORIZONTAL,32 ,10, 1, 1024);
		add("South", slider);
		slider.addAdjustmentListener(this);

	applyButton = new Button("Push me to Segment!");			
	applyButton.addActionListener(this);	
	add("Center",applyButton);
		
    } 
    
    // Slider for Q
    public void adjustmentValueChanged(AdjustmentEvent e) {
		applet.Q = slider.getValue();
		applet.imageCanvas.repaint();
	} 

 
    public void actionPerformed(ActionEvent ev) {
	
	if (ev.getActionCommand().equals("Push me to Segment!")) {
	    applyButton.setEnabled(false);
    restore();
	    
	}
	
	}
	
	// Get the choice item events here
    public void itemStateChanged(ItemEvent e) {
	}
	
    public void restore() {
    applet.OneRound();
    applet.imageCanvas.repaint();
	applyButton.setEnabled(true);
    }
}


//
// Canvas part for drawing the image and the segmented image.
// Indicate various statistics too.
//
class ImageCanvas extends Canvas {
    
    Srmjava applet;
    
    ImageCanvas(Srmjava applet) {	
	this.applet = applet;
	setBackground(Color.white);
    }
    
    
    public void paint(Graphics g) {
    	int w;
    	int h;
    	double scale;
    	
	g.setColor(Color.red);

	// Preserve aspect ratio
	w= getSize().width/2;
	h=(int)(applet.aspectratio*getSize().width/2.0);
	
	if (h<applet.appleth-140) scale=1.0; 
			else scale=(applet.appleth-140)/(double)h;
			
			w=(int)(scale*w);
			h=(int)(scale*h);
	
	// Draw the source image
	g.drawImage(applet.img, 0, 0,w, h, this);
	
	// Draw the segmented image
	g.drawImage(applet.imgseg, getSize().width/2+10, 0, w, h, this);
	
	g.setFont(applet.helveticafont2);
	g.drawString( "Q=" + applet.Q,5,50);
	//+"(Image dimension: "+applet.img.getWidth(this)+","+applet.img.getHeight(this)+")"
    }
    
}

//
// Union Find Data Structure of Tarjan for Disjoint Sets
//
class UnionFind{

	int  []rank; 
	int []parent; 

//
// Create a UF for n elements
//
UnionFind(int n)
{int k;

parent=new int[n]; 
rank=new int[n];

System.out.println("Creating a UF DS for "+n+" elements");

for (k = 0; k < n; k++)
      {parent[k]   = k;
      rank[k] = 0;     }
}

//
// Find procedures
//
int Find(int k)
{
	while (parent[k]!=k ) k=parent[k];
    return k;}

//
// Assume x and y being roots
//
int UnionRoot(int x, int y)
{  
	if ( x == y ) return -1;

      if (rank[x] > rank[y])  
      {parent[y]=x; return x;}
      else                       
      { parent[x]=y;if (rank[x]==rank[y]) rank[y]++;return y;}
}
}


//
// An edge: two indices and a difference value
//
class Rmpair
{
int r1,r2;
int diff;

Rmpair()
{
	r1=0; 
	r2=0; 
	diff=0;
}

}




