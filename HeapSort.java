//Chase Strickler
//Heap Sort Performance Analysis
//16 February 2018

package heapsort;

import java.lang.*;
import java.util.Random;

/**
 * This program performs heap sort on randomly generated 
 * arrays of varying lengths and input to analyze the performance of the algorithm..
 * @author Chase Strickler
 */
public class HeapSort {

    /**
     * The main function is only used to call heap sort and the method
     * to randomly generate arrays.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       double expected;    //calculated O(n(lgn)) for comparison with average swaps
       double delta;       //= average/expected; This is to see if there exists a constant in the run time.
      
       for(int j = 1; j < 7; j++){
            int total_count = 0;    //tracks all of the comparisons/inversions across all arrays of one size
            int size = (int) Math.pow(10, j);   //increase j exponentially for arrays of size: 10 100 1,000 10,000 100,000
            System.out.println("Array Size: " + size + '\n' + '\t' + "Number of Swaps/Comparisons: " + 
                    '\n' + '\t' + "----------------------------");
            
            for(int i = 0; i < 14; i++){
                int count = 0;  //reset local counter for each new array
                int arr[] = createRandomIntArray(size);
                int n = arr.length;
                
                HeapSort ob = new HeapSort();
                count = HeapSort.heapSort(arr, count);
                
                System.out.println('\t' + "Swaps on array " + i + ": " + count);
                
                total_count = total_count + count;
            }
            
            int average = total_count/15;
            expected = nLogn(size);
            delta = average/expected;
            
            System.out.println('\t' + "----------------------------" + '\n' + '\t' 
                                + "Average swaps and comparisons: " + average + '\n'
                                + '\t' + "Expected --> O(n(lgn)): " + expected + '\n'
                                + '\t' + "Actual/Expected = " + delta + '\n');
        }
    }
    
    /**
     * This method randomly generates an array.
     * 
     * @param size size of the array to be generated
     * @return the randomly generated array is returned.
     */
    public static int[] createRandomIntArray(int size) {    //method generates a random array
        int[] answer = new int[size];                       //create new array
        int index = 0;                                      //will be used to iterate across our new array and populate it with element
        Random generator = new Random();
            for(int element : answer) {
                element = generator.nextInt(1000) + 1;       //element is the randomly generated number from 1-1000
                answer[index] = element;                     //place the random number within the array
                index++;
            }
        return answer;  //return the randomly generated array
    }
    
    /**
    * This is the sorting function, which first calls buildMaxHeap to build the 
    * heap, swaps the necessary values, and then calls maxHeapify to prepare for
    * the next iteration of the loop, as it is necessary for the heap to be a 
    * max heap when using heap sort. In terms of time, this loop always takes n-1 time,
    * so there is no need to add a counter here. We need to look at
    * buildMaxHeap and maxHeapify to see how long heapSort takes.
    * 
    * @param A input array
    * @param count passing count through for maxHeapify count check
    * @return count
    */
    public static int heapSort(int A[], int count){
        int n = A.length;
        buildMaxHeap(A, n, count);
        for (int i = n - 1; i > 0; i--){
            
            //Swap - In terms of time, this loop always takes n-1 time. We need to look at
            //buildMaxHeap and maxHeapify to see how long heapSort takes.
            count++;
            int temp = A[0];
            A[0] = A[i];
            A[i] = temp;
            
            //Create a max heap from the heap we just built
            count = maxHeapify(A, 0, i, count);
        }
        return count;
    }
    
    /**
     * This is the maxHeapify function. 
     * It creates a max heap on the given sub-array.
     * 
     * @param A input array
     * @param i root value of sub-array
     * @param n size of array
     * @param count counts swaps & comparisons
     * @return count
     */
    public static int maxHeapify(int A[], int i, int n, int count){
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        
        //If left child is greater than largest, update
        if (left < n && A[left] > A[largest]){
         largest = left;
         count++;
        }
        
        //If right child is greater than largest, update
        if (right < n && A[right] > A[largest]){
            largest = right;
            count++;
        }         
        
        //If largest is not i or the root
        if (largest != i){
            count++;
            
            int temp = A[i];
            A[i] = A[largest];
            A[largest] = temp;
            
            //Recursive call to maxHeapify on the sub-array
            count = maxHeapify(A, largest, n, count);
        }
        return count;
    }
    
    /**
     * This function builds a max heap for each node in the tree above
     * the bottom level of the tree. For time, it takes n time multiplied by
     * the amount of time it takes to perform maxHeapify
     * 
     * @param A input array
     * @param n size of array
     * @param count pass count through for maxHeapify
     * @return count
     */
    public static int buildMaxHeap(int A[], int n, int count){
        
        for (int i = (int) Math.floor(n/2); i >= 0; i--){
            count++;
            count = maxHeapify(A, i, n, count);
        }
        return count;
    }
    
    /**
     * This function calculates O(n(lgn)).
     * @param x the n in O(n(lgn))
     * @return the value of O(n(lgn))
     */
    public static int nLogn(int x){
        return (int) ((Math.log(x) / Math.log(2)) * x);
    }    
}
