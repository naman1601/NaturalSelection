import java.util.Random;

//this is now going on GitHub.

public class Sims {

  int daysLived = 0;
  double range = 0;
  boolean isAlive = false;
  boolean hasFood = false;
  //boolean canReproduce = false;
  int xc = 0;
  int yc = 0;

  static double getRange(int val) {

    double temp = (1.0*val)/2;

    double retval = 0.8 * Math.sqrt(2*temp*temp);

    return retval;

  }

  public static void main(String[] args) {
    
    //TWEAK THESE VARIABLES TO PLAY AROUND WITH THE WORLD'S SETTINGS(LAWS OF NATURE?) -- COMPLETELY HARMLESS(HOPEFULLY)
    //----------------------------------------
    //int population = 196;
    int side = 50;
    int population = 4*(side - 1);
    int blobSpawn = 43;
    int foodSpawn = 28; 
    double mutation = 0.003;
    int reproductionCycle = 5;
    int cycles = 25000;
    //dividing blobSpawn by 100 gives the probability of a blob spawning in a particular grid (only on the boundaries)
    //dividing foodSpawn by 1000 gives the probability of a food particle spawning in a particular grid (all over the grid world)
    //----------------------------------------
    //HARMLESSLY TWEAK-ABLE VARIABLES END HERE.


    //And then Naman said, "Let there be light" -- (laying the basic structure for the world)
    
    Random random = new Random();
    Sims[] blob = new Sims[population];
    int[][] field = new int[side][side];
    int randomNum = 0;
    int fastest = 0;
    int blobsAlive = 0;
    double foodLeft = 0;

    //And the Naman said, "Let there be no food initially"

    for(int i = 0; i < side; i += 1) {

      for(int j = 0; j < side; j+= 1) {

        field[i][j] = 0;

      }

    }

    //And then Naman said, "Let some blobs be brought to life"

    for(int i = 0; i < population; i += 1) {

      blob[i] = new Sims();
      randomNum = random.nextInt(100);
      if(randomNum < blobSpawn) {

        blob[i].isAlive = true;
        blob[i].range = getRange(side);
        blobsAlive += 1;

      }

    }

    System.out.println("Initially --> Alive : " + blobsAlive + "   Range : " + getRange(side));

    int x = 0;

    //And then Naman said, "Let us give to our tiny blobs little huts which they can call home"
    //(assigning geometrical co-ordinates to all blobs)

    for(int i = 0; i < side; i += 1){

      if((i == 0) || (i == (side - 1))) {

        for(int j = 0; j < side; j += 1) {

          blob[x].xc = i;
          blob[x].yc = j;
          x += 1;

        }

      } else {

        blob[x].xc = i;
        blob[x].yc = 0;
        x += 1;

        blob[x].xc = i;
        blob[x].yc = side - 1;
        x += 1;

      }

    }

    /*for(int i = 0; i < population; i += 1) {

      System.out.printf("Blob no. %d, co-ordinates : %d, %d\n", i, blob[i].xc, blob[i].yc);

    }*/

    
    //and then Naman said, "Let's go"
    
    for(int i = 0; i < cycles; i += 1) {

      //and then Naman said, "Let the world be filled with food so that the blobs may eat and live"

      for(int j = 0; j < side; j += 1) {

        for(int k = 0; k < side; k += 1) {

          randomNum = random.nextInt(1000);
          if(randomNum < foodSpawn) {

            field[j][k] = 1;
            foodLeft += 1;

          }

        }

      }

      //checking each food particle one-by-one to see which blob(if any) can get to it in time, and the giving it to the fastest blob(if any)

      for(int j = 0; j < side; j += 1) {

        for(int k = 0; k < side; k += 1) {

          if(field[j][k] == 1) {

            double time = 1000;

            fastest = -1;

            //finding the fastest blob (if any) for the food particle.

            for(int l = 0; l < population; l += 1) {

              if(blob[l].isAlive && (!blob[l].hasFood)) {

                double tt = Math.sqrt((j - blob[l].xc)*(j - blob[l].xc) + (k - blob[l].yc)*(k - blob[l].yc))/blob[l].range;
                //double feasible = Math.sqrt((j - blob[l].xc)*(j - blob[l].xc) + (k - blob[l].yc)*(k - blob[l].yc));
                if((tt < time) && (tt <= 1)) {

                  fastest = l;
                  time = tt;

                }

              }

            }

            //giving the food particle to the fastest blob(if any)

            if(fastest != -1) {

              blob[fastest].hasFood = true;
              field[j][k] = 0;
              foodLeft -= 1;

            }

          }

        }

      }

      //now the blobs have made it back to their homes
      //if they managed to get food, they'll live and might even reproduce!

      double avgRange = 0;
      int count = 0;
      int notAlive = 0;

      for(int j = 0; j < population; j += 1) {

        if(!blob[j].hasFood) {

          blob[j].isAlive = false;
          blob[j].daysLived = 0;
          notAlive += 1;

        } else {

          blob[j].hasFood = false;
          blob[j].daysLived += 1;

          if(blob[j].daysLived % reproductionCycle == 0) {

            count += 1;
            avgRange += blob[j].range;

          }

        }

      }

      avgRange = avgRange/count;

      //double avgRange = 0;
      //int toss = 0;
      //int count = 0;
      //int notAlive = 0;

      /*for(int j = 0; j < population; j += 1) {

        if(!blob[j].isAlive) notAlive += 1;

        if(blob[j].canReproduce) {

          blob[j].canReproduce = false;
          avgRange += blob[j].range;
          count += 1;

        }

        avgRange = avgRange/count;

      }*/


      //and then Naman said, "Go forth and multiply!"

      if(count > notAlive) count = notAlive;

      int counter = 0;
      int toss = 0;

      for(int j = 0; counter < count; j += 1) {

        if(!blob[j].isAlive) {

          blob[j].isAlive = true;
          toss = random.nextInt(2);
          if(toss == 0) blob[j].range = (1 + mutation) * avgRange;
          if(toss == 1) blob[j].range = (1 - mutation) * avgRange;
          counter += 1;

        }

      }

      //and then Naman said, "Let the world be rid of all uneaten food, so that the next day, we may begin anew!"

      for(int j = 0; j < side; j += 1) {

        for(int k = 0; k < side; k += 1) {

          field[j][k] = 0;

        }

      }

    }

    double avg = 0;
    blobsAlive = 0;

    //counting the number of blobs alive at the end, and their average range

    for(int i = 0; i < population; i += 1) {

      if(blob[i].isAlive) {

        blobsAlive += 1;
        avg += blob[i].range;

      }

    }

    avg = avg/blobsAlive;
    
    foodLeft = foodLeft/cycles;

    System.out.println("Finally --> Alive : " + blobsAlive + "   Range : " + avg);
    
    System.out.println("Average food left uneaten per day : " + foodLeft);

  }

}
