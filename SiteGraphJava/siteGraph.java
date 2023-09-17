import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class siteGraph {
    public static void main(String[] args) {
        String fileName = "sitelist.txt";
        File inputFile = new File(fileName);

        String[] siteNames = new String[100]; // assuming max 100 sites
        int[] xCoords = new int[100];
        int[] yCoords = new int[100];
        String[][] adjacentSites = new String[100][100]; // assuming max 100 sites with 100 adjacent sites
        int[][] distances = new int[100][100]; // distances[i][j] is the distance from site i to site j

        try {
            Scanner scanner = new Scanner(inputFile);
            int i = 0; // index for siteNames, xCoords, yCoords arrays
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");
                siteNames[i] = tokens[0];
                xCoords[i] = Integer.parseInt(tokens[1]);
                yCoords[i] = Integer.parseInt(tokens[2]);

                int j = 0; // index for adjacentSites, distances arrays
                for (int k = 3; k < tokens.length; k += 2) {
                    adjacentSites[i][j] = tokens[k];
                    distances[i][j] = Integer.parseInt(tokens[k+1]);
                    j++;
                }

                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return;
        }

        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Welcome to the Elden Ring tourist information program.");
            System.out.println("Please use the following funcions by entering their associated number.");
            System.out.println("------------Menu------------");
            System.out.println("1. Search for a site");
            System.out.println("2. Display sites connected to given site");
            System.out.println("3. Add connection between sites");
            System.out.println("4. Display closest site to given site");
            System.out.println("5. Exit");
            
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    searchSite(input, siteNames, xCoords, yCoords);
                    break;
                case 2:
                    showConnections(input, siteNames, adjacentSites, distances);
                    break;
                case 3:
                    Scanner scanner = new Scanner(System.in);
                    addConnection(scanner, siteNames, adjacentSites, distances);
                    break;
                case 4:
                    findClosestSite(input, siteNames, xCoords, yCoords, adjacentSites, distances);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again");
            }
            System.out.println();
        } while (choice != 5);
    }

    public static int findSiteIndex(String[] siteNames, String name) {
        for (int i = 0; i < siteNames.length; i++) {
            if (siteNames[i] == null) {
                break;
            }
            if (siteNames[i].equals(name)) {
                return i;
            }
        }
        return -1; // site not found
    }

    public static void searchSite(Scanner input, String[] siteNames, int[] xCoords, int[] yCoords) {
        System.out.print("Enter the name of the site to search for: ");
        String siteName = input.nextLine();

        boolean foundSite = false;
        for (int i = 0; i < siteNames.length; i++) {
            if (siteName.equals(siteNames[i])) {
                System.out.println("Site found!");
                System.out.println("Site name: "+ siteNames[i] +", " + " x-Coordinate: " + xCoords[i] +", " + " y-Coordinate: " + yCoords[i]);
                foundSite = true;
                break;
            } 
        }

        if (!foundSite) {
            System.out.println("Site not found: " + siteName);
        }
            System.out.print("Press Enter to return to the menu...");
            input.nextLine(); // wait for the user to press Enter
    }

    public static void showConnections(Scanner input, String[] siteNames, String[][] adjacentSites, int[][] distances) {
        System.out.print("Enter the name of the site: ");
        String siteName = input.nextLine();
    
        int siteIndex = -1;
        for (int i = 0; i < siteNames.length; i++) {
            if (siteNames[i] == null) {
                break;
            }
            if (siteNames[i].equals(siteName)) {
                siteIndex = i;
                break;
            }
        }
    
        if (siteIndex == -1) {
            System.out.println("Site not found");
            return;
        }
    
        System.out.println("Connections for site " + siteName + ":");
        for (int i = 0; i < adjacentSites[siteIndex].length; i++) {
            if (adjacentSites[siteIndex][i] == null) {
                break;
            }
            String connectedSiteName = adjacentSites[siteIndex][i];
            int distance = distances[siteIndex][i];
            System.out.println("- " + connectedSiteName + " (distance " + distance + ")");
        }
        System.out.print("Press Enter to return to the menu...");
        input.nextLine(); // wait for the user to press Enter
    } 

    public static void addConnection(Scanner input, String[] siteNames, String[][] adjacentSites, int[][] distances) {
        System.out.print("Enter the name of the first site: ");
        String site1Name = input.nextLine();
        System.out.print("Enter the name of the second site: ");
        String site2Name = input.nextLine();
        System.out.print("Enter the distance between the sites: ");
        int distance = input.nextInt();
        input.nextLine(); // consume the newline character
    
        int site1Index = -1;
        int site2Index = -1;
        for (int i = 0; i < siteNames.length; i++) {
            if (siteNames[i] == null) {
                break;
            }
            if (siteNames[i].equals(site1Name)) {
                site1Index = i;
            }
            if (siteNames[i].equals(site2Name)) {
                site2Index = i;
            }
        }
    
        if (site1Index == -1 || site2Index == -1) {
            System.out.println("Site not found");
            return;
        }
    
        boolean connectionAdded = false;
        for (int i = 0; i < adjacentSites[site1Index].length; i++) {
            if (adjacentSites[site1Index][i] == null) {
                adjacentSites[site1Index][i] = site2Name;
                distances[site1Index][i] = distance;
                connectionAdded = true;
                System.out.println("Connection added successfully!");
                break;
            }
            if (adjacentSites[site1Index][i].equals(site2Name)) {
                System.out.println("Connection already exists");
                return;
            }
        }
    
        if (!connectionAdded) {
            System.out.println("Maximum number of adjacent sites reached for site " + site1Name);
        }
        System.out.print("Press Enter to return to the menu...");
        input.nextLine(); // wait for the user to press Enter
    }
    public static void findClosestSite(Scanner input, String[] siteNames, int[] xCoords, int[] yCoords, String[][] adjacentSites, int[][] distances) {
        System.out.print("Enter site name: ");
        String name = input.nextLine();
        int index = findSiteIndex(siteNames, name);
        if (index == -1) {
            System.out.println("Site not found");
            return;
        }
    
        int closestIndex = -1;
        int closestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < siteNames.length; i++) {
            if (siteNames[i] == null) {
                break;
            }
            if (i == index) {
                continue; // skip the current site
            }
            for (int j = 0; j < adjacentSites[i].length; j++) {
                if (adjacentSites[i][j] == null) {
                    break;
                }
                if (adjacentSites[i][j].equals(name)) {
                    int distance = distances[i][j];
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestIndex = i;
                    }
                }
            }
        }
    
        if (closestIndex == -1) {
            System.out.println("No closest site found");
        } else {
            System.out.println("Closest site to " + name + " is " + siteNames[closestIndex] + " with distance " + closestDistance);
        }
    
        System.out.print("Press Enter to return to the menu...");
        input.nextLine(); // wait for the user to press Enter
    }
}
