/*
 * FullWindow.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package org.simedape.wp.metric.dtw;


import org.simedape.wp.metric.timeseries.TimeSeries;

public class FullWindow extends SearchWindow
{

   // CONSTRUCTOR
   public FullWindow(TimeSeries tsI, TimeSeries tsJ)
   {
      super(tsI.size(), tsJ.size());

      for (int i=0; i<tsI.size(); i++)
      {
         super.markVisited(i, minJ());
         super.markVisited(i, maxJ());
      }  // end for loop
   }  // end CONSTRUCTOR

}  // end class FullWindow
