# Notes

## Problems encountered
- When zooming out a long way. The screen would shift because the coordinates of the top left point where greater than the 32-bit binary integer limit
	- fixed by converting all positions to doubles