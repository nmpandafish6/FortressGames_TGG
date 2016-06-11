# Diamond Square 
Uses the diamond square fractal algorithm in order to create a random terrain to start with. More information on the algorithm can be found at https://en.wikipedia.org/wiki/Diamond-square_algorithm  

Variable | Suggested Value
--------------------------- | -----------------------------------------  
 Size :  |  2^x + 1 (Ex : 129, 257, 513)  
 Random Divider : | ~1
 Upper Left : | ~100
 Upper Right : | ~100
 Bottom Left : | ~100
 Bottom Right : | ~100
 Random Scalar : | ~100
 
 
# Flood
For each element less than `height` in the array, sets the value to `height`

Variable | Suggested Value
--------------------------- | -----------------------------------------  
 Height :  |  ~100
 
# Ln Function
For each element in the array, sets the value to the natural log of the value. `val = ln(val)`

# Add Gaussian Randomness
Adds Gaussian Randomness (`Random.nextGaussian`) to each value in the array.

Variable | Suggested Value
--------------------------- | -----------------------------------------  
 Min :  |  ~10
 Max :  |  ~10
 
# Add Randomness  
Adds Randomness (`Random.nextDouble`) to each value in the array.

Variable | Suggested Value
--------------------------- | -----------------------------------------  
 Min :  |  ~10
 Max :  |  ~10
 
# Laplacian
Uses the laplacian smoothing algorithm in order to smooth random terrain and reduce noise. More information on the algorithm can be found at https://en.wikipedia.org/wiki/Laplacian_smoothing  

Variable | Suggested Value
--------------------------- | -----------------------------------------  
 Repeat :  | ~3
 
# Make Flat Shape
Draws a flat [Regular Polygon](https://en.wikipedia.org/wiki/Regular_polygon) at the defined coordinates with the specified `height`. The `radius` is the distance used for a circumscribed circle around the regular polygon.  

Variable | Suggested Value
--------------------------- | -----------------------------------------  
 Number of Sides of Shape :  |  s > 3
 Center X : | 0 < x < width
 Center Y : | 0 < y < height
 Radius : | r > 0
 Height : | ~mean
 
 # Keep Shape
Cuts out a flat [Regular Polygon](https://en.wikipedia.org/wiki/Regular_polygon) from the array at the defined coordinates. Sets all values not included in the shape to 0. The `radius` is the distance used for a circumscribed circle around the regular polygon.  

Variable | Suggested Value
--------------------------- | -----------------------------------------  
 Number of Sides of Shape :  |  s > 3
 Center X : | 0 < x < width
 Center Y : | 0 < y < height
 Radius : | r > 0

# Stats
Displays various stats about the data including minimum, maximum, and mean.

# Map Scale
Applies a [Map Function](https://www.arduino.cc/en/Reference/Map) to each element in the array. Values are mapped from the source values to the range (0..255). If the source values, which are mapped, are outside of the range (Min..Max) or (Max..Min), values might overflow and simply appear white on the grid.

Variable | Suggested Value
--------------------------- | -----------------------------------------  
 Source Low :  |  low <= Min  OR low >= Max
 Source High :  |  high >= Max OR high <= Min

# Binary AND
Compares the values of two matrices at the given user location. Provided matrices should be in **raw** format.
The resulting matrix is essentially the intersection of matrix1 and matrix2.  
The resulting matrix is the result of `matrix1[row][col] & matrix2[row][col]`. This function is useful for mapping a black and white image representing island shapes to a set of random terrain data.  
**Note: ** `&` denotes the binary [and](https://en.wikipedia.org/wiki/Bitwise_operation#AND) operation.

# Binary OR
Compares the values of two matrices at the given user location. Provided matrices should be in **raw** format.
The resulting matrix is essentially the union of matrix1 and matrix 2.  
The resulting matrix is the result of `matrix1[row][col] | matrix2[row][col]`. This function is useful for creating organic shapes of islands by combining sets of data with different cut out shapes.  
**Note: ** `|` denotes the binary [or](https://en.wikipedia.org/wiki/Bitwise_operation#OR) operation.

# Binary XOR
Compares the values of two matrices at the given user location. Provided matrices should be in **raw** format.
The resulting matrix is the result of `matrix1[row][col] ^ matrix2[row][col]`. This function is for toggling certain bits of information in the data array.  
**Note: ** `^` denotes the binary [xor](https://en.wikipedia.org/wiki/Bitwise_operation#XOR) operation.

# Binary NOT
Inverts the values of the given matrix provided at the given user location. Provided matrices should be in **raw** format.
The resulting matrix is the result of `~matrix1[row][col]`. This function is for inverting the bits of information in the data array and is useful for inverting slopes in the terrain essentially turning mountains into valleys and vice versa.   
**Note: ** `~` denotes the binary [not](https://en.wikipedia.org/wiki/Bitwise_operation#NOT) operation.
