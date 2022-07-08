import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        // Constant - amount of paint per square metre
        double PAINT_PER_SQ_M = 0.0833;
        double PI = Math.PI;

        // get number of walls to paint
        System.out.println("How many walls do you want to paint?");
        byte numberOfWalls = Byte.parseByte(myScanner.nextLine());

        // get amount of coats wanted
        System.out.println("How many coats would you like?");
        byte numberOfCoats = Byte.parseByte(myScanner.nextLine());



        // find out the colour the user wants, take input and check if it is valid colour
        String[] colours = {"blue", "green", "orange", "red", "yellow", "purple"};
        String colourMessage = "What colour would you like? ";

        for (byte i = 0; i < colours.length; i++) {
            if (i == colours.length - 1) {
                colourMessage = colourMessage.concat(" or ");
            } else if (i != 0) {
                colourMessage = colourMessage.concat(", ");
            }
            colourMessage = colourMessage.concat(colours[i]);
        }

        boolean validColour = false;
        String wallColour = "";
        while(!validColour) {
            System.out.println(colourMessage);
            wallColour = myScanner.nextLine();
            for(int i = 0; i < colours.length; i++) {
                if (colours[i].contains(wallColour)) {
                    validColour = true;
                    break;
                } else if (i == colours.length - 1) {
                    System.out.println("Hm, don't know that colour. Try again");
                }
            }
        }




        //get the width and any obstacles on each wall. Then return the total paint area minus obstacles.
        byte totalPaintArea = 0;
        for (byte i = 1; i <= numberOfWalls; i++) {
            double totalObstacleArea = 0;

            System.out.println("How tall is wall " + i + "? (in metres and centimetres i.e 1.5, 2.34)");
            double height = Double.parseDouble(myScanner.nextLine());

            System.out.println("How wide is wall " + i + "(in metres and centimetres i.e 1.5, 2.34)");
            double width = Double.parseDouble(myScanner.nextLine());

            double wallArea = height * width;

            System.out.println("Is there something on wall " + i + "? (t/f)");
            boolean obstacle = Boolean.parseBoolean(myScanner.nextLine());
            // if there is an obstacle removal
            if (obstacle) {

                System.out.println("How many obstacles are there?");
                byte numOfObstacles = Byte.parseByte(myScanner.nextLine());

                for (byte j = 0; j < numOfObstacles; j++) {
                    System.out.println("What shape is obstacle " + j + "? (circle, square, rectangle, triangle)");
                    String obstacleShape = myScanner.nextLine();

                    double obstacleArea = 0;
                    switch (obstacleShape) {
                        case "square":
                            System.out.println("How wide is obstacle" + j + "(in metres and centimetres i.e 1.5, 0.34)");
                            double obstacleHeightSq = Double.parseDouble(myScanner.nextLine());

                            obstacleArea = Math.pow((obstacleHeightSq), 2) ;
                            break;
                        case "rectangle":
                            System.out.println("How tall is obstacle" + j + "? (in metres and centimetres i.e 1.5, 0.34)");
                            double obstacleHeightRec = Double.parseDouble(myScanner.nextLine());

                            System.out.println("How wide is obstacle" + j + "? \"(in metres and centimetres i.e 1.5, 0.34)");
                            double obstacleWidthRec = Double.parseDouble(myScanner.nextLine());

                            obstacleArea = (obstacleHeightRec * obstacleWidthRec);
                            break;
                        case "triangle":
                            System.out.println("How tall is obstacle" + j + "(in metres and centimetres i.e 1.5, 0.34)");
                            double obstacleHeightTri = Double.parseDouble(myScanner.nextLine());

                            System.out.println("How wide is the base of obstacle" + j + "(in metres and centimetres i.e 1.5, 0.34)");
                            double obstacleWidthTri = Double.parseDouble(myScanner.nextLine());

                            obstacleArea = (obstacleHeightTri * obstacleWidthTri / 2);
                            break;
                        case "circle":
                            System.out.println("How wide is obstacle" + j + "(in metres and centimetres i.e 1.5, 0.34)");
                            double obstacleWidthCir = Double.parseDouble(myScanner.nextLine());
                            double radius = obstacleWidthCir/ 2;

                            obstacleArea = (Math.pow(radius, 2)  * PI);
                            break;
                        default:
                            System.out.println("Hmm, I don't understand");
                            break;
                    }
                    totalObstacleArea += obstacleArea;
                }
            }
            totalPaintArea += wallArea - totalObstacleArea;
        }


        //work out amount of paint needed
        double paintInLitres = (totalPaintArea * PAINT_PER_SQ_M * numberOfCoats);
        double amountPaintNeeded = paintInLitres; // necessary for the final print out


        //price list
        byte L2price = 20;
        byte L1price = 12;
        byte LHalfPrice = 7;

        // Find out if user knows what kind of paint  they will use
        System.out.println("Do you know the paint can size you'll be using? (t/f)");
        boolean userPaintCanBool = Boolean.parseBoolean(myScanner.nextLine());

        if (userPaintCanBool) {
            System.out.println("How big is the paint can in litres? (0.5, 1 or 2)");
            String paintCanSize = myScanner.nextLine();
            double paintCanSizeNum = Double.parseDouble(paintCanSize);

            // total price
            int totalCostUserChoice = 0;

            //Calculate number of paint cans needed
            int numPaintCans;
            switch (paintCanSize) {
                case "0.5":
                    numPaintCans = (int) Math.ceil(paintInLitres / paintCanSizeNum);
                    totalCostUserChoice = numPaintCans * LHalfPrice;
                    break;
                case "1":
                    numPaintCans = (int) Math.ceil(paintInLitres);
                    totalCostUserChoice = numPaintCans * L1price;
                    break;
                case "2":
                    if (paintInLitres < paintCanSizeNum) {
                        numPaintCans = 1;
                        totalCostUserChoice = numPaintCans * L2price;
                    } else {
                        numPaintCans = (int) Math.ceil(paintInLitres / paintCanSizeNum);
                        totalCostUserChoice = numPaintCans * L2price;
                    }
                    break;
                default:
                    System.out.println("Hm, I don't understand");
                    numPaintCans = 0;
            }

            System.out.println("The area to paint is " + totalPaintArea  + " square meters. The amount of " + wallColour + " paint needed is " + Math.round(amountPaintNeeded*100.0)/100.0 +
                    "L. Which is " + numPaintCans + " can(s) of " + paintCanSize + "L. paint. The amount of paint left over is " + Math.round(((paintCanSizeNum * numPaintCans) - amountPaintNeeded )*100.0)/100.0 + "L." +
                    "The cost will be " + totalCostUserChoice  + " pounds.");
        } else {

            int totalCostCompChoice = 0;

            // Work out number of paint cans needed
            byte num2Ls = 0;
            byte num1Ls = 0;
            byte numHalfLs = 0;
            double totalPaintCanLitres = 0;

            while (paintInLitres > 0) {
                if (paintInLitres > 1.0) {
                    num2Ls++;
                    totalPaintCanLitres += 2;
                    paintInLitres -= 2.0;
                    totalCostCompChoice+= L2price;
                } else if (paintInLitres > 0.5) {
                    num1Ls++;
                    totalPaintCanLitres += 1;
                    paintInLitres -= 1.0;
                    totalCostCompChoice += L1price;
                } else {
                    numHalfLs++;
                    totalPaintCanLitres += 0.5;
                    paintInLitres -= 0.5;
                    totalCostCompChoice += LHalfPrice;
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
                if (numHalfLs != 0) {
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

            // add cost
            returnMessage = returnMessage.concat(" The cost is " + totalCostCompChoice + " pounds.");

            // return message
            System.out.println(returnMessage);
        }
    }
}
