/* 
 *                               COMPLEXITIES
 *          DATA:   O( (K)+(N)+(K) ) -> O(n)
 *  MAX DISTANCE:   O[ (N-1)+(N-2)+(N-3)+(N-1)+(N-2) ] -> O(n)         
 * MINIMUN MOVES:   O[ (N)+(N-1)+(N-1) ] -> O(n)         
 *       THE END:   O(1)
 *
 *                           GENERAL COMPLEXITY
 *                                  O(n)
 */ 


import java.util.*;
import java.io.*;


public class Round{
	public static void main(String[] args) throws FileNotFoundException{


//=================================================================================
//                                       DATA
//=================================================================================
         Scanner S = new Scanner(new File(args[0]));

         int N = S.nextInt();            // N cities
         int K = S.nextInt();            // K cars

         if (K == 0 || N == 0)           // Case of 0 cars or cities
            System.exit(0);

         int[] cars = new int[K];

         if (K == 1){                    // Case of 1 car
            System.out.print(0);
            System.out.print(" ");
            System.out.print(0);         // 0 0 always
            System.exit(0);         
         }

         for (int i = 0; i < K; i++)     // In which city can we find each car?
            cars[i] = S.nextInt();
           
         int[] cities = new int[N];
         int[] max = new int[N];
        
         for (int i = 0; i < N; i++){    // How many cars does each city have?
            cities[i] = 0;               // Initialization of cities
         } 
         for (int i = 0; i < K; i++)     // Here we compute how many cars each city has.
            cities[cars[i]] += 1;  

//=================================================================================
//                                  MAX DISTANCE
//================================================================================= 
// Finding max distance of a city, which does have a car, from our city-destination
//                          We do this for every city   

		   for (int i = 0; i < N-1; i++){            // Finding furthest city with car, from city N-1 
		     if(cities[i]==1 && cities[i+1]==0){     // via brute force
			     max[N-1] = N-1-i;                    // Calculating the distance which is max
			     break;
			  }
			  if(cities[i]>0){
					max[N-1] = 0;
					break;
				}
			}

			for (int i = 1; i < N-1; i++){            // Finding furthest city with car, from city 0
				if(cities[i]==1 && cities[i+1]==0){    // via brute force
					max[0] = N-i;                       // Calculating the distance which is max
					break;
				}
				if(cities[i]>0){
					max[0] = 0;
					break;
				}
			}


         for (int i = 2; i < N-1; i++){            // Finding furthest city with car, from city 1 (special case),
				if(cities[i]==1 && cities[i+1]==0) {   // because afterwards we use i-2. We do this again via brute force
					max[1] = N-i+1;                     // Calculating the distance which is max 
					break;
				 }
  			   if(cities[i]>0){
					max[1] = 0;
					break;
            }
			}

         if(max[1]==0){                            // Corner cases for furthest city with car from city 1
            if(cities[0]==0)
               max[1] = max[0]-1;
            else if(cities[0]>0)
               max[1] = 0;
            if(cities[0]==1 && cities[N-1]==0)
               max[1] = N-1;
		   }

// The idea to find the max distance of a city with a car fro a city destination in O(N), relys upon 
// the max of a previous city, as we would do in dynamic programming. In this implementation we only 
// calculate the max distance of the problematic cities-destinations. A problematic city-destination 
// is a city from where the furthest city (i)-st with a car has only 1 car and the (i-1)-st has 0 cars.
// So when we don't have problematic cities-destinations, we have max==0 and so we need another kind of
// data in order to implement the idea of dynamic programming. This is why we save the value of the last 
// max we found, which is greater than zero (>0) and how many cities apart we have to calculate the next 
// max. So, if the max isn't easy to calculate immediately, via DP, we calculate the max from the previous
// max greater than zero that we saved and then subtract the counter of the cities in between, because now
// our city-destination is closer to the furthest city by the value of the counter.
// The above should be done both for left to right and right to left.

/***************************************left to right**************************************/
         int max_parelthontos = 0;                 
         int poses_theseis_prin = 0;          
           
         for (int i = 1; i < N; i++){
            if(i == 0  || i==1 || i == N-1)
               continue;
            if(cities[i-1]>1) 
               max[i]=0;
            if(cities[i-1]==0 && max[i-1]==0) //&& cities[i] == 0)
               max[i]=0;
              
            if(cities[i-1]==0 && max[i-1]>0)
               max[i] = max[i-1] - 1;
            if (cities[i-2]==0 && cities[i-1]==1)
               max[i] = N - 1;
              
            if(max[i]>0){
               max_parelthontos = max[i];
               poses_theseis_prin += 1;
            }
            if(cities[i-1]==0 && max[i-1]==0 && cities[i] > 0 && max[i] ==0){
               max[i]= max_parelthontos-poses_theseis_prin;
            }
         }

/***************************************right to left**************************************/
         int max_mellontos = 0; 
         int poses_theseis_pisw = 0;
           
         for (int i = N-1; i > 0; i--){//mporei na enswmatwthei kai panw
            if(i == 0  || i == 1 || i == N-1)
               continue;
             
            if(max[i]>0){
               max_mellontos = max[i];
               poses_theseis_pisw += 1;
            }
            if(cities[i-1]==0 && max[i-1]==0 && cities[i] > 0 && max[i] ==0){
               max[i]= max_mellontos-poses_theseis_pisw;
            }
         }

 /***********************************DEBUGGING***********************************/          
     /* System.out.print("Posa aytokinhta exei kathe polh");
        System.out.println();
         for (int i = 0; i < N; i++){
            System.out.print(cities[i]); 
            System.out.print(" ");
         }
         System.out.println();
         System.out.println();

         System.out.print("Max apostash aytokinhtoy gia kathe polh proorismo");
         System.out.println();
         for (int i = 0; i < N; i++){
            System.out.print(max[i]); 
            System.out.print(" ");
         }
         System.out.println();
         System.out.println();*/

//=================================================================================
//                                MINIMUM MOVES
//=================================================================================  
//          Here we calculate the moves required to arrive to each city, 
//                     via dynamic programming technique.  
//                      We only keep the valid solutions 
//             with the help of the max distance we found above
//                   (Condition: moves[i+1] >= 2*max[i+1]-1)
//                    Finally, we keep the minimun solution 

         int[] moves = new int[N];
         for (int i = 0; i < N; i++) 
            moves[i] = 0;
      
         for(int i = 1; i < N; i++)
            moves[0] += cities[i] * (N-i);
        
         int minmoves, city=0;
         if(moves[0] >= 2*max[0]-1)
            minmoves = moves[0];
         else
            minmoves = N*K+1;        // Impossible for minmoves to be this many

/////// System.out.print("Kinhseis");
/////// System.out.println();

         for(int i = 0; i < N-1; i++){ 
            moves[i+1] = moves[i] + (K-cities[i+1]) - cities[i+1]*(N-1);
///////     System.out.print(max);
            if(minmoves > moves[i+1] && moves[i+1] >= 2*max[i+1]-1){
               minmoves = moves[i+1];
               city = i+1;
            }      
///////     System.out.print(moves[i]);
///////     System.out.print(" ");
         }

// Gia na vrw tis kinhseis ths epomenhs polhs deksistrofa:
// afairw apo tis kinhseis ths prohgoumenhs polhs tis kinhseis twn aytokinhtwn auths
// poy eksetazoume, efoson einai hdh ekei kai den xreiazetai na kinithoun. Etsi:
//                                moves[i] - cities[i+1]*(N-1)
// Omws prepei na prosthesoume ta aytokinhta ths prohgoumenhs polhs poy prin den ta
// lavame ypopshn. Omws, oi prohgoumenh polh apexei mia kinhsh apo thn epomenh ths,
// pou eksetazoume twra. Ara, prosthetoume ton arithmo aytokinhtwn ths prohgoumenhs
// polhs, o arihmos twn opoiwn apoteloun kai tis kinhseis gia na pame sthn twrinh/epomenh polh.
// Meta ola ta enapomeinanta cars kanoun syn mia kinhsh. Ta parapanw, omws, mas dinoun ton arithmo
// twn aytokinhtwn poy vriskontai ektos thspolhs pou eksetazoume. Ara, exoume:
//                                      +(K-cities[i+1])


//=================================================================================
//                                   THE END
//=================================================================================       
/////// System.out.print(moves[N-1]);
/////// System.out.println();
/////// System.out.println();
        System.out.print(minmoves);
        System.out.print(" ");
        System.out.print(city);


	}
}
