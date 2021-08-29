# Notes

## Issues encountered
 - Zooming is inconsistent in the amount by which it zooms in or out. Zooming far away, objects disappear instantly and close up it takes ages
 	 - fixed by multiplying the zoom by a percentile value rather than adding a certain amount each time
 - When zooming out a long way. The screen would shift because the coordinates of the top left point where greater than the 32-bit binary integer limit
	 - fixed by converting all positions to doubles