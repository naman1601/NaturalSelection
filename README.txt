This is an attempt to simulate a very basic version of natural selection.

Explanation :

We have a 50x50 grid world. On the grids at the boundary of the world (which means 196 grids), we spawn blobs with each grid having a 43% probability of spawning a blob.

Now, our blobs have just one attribute -> range.

They also have a speed attribute, but I've decided that speed is directly proportional to range, and since the constant of proportionality is same for all the blobs, range will be serving as both range and speed.

Range basically is the total distance a blob can travel in one day. Eg : if a blob has a range of 30, he can go 15 units away from his home and 15 back, or less if he chooses to.

Now, each blob has to get a food particle and return home if it wants to survive.

Now, a loop starts.

All over the grid world, food spawns with each grid having a 2.8% probability of spawning food. It means that a food particle might also spawn on a grid which has a blob (lucky guy!)

For each food particle, we calculate the distance from each blob. That distance is divided by the range of each blob, essentially giving us the time taken by each blob to reach that food particle. The blob with the minimum amount of time will get that food particle, provided that the distance is such that he can return home after having gotten the food.

Then, that food particle is removed from the grid world and that blob will no longer be taking any more food particles that day.

We do this for all food particles. Ultimately, a few slower blobs are left without any food particles or shelter for the night, and they die(are removed from the grid world) leaving their homes empty.

The blobs have a reproduction cycle of 5 days, so a blob will reproduce every 5 days. However, the number of blobs will not exceed 196, which means that if there are 196 blobs present (very very unlikely, almost impossible with the constrains we have set), then no blobs will reproduce.

The baby blobs have range ±0.3% that of their parents.

We continue this loop 25,000 times.

The initial and final values of population and average range is then printed.

I have tried to include all variables near the top so that you can play around with the values for yourself.

I would really love to hear what you have to say about this! (Suggestions/review or just about anything is welcome!)

Thank you for being such a patient reader.

Have fun!
