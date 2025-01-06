/*
Post-Coding Note:
This was my first major coding project, one which I submitted for an ungraded Computer Science I project. I had sought out to create
an algorithm that solves a system of equations using matrix row reduction. While it worked in some cases, it failed
often due to its pivoting procedure, as the multi-dimensional sorting algorithm did not use recursion and as such it was
unable to view the other values in a row beyond the first two. An updated version of this project can be found under
my Mathematics repository under the LinearAlgebra class method RREF(Matrix A).
Completed in May 2024.
*/

import java.util.Scanner;
import java.lang.Math;

public class Matrix{

    //Prints the matrix
    //Make a method to remove -0.0
    private static void printMatrix(double [][] matrix, int rows, int cols) {
        int i;
        int j;
        for (i = 0; i < rows; i++) {
            for (j = 0; j < cols; j++) {
                if (j==0) {
                    System.out.print("[");
                    System.out.printf("%.2f", matrix[i][j]);
                    System.out.print(" ");
                }
                else if (j==(cols-1)) {
                    System.out.printf("%.2f", matrix[i][j]);
                    System.out.print("]");
                }
                else {
                    System.out.printf("%.2f", matrix[i][j]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    //Elementry Row Opperations

    //Swaps 2 Rows
    private static double[][] rowSwap(double[][] matrix, int row1, int row2) {
        double[] tempArray = new double[matrix.length];

        tempArray = matrix[row1];
        matrix[row1]=matrix[row2];
        matrix[row2]=tempArray;

        return matrix;
    }

    // Adds x Row 1 to Row 2
    private static double[][] rowAdd(double[][] matrix, int row1, int row2, double scalar) {
        int i;
        for (i = 0; i < matrix.length; ++i) {
            matrix[row2][i] = matrix[row2][i] + scalar * matrix[row1][i];
        }

        return matrix;
    }

    //Adding or Subtracting Matracies
    private static double[][] matrixAdd(double[][] matrix1, double[][] matrix2, double scalar) {

        double[][] tempMatrix = new double[matrix1.length][matrix1[0].length];
        int i;
        int j;

        for (i=0; i < matrix1.length; ++i ) {
            for (j=0; j < matrix1[0].length; ++j) {
                tempMatrix[i][j] = matrix1[i][j] + scalar * matrix2[i][j];
            }
        }

        return tempMatrix;
    }

    //Makes a size n identity matrix
    private static double[][] identity(int n) {
        double[][] identityMatrix = new double [n][n];
        int i;
        int j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (j==i) {
                    identityMatrix[i][j] = 1.0;
                }
                else{
                    identityMatrix[i][j] = 0.0;
                }

            }
        }
        return identityMatrix;
    }

    // Scales a row by x
    private static double[][] rowScale(double[][] matrix, int row, double scalar) {
        int i;
        for (i = 0; i < matrix.length; ++i) {
            matrix[row][i] = scalar * matrix[row][i];
        }

        return matrix;
    }

    //RREF Calculator (ONLY WORKS FOR SQUARE MATRICES)
    private static double[][] RREF(double[][] matrix, int rows, int cols) {
        int i = 0;
        int j;
        int k = 0;
        double pivotValue = 0.0;

        //REF
        for (j = 0; j < cols; ++j) {

            // Puts column i in order
            for (i = 0; i < rows - 1; ++i) {
                //First column swap
                if ((Math.abs(matrix[i][j]) < Math.abs(matrix[i+1][j])) && j == 0) {
                    rowSwap(matrix, i, i+1);
                }
                //Swap for not the first column. This checks if the value bellow [i][j] is larger and that the previous column is not sorted.
                else if ((Math.abs(matrix[i][j]) < Math.abs(matrix[i+1][j])) && (Math.abs(matrix[i][j-1]) == Math.abs(matrix[i+1][j-1]))) {
                    rowSwap(matrix, i, i+1);
                }
            }



            //New for loop, getting 0's bellow the pivot position

            //Finds pivot values
            for (k = 0; k < cols; ++k) {
                pivotValue = matrix[j][k];
                if (pivotValue != 0) break;
            }

            for (k = j; k < rows-1; ++k) {
                if (pivotValue != 0) {
                    rowAdd(matrix, j, k+1, -matrix[k+1][j] / pivotValue);
                }
            }

            i=j+1;

        }

        //RREF
        for (i = rows -1; i>=0; --i) {
            k = 0;

            //Finds the pivot value
            for (k = 0; k < cols; ++k) {
                pivotValue = matrix[i][k];
                if (pivotValue != 0) break;
                if (k == cols - 1 && pivotValue == 0.0) {
                    pivotValue = 1.0;
                }
            }

            //Makes the pivot value 1
            rowScale(matrix, i, 1/pivotValue);

            //Subtracting row from those above it
            for (k = i; k > 0; --k) {
                rowAdd(matrix, i, k-1, -matrix[k-1][i]);
            }

        }
        return matrix;
    }


    //Determinant
    private  static double determinant(double[][] matrix, int rows) {
        double determinant = 0.0;

        //2x2 Definition
        if (rows == 2) {
            determinant = matrix[0][0] * matrix [1][1] - matrix[1][0] * matrix [0][1];
        }
        //3x3 definition
        else if (rows == 3) {
            determinant = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[2][1] * matrix[1][2]) - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[2][0] * matrix[1][2]) + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[2][0] * matrix[1][1]);
        }

        return determinant;
    }



