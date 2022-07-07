import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        // Constant - amount of paint per square metre
        double PAINT_PER_SQ_M = 0.0833;

        // get number of walls to paint
        System.out.println("How many walls do you want to paint?");
        int numberOfWalls = Integer.parseInt(myScanner.nextLine());

        // get amount of coats wanted
        System.out.println("How many coats would you like?");
        int numberOfCoats = Integer.parseInt(myScanner.nextLine());

        // find out the colour the user wants
        String[] colours = {"blue", "green", "orange", "red", "yellow", "purple"};
        String colourMessage = "What colour would you like? ";

        for (int i = 0; i < colours.length; i++) {
            if (i == colours.length - 1) {
                colourMessage = colourMessage.concat(" or ");
            } else if (!(i == 0)) {
                colourMessage = colourMessage.concat(", ");
            }
            colourMessage = colourMessage.concat(colours[i]);
        }
        System.out.println(colourMessage);
        String wallColour = myScanner.nextLine();


        //get the width and any obstacles on each wall. Then return the total paint area minus obstacles
        int totalPaintArea = 0;
        for (int i = 1; i <= numberOfWalls; i++) {
            double totalObstacleArea = 0;

            System.out.println("How tall is wall " + i + "? (to nearest metre)");
            int height = Integer.parseInt(myScanner.nextLine());

            System.out.println("How wide is wall " + i + "? (to nearest metre)");
            int width = Integer.parseInt(myScanner.nextLine());

            System.out.println("Is there something on wall " + i + "? (true or false)");
            boolean obstacle = Boolean.parseBoolean(myScanner.nextLine());

            if (obstacle) {
                double obstacleArea;

                System.out.println("How many obstacles are there?");
                int numOfObstacles = Integer.parseInt(myScanner.nextLine());

                for (int j = 0; j < numOfObstacles; j++) {

                    System.out.println("How tall is obstacle" + j + "? (to nearest cm)");
                    int obstacleHeight = Integer.parseInt(myScanner.nextLine());

                    System.out.println("How wide is obstacle" + j + "? (to nearest cm)");
                    int obstacleWidth = Integer.parseInt(myScanner.nextLine());

                    obstacleArea = (obstacleHeight * 100) * (obstacleWidth * 100);
                    totalObstacleArea += obstacleArea;
                }
            }
            totalPaintArea += (height * width) - totalObstacleArea;
        }
        //work out amount of paint needed
        float paintInLitres = (float) (totalPaintArea * PAINT_PER_SQ_M * numberOfCoats);
        float amountPaintNeeded = paintInLitres; // necessary for the final print out

        // Find out if user knows what kind of paint can they will use
        System.out.println("Do you know the paint can size you'll be using? (true or false)");
        boolean userPaintCanBool = Boolean.parseBoolean(myScanner.nextLine());

        if (userPaintCanBool) {
            System.out.println("How big is the paint can in litres? (0.5, 1 or 2)");
            String paintCanSize = myScanner.nextLine();
            double paintCanSizeNum = Double.parseDouble(paintCanSize);

            //Calculate number of paint cans needed
            int numPaintCans;
            switch (paintCanSize) {
                case "0.5":
                    numPaintCans = (int) Math.ceil(paintInLitres / paintCanSizeNum);
                    break;
                case "1":
                    numPaintCans = (int) Math.ceil(paintInLitres);
                    break;
                case "2":
                    if (paintInLitres < paintCanSizeNum) {
                        numPaintCans = 1;
                    } else {
                        numPaintCans = (int) Math.ceil(paintInLitres / paintCanSizeNum);
                    }
                    break;
                default:
                    System.out.println("Hm, I don't understand");
                    numPaintCans = 0;
            }
            System.out.println("The area to paint is " + totalPaintArea  + " square meters. The amount of " + wallColour + " paint needed is " + Math.round(amountPaintNeeded*100.0)/100.0 +
                    "L. Which is " + numPaintCans + " can(s) of " + paintCanSize + "L. paint. The amount of paint left over is " + Math.round(((paintCanSizeNum * numPaintCans) - amountPaintNeeded )*100.0)/100.0 + "L." );
        } else {

            // Work out number of paint cans needed
            int num2Ls = 0;
            int num1Ls = 0;
            int numHalfLs = 0;
            double totalPaintCanLitres = 0;

            while (paintInLitres > 0) {
                if (paintInLitres > 1.0) {
                    num2Ls += 1;
                    totalPaintCanLitres += 2;
                    paintInLitres -= 2.0;
                } else if (paintInLitres > 0.5) {
                    num1Ls += 1;
                    totalPaintCanLitres += 1;
                    paintInLitres -= 1.0;
                } else {
                    numHalfLs += 1;
                    totalPaintCanLitres += 0.5;
                    paintInLitres -= 0.5;
                }
            }

            double paintLeftOver = totalPaintCanLitres - amountPaintNeeded ;

            String returnMessage = "The area to paint is " + totalPaintArea  + " square meter(s). The amount of " + wallColour + " paint needed is " + Math.round(amountPaintNeeded*100.0)/100.0 + "L which is";
            if ( num2Ls > 0 ) {
                String L2message = " " + num2Ls + " can(s) of two litre paint";
                returnMessage = returnMessage.concat(L2message);
            }
            if ( num1Ls > 0 ) {
                String L1message = num1Ls + " can of 1 litre paint";
                if (numHalfLs == 0) {
                    String and = " and ";
                    L1message = and.concat(L1message);
                } else {
                    String comma = ", ";
                    L1message = comma.concat(L1message);
                }
                returnMessage = returnMessage.concat(L1message);
            }
            if ( numHalfLs > 0) {
                if (num2Ls != 0 && num1Ls != 0) {
                    returnMessage = " and ".concat(returnMessage);
                }
                returnMessage = returnMessage.concat(" " + numHalfLs + " can of half litre paint.");
            }
            // add paint left over
            returnMessage = returnMessage.concat(" The amount of paint left over is " + Math.round(paintLeftOver*100.0)/100.0 + ".");

            // return message
            System.out.println(returnMessage);
        }
    }
}
//Math.round(paintLeftOver*100.0)/100.0
//Math.round(amountPaintNeeded*100.0)/100.0