    //Builds the users matrix and prints it
    public static void main (String[] args) {
        Scanner scnr = new Scanner(System.in);
        int rows = 3;
        int cols = 3;
        int i;
        int j;
        int choice1 = 0;
        int choice2;
        int tempRow;
        int tempCol;
        double newValue;
		

		System.out.println("Note: This program only supports square matracies.");
		System.out.println("Please eneter the number of rows in your matrix");
		rows = scnr.nextInt();
		System.out.println("Please eneter the number of columns in your matrix");
		cols = scnr.nextInt();

        double[][] matrix = new double [rows][cols];

		System.out.println("Please enter the values of your matrix, going from left to right. Start at the first row.");
		
		//Recives matrix values and puts them into the array
		for (i=0; i<rows; ++i) {
			for (j=0; j<cols; ++j ) {
				matrix[i][j] = scnr.nextDouble();
			}
			if (i < rows - 1) {
			System.out.println("You are now on row " + (i+2) + ".");
			}
			
		}
		
		//Allows the user to verify the matrix they are using.
		System.out.println();
		while (choice1 == 0) {
			System.out.println("Is this the matrix you want to use?");
			printMatrix(matrix, rows, cols);
			System.out.println("Type 1 if the matrix is correct. Type 0 if it is incorrect and you would like to replace an element in the matrix.");
			choice1 = scnr.nextInt();
			//Allows the user to change a matrix element
			if (choice1 == 0) {
				System.out.println("What row is the element that you want to change in?");
				tempRow = scnr.nextInt();
				System.out.println("What column is the element that you want to change in?");
				tempCol = scnr.nextInt();
				System.out.println("Please enter the new value for " + tempRow +", " + tempCol +".");
				newValue = scnr.nextDouble();
				matrix[tempRow-1][tempCol-1] = newValue;
			}
		}
		
		//Asks the user for their calculator option
		System.out.println();
		System.out.println("Please select an option from the following list:");
		System.out.println("1-RREF");
		System.out.println("2-Determinant");
		System.out.println("3-TBD");
		choice2 = scnr.nextInt();
		
		//RREF Choice
		if (choice2 == 1) {
			System.out.println();
			RREF(matrix, rows, cols);
			System.out.println();
			System.out.println("RREF Matrix:");
			printMatrix(matrix, rows, cols);
		}
		//Calls the determinant method
		else if (choice2 == 2) {
			System.out.println();
			if (rows == cols && rows <= 3) {
				System.out.println();
				System.out.print("Determinant: ");
			    System.out.print(determinant(matrix, rows));
			}
			else {
				System.out.println("Matrix size not supported.");
			}
			
		}
		
		else {
			System.out.println("Option not supported.");
		}


    }


}